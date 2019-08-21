package com.company.RebeccaAndersonU1Capstone.dao;

import com.company.RebeccaAndersonU1Capstone.dto.Tax;

public interface TaxDao {
    Tax getTaxByState(String state);
}
