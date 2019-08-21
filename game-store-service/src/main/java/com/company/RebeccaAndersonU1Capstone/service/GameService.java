package com.company.RebeccaAndersonU1Capstone.service;

import com.company.RebeccaAndersonU1Capstone.dao.GameDao;
import com.company.RebeccaAndersonU1Capstone.dto.Game;
import com.company.RebeccaAndersonU1Capstone.exception.NotFoundException;
import com.company.RebeccaAndersonU1Capstone.viewModel.GameViewModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class GameService {

    private GameDao gameDao;

    @Autowired
    public GameService(GameDao gameDao) {
        this.gameDao = gameDao;
    }

    public GameViewModel saveGame(GameViewModel gvm) {
        Game game = buildGame(gvm);
        game = gameDao.addGame(game);
        gvm.setId(game.getId());
        return gvm;
    }

    public GameViewModel updateGame(GameViewModel gvm) {
        Game game = buildGame(gvm);
        if (!checkIfExists(game.getId())) {
            throw new NotFoundException("No game of id " + game.getId() + " exists to update");
        }
        gameDao.updateGame(game);
        return gvm;
    }

    public GameViewModel getGameById(int id){
        Game game = gameDao.getGame(id);
        if (game == null) {
            return null;
        }
        GameViewModel gvm = buildGVM(game);
        return gvm;
    }

    public List<GameViewModel> getAllGames() {
        List<Game> games = gameDao.getAllGames();
        List<GameViewModel> gvms = new ArrayList<>();

        for (Game game : games) {
            GameViewModel gvm = buildGVM(game);
            gvms.add(gvm);
        }

        return gvms;
    }

    public List<GameViewModel> getGameByStudio(String studio) {
        List<Game> games = gameDao.getGamesByStudio(studio);
        List<GameViewModel> gvms = new ArrayList<>();

        for (Game game : games) {
            GameViewModel gvm = buildGVM(game);
            gvms.add(gvm);
        }
        return gvms;
    }

    public List<GameViewModel> getGameByRating(String esrb) {
        List<Game> games = gameDao.getGamesByESRB(esrb);
        List<GameViewModel> gvms = new ArrayList<>();

        for (Game game : games) {
            GameViewModel gvm = buildGVM(game);
            gvms.add(gvm);
        }
        return gvms;
    }

    public List<GameViewModel> getGameByTitle(String title) {
        List<Game> games = gameDao.getGamesByTitle(title);
        List<GameViewModel> gvms = new ArrayList<>();

        for (Game game : games) {
            GameViewModel gvm = buildGVM(game);
            gvms.add(gvm);
        }
        return gvms;
    }

    public void removeGame(int id) {
        if (!checkIfExists(id)) {
            throw new NotFoundException("No game of id " + id + " exists to delete");
        }
        gameDao.deleteGame(id);

    }

    // Helpers
    private GameViewModel buildGVM(Game game) {
        GameViewModel gvm = new GameViewModel();
        gvm.setId(game.getId());
        gvm.setDescription(game.getDescription());
        gvm.setEsrbRating(game.getEsrbRating());
        gvm.setPrice(game.getPrice());
        gvm.setQuantity(game.getQuantity());
        gvm.setStudio(game.getStudio());
        gvm.setTitle(game.getTitle());
        return gvm;
    }

    private Game buildGame(GameViewModel gvm) {
        Game game = new Game();
        game.setId(gvm.getId());
        game.setDescription(gvm.getDescription());
        game.setEsrbRating(gvm.getEsrbRating());
        game.setPrice(gvm.getPrice());
        game.setQuantity(gvm.getQuantity());
        game.setStudio(gvm.getStudio());
        game.setTitle(gvm.getTitle());
        return game;
    }

    private boolean checkIfExists(int id) {
        if (gameDao.getGame(id) != null) {
            return true;
        } else {
            return false;
        }
    }
}
