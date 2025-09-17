package com.example.bff_service.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;


@RestController
public class BffController {

    private final WebClient webClient;

    public BffController(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.build();
    }

    @GetMapping(value = "/user/{userId}", produces = MediaType.TEXT_PLAIN_VALUE)
    public Mono<String> getUserProfile(@PathVariable Long userId) {
        System.out.println("BFF: Received request for user ID: " + userId);

        // Получаем данные пользователя
        Mono<com.example.bff_service.model.User> userMono = webClient.get()
                .uri("http://localhost:8881/api/users/{userId}", userId)
                .retrieve()
                .bodyToMono(com.example.bff_service.model.User.class);

        // Получаем заказы пользователя
        Mono<java.util.List<com.example.bff_service.model.Order>> ordersMono = webClient.get()
                .uri("http://localhost:8882/api/orders/by-user/{userId}", userId)
                .retrieve()
                .bodyToFlux(com.example.bff_service.model.Order.class)
                .collectList();

        // Объединяем и форматируем результат
        return Mono.zip(userMono, ordersMono)
                .map(tuple -> {
                    com.example.bff_service.model.User user = tuple.getT1();
                    java.util.List<com.example.bff_service.model.Order> orders = tuple.getT2();

                    return formatUserProfile(user, orders);
                });
    }

    private String formatUserProfile(com.example.bff_service.model.User user,
                                     java.util.List<com.example.bff_service.model.Order> orders) {
        StringBuilder sb = new StringBuilder();

        sb.append("══════════════════════════════════════════════════════════════\n\n");
        sb.append("                    ПРОФИЛЬ ПОЛЬЗОВАТЕЛЯ                    \n");
        sb.append("══════════════════════════════════════════════════════════════\n\n");

        sb.append(" ДАННЫЕ ПОЛЬЗОВАТЕЛЯ:\n");
        sb.append("──────────────────────────────────────────────────────────────\n");
        sb.append(String.format("ID:           %d\n", user.getId()));
        sb.append(String.format("ФИО:          %s\n", user.getFullName()));
        sb.append(String.format("Адрес:        %s\n", user.getDeliveryAddress()));
        sb.append(String.format("Телефон:      %s\n", user.getPhoneNumber()));
        sb.append(String.format("Email:        %s\n", user.getEmail()));
        sb.append("\n");

        sb.append(" ИСТОРИЯ ЗАКАЗОВ:\n");
        sb.append("──────────────────────────────────────────────────────────────\n");

        if (orders.isEmpty()) {
            sb.append("Заказы не найдены\n");
        } else {
            sb.append(String.format("Всего заказов: %d\n\n", orders.size()));

            for (int i = 0; i < orders.size(); i++) {
                com.example.bff_service.model.Order order = orders.get(i);

                sb.append(String.format("ЗАКАЗ #%d (ID: %d)\n", i + 1, order.getId()));
                sb.append(String.format("Общая сумма: %.2f %s\n", order.getTotalAmount(), order.getCurrency()));
                sb.append("Товары:\n");

                for (com.example.bff_service.model.OrderItem item : order.getItems()) {
                    sb.append(String.format("  • %s x%d - %.2f %s\n",
                            item.getProductName(),
                            item.getQuantity(),
                            item.getPrice(),
                            order.getCurrency()));
                }

                double itemsTotal = order.getItems().stream()
                        .mapToDouble(item -> item.getPrice() * item.getQuantity())
                        .sum();

                sb.append(String.format("  Итого по заказу: %.2f %s\n", itemsTotal, order.getCurrency()));
                sb.append("\n");
            }

            double totalSpent = orders.stream()
                    .mapToDouble(com.example.bff_service.model.Order::getTotalAmount)
                    .sum();

            sb.append(String.format("ОБЩАЯ СУММА ПО ВСЕМ ЗАКАЗАМ: %.2f %s\n", totalSpent, orders.get(0).getCurrency()));
        }

        sb.append("\n══════════════════════════════════════════════════════════════\n");
        sb.append("               КОНЕЦ ДАННЫХ НА - " + java.time.LocalDateTime.now()
                .format(java.time.format.DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm")) + "               \n");
        sb.append("══════════════════════════════════════════════════════════════\n");

        return sb.toString();
    }
}