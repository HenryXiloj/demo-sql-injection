package com.henry.hibernate_attacks;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.henry.model.Customer;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class BypassingLoginAttacksHibernate {

    private static ObjectMapper mapper = new ObjectMapper();
    public static void main(String[] args) throws JsonProcessingException {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("persistence");
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        System.out.println("Starting Transaction");
        entityManager.getTransaction().begin();

        //insecure
        /** No works
         Henry' --
         Henry' #
         Henry'/*
         Henry' or 1=1 --
         Henry' or 1=1 #
         Henry' or 1=1 /*
        * */

        var name = "Henry' or '1'='1 --";
        var pass = "123";
        var  list = entityManager
                .createQuery("SELECT e FROM customers e where name='" + name + "' AND password='"+pass+"'", Customer.class)

                .getResultList();

        System.out.println("json **** "+mapper.writeValueAsString(list));


        //secure

          list = entityManager
                .createQuery("SELECT e FROM customers e where name=:name AND password=:password", Customer.class)
                  .setParameter("name", name)
                  .setParameter("password", pass)
                .getResultList();

        System.out.println("secure **** "+mapper.writeValueAsString(list));
        // close the entity manager
        entityManager.close();
        entityManagerFactory.close();

    }

}
