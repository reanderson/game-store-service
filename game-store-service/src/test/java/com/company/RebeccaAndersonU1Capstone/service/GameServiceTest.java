package com.company.RebeccaAndersonU1Capstone.service;

import com.company.RebeccaAndersonU1Capstone.dao.GameDao;
import com.company.RebeccaAndersonU1Capstone.dao.GameDaoJdbcImpl;
import com.company.RebeccaAndersonU1Capstone.dto.Game;
import com.company.RebeccaAndersonU1Capstone.viewModel.GameViewModel;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

public class GameServiceTest {
    GameDao gameDao;
    GameService gameService;

    @Before
    public void setUp() throws Exception {
        setUpGameDaoMock();

        gameService = new GameService(gameDao);
    }

    @Test
    public void saveGetOneGame() {
        GameViewModel game = new GameViewModel();
        game.setTitle("Title");
        game.setEsrbRating("M");
        game.setDescription("Description");
        game.setPrice(new BigDecimal("12.59").setScale(2));
        game.setStudio("Studio");
        game.setQuantity(3);

        game = gameService.saveGame(game);

        GameViewModel fromService = gameService.getGameById(game.getId());

        assertEquals(game, fromService);
    }

    @Test
    public void updateGame() {
        GameViewModel updated = new GameViewModel();
        updated.setId(11);
        updated.setTitle("Title");
        updated.setEsrbRating("M");
        updated.setDescription("Description");
        updated.setPrice(new BigDecimal("12.59").setScale(2));
        updated.setStudio("Studio");
        updated.setQuantity(5);

        GameViewModel fromService = gameService.updateGame(updated);

        assertEquals(updated, fromService);
    }


    @Test
    public void getAllGames() {
        GameViewModel game = new GameViewModel();
        game.setId(11);
        game.setTitle("Title");
        game.setEsrbRating("M");
        game.setDescription("Description");
        game.setPrice(new BigDecimal("12.59").setScale(2));
        game.setStudio("Studio");
        game.setQuantity(3);

        List<GameViewModel> gList = new ArrayList<>();
        gList.add(game);

        List<GameViewModel> fromService = gameService.getAllGames();

        assertEquals(gList, fromService);
    }

    @Test
    public void getGameByStudio() {
        GameViewModel game = new GameViewModel();
        game.setId(11);
        game.setTitle("Title");
        game.setEsrbRating("M");
        game.setDescription("Description");
        game.setPrice(new BigDecimal("12.59").setScale(2));
        game.setStudio("Studio");
        game.setQuantity(3);

        List<GameViewModel> gList = new ArrayList<>();
        gList.add(game);

        List<GameViewModel> fromService = gameService.getGameByStudio("Studio");

        assertEquals(gList, fromService);
    }

    @Test
    public void getGameByRating() {
        GameViewModel game = new GameViewModel();
        game.setId(11);
        game.setTitle("Title");
        game.setEsrbRating("M");
        game.setDescription("Description");
        game.setPrice(new BigDecimal("12.59").setScale(2));
        game.setStudio("Studio");
        game.setQuantity(3);

        List<GameViewModel> gList = new ArrayList<>();
        gList.add(game);

        List<GameViewModel> fromService = gameService.getGameByRating("M");

        assertEquals(gList, fromService);
    }

    @Test
    public void getGameByTitle() {
        GameViewModel game = new GameViewModel();
        game.setId(11);
        game.setTitle("Title");
        game.setEsrbRating("M");
        game.setDescription("Description");
        game.setPrice(new BigDecimal("12.59").setScale(2));
        game.setStudio("Studio");
        game.setQuantity(3);

        List<GameViewModel> gList = new ArrayList<>();
        gList.add(game);

        List<GameViewModel> fromService = gameService.getGameByTitle("Title");

        assertEquals(gList, fromService);
    }


    // Mock
    private void setUpGameDaoMock() {
        gameDao = mock(GameDaoJdbcImpl.class);

        Game game = new Game();
        game.setId(11);
        game.setTitle("Title");
        game.setEsrbRating("M");
        game.setDescription("Description");
        game.setPrice(new BigDecimal("12.59").setScale(2));
        game.setStudio("Studio");
        game.setQuantity(3);

        Game noId = new Game();
        noId.setTitle("Title");
        noId.setEsrbRating("M");
        noId.setDescription("Description");
        noId.setPrice(new BigDecimal("12.59").setScale(2));
        noId.setStudio("Studio");
        noId.setQuantity(3);

        Game updated = new Game();
        updated.setId(11);
        updated.setTitle("Title");
        updated.setEsrbRating("M");
        updated.setDescription("Description");
        updated.setPrice(new BigDecimal("12.59").setScale(2));
        updated.setStudio("Studio");
        updated.setQuantity(5);

        List<Game> gList = new ArrayList<>();
        gList.add(game);

        doReturn(game).when(gameDao).addGame(noId);
        doReturn(updated).when(gameDao).updateGame(updated);
        doReturn(game).when(gameDao).getGame(11);
        doReturn(gList).when(gameDao).getAllGames();
        doReturn(gList).when(gameDao).getGamesByESRB("M");
        doReturn(gList).when(gameDao).getGamesByStudio("Studio");
        doReturn(gList).when(gameDao).getGamesByTitle("Title");
    }
}