package com.zxc.ticketsys;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class TicketsysApplication {
    public static void main(String[] args) {
        SpringApplication.run(TicketsysApplication.class, args);
    }
}
