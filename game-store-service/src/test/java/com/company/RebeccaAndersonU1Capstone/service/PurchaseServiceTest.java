package com.company.RebeccaAndersonU1Capstone.service;

import com.company.RebeccaAndersonU1Capstone.dao.*;
import com.company.RebeccaAndersonU1Capstone.dto.*;
import com.company.RebeccaAndersonU1Capstone.viewModel.*;
import org.junit.Before;
import org.junit.Test;
import org.mockito.internal.matchers.Or;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

public class PurchaseServiceTest {
    ConsoleDao consoleDao;
    GameDao gameDao;
    TShirtDao tShirtDao;
    InvoiceDao invoiceDao;
    TaxDao taxDao;
    ProcessingFeeDao processingFeeDao;

    PurchaseService purchaseService;

    @Before
    public void setUp() throws Exception {
        setUpConsoleDaoMock();
        setUpGameDaoMock();
        setUpTShirtDaoMock();
        setUpInvoiceDaoMock();
        setUpTaxDaoMock();
        setUpProcessingFeeDaoMock();

        purchaseService = new PurchaseService(consoleDao, gameDao, tShirtDao, invoiceDao, taxDao, processingFeeDao);
    }

    // Basic Test
    @Test
    public void makeGetOneValidPurchase() {
        Order order = new Order();
        order.setName("Customer");
        order.setStreet("Street");
        order.setCity("City");
        order.setState("AL");
        order.setZipcode("12345");
        order.setItemType("T Shirt");
        order.setItemId(21);
        order.setQuantity(5);

        InvoiceViewModel invoice = purchaseService.makePurchase(order);
        InvoiceViewModel fromService = purchaseService.findOnePurchase(invoice.getId());

        assertEquals(invoice, fromService);
    }

    // Test for a purchase of more than 10, make sure extra processing fee is added
    @Test
    public void checkExtraProcessingFee() {
        Order order = new Order();
        order.setName("Customer");
        order.setStreet("Street");
        order.setCity("City");
        order.setState("AL");
        order.setZipcode("12345");
        order.setItemType("Console");
        order.setItemId(1);
        order.setQuantity(10);

        InvoiceViewModel invoice = purchaseService.makePurchase(order);
        InvoiceViewModel fromService = purchaseService.findOnePurchase(invoice.getId());

        assertEquals(invoice, fromService);
    }

    // Test for invalid state; should throw an exception
    @Test(expected = IllegalArgumentException.class)
    public void checkBadState(){
        Order order = new Order();
        order.setName("Customer");
        order.setStreet("Street");
        order.setCity("City");
        order.setState("XX");
        order.setZipcode("12345");
        order.setItemType("T Shirt");
        order.setItemId(21);
        order.setQuantity(5);

        InvoiceViewModel invoiceViewModel = purchaseService.makePurchase(order);
    }

    // Test for invalid item type; should throw an exception
    @Test(expected = IllegalArgumentException.class)
    public void checkBadItemType() {
        Order order = new Order();
        order.setName("Customer");
        order.setStreet("Street");
        order.setCity("City");
        order.setState("AL");
        order.setZipcode("12345");
        order.setItemType("Other");
        order.setItemId(21);
        order.setQuantity(5);

        InvoiceViewModel invoiceViewModel = purchaseService.makePurchase(order);
    }

    // Test for bad item; should throw an exception
    @Test(expected = IllegalArgumentException.class)
    public void checkBadItem() {
        Order order = new Order();
        order.setName("Customer");
        order.setStreet("Street");
        order.setCity("City");
        order.setState("AL");
        order.setZipcode("12345");
        order.setItemType("Game");
        order.setItemId(12);
        order.setQuantity(5);

        InvoiceViewModel invoice = purchaseService.makePurchase(order);
    }

    // Test for trying to buy too many items; should throw an exception
    @Test(expected = IllegalArgumentException.class)
    public void checkBadQuantity() {
        Order order = new Order();
        order.setName("Customer");
        order.setStreet("Street");
        order.setCity("City");
        order.setState("AL");
        order.setZipcode("12345");
        order.setItemType("Game");
        order.setItemId(11);
        order.setQuantity(5);

        InvoiceViewModel invoice = purchaseService.makePurchase(order);
    }


