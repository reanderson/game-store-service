package com.company.RebeccaAndersonU1Capstone.dao;

import com.company.RebeccaAndersonU1Capstone.dto.TShirt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class TShirtDaoJdbcImpl implements TShirtDao {
    private JdbcTemplate jdbc;

    @Autowired
    public TShirtDaoJdbcImpl(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    // Prepared Statements
    private static final String GET_ALL_SHIRT_SQL =
            "select * from t_shirt";
    private static final String GET_SHIRT_BY_COLOR_SQL =
            "select * from t_shirt where color = ?";
    private static final String GET_SHIRT_BY_SIZE_SQL =
            "select * from t_shirt where size = ?";
    private static final String ADD_SHIRT_SQL =
            "insert into t_shirt (size, color, description, price, quantity) values (?, ?, ?, ?, ?)";
    private static final String GET_ONE_SHIRT_SQL =
            "select * from t_shirt where t_shirt_id = ?";
    private static final String UPDATE_SHIRT_SQL =
            "update t_shirt set size = ?, color = ?, description = ?, price = ?, quantity = ? where t_shirt_id = ?";
    private static final String DELETE_SHIRT_SQL =
            "delete from t_shirt where t_shirt_id = ?";

    // Row Mapper
    private TShirt mapRowToTShirt(ResultSet rs, int rowNum) throws SQLException {
        TShirt tshirt = new TShirt();
        tshirt.setId(rs.getInt("t_shirt_id"));
        tshirt.setSize(rs.getString("size"));
        tshirt.setColor(rs.getString("color"));
        tshirt.setDescription(rs.getString("description"));
        tshirt.setPrice(rs.getBigDecimal("price"));
        tshirt.setQuantity(rs.getInt("quantity"));
        return tshirt;
    }

    @Override
    public List<TShirt> getAllTShirts() {
        return jdbc.query(GET_ALL_SHIRT_SQL, this::mapRowToTShirt);
    }

    @Override
    public List<TShirt> getTShirtsByColor(String color) {
        return jdbc.query(GET_SHIRT_BY_COLOR_SQL, this::mapRowToTShirt, color);
    }

    @Override
    public List<TShirt> getTShirtsBySize(String size) {
        return jdbc.query(GET_SHIRT_BY_SIZE_SQL, this::mapRowToTShirt, size);
    }

    @Override
    @Transactional
    public TShirt addTShirt(TShirt tShirt) {
        jdbc.update(ADD_SHIRT_SQL, tShirt.getSize(), tShirt.getColor(), tShirt.getDescription(), tShirt.getPrice(),
                tShirt.getQuantity());

        int id = jdbc.queryForObject("select LAST_INSERT_ID()", Integer.class);
        tShirt.setId(id);

        return tShirt;
    }

    @Override
    public TShirt getTShirt(int id) {
        try {
            return jdbc.queryForObject(GET_ONE_SHIRT_SQL, this::mapRowToTShirt, id);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public TShirt updateTShirt(TShirt tShirt) {
        jdbc.update(UPDATE_SHIRT_SQL, tShirt.getSize(), tShirt.getColor(), tShirt.getDescription(), tShirt.getPrice(),
                tShirt.getQuantity(), tShirt.getId());
        return tShirt;
    }

    @Override
    public void deleteTShirt(int id) {
        jdbc.update(DELETE_SHIRT_SQL, id);
    }
}
