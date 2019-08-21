package com.company.RebeccaAndersonU1Capstone.dao;

import com.company.RebeccaAndersonU1Capstone.dto.Game;

import java.util.List;

public interface GameDao {
    Game addGame(Game game);

    List<Game> getAllGames();

    Game getGame(int id);

    List<Game> getGamesByStudio(String studio);
    List<Game> getGamesByESRB(String esrb);
    List<Game> getGamesByTitle(String title);

    Game updateGame(Game game);

    void deleteGame(int id);
}
