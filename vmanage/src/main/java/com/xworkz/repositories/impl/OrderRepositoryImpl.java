package com.xworkz.repositories.impl;

import com.xworkz.entities.OrderEntity;
import com.xworkz.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.*;
import java.util.List;

@Repository
public class OrderRepositoryImpl implements OrderRepository {

    @Autowired
    private EntityManagerFactory entityManagerFactory;

    @Override
    public boolean saveOrder(OrderEntity orderEntity) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        try {
            entityTransaction.begin();
            entityManager.persist(orderEntity);
            entityTransaction.commit();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            if (entityTransaction.isActive()) {
                entityTransaction.rollback();
                return false;
            }

        } finally {
            entityManager.close();

        }
        return false;
    }

    @Override
    public boolean updateOrderStatusAndAmount(int orderId, String OrderStatus, double orderAmount,
                                              String paymentStatus) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        System.out.println("connection is ok");
        try {
            entityTransaction.begin();
            Query query = entityManager.createNamedQuery("updateOrderStatus");
            query.setParameter("orderAmount", orderAmount);
            query.setParameter("orderStatus", OrderStatus);
            query.setParameter("orderId", orderId);
            query.setParameter("paymentStatus", paymentStatus);
            int rowsUpdated = query.executeUpdate();
            entityTransaction.commit();
            if (rowsUpdated == 0) {
                return false;
            }
        } catch (PersistenceException e) {
            e.printStackTrace();
            if (entityTransaction.isActive()) {
                entityTransaction.rollback();
                return false;
            }
        } finally {
            entityManager.close();
        }
        return true;
    }

    @Override
    public boolean updateOrderStatusAndPaymentStatusByAdmin(int orderId, String OrderStatus, String paymentStatus) {

        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        System.out.println("connection is ok");
        try {
            entityTransaction.begin();
            Query query = entityManager.createNamedQuery("updateOrderStatusAndPaymentStatusByAdmin");
            query.setParameter("orderStatus", OrderStatus);
            query.setParameter("orderId", orderId);
            query.setParameter("paymentStatus", paymentStatus);
            int rowsUpdated = query.executeUpdate();
            entityTransaction.commit();
            if (rowsUpdated == 0) {
                return false;
            }
        } catch (PersistenceException e) {
            e.printStackTrace();
            if (entityTransaction.isActive()) {
                entityTransaction.rollback();
                return false;
            }
        } finally {
            entityManager.close();
        }
        return true;
    }

    @Override
    public boolean updateOrderByOrderId(int orderId, OrderEntity orderEntity) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        System.out.println("connection is ok");
        try {
            entityTransaction.begin();
            OrderEntity entity = entityManager.find(OrderEntity.class, orderId);
            entity.setOrderQuantity(orderEntity.getOrderQuantity());
            entity.setDeliveryAddress(orderEntity.getDeliveryAddress());
            entity.setDeliveryDate(orderEntity.getDeliveryDate());
            entity.setMessage(orderEntity.getMessage());
            entityManager.merge(entity);
            entityTransaction.commit();
            return true;
        } catch (PersistenceException e) {
            e.printStackTrace();
            if (entityTransaction.isActive()) {
                entityTransaction.rollback();
                return false;
            }
        } finally {
            entityManager.close();
        }
        return false;
    }

    @Override
    public List<OrderEntity> getOrderDetailesByvendorID(int vendorID) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        return entityManager.createNamedQuery("getOrderDetailsByVendorID", OrderEntity.class)
                .setParameter("vendorId", vendorID).getResultList();
    }

    @Override
    public OrderEntity getOrderEntityByOrderId(int orderId) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        return entityManager.createNamedQuery("getOrderEntityByOrderId", OrderEntity.class)
                .setParameter("orderId", orderId).getSingleResult();
    }

    @Override
    public List<OrderEntity> getOrderDetailsByAdmin() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        return entityManager.createNamedQuery("getOrderDetailsByAdmin", OrderEntity.class).getResultList();
    }

}
