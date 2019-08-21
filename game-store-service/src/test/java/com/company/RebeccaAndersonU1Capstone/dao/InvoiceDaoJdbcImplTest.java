package com.company.RebeccaAndersonU1Capstone.dao;

import com.company.RebeccaAndersonU1Capstone.dto.*;
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
public class InvoiceDaoJdbcImplTest {
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
    public void addGetGetAllDeleteInvoice() {
        // We don't need to test adding an invoice for different item types,
        // since it's the service layer that's going to handle making sure the item type/id are valid,
        // when translating the user's Order into an Invoice
        // So for this test, we just need any old invoice with dummy data
        Invoice inv = new Invoice();
        inv.setName("Name");
        inv.setStreet("Street");
        inv.setCity("City");
        inv.setState("XX");
        inv.setZipcode("12345");
        inv.setItemType("Item");
        inv.setItemId(3);
        inv.setUnitPrice(new BigDecimal("12.35").setScale(2));
        inv.setQuantity(1);
        inv.setSubtotal(new BigDecimal("12.35").setScale(2));
        inv.setTax(new BigDecimal("1.50").setScale(2));
        inv.setProcessingFee(new BigDecimal("1.10").setScale(2));
        inv.setTotal(new BigDecimal("14.95").setScale(2));

        invoiceDao.addInvoice(inv);
        inv = invoiceDao.addInvoice(inv);

        Invoice fromDb = invoiceDao.getInvoice(inv.getId());

        assertEquals(inv, fromDb);

        List<Invoice> invList = invoiceDao.getAllInvoice();
        assertEquals(2, invList.size());

        invoiceDao.deleteInvoice(inv.getId());

        assertNull(invoiceDao.getInvoice(inv.getId()));
    }
}