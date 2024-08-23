package com.xworkz.repositories;

import com.xworkz.entities.OrderEntity;

import java.util.List;

public interface OrderRepository {

    boolean saveOrder(OrderEntity orderEntity);

    boolean updateOrderStatusAndAmount(int orderId, String OrderStatus, double orderAmount, String paymentStatus);

    boolean updateOrderStatusAndPaymentStatusByAdmin(int orderId, String OrderStatus, String paymentStatus);

    List<OrderEntity> getOrderDetailesByvendorID(int vendorID);

    OrderEntity getOrderEntityByOrderId(int orderId);

    List<OrderEntity> getOrderDetailsByAdmin();

    boolean updateOrderByOrderId(int orderId, OrderEntity orderEntity);
}
