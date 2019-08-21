package com.company.RebeccaAndersonU1Capstone.dao;

import com.company.RebeccaAndersonU1Capstone.dto.Tax;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class TaxDaoTest {
    @Autowired
    TaxDao taxDao;

    @Test
    public void getTaxByState() {
        // It's a bit time consuming to check all 50 states; I have to assume the data in my database is good,
        // and just try getting something
        Tax tax = new Tax();
        tax.setRate(new BigDecimal("0.04").setScale(2));
        tax.setState("IA");

        Tax fromDb = taxDao.getTaxByState("IA");

        assertEquals(tax, fromDb);
    }
}