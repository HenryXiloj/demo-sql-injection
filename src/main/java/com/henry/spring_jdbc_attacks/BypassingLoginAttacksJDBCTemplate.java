package com.henry.spring_jdbc_attacks;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.henry.config.AppConfig;
import com.henry.dao.CustomerDAO;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class BypassingLoginAttacksJDBCTemplate {

    private static ObjectMapper mapper = new ObjectMapper();
    public static void main(String[] args) throws JsonProcessingException {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);

        CustomerDAO customerDAO = context.getBean(CustomerDAO.class);

        //Insecure
        /** No works
         Henry' /*
         Henry' or 1=1 /*
         * */

        // SQL injection Ok
        //var name = "Henry' -- ";
        //var name = "Henry' # ";
        //var name = "Henry' or 1=1 -- ";
        //var name = "Henry' or 1=1 # ";
        var name = "Henry' or '1'='1 --";
        var pass = "123";
        System.out.println("list "+mapper.writeValueAsString(customerDAO.bypassingLoginAttacks(name, pass)) );

        //Secure
        System.out.println("Secure*** "+mapper.writeValueAsString(customerDAO.bypassingLoginAttacksNamedParameterJdbcTemplate(name, pass)) );


    }
}
