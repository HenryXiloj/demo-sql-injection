package com.henry.spring_jdbc_attacks;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.henry.config.AppConfig;
import com.henry.dao.CustomerDAO;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class LineCommentsAttacksJDBCTemplate {

    private static ObjectMapper mapper = new ObjectMapper();
    public static void main(String[] args) throws JsonProcessingException {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);

        CustomerDAO customerDAO = context.getBean(CustomerDAO.class);

        //Insecure
        var  name = "Henry' -- '";
        var id = 1;
        System.out.println("list "+mapper.writeValueAsString(customerDAO.findByNameAndId(name, id)) );

        name = "'Henry' -- ";
        System.out.println("list "+mapper.writeValueAsString(customerDAO.findByNameAndIdWithReplace(name, id)) );

        //secure
        System.out.println("secure*** "+mapper.writeValueAsString(customerDAO.findByNameAndIdSecure(name, id)) );

        System.out.println("secure 1*** "+mapper.writeValueAsString(customerDAO.findByNameAndIdNamedParameterJdbcTemplate(name, id)) );
    }
}
