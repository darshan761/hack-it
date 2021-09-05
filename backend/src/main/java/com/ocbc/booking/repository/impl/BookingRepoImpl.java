//package com.ocbc.booking.repository.impl;
//
//import com.ocbc.booking.model.User;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.jdbc.core.JdbcTemplate;
//import org.springframework.stereotype.Repository;
//
//@Repository
//public class BookingRepoImpl {
//    @Autowired
//    JdbcTemplate jdbcTemplate;
//
//    public User getData(){
//        User demo = jdbcTemplate.queryForObject(
//                "SELECT * FROM user limit 1",
//                (rs, rowNum) -> new User(rs.getInt("id"), rs.getString("name"))
//        );
//        return demo;
//    }
//}
