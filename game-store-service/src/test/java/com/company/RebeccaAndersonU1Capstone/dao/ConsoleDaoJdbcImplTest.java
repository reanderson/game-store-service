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
public class ConsoleDaoJdbcImplTest {
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
    public void addGetDeleteConsole() {
        Console console = new Console();
        console.setModel("Model");
        console.setManufacturer("Manufacturer");
        console.setMemoryAmount("Memory");
        console.setProcessor("Processor");
        console.setPrice(new BigDecimal("34.21").setScale(2));
        console.setQuantity(42);

        console = consoleDao.addConsole(console);

        Console fromDb = consoleDao.getConsole(console.getId());

        assertEquals(console, fromDb);

        consoleDao.deleteConsole(console.getId());

        fromDb = consoleDao.getConsole(console.getId());

        assertNull(fromDb);
    }


    @Test
    public void updateConsole() {
        Console console = new Console();
        console.setModel("Model");
        console.setManufacturer("Manufacturer");
        console.setMemoryAmount("Memory");
        console.setProcessor("Processor");
        console.setPrice(new BigDecimal("34.21").setScale(2));
        console.setQuantity(42);

        console = consoleDao.addConsole(console);

        console.setQuantity(35);

        console = consoleDao.updateConsole(console);

        Console fromDb = consoleDao.getConsole(console.getId());

        assertEquals(console, fromDb);
    }

    @Test
    public void getAllConsole() {
        Console console = new Console();
        console.setModel("Model");
        console.setManufacturer("Manufacturer");
        console.setMemoryAmount("Memory");
        console.setProcessor("Processor");
        console.setPrice(new BigDecimal("34.21").setScale(2));
        console.setQuantity(42);

        consoleDao.addConsole(console);
        consoleDao.addConsole(console);

        List<Console> consoles = consoleDao.getAllConsole();

        assertEquals(2, consoles.size());
    }

    @Test
    public void getConsolesByManufacturer() {
        Console console = new Console();
        console.setModel("Model");
        console.setManufacturer("Manufacturer");
        console.setMemoryAmount("Memory");
        console.setProcessor("Processor");
        console.setPrice(new BigDecimal("34.21").setScale(2));
        console.setQuantity(42);

        consoleDao.addConsole(console);

        console.setManufacturer("Another Manufacturer");

        consoleDao.addConsole(console);

        List<Console> consoles = consoleDao.getConsolesByManufacturer("Manufacturer");

        assertEquals(1, consoles.size());
    }

}