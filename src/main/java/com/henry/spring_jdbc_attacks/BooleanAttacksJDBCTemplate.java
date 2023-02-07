package com.henry.spring_jdbc_attacks;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.henry.config.AppConfig;
import com.henry.dao.CustomerDAO;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class BooleanAttacksJDBCTemplate {


        private static ObjectMapper mapper = new ObjectMapper();
        public static void main(String[] args) throws JsonProcessingException {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);

        CustomerDAO customerDAO = context.getBean(CustomerDAO.class);

        //insecure
        var name = "abc' or '1'='1";
        System.out.println("list "+mapper.writeValueAsString(customerDAO.booleanAttacks(name)) );

        //secure
        System.out.println("secure *** "+mapper.writeValueAsString(customerDAO.booleanAttacksNamedParameterJdbcTemplate(name)) );
    }

}
