package com.company.RebeccaAndersonU1Capstone.service;

import com.company.RebeccaAndersonU1Capstone.dao.ConsoleDao;
import com.company.RebeccaAndersonU1Capstone.dto.Console;
import com.company.RebeccaAndersonU1Capstone.exception.NotFoundException;
import com.company.RebeccaAndersonU1Capstone.viewModel.ConsoleViewModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ConsoleService {

    private ConsoleDao consoleDao;

    @Autowired
    public ConsoleService (ConsoleDao consoleDao) {
        this.consoleDao = consoleDao;
    }

    public ConsoleViewModel saveConsole(ConsoleViewModel cvm) {
        Console console = buildConsole(cvm);
        console = consoleDao.addConsole(console);
        cvm.setId(console.getId());
        return cvm;
    }

    public ConsoleViewModel updateConsole(ConsoleViewModel cvm) {
        if(!checkIfExists(cvm.getId())) {
            throw new NotFoundException("No Console of id " + cvm.getId() + " exists to update.");
        }
        Console console = buildConsole(cvm);
        consoleDao.updateConsole(console);
        return cvm;
    }

    public ConsoleViewModel findConsoleById(int id) {
        Console console = consoleDao.getConsole(id);
        if (console == null) {
            return null;
        }
        ConsoleViewModel cvm = buildCVM(console);
        return cvm;
    }

    public List<ConsoleViewModel> findAllConsole() {
        List<Console> consoles = consoleDao.getAllConsole();
        List<ConsoleViewModel> cvms = new ArrayList<>();

        for (Console console : consoles) {
            ConsoleViewModel cvm = buildCVM(console);
            cvms.add(cvm);
        }

        return cvms;
    }

    public List<ConsoleViewModel> findConsoleByManufacturer(String manufacturer) {
        List<Console> consoles = consoleDao.getConsolesByManufacturer(manufacturer);
        List<ConsoleViewModel> cvms = new ArrayList<>();

        for (Console console : consoles) {
            ConsoleViewModel cvm = buildCVM(console);
            cvms.add(cvm);
        }

        return cvms;
    }

    public void removeConsole(int id) {
        if(!checkIfExists(id)) {
        throw new NotFoundException("No Console of id " + id + " exists to delete.");
        }
        consoleDao.deleteConsole(id);
    }

    // helpers
    private ConsoleViewModel buildCVM(Console console) {
        ConsoleViewModel cvm = new ConsoleViewModel();
        cvm.setId(console.getId());
        cvm.setManufacturer(console.getManufacturer());
        cvm.setMemoryAmount(console.getMemoryAmount());
        cvm.setModel(console.getModel());
        cvm.setPrice(console.getPrice());
        cvm.setProcessor(console.getProcessor());
        cvm.setQuantity(console.getQuantity());
        return cvm;
    }

    private Console buildConsole(ConsoleViewModel cvm) {
        Console console = new Console();
        console.setId(cvm.getId());
        console.setManufacturer(cvm.getManufacturer());
        console.setMemoryAmount(cvm.getMemoryAmount());
        console.setModel(cvm.getModel());
        console.setProcessor(cvm.getProcessor());
        console.setPrice(cvm.getPrice());
        console.setQuantity(cvm.getQuantity());
        return console;
    }

    private boolean checkIfExists(int id) {
        if (consoleDao.getConsole(id) != null) {
            return true;
        } else {
            return false;
        }
    }
}
