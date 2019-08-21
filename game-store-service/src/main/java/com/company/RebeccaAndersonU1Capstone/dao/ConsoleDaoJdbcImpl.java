package com.company.RebeccaAndersonU1Capstone.dao;

import com.company.RebeccaAndersonU1Capstone.dto.Console;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class ConsoleDaoJdbcImpl implements ConsoleDao {
    private JdbcTemplate jdbc;

    @Autowired
    public ConsoleDaoJdbcImpl(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    // Prepared Statements
    private static final String ADD_CONSOLE_SQL =
            "insert into console (model, manufacturer, memory_amount, processor, price, quantity) " +
                    "values (?, ?, ?,?, ?, ?)";
    private static final String GET_ONE_CONSOLE_SQL =
            "select * from console where game_id = ?";
    private static final String UPDATE_CONSOLE_SQL =
            "update console set model = ?, manufacturer = ?, memory_amount = ?, processor = ?, " +
                    "price = ?, quantity = ? where game_id = ?";
    private static final String GET_ALL_CONSOLE_SQL =
            "select * from console";
    private static final String GET_CONSOLE_BY_MANUFACTURER_SQL =
            "select * from console where manufacturer = ?";
    private static final String DELETE_CONSOLE_SQL =
            "delete from console where game_id = ?";

    // Row Mapper
    private Console mapRowToConsole(ResultSet rs, int rowNum) throws SQLException {
        Console console = new Console();
        console.setId(rs.getInt("game_id"));
        console.setModel(rs.getString("model"));
        console.setManufacturer(rs.getString("manufacturer"));
        console.setMemoryAmount(rs.getString("memory_amount"));
        console.setProcessor(rs.getString("processor"));
        console.setPrice(rs.getBigDecimal("price"));
        console.setQuantity(rs.getInt("quantity"));
        return console;
    }

    @Override
    @Transactional
    public Console addConsole(Console console) {
        jdbc.update(ADD_CONSOLE_SQL, console.getModel(), console.getManufacturer(), console.getMemoryAmount(),
                console.getProcessor(), console.getPrice(), console.getQuantity());

        int id = jdbc.queryForObject("select LAST_INSERT_ID()", Integer.class);
        console.setId(id);

        return console;
    }

    @Override
    public Console getConsole(int id) {
        try {
            return jdbc.queryForObject(GET_ONE_CONSOLE_SQL, this::mapRowToConsole, id);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public Console updateConsole(Console console) {
        jdbc.update(UPDATE_CONSOLE_SQL, console.getModel(), console.getManufacturer(), console.getMemoryAmount(),
                console.getProcessor(), console.getPrice(), console.getQuantity(), console.getId());
        return console;
    }

    @Override
    public List<Console> getAllConsole() {
        return jdbc.query(GET_ALL_CONSOLE_SQL, this::mapRowToConsole);
    }

    @Override
    public List<Console> getConsolesByManufacturer(String manufacturer) {
        return jdbc.query(GET_CONSOLE_BY_MANUFACTURER_SQL, this::mapRowToConsole, manufacturer);
    }

    @Override
    public void deleteConsole(int id) {
        jdbc.update(DELETE_CONSOLE_SQL, id);
    }
}
