package com.company.RebeccaAndersonU1Capstone.service;

import com.company.RebeccaAndersonU1Capstone.dao.ConsoleDao;
import com.company.RebeccaAndersonU1Capstone.dao.ConsoleDaoJdbcImpl;
import com.company.RebeccaAndersonU1Capstone.dto.Console;
import com.company.RebeccaAndersonU1Capstone.viewModel.ConsoleViewModel;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

public class ConsoleServiceTest {

    ConsoleDao consoleDao;
    ConsoleService consoleService;


    @Before
    public void setUp() throws Exception {
        setUpConsoleDaoMock();

        consoleService = new ConsoleService(consoleDao);
    }

    @Test
    public void saveFindOneConsole() {
        ConsoleViewModel console = new ConsoleViewModel();
        console.setQuantity(15);
        console.setPrice(new BigDecimal("43.95").setScale(2));
        console.setModel("Model");
        console.setManufacturer("Company");
        console.setMemoryAmount("Memory");
        console.setProcessor("Processor");

        console = consoleService.saveConsole(console);
        ConsoleViewModel fromService = consoleService.findConsoleById(console.getId());

        assertEquals(console, fromService);
    }

    @Test
    public void updateConsole() {
        ConsoleViewModel updated = new ConsoleViewModel();
        updated.setId(1);
        updated.setQuantity(10);
        updated.setPrice(new BigDecimal("43.95").setScale(2));
        updated.setModel("Model");
        updated.setManufacturer("Company");
        updated.setMemoryAmount("Memory");
        updated.setProcessor("Processor");

        ConsoleViewModel fromService = consoleService.updateConsole(updated);

        assertEquals(updated, fromService);
    }


    @Test
    public void findAllConsole() {
        ConsoleViewModel console = new ConsoleViewModel();
        console.setId(1);
        console.setQuantity(15);
        console.setPrice(new BigDecimal("43.95").setScale(2));
        console.setModel("Model");
        console.setManufacturer("Company");
        console.setMemoryAmount("Memory");
        console.setProcessor("Processor");
        List<ConsoleViewModel> cList = new ArrayList<>();
        cList.add(console);

        List<ConsoleViewModel> fromService = consoleService.findAllConsole();

        assertEquals(cList, fromService);
    }

    @Test
    public void findConsoleByManufacturer() {
        ConsoleViewModel console = new ConsoleViewModel();
        console.setId(1);
        console.setQuantity(15);
        console.setPrice(new BigDecimal("43.95").setScale(2));
        console.setModel("Model");
        console.setManufacturer("Company");
        console.setMemoryAmount("Memory");
        console.setProcessor("Processor");
        List<ConsoleViewModel> cList = new ArrayList<>();
        cList.add(console);

        List<ConsoleViewModel> fromService = consoleService.findConsoleByManufacturer("Company");

        assertEquals(cList, fromService);
    }

    // Mocks
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

        Console noId = new Console();
        noId.setQuantity(15);
        noId.setPrice(new BigDecimal("43.95").setScale(2));
        noId.setModel("Model");
        noId.setManufacturer("Company");
        noId.setMemoryAmount("Memory");
        noId.setProcessor("Processor");

        Console updated = new Console();
        updated.setId(1);
        updated.setQuantity(10);
        updated.setPrice(new BigDecimal("43.95").setScale(2));
        updated.setModel("Model");
        updated.setManufacturer("Company");
        updated.setMemoryAmount("Memory");
        updated.setProcessor("Processor");

        List<Console> cList = new ArrayList<>();
        cList.add(console);

        doReturn(console).when(consoleDao).addConsole(noId);
        doReturn(updated).when(consoleDao).updateConsole(updated);
        doReturn(console).when(consoleDao).getConsole(1);
        doReturn(cList).when(consoleDao).getAllConsole();
        doReturn(cList).when(consoleDao).getConsolesByManufacturer("Company");

    }
}