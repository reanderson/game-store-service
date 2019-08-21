package com.company.RebeccaAndersonU1Capstone.dao;

import com.company.RebeccaAndersonU1Capstone.dto.ProcessingFee;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class ProcessingFeeDaoJdbcImplTest {
    @Autowired
    ProcessingFeeDao feeDao;

    @Test
    public void getFeeByItemType() {
        // Could test all three item types, but just going to test one,
        // since it should be save to assume that our data is good in the database
        ProcessingFee fee = new ProcessingFee();
        fee.setFee(new BigDecimal("1.98").setScale(2));
        fee.setItemType("t_shirt");

        assertEquals(fee, feeDao.getFeeByItemType("t_shirt"));
    }
}