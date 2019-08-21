package com.company.RebeccaAndersonU1Capstone.dao;

import com.company.RebeccaAndersonU1Capstone.dto.Console;

import java.util.List;

public interface ConsoleDao {
    Console addConsole(Console console);

    List<Console> getAllConsole();

    Console getConsole(int id);

    List<Console> getConsolesByManufacturer(String manufacturer);

    Console updateConsole(Console console);

    void deleteConsole(int id);
}
