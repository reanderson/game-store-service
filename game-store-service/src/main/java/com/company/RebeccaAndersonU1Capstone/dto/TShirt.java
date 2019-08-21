package com.company.RebeccaAndersonU1Capstone.dto;

import java.math.BigDecimal;
import java.util.Objects;

public class TShirt extends Item {
    private int id;
    private String size;
    private String color;
    private String description;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TShirt tShirt = (TShirt) o;
        return getId() == tShirt.getId() &&
                getQuantity() == tShirt.getQuantity() &&
                Objects.equals(getSize(), tShirt.getSize()) &&
                Objects.equals(getColor(), tShirt.getColor()) &&
                Objects.equals(getDescription(), tShirt.getDescription()) &&
                Objects.equals(getPrice(), tShirt.getPrice());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getSize(), getColor(), getDescription(), getPrice(), getQuantity());
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
