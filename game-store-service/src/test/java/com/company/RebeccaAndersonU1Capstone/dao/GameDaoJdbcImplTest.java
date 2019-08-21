package com.company.RebeccaAndersonU1Capstone.dao;

import com.company.RebeccaAndersonU1Capstone.dto.Console;
import com.company.RebeccaAndersonU1Capstone.dto.Game;
import com.company.RebeccaAndersonU1Capstone.dto.Invoice;
import com.company.RebeccaAndersonU1Capstone.dto.TShirt;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class GameDaoJdbcImplTest {
    // autowire all daos
    @Autowired
    ConsoleDao consoleDao;
    @Autowired
    GameDao gameDao;
    @Autowired
    InvoiceDao invoiceDao;
    @Autowired
    TShirtDao tShirtDao;

    @Before
    public void setUp() throws Exception {
        // clear out db
        List<Console> cList = consoleDao.getAllConsole();
        for (Console c : cList) {
            consoleDao.deleteConsole(c.getId());
        }

        List<Game> gList = gameDao.getAllGames();
        for (Game g : gList) {
            gameDao.deleteGame(g.getId());
        }

        List<Invoice> iList = invoiceDao.getAllInvoice();
        for (Invoice i : iList) {
            invoiceDao.deleteInvoice(i.getId());
        }

        List<TShirt> tList = tShirtDao.getAllTShirts();
        for (TShirt t : tList) {
            tShirtDao.deleteTShirt(t.getId());
        }
    }

    @Test
    public void addGetDeleteGame() {
        Game game = new Game();
        game.setTitle("Title");
        game.setEsrbRating("ESRB");
        game.setDescription("Description");
        game.setPrice(new BigDecimal("12.39").setScale(2));
        game.setStudio("Studio");
        game.setQuantity(28);

        game = gameDao.addGame(game);

        Game fromDb = gameDao.getGame(game.getId());

        assertEquals(game, fromDb);

        gameDao.deleteGame(game.getId());

        assertNull(gameDao.getGame(game.getId()));
    }


    @Test
    public void updateGame() {
        Game game = new Game();
        game.setTitle("Title");
        game.setEsrbRating("ESRB");
        game.setDescription("Description");
        game.setPrice(new BigDecimal("12.39").setScale(2));
        game.setStudio("Studio");
        game.setQuantity(28);

        game = gameDao.addGame(game);

        game.setDescription("Oh look, an updated description");

        game = gameDao.updateGame(game);

        Game fromDb = gameDao.getGame(game.getId());

        assertEquals(game, fromDb);
    }

    @Test
    public void getAllGames() {
        Game game = new Game();
        game.setTitle("Title");
        game.setEsrbRating("ESRB");
        game.setDescription("Description");
        game.setPrice(new BigDecimal("12.39").setScale(2));
        game.setStudio("Studio");
        game.setQuantity(28);

        gameDao.addGame(game);
        gameDao.addGame(game);

        List<Game> listFromDb = gameDao.getAllGames();

        assertEquals(2, listFromDb.size());
    }

    @Test
    public void getGamesByESRB() {
        Game game = new Game();
        game.setTitle("Title");
        game.setEsrbRating("ESRB");
        game.setDescription("Description");
        game.setPrice(new BigDecimal("12.39").setScale(2));
        game.setStudio("Studio");
        game.setQuantity(28);

        game = gameDao.addGame(game);

        Game game2 = new Game();
        game2.setTitle("Title II");
        game2.setEsrbRating("ESRB 2");
        game2.setDescription("Description");
        game2.setPrice(new BigDecimal("12.39").setScale(2));
        game2.setStudio("Studio 2");
        game2.setQuantity(28);

        game2 = gameDao.addGame(game2);

        // Database should now have two games, with different ESRBs
        List<Game> listFromDb = gameDao.getGamesByESRB(game.getEsrbRating());

        // So the list should have 1 item in it
        assertEquals(1, listFromDb.size());
    }

    @Test
    public void getGamesByStudio() {
        Game game = new Game();
        game.setTitle("Title");
        game.setEsrbRating("ESRB");
        game.setDescription("Description");
        game.setPrice(new BigDecimal("12.39").setScale(2));
        game.setStudio("Studio");
        game.setQuantity(28);

        game = gameDao.addGame(game);

        Game game2 = new Game();
        game2.setTitle("Title II");
        game2.setEsrbRating("ESRB 2");
        game2.setDescription("Description");
        game2.setPrice(new BigDecimal("12.39").setScale(2));
        game2.setStudio("Studio 2");
        game2.setQuantity(28);

        game2 = gameDao.addGame(game2);

        // Database should now have two games, with different Studios
        List<Game> listFromDb = gameDao.getGamesByStudio(game.getStudio());

        // So the list should have 1 item in it
        assertEquals(1, listFromDb.size());
    }

    @Test
    public void getGamesByTitle() {
        Game game = new Game();
        game.setTitle("Title");
        game.setEsrbRating("ESRB");
        game.setDescription("Description");
        game.setPrice(new BigDecimal("12.39").setScale(2));
        game.setStudio("Studio");
        game.setQuantity(28);

        game = gameDao.addGame(game);

        Game game2 = new Game();
        game2.setTitle("Title II");
        game2.setEsrbRating("ESRB 2");
        game2.setDescription("Description");
        game2.setPrice(new BigDecimal("12.39").setScale(2));
        game2.setStudio("Studio 2");
        game2.setQuantity(28);

        gameDao.addGame(game2);

        game2.setTitle("Something completely different");
        game2 = gameDao.addGame(game2);

        // Database should now have three games. Two of them include "Title" in the title, one does not
        List<Game> listFromDb = gameDao.getGamesByTitle("Title");

        // The list should get back both games that include "Title" in the title
        assertEquals(2, listFromDb.size());
    }

}