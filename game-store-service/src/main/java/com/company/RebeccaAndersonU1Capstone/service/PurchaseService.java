package com.company.RebeccaAndersonU1Capstone.service;

import com.company.RebeccaAndersonU1Capstone.dao.*;
import com.company.RebeccaAndersonU1Capstone.dto.*;
import com.company.RebeccaAndersonU1Capstone.viewModel.InvoiceViewModel;
import com.company.RebeccaAndersonU1Capstone.viewModel.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Component
public class PurchaseService {
    private ConsoleDao consoleDao;
    private GameDao gameDao;
    private TShirtDao tShirtDao;
    private InvoiceDao invoiceDao;
    private TaxDao taxDao;
    private ProcessingFeeDao processingFeeDao;

    @Autowired
    public PurchaseService(ConsoleDao consoleDao, GameDao gameDao, TShirtDao tShirtDao, InvoiceDao invoiceDao,
                           TaxDao taxDao, ProcessingFeeDao processingFeeDao) {
        this.consoleDao = consoleDao;
        this.gameDao = gameDao;
        this.tShirtDao = tShirtDao;
        this.invoiceDao = invoiceDao;
        this.taxDao = taxDao;
        this.processingFeeDao = processingFeeDao;
    }

    @Transactional
    public InvoiceViewModel makePurchase(Order order) {
        // First we want to create a new Invoice, based on the Order
        Invoice invoice = new Invoice();
        invoice.setName(order.getName());
        invoice.setStreet(order.getStreet());
        invoice.setCity(order.getCity());
        invoice.setState(order.getState().toUpperCase());
        invoice.setZipcode(order.getZipcode());
        invoice.setItemType(order.getItemType().toLowerCase());
        invoice.setItemId(order.getItemId());
        invoice.setQuantity(order.getQuantity());

        // We need to check if the user inputted a valid state code.
        Tax tax = taxDao.getTaxByState(invoice.getState());
        if (tax == null) {
            throw new IllegalArgumentException("Invalid state: no state with state code " + invoice.getState() +
                    " found.");
        }

        // We need to check if the item type is valid. At the same time, we can get the processing fee
        ProcessingFee fee;
        if (invoice.getItemType().equals("t shirt") ||
                 invoice.getItemType().equals("tshirt")){
            invoice.setItemType("t-shirt");
            fee = processingFeeDao.getFeeByItemType("t-shirt");
        } else {
            fee = processingFeeDao.getFeeByItemType(invoice.getItemType());
        }

        if (fee == null) {
            // If the database returns null, that means that it can't find a matching item type,
            // so we need to throw an exception
            throw new IllegalArgumentException("Invalid item type: This shop stocks Games, Consoles, and T-Shirts");
        }

        invoice.setProcessingFee(fee.getFee());
        // If 10 or more items are being bought, add the extra amount
        if (invoice.getQuantity() >= 10) {
            invoice.setProcessingFee(invoice.getProcessingFee().add(new BigDecimal("15.49").setScale(2)));
        }

        // Next, we need to check the item type's database to see if there's an item with the appropriate id
        // The actual lookup might be possible to generalize a bit better, but for now, since there's only three item types,
        // it's reasonable to do a Switch statement for it.
        Item item;

        switch (invoice.getItemType()) {
            case "t-shirt":
                item = tShirtDao.getTShirt(invoice.getItemId());
                break;
            case "game":
                item = gameDao.getGame(invoice.getItemId());
                break;
            case "console":
                item = consoleDao.getConsole(invoice.getItemId());
                break;
            default:
                // this should never be reached because the previous item type check should have caught this possibility,
                // but just in case, it's here.
                item = null;
                break;
        }

        if (item == null) {
            throw new IllegalArgumentException("Invalid item: no " + invoice.getItemType() + " with id " + invoice.getItemId() + " found.");
        }

        // Compare quantity availability
        if (item.getQuantity() < invoice.getQuantity()) {
            throw new IllegalArgumentException("There are currently " + item.getQuantity() + " of this item in stock. You tried to purchase " +
                    invoice.getQuantity() + ". You cannot buy more than the current stock.");
        }

        // if it passes, we update that quantity
        item.setQuantity(item.getQuantity() - invoice.getQuantity());

        switch (invoice.getItemType()) {
            case "t-shirt":
                item = tShirtDao.updateTShirt((TShirt)item);
                break;
            case "game":
                item = gameDao.updateGame((Game)item);
                break;
            case "console":
                item = consoleDao.updateConsole((Console)item);
                break;
            default:
                break;
        }

        // and set the invoice price
        invoice.setUnitPrice(item.getPrice());

        // It's math time! Step one is the subtotal, which is the unit price multiplied by the quantity.
        invoice.setSubtotal(invoice.getUnitPrice().multiply(new BigDecimal(invoice.getQuantity())).setScale(2, RoundingMode.HALF_UP ));

        // next we need to get the amount added by tax. This is calculated by multiplying the subtotal by the tax rate.
        invoice.setTax(invoice.getSubtotal().multiply(tax.getRate()).setScale(2, RoundingMode.HALF_UP ));

        // We have the processing fee from earlier, so now it's just a matter of adding these values together
        invoice.setTotal(invoice.getSubtotal().add(invoice.getTax()).add(invoice.getProcessingFee()));


        // Now that the invoice is fully built, we need to send it to the database.
        invoice = invoiceDao.addInvoice(invoice);

        InvoiceViewModel ivm = buildIVM(invoice);
        return ivm;
    }

    public InvoiceViewModel findOnePurchase(int id) {
        Invoice invoice = invoiceDao.getInvoice(id);
        InvoiceViewModel ivm = buildIVM(invoice);
        return ivm;
    }

    // helper methods
    private InvoiceViewModel buildIVM(Invoice invoice) {
        InvoiceViewModel ivm = new InvoiceViewModel();
        ivm.setId(invoice.getId());
        ivm.setName(invoice.getName());
        ivm.setStreet(invoice.getStreet());
        ivm.setCity(invoice.getCity());
        ivm.setState(invoice.getState());
        ivm.setZipcode(invoice.getZipcode());
        ivm.setItemType(invoice.getItemType());
        ivm.setItemId(invoice.getItemId());
        ivm.setUnitPrice(invoice.getUnitPrice());
        ivm.setQuantity(invoice.getQuantity());
        ivm.setSubtotal(invoice.getSubtotal());
        ivm.setTax(invoice.getTax());
        ivm.setProcessingFee(invoice.getProcessingFee());
        ivm.setTotal(invoice.getTotal());
        return ivm;
    }
}
