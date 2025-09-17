package com.example.order_service.controller;


import com.example.order_service.model.Order;
import com.example.order_service.model.OrderItem;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @GetMapping("/by-user/{userId}")
    public List<Order> getOrdersByUser(@PathVariable Long userId) {
        return getMockOrders(userId);
    }

    private List<Order> getMockOrders(Long userId) {
        switch (userId.intValue()) {
            case 1:
                OrderItem item1 = new OrderItem("Онлайн-курс: Java-Разработчик", 1, 115000.0);
                OrderItem item2 = new OrderItem("Ноутбук Huawei", 1, 45000.0);
                Order order1 = new Order(101L, 1L, item1.getPrice()+item2.getPrice(), "RUB", Arrays.asList(item1, item2));

                OrderItem item3 = new OrderItem("Моноблок", 1, 105000.0);
                Order order2 = new Order(102L, 1L, item3.getPrice(), "RUB", Arrays.asList(item3));

                return Arrays.asList(order1, order2);

            case 2:
                OrderItem item4 = new OrderItem("Смартфон Samsung", 1, 45000.0);
                OrderItem item5 = new OrderItem("Чехол для телефона", 1, 1500.0);
                Order order3 = new Order(201L, 2L, 46500.0, "RUB", Arrays.asList(item4, item5));

                return Arrays.asList(order3);

            default:
                return Arrays.asList();
        }
    }
}