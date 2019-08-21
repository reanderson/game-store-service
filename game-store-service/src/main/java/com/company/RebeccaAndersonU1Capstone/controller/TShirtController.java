package com.company.RebeccaAndersonU1Capstone.controller;

import com.company.RebeccaAndersonU1Capstone.service.TShirtService;
import com.company.RebeccaAndersonU1Capstone.viewModel.TShirtViewModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/tshirt")
public class TShirtController {
    @Autowired
    TShirtService tShirtService;

    // Create
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TShirtViewModel createTShirt(@RequestBody @Valid TShirtViewModel svm) {
        return tShirtService.saveTShirt(svm);
    }

    // Get One
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public TShirtViewModel getOneShirt(@PathVariable("id") int shirtId) {
        return tShirtService.getTShirtById(shirtId);
    }

    // Get all
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<TShirtViewModel> getAllShirt() {
        return tShirtService.getAllTShirt();
    }

    // Get by size
    @GetMapping("/size/{size}")
    @ResponseStatus(HttpStatus.OK)
    public List<TShirtViewModel> getBySize(@PathVariable("size") String size) {
        return tShirtService.getTShirtBySize(size);
    }

    // Get by color
    @GetMapping("/color/{color}")
    @ResponseStatus(HttpStatus.OK)
    public List<TShirtViewModel> getByColor(@PathVariable("color") String color) {
        return tShirtService.getTShirtByColor(color);
    }
    // Update
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public TShirtViewModel updateShirt(@PathVariable("id") int shirtId, @RequestBody @Valid TShirtViewModel svm) {
        if (svm.getId() == 0)
            svm.setId(shirtId);
        if (shirtId != svm.getId()) {
            throw new IllegalArgumentException("Console ID on path must match the ID in Console object");
        }
        return tShirtService.updateTShirt(svm);
    }

    // Delete
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteShirt(@PathVariable("id") int shirtId) {
        tShirtService.removeTShirt(shirtId);
    }
}
