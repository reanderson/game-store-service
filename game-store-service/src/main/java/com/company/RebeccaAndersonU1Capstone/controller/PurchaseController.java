package com.company.RebeccaAndersonU1Capstone.controller;

import com.company.RebeccaAndersonU1Capstone.service.PurchaseService;
import com.company.RebeccaAndersonU1Capstone.viewModel.InvoiceViewModel;
import com.company.RebeccaAndersonU1Capstone.viewModel.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/purchase")
public class PurchaseController {

    @Autowired
    PurchaseService purchaseService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public InvoiceViewModel purchaseItem(@RequestBody @Valid Order order) {
        return purchaseService.makePurchase(order);
    }
}
