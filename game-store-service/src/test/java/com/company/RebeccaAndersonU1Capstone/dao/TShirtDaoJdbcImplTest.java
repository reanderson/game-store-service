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
public class TShirtDaoJdbcImplTest {
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
    public void addGetDeleteTShirt() {
        TShirt tshirt = new TShirt();
        tshirt.setSize("Size");
        tshirt.setColor("Color");
        tshirt.setDescription("Description");
        tshirt.setPrice(new BigDecimal("12.99").setScale(2));
        tshirt.setQuantity(15);

        tshirt = tShirtDao.addTShirt(tshirt);

        TShirt fromDb = tShirtDao.getTShirt(tshirt.getId());

        assertEquals(tshirt, fromDb);

        tShirtDao.deleteTShirt(tshirt.getId());

        fromDb = tShirtDao.getTShirt(tshirt.getId());

        assertNull(fromDb);
    }


    @Test
    public void getAllTShirts() {
        TShirt tshirt = new TShirt();
        tshirt.setSize("Size");
        tshirt.setColor("Color");
        tshirt.setDescription("Description");
        tshirt.setPrice(new BigDecimal("12.99").setScale(2));
        tshirt.setQuantity(15);

        tShirtDao.addTShirt(tshirt);
        tShirtDao.addTShirt(tshirt);

        List<TShirt> shirts = tShirtDao.getAllTShirts();

        assertEquals(2, shirts.size());
    }

    @Test
    public void getTShirtsByColor() {
        TShirt tshirt = new TShirt();
        tshirt.setSize("Size");
        tshirt.setColor("Color");
        tshirt.setDescription("Description");
        tshirt.setPrice(new BigDecimal("12.99").setScale(2));
        tshirt.setQuantity(15);

        tShirtDao.addTShirt(tshirt);

        tshirt.setColor("Another color");
        tShirtDao.addTShirt(tshirt);

        List<TShirt> shirts = tShirtDao.getTShirtsByColor("Color");

        assertEquals(1, shirts.size());
    }

    @Test
    public void getTShirtsBySize() {
        TShirt tshirt = new TShirt();
        tshirt.setSize("Size");
        tshirt.setColor("Color");
        tshirt.setDescription("Description");
        tshirt.setPrice(new BigDecimal("12.99").setScale(2));
        tshirt.setQuantity(15);

        tShirtDao.addTShirt(tshirt);

        tshirt.setSize("L");
        tShirtDao.addTShirt(tshirt);

        List<TShirt> shirts = tShirtDao.getTShirtsBySize("L");

        assertEquals(1, shirts.size());
    }

    @Test
    public void updateTShirt() {
        TShirt tshirt = new TShirt();
        tshirt.setSize("Size");
        tshirt.setColor("Color");
        tshirt.setDescription("Description");
        tshirt.setPrice(new BigDecimal("12.99").setScale(2));
        tshirt.setQuantity(15);

        tshirt = tShirtDao.addTShirt(tshirt);

        tshirt.setSize("L");

        tshirt = tShirtDao.updateTShirt(tshirt);

        TShirt fromDb = tShirtDao.getTShirt(tshirt.getId());

        assertEquals(tshirt, fromDb);
    }
}

