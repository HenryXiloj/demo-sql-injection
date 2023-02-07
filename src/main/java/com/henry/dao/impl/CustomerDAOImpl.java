package com.henry.dao.impl;

import com.henry.dao.CustomerDAO;
import com.henry.model.Customer;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CustomerDAOImpl implements CustomerDAO {

    private final JdbcTemplate jdbcTemplate;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public CustomerDAOImpl(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public List<Customer> findByNameAndId(String name, int id) {
        String sql = "SELECT  * from customers where name ='"+name+"' AND id="+id;
        var list = jdbcTemplate.query(sql,   (rs, rowNum) ->
                Customer.builder()
                        .id(rs.getInt("id"))
                        .name(rs.getString("name"))
                        .build()
        );

        return list;
    }

    @Override
    public List<Customer> findByNameAndIdSecure(String name, int id) {
        String sql = "SELECT  * from customers where name =? AND id=?";
        var list = jdbcTemplate.query(sql,   (rs, rowNum) ->
                Customer.builder()
                        .id(rs.getInt("id"))
                        .name(rs.getString("name"))
                        .build()
        ,new Object[]{name, id});

        return list;
    }

    @Override
    public List<Customer> findByNameAndIdNamedParameterJdbcTemplate(String name, int id) {
        String sql = "SELECT  * from customers where name =:name AND id=:id";

        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("name", name);
        mapSqlParameterSource.addValue("id", id);


        var list = namedParameterJdbcTemplate.query(sql, mapSqlParameterSource,  (rs, rowNum) ->
                        Customer.builder()
                                .id(rs.getInt("id"))
                                .name(rs.getString("name"))
                                .build()
                );

        return list;
    }

    @Override
    public List<Customer> findByNameAndIdWithReplace(String name, int id) {
        String sql = "SELECT  * from customers where name =:name AND id=:id";
        sql = sql.replace(":name", name).replace(":id",String.valueOf(id));
        var list = jdbcTemplate.query(sql,   (rs, rowNum) ->
                Customer.builder()
                        .id(rs.getInt("id"))
                        .name(rs.getString("name"))
                        .build()
        );

        return list;
    }

    @Override
    public List<Customer> bypassingLoginAttacks(String name, String password) {
        String sql = "SELECT  * from customers where name ='"+name+"' AND password='"+password+"'";
        var list = jdbcTemplate.query(sql,   (rs, rowNum) ->
                Customer.builder()
                        .id(rs.getInt("id"))
                        .name(rs.getString("name"))
                        .build()
        );

        return list;
    }

    @Override
    public List<Customer> bypassingLoginAttacksNamedParameterJdbcTemplate(String name, String password) {
        String sql = "SELECT  * from customers where name =:name AND password=:password";
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("name", name);
        mapSqlParameterSource.addValue("password", password);

        var list = namedParameterJdbcTemplate.query(sql, mapSqlParameterSource,  (rs, rowNum) ->
                Customer.builder()
                        .id(rs.getInt("id"))
                        .name(rs.getString("name"))
                        .build()
        );

        return list;
    }

    @Override
    public List<Customer> booleanAttacks(String name) {
        String sql = "SELECT  * from customers where name ='"+name+"'";
        var list = jdbcTemplate.query(sql,   (rs, rowNum) ->
                Customer.builder()
                        .id(rs.getInt("id"))
                        .name(rs.getString("name"))
                        .build()
        );

        return list;
    }

    @Override
    public List<Customer> booleanAttacksNamedParameterJdbcTemplate(String name) {
        String sql = "SELECT  * from customers where name =:name";

        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("name", name);


        var list = namedParameterJdbcTemplate.query(sql, mapSqlParameterSource,  (rs, rowNum) ->
                Customer.builder()
                        .id(rs.getInt("id"))
                        .name(rs.getString("name"))
                        .build()
        );

        return list;


    }

}
