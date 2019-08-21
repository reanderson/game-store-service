package com.company.RebeccaAndersonU1Capstone.dao;

import com.company.RebeccaAndersonU1Capstone.dto.ProcessingFee;

public interface ProcessingFeeDao {
    ProcessingFee getFeeByItemType(String itemType);
}
