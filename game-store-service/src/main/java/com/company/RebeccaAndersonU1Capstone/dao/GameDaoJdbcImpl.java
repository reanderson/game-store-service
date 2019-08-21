package com.company.RebeccaAndersonU1Capstone.dao;

import com.company.RebeccaAndersonU1Capstone.dto.Game;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class GameDaoJdbcImpl implements GameDao {
    private JdbcTemplate jdbc;

    @Autowired
    public GameDaoJdbcImpl(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    // Prepared Statements
    // NOTE: Schema had a typo where esrb_rating is actually ersb_rating.
    // I considered modifying my schema, but decided that this was the schema I was given,
    // and so I should stick with that.
    private static final String ADD_GAME_SQL =
            "insert into game (title, ersb_rating, description, price, studio, quantity) values (?, ?, ?, ?, ?, ?)";
    private static final String GET_ONE_GAME_SQL =
            "select * from game where game_id = ?";
    private static final String UPDATE_GAME_SQL =
            "update game set title = ?, ersb_rating = ?, description = ?, price = ?, studio = ?, quantity = ? " +
                    "where game_id = ?";
    private static final String GET_ALL_GAME_SQL =
            "select * from game";
    private static final String GET_GAME_BY_ESRB =
            "select * from game where ersb_rating = ?";
    private static final String GET_GAME_BY_STUDIO =
            "select * from game where studio = ?";
    private static final String GET_GAME_BY_TITLE =
            "select * from game where title like ?";
    private static final String DELETE_GAME_SQL =
            "delete from game where game_id = ?";

    // Row Mapper
    private Game mapRowToGame(ResultSet rs, int rowNum) throws SQLException {
        Game game = new Game();
        game.setId(rs.getInt("game_id"));
        game.setTitle(rs.getString("title"));
        game.setEsrbRating(rs.getString("ersb_rating"));
        game.setDescription(rs.getString("description"));
        game.setPrice(rs.getBigDecimal("price"));
        game.setStudio(rs.getString("studio"));
        game.setQuantity(rs.getInt("quantity"));
        return game;
    }

    @Override
    @Transactional
    public Game addGame(Game game) {
        jdbc.update(ADD_GAME_SQL, game.getTitle(), game.getEsrbRating(), game.getDescription(),
                game.getPrice(), game.getStudio(), game.getQuantity());

        int id = jdbc.queryForObject("select LAST_INSERT_ID()", Integer.class);
        game.setId(id);

        return game;
    }

    @Override
    public Game getGame(int id) {
        try {
            return jdbc.queryForObject(GET_ONE_GAME_SQL, this::mapRowToGame, id);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public Game updateGame(Game game) {
        jdbc.update(UPDATE_GAME_SQL, game.getTitle(), game.getEsrbRating(), game.getDescription(),
                game.getPrice(), game.getStudio(), game.getQuantity(), game.getId());
        return game;
    }

    @Override
    public List<Game> getAllGames() {
        return jdbc.query(GET_ALL_GAME_SQL, this::mapRowToGame);
    }

    @Override
    public List<Game> getGamesByESRB(String esrb) {
        return jdbc.query(GET_GAME_BY_ESRB, this::mapRowToGame, esrb);
    }

    @Override
    public List<Game> getGamesByStudio(String studio) {
        return jdbc.query(GET_GAME_BY_STUDIO, this::mapRowToGame, studio);
    }

    @Override
    public List<Game> getGamesByTitle(String title) {
        // need to take title and add % to either side of it, to make sure the like statement searches
        // for games whose title include the searched for string
        title = "%" + title + "%";
        return jdbc.query(GET_GAME_BY_TITLE, this::mapRowToGame, title);
    }

    @Override
    public void deleteGame(int id) {
        jdbc.update(DELETE_GAME_SQL, id);
    }

}
