package com.company.RebeccaAndersonU1Capstone.dao;

import com.company.RebeccaAndersonU1Capstone.dto.Tax;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class TaxDaoJdbcImpl  implements TaxDao{
    private JdbcTemplate jdbc;

    @Autowired
    public TaxDaoJdbcImpl(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    // prepared statement
    private static final String SELECT_TAX_SQL =
            "select * from sales_tax_rate where state = ?";

    // Row Mapper
    private Tax mapRowToTax(ResultSet rs, int rowNum) throws SQLException {
        Tax tax = new Tax();
        tax.setState(rs.getString("state"));
        tax.setRate(rs.getBigDecimal("rate"));
        return tax;
    }


    @Override
    public Tax getTaxByState(String state) {
        try {
            return jdbc.queryForObject(SELECT_TAX_SQL, this::mapRowToTax, state);
        } catch(EmptyResultDataAccessException e) {
            return null;
        }
    }


}
