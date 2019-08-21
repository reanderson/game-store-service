package com.company.RebeccaAndersonU1Capstone.dao;

import com.company.RebeccaAndersonU1Capstone.dto.TShirt;

import java.util.List;

public interface TShirtDao {
    TShirt addTShirt(TShirt tShirt);

    TShirt getTShirt(int id);

    List<TShirt> getAllTShirts();

    List<TShirt> getTShirtsByColor(String color);
    List<TShirt> getTShirtsBySize(String size);

    TShirt updateTShirt(TShirt tShirt);

    void deleteTShirt(int id);
}
