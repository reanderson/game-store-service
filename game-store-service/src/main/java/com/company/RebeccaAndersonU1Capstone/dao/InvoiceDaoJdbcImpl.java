package com.company.RebeccaAndersonU1Capstone.dao;

import com.company.RebeccaAndersonU1Capstone.dto.Invoice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class InvoiceDaoJdbcImpl implements InvoiceDao {
    private JdbcTemplate jdbc;

    @Autowired
    public InvoiceDaoJdbcImpl(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    // Prepared Statements
    private static final String ADD_INVOICE_SQL =
            "insert into invoice (name, street, city, state, zipcode, item_type, item_id, unit_price, quantity, " +
                    "subtotal, tax, processing_fee, total) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String GET_ONE_INVOICE_SQL =
            "select * from invoice where invoice_id = ?";
    private static final String GET_ALL_INVOICE_SQL =
            "select * from invoice";
    private static final String DELETE_INVOICE_SQL =
            "delete from invoice where invoice_id = ?";

    // Row Mapper
    private Invoice mapRowToInvoice(ResultSet rs, int rowNum) throws SQLException {
        Invoice invoice = new Invoice();
        invoice.setId(rs.getInt("invoice_id"));
        invoice.setName(rs.getString("name"));
        invoice.setStreet(rs.getString("street"));
        invoice.setCity(rs.getString("city"));
        invoice.setState(rs.getString("state"));
        invoice.setZipcode(rs.getString("zipcode"));
        invoice.setItemType(rs.getString("item_type"));
        invoice.setItemId(rs.getInt("item_id"));
        invoice.setUnitPrice(rs.getBigDecimal("unit_price"));
        invoice.setQuantity(rs.getInt("quantity"));
        invoice.setSubtotal(rs.getBigDecimal("subtotal"));
        invoice.setTax(rs.getBigDecimal("tax"));
        invoice.setProcessingFee(rs.getBigDecimal("processing_fee"));
        invoice.setTotal(rs.getBigDecimal("total"));
        return invoice;
    }

    @Override
    @Transactional
    public Invoice addInvoice(Invoice invoice) {
        jdbc.update(ADD_INVOICE_SQL, invoice.getName(), invoice.getStreet(), invoice.getCity(), invoice.getState(),
                invoice.getZipcode(), invoice.getItemType(), invoice.getItemId(), invoice.getUnitPrice(),
                invoice.getQuantity(), invoice.getSubtotal(), invoice.getTax(), invoice.getProcessingFee(),
                invoice.getTotal());
        int id = jdbc.queryForObject("select LAST_INSERT_ID()", Integer.class);
        invoice.setId(id);
        return invoice;
    }

    @Override
    public Invoice getInvoice(int id) {
        try {
            return jdbc.queryForObject(GET_ONE_INVOICE_SQL, this::mapRowToInvoice, id);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public List<Invoice> getAllInvoice() {
        return jdbc.query(GET_ALL_INVOICE_SQL, this::mapRowToInvoice);
    }

    @Override
    public void deleteInvoice(int id) {
        jdbc.update(DELETE_INVOICE_SQL, id);
    }
}
