package com.company.RebeccaAndersonU1Capstone.controller;

import com.company.RebeccaAndersonU1Capstone.service.ConsoleService;
import com.company.RebeccaAndersonU1Capstone.viewModel.ConsoleViewModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/console")
public class ConsoleController {
    @Autowired
    ConsoleService consoleService;

    // Create
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ConsoleViewModel createConsole(@RequestBody @Valid ConsoleViewModel cvm) {
        return consoleService.saveConsole(cvm);
    }

    // Get one
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ConsoleViewModel getOneConsole(@PathVariable("id") int consoleId) {
        return consoleService.findConsoleById(consoleId);
    }

    // Get all
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ConsoleViewModel> getAllConsole() {
        return consoleService.findAllConsole();
    }

    // Get by manufacturer
    @GetMapping("/manufacturer/{manufacturer}")
    @ResponseStatus(HttpStatus.OK)
    public List<ConsoleViewModel> getByManufacturer(@PathVariable("manufacturer") String manufacturer) {
        return consoleService.findConsoleByManufacturer(manufacturer);
    }

    // update
    // I still want to have the update return the updated object, even though it's the same as what the user inputted
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ConsoleViewModel updateConsole(@PathVariable("id") int consoleId, @RequestBody @Valid ConsoleViewModel cvm) {
        if (cvm.getId() == 0)
            cvm.setId(consoleId);
        if (consoleId != cvm.getId()) {
            throw new IllegalArgumentException("Console ID on path must match the ID in Console object");
        }

        return consoleService.updateConsole(cvm);
    }

    // delete
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteConsole(@PathVariable("id") int consoleId) {
        consoleService.removeConsole(consoleId);
    }
}
