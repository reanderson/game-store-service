package com.company.RebeccaAndersonU1Capstone.service;

import com.company.RebeccaAndersonU1Capstone.dao.TShirtDao;
import com.company.RebeccaAndersonU1Capstone.dto.TShirt;
import com.company.RebeccaAndersonU1Capstone.exception.NotFoundException;
import com.company.RebeccaAndersonU1Capstone.viewModel.TShirtViewModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class TShirtService {

    private TShirtDao tShirtDao;

    @Autowired
    public TShirtService(TShirtDao tShirtDao) {
        this.tShirtDao = tShirtDao;
    }

    public TShirtViewModel saveTShirt(TShirtViewModel tvm) {
        TShirt shirt = buildTShirt(tvm);
        shirt = tShirtDao.addTShirt(shirt);
        tvm.setId(shirt.getId());
        return tvm;
    }

    public TShirtViewModel updateTShirt(TShirtViewModel tvm) {
        if(!checkIfExists(tvm.getId())) {
            throw new NotFoundException("No T-Shirt of id " + tvm.getId() + " exists to delete.");
        }
        TShirt shirt = buildTShirt(tvm);
        tShirtDao.updateTShirt(shirt);
        return tvm;
    }

    public TShirtViewModel getTShirtById(int id) {
        TShirt shirt = tShirtDao.getTShirt(id);
        if (shirt == null) {
            return null;
        }
        TShirtViewModel tvm = buildTVM(shirt);
        return tvm;
    }

    public List<TShirtViewModel> getAllTShirt() {
        List<TShirt> shirts = tShirtDao.getAllTShirts();
        List<TShirtViewModel> tvms = new ArrayList<>();

        for (TShirt shirt : shirts) {
            TShirtViewModel tvm = buildTVM(shirt);
            tvms.add(tvm);
        }

        return tvms;
    }

    public List<TShirtViewModel> getTShirtBySize(String size) {
        List<TShirt> shirts = tShirtDao.getTShirtsBySize(size);
        List<TShirtViewModel> tvms = new ArrayList<>();

        for (TShirt shirt : shirts) {
            TShirtViewModel tvm = buildTVM(shirt);
            tvms.add(tvm);
        }

        return tvms;
    }

    public List<TShirtViewModel> getTShirtByColor(String color) {
        List<TShirt> shirts = tShirtDao.getTShirtsByColor(color);
        List<TShirtViewModel> tvms = new ArrayList<>();

        for (TShirt shirt : shirts) {
            TShirtViewModel tvm = buildTVM(shirt);
            tvms.add(tvm);
        }

        return tvms;
    }

    public void removeTShirt(int id){
        if(!checkIfExists(id)) {
            throw new NotFoundException("No T-Shirt of id " + id + " exists to delete.");
        }
        tShirtDao.deleteTShirt(id);
    }

    // Helpers
    private TShirtViewModel buildTVM(TShirt shirt) {
        TShirtViewModel tvm = new TShirtViewModel();
        tvm.setId(shirt.getId());
        tvm.setColor(shirt.getColor());
        tvm.setDescription(shirt.getDescription());
        tvm.setPrice(shirt.getPrice());
        tvm.setQuantity(shirt.getQuantity());
        tvm.setSize(shirt.getSize());
        return tvm;
    }

    private TShirt buildTShirt(TShirtViewModel tvm) {
        TShirt shirt = new TShirt();
        shirt.setId(tvm.getId());
        shirt.setColor(tvm.getColor());
        shirt.setDescription(tvm.getDescription());
        shirt.setPrice(tvm.getPrice());
        shirt.setQuantity(tvm.getQuantity());
        shirt.setSize(tvm.getSize());
        return shirt;
    }

    private boolean checkIfExists(int id) {
        if (tShirtDao.getTShirt(id) != null) {
            return true;
        } else {
            return false;
        }
    }
}