    // Mocks
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

        doReturn(game).when(gameDao).getGame(11);
        doReturn(null).when(gameDao).getGame(12);
    }
    private void setUpTShirtDaoMock() {
        tShirtDao = mock(TShirtDaoJdbcImpl.class);

        TShirt tShirt = new TShirt();
        tShirt.setId(21);
        tShirt.setSize("S");
        tShirt.setColor("Color");
        tShirt.setDescription("Description");
        tShirt.setPrice(new BigDecimal("9.99").setScale(2));
        tShirt.setQuantity(25);

        TShirt updated = new TShirt();
        updated.setId(21);
        updated.setSize("S");
        updated.setColor("Color");
        updated.setDescription("Description");
        updated.setPrice(new BigDecimal("9.99").setScale(2));
        updated.setQuantity(20);

        doReturn(updated).when(tShirtDao).updateTShirt(updated);
        doReturn(tShirt).when(tShirtDao).getTShirt(21);
    }
    private void setUpConsoleDaoMock() {
        consoleDao = mock(ConsoleDaoJdbcImpl.class);

        Console console = new Console();
        console.setId(1);
        console.setQuantity(15);
        console.setPrice(new BigDecimal("43.95").setScale(2));
        console.setModel("Model");
        console.setManufacturer("Company");
        console.setMemoryAmount("Memory");
        console.setProcessor("Processor");

        Console updated = new Console();
        updated.setId(1);
        updated.setQuantity(5);
        updated.setPrice(new BigDecimal("43.95").setScale(2));
        updated.setModel("Model");
        updated.setManufacturer("Company");
        updated.setMemoryAmount("Memory");
        updated.setProcessor("Processor");

        doReturn(updated).when(consoleDao).updateConsole(updated);
        doReturn(console).when(consoleDao).getConsole(1);

    }
    private void setUpTaxDaoMock() {
        taxDao = mock(TaxDaoJdbcImpl.class);

        Tax taxAlabama = new Tax();
        taxAlabama.setRate(new BigDecimal(".05").setScale(2));
        taxAlabama.setState("AL");

        doReturn(taxAlabama).when(taxDao).getTaxByState("AL");
        doReturn(null).when(taxDao).getTaxByState("XX");
    }
    private void setUpProcessingFeeDaoMock() {
        processingFeeDao = mock(ProcessingFeeDaoJdbcImpl.class);

        ProcessingFee feeConsole = new ProcessingFee();
        feeConsole.setItemType("console");
        feeConsole.setFee(new BigDecimal("14.99").setScale(2));

        ProcessingFee feeShirt = new ProcessingFee();
        feeShirt.setItemType("t-shirt");
        feeShirt.setFee(new BigDecimal("1.98").setScale(2));

        ProcessingFee feeGame = new ProcessingFee();
        feeGame.setItemType("game");
        feeGame.setFee(new BigDecimal("1.49").setScale(2));

        doReturn(feeConsole).when(processingFeeDao).getFeeByItemType("console");
        doReturn(feeShirt).when(processingFeeDao).getFeeByItemType("t-shirt");
        doReturn(feeGame).when(processingFeeDao).getFeeByItemType("game");
        doReturn(null).when(processingFeeDao).getFeeByItemType("Other");
    }
    private void setUpInvoiceDaoMock() {
        invoiceDao = mock(InvoiceDaoJdbcImpl.class);

        Invoice shirtInvoice = new Invoice();
        shirtInvoice.setId(41);
        shirtInvoice.setName("Customer");
        shirtInvoice.setStreet("Street");
        shirtInvoice.setCity("City");
        shirtInvoice.setState("AL");
        shirtInvoice.setZipcode("12345");
        shirtInvoice.setItemType("t-shirt");
        shirtInvoice.setItemId(21);
        shirtInvoice.setQuantity(5);
        shirtInvoice.setUnitPrice(new BigDecimal("9.99").setScale(2));
        shirtInvoice.setSubtotal(shirtInvoice.getUnitPrice().multiply(new BigDecimal(shirtInvoice.getQuantity())).setScale(2, RoundingMode.HALF_UP ));
        shirtInvoice.setTax(shirtInvoice.getSubtotal().multiply(new BigDecimal(".05")).setScale(2, RoundingMode.HALF_UP ));
        shirtInvoice.setProcessingFee(new BigDecimal("1.98").setScale(2));
        shirtInvoice.setTotal(shirtInvoice.getSubtotal()
                .add(shirtInvoice.getTax())
                .add(shirtInvoice.getProcessingFee()));

        Invoice shirtInvoiceNoId = new Invoice();
        shirtInvoiceNoId.setName("Customer");
        shirtInvoiceNoId.setStreet("Street");
        shirtInvoiceNoId.setCity("City");
        shirtInvoiceNoId.setState("AL");
        shirtInvoiceNoId.setZipcode("12345");
        shirtInvoiceNoId.setItemType("t-shirt");
        shirtInvoiceNoId.setItemId(21);
        shirtInvoiceNoId.setQuantity(5);
        shirtInvoiceNoId.setUnitPrice(new BigDecimal("9.99").setScale(2));
        shirtInvoiceNoId.setSubtotal(shirtInvoice.getSubtotal());
        shirtInvoiceNoId.setTax(shirtInvoice.getTax());
        shirtInvoiceNoId.setProcessingFee(new BigDecimal("1.98").setScale(2));
        shirtInvoiceNoId.setTotal(shirtInvoice.getTotal());

        Invoice consoleInvoice = new Invoice();
        consoleInvoice.setId(42);
        consoleInvoice.setName("Customer");
        consoleInvoice.setStreet("Street");
        consoleInvoice.setCity("City");
        consoleInvoice.setState("AL");
        consoleInvoice.setZipcode("12345");
        consoleInvoice.setItemType("console");
        consoleInvoice.setItemId(1);
        consoleInvoice.setQuantity(10);
        consoleInvoice.setUnitPrice(new BigDecimal("43.95").setScale(2));
        consoleInvoice.setSubtotal(consoleInvoice.getUnitPrice().multiply(new BigDecimal(consoleInvoice.getQuantity())).setScale(2, RoundingMode.HALF_UP ));
        consoleInvoice.setTax(consoleInvoice.getSubtotal().multiply(new BigDecimal(".05")).setScale(2, RoundingMode.HALF_UP));
        consoleInvoice.setProcessingFee(new BigDecimal("14.99").setScale(2).add(new BigDecimal("15.49")).setScale(2, RoundingMode.HALF_UP ));
        consoleInvoice.setTotal(consoleInvoice.getSubtotal()
                .add(consoleInvoice.getTax())
                .add(consoleInvoice.getProcessingFee()));

        Invoice consoleInvoiceNoId = new Invoice();
        consoleInvoiceNoId.setName("Customer");
        consoleInvoiceNoId.setStreet("Street");
        consoleInvoiceNoId.setCity("City");
        consoleInvoiceNoId.setState("AL");
        consoleInvoiceNoId.setZipcode("12345");
        consoleInvoiceNoId.setItemType("console");
        consoleInvoiceNoId.setItemId(1);
        consoleInvoiceNoId.setQuantity(10);
        consoleInvoiceNoId.setUnitPrice(new BigDecimal("43.95").setScale(2));
        consoleInvoiceNoId.setSubtotal(consoleInvoice.getSubtotal());
        consoleInvoiceNoId.setTax(consoleInvoice.getTax());
        consoleInvoiceNoId.setProcessingFee(consoleInvoice.getProcessingFee());
        consoleInvoiceNoId.setTotal(consoleInvoice.getTotal());

        doReturn(shirtInvoice).when(invoiceDao).addInvoice(shirtInvoiceNoId);
        doReturn(consoleInvoice).when(invoiceDao).addInvoice(consoleInvoiceNoId);
        doReturn(shirtInvoice).when(invoiceDao).getInvoice(41);
        doReturn(consoleInvoice).when(invoiceDao).getInvoice(42);

    }
}