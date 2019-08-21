package com.company.RebeccaAndersonU1Capstone.service;

import com.company.RebeccaAndersonU1Capstone.dao.TShirtDao;
import com.company.RebeccaAndersonU1Capstone.dao.TShirtDaoJdbcImpl;
import com.company.RebeccaAndersonU1Capstone.dto.TShirt;
import com.company.RebeccaAndersonU1Capstone.viewModel.TShirtViewModel;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

public class TShirtServiceTest {
    TShirtDao tShirtDao;
    TShirtService tShirtService;

    @Before
    public void setUp() throws Exception {
        setUpTShirtDaoMock();

        tShirtService = new TShirtService(tShirtDao);
    }

    @Test
    public void saveGetOneTShirt() {
        TShirtViewModel tShirt = new TShirtViewModel();
        tShirt.setSize("S");
        tShirt.setColor("Color");
        tShirt.setDescription("Description");
        tShirt.setPrice(new BigDecimal("9.99").setScale(2));
        tShirt.setQuantity(25);

        tShirt = tShirtService.saveTShirt(tShirt);
        TShirtViewModel fromService = tShirtService.getTShirtById(tShirt.getId());

        assertEquals(tShirt, fromService);
    }

    @Test
    public void updateTShirt() {
        TShirtViewModel updated = new TShirtViewModel();
        updated.setId(21);
        updated.setSize("S");
        updated.setColor("Color");
        updated.setDescription("Description");
        updated.setPrice(new BigDecimal("9.99").setScale(2));
        updated.setQuantity(20);

        TShirtViewModel fromService = tShirtService.updateTShirt(updated);

        assertEquals(updated, fromService);
    }


    @Test
    public void getAllTShirt() {
        TShirtViewModel tShirt = new TShirtViewModel();
        tShirt.setId(21);
        tShirt.setSize("S");
        tShirt.setColor("Color");
        tShirt.setDescription("Description");
        tShirt.setPrice(new BigDecimal("9.99").setScale(2));
        tShirt.setQuantity(25);

        List<TShirtViewModel> tList = new ArrayList<>();
        tList.add(tShirt);

        List<TShirtViewModel> fromService = tShirtService.getAllTShirt();

        assertEquals(tList, fromService);
    }

    @Test
    public void getTShirtBySize() {
        TShirtViewModel tShirt = new TShirtViewModel();
        tShirt.setId(21);
        tShirt.setSize("S");
        tShirt.setColor("Color");
        tShirt.setDescription("Description");
        tShirt.setPrice(new BigDecimal("9.99").setScale(2));
        tShirt.setQuantity(25);

        List<TShirtViewModel> tList = new ArrayList<>();
        tList.add(tShirt);

        List<TShirtViewModel> fromService = tShirtService.getTShirtBySize("S");

        assertEquals(tList, fromService);
    }

    @Test
    public void getTShirtByColor() {
        TShirtViewModel tShirt = new TShirtViewModel();
        tShirt.setId(21);
        tShirt.setSize("S");
        tShirt.setColor("Color");
        tShirt.setDescription("Description");
        tShirt.setPrice(new BigDecimal("9.99").setScale(2));
        tShirt.setQuantity(25);

        List<TShirtViewModel> tList = new ArrayList<>();
        tList.add(tShirt);

        List<TShirtViewModel> fromService = tShirtService.getTShirtByColor("Color");

        assertEquals(tList, fromService);
    }


    // mock
    private void setUpTShirtDaoMock() {
        tShirtDao = mock(TShirtDaoJdbcImpl.class);

        TShirt tShirt = new TShirt();
        tShirt.setId(21);
        tShirt.setSize("S");
        tShirt.setColor("Color");
        tShirt.setDescription("Description");
        tShirt.setPrice(new BigDecimal("9.99").setScale(2));
        tShirt.setQuantity(25);

        TShirt noId = new TShirt();
        noId.setSize("S");
        noId.setColor("Color");
        noId.setDescription("Description");
        noId.setPrice(new BigDecimal("9.99").setScale(2));
        noId.setQuantity(25);

        TShirt updated = new TShirt();
        updated.setId(21);
        updated.setSize("S");
        updated.setColor("Color");
        updated.setDescription("Description");
        updated.setPrice(new BigDecimal("9.99").setScale(2));
        updated.setQuantity(20);

        List<TShirt> tList = new ArrayList<>();
        tList.add(tShirt);

        doReturn(tShirt).when(tShirtDao).addTShirt(noId);
        doReturn(updated).when(tShirtDao).updateTShirt(updated);
        doReturn(tShirt).when(tShirtDao).getTShirt(21);
        doReturn(tList).when(tShirtDao).getAllTShirts();
        doReturn(tList).when(tShirtDao).getTShirtsByColor("Color");
        doReturn(tList).when(tShirtDao).getTShirtsBySize("S");
    }
}