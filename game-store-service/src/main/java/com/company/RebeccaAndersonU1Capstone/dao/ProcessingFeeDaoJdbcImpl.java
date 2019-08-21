package com.company.RebeccaAndersonU1Capstone.dao;

import com.company.RebeccaAndersonU1Capstone.dto.ProcessingFee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class ProcessingFeeDaoJdbcImpl implements ProcessingFeeDao {
    private JdbcTemplate jdbc;

    @Autowired
    public ProcessingFeeDaoJdbcImpl(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    // Prepared statement
    private static final String GET_FEE_SQL =
            "select * from processing_fee where product_type = ?";

    // Row Mapper
    private ProcessingFee mapRowToFee(ResultSet rs, int rowNum) throws SQLException {
        ProcessingFee fee = new ProcessingFee();
        fee.setFee(rs.getBigDecimal("fee"));
        fee.setItemType(rs.getString("product_type"));
        return fee;
    }

    @Override
    public ProcessingFee getFeeByItemType(String itemType) {
        try {
            return jdbc.queryForObject(GET_FEE_SQL, this::mapRowToFee, itemType);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }
}
