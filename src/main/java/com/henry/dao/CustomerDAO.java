package com.henry.dao;

import com.henry.model.Customer;

import java.util.List;

public interface CustomerDAO {

    List<Customer> findByNameAndId(String name, int id);

    List<Customer> findByNameAndIdSecure(String name, int id);

    List<Customer> findByNameAndIdNamedParameterJdbcTemplate(String name, int id);
    List<Customer> findByNameAndIdWithReplace(String name, int id);

    List<Customer> bypassingLoginAttacks(String name, String password);

    List<Customer> bypassingLoginAttacksNamedParameterJdbcTemplate(String name, String password);

    List<Customer> booleanAttacks(String name);

    List<Customer> booleanAttacksNamedParameterJdbcTemplate(String name);
}