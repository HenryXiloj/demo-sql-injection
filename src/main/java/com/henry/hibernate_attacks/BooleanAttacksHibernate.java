package com.henry.hibernate_attacks;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.henry.model.Customer;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class BooleanAttacksHibernate {

    private static ObjectMapper mapper = new ObjectMapper();
    public static void main(String[] args) throws JsonProcessingException {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("persistence");
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        System.out.println("Starting Transaction");
        entityManager.getTransaction().begin();

        var name = "abc' or '1'='1";
        var  list = entityManager
                .createQuery("SELECT e FROM customers e where name='" + name + "'", Customer.class)
                .getResultList();

        System.out.println("json **** "+mapper.writeValueAsString(list));

        list = entityManager
                .createQuery("SELECT e FROM customers e where name=:name", Customer.class)
                .setParameter("name", name)
                .getResultList();

        System.out.println("Secure **** "+mapper.writeValueAsString(list));
        // close the entity manager
        entityManager.close();
        entityManagerFactory.close();

    }

}
