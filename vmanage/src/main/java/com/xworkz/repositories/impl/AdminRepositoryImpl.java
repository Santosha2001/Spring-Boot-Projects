package com.xworkz.repositories.impl;

import com.xworkz.entities.AdminEntity;
import com.xworkz.repositories.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.*;

@Repository
public class AdminRepositoryImpl implements AdminRepository {

    @Autowired
    private EntityManagerFactory entityManagerFactory;

    @Override
    public AdminEntity readByEmailId(String emailId) {
        AdminEntity entity = null;
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            Query query = entityManager.createNamedQuery("readByEmailId");
            query.setParameter("emailId", emailId);
            entity = (AdminEntity) query.getSingleResult();
        } catch (NoResultException e) {
            System.out.println("No entity found for email: " + emailId);
        } finally {
            if (entityManager != null && entityManager.isOpen()) {
                entityManager.close();
            }
        }
        return entity;
    }

    @Override
    public boolean updateOptById(int id, String otp) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        try {
            entityTransaction.begin();
            AdminEntity existingEntity = entityManager.find(AdminEntity.class, id);
            if (existingEntity != null) {
                existingEntity.setOtp(otp);
                entityManager.merge(existingEntity);
                entityTransaction.commit();
                System.out.println("OTP updated successfully");
                return true;
            } else {
                return false; // Entity with the given ID not found
            }
        } catch (PersistenceException e) {
            e.printStackTrace();
            if (entityTransaction.isActive()) {
                entityTransaction.rollback();
            }
            return false; // Error occurred during update
        } finally {
            entityManager.close();
        }
    }

}
