package com.henry.hibernate_attacks;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.henry.model.Customer;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class LineCommentsAttacksHibernate {

    private static  ObjectMapper mapper = new ObjectMapper();
    public static void main(String[] args) throws JsonProcessingException {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("persistence");
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        System.out.println("Starting Transaction");
        entityManager.getTransaction().begin();

       //insecure
       var  name = "Henry' -- '";
       var id = 1;
       String sql = "SELECT e FROM customers e where name='" + name + "'AND id="+id;
       var list = entityManager
        .createQuery(sql, Customer.class)
        .getResultList();

        System.out.println("json ******** "+mapper.writeValueAsString(list));

        name = "'Henry' -- ";
        var lastname = "'test1'";
        sql = "SELECT  e from customers e where name =:name AND lastname =:lastname";
        sql = sql.replace(":name", name).replace(":lastname",lastname);
        System.out.println(sql);
        list = entityManager
                .createQuery(sql, Customer.class)
                .getResultList();

        System.out.println("json **** "+mapper.writeValueAsString(list));

        //secure

         sql = "SELECT e FROM customers e where name=:name AND id=:id";
         list = entityManager
                .createQuery(sql, Customer.class)
                 .setParameter("name",name)
                 .setParameter("id", id)
                .getResultList();

        System.out.println("Secure **** "+mapper.writeValueAsString(list));

        // close the entity manager
        entityManager.close();
        entityManagerFactory.close();

    }
}
