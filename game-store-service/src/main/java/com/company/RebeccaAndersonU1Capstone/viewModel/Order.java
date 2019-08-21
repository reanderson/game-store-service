package com.company.RebeccaAndersonU1Capstone.viewModel;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Objects;

public class Order {
    @NotEmpty(message="Please supply your name")
    private String name;
    @NotEmpty(message="Please supply your street address")
    private String street;
    @NotEmpty(message="Please supply your city")
    private String city;
    @NotEmpty(message ="Please supply your state")
    @Size(min=2, max=2, message = "State should be entered in two-character state code format")
    private String state;
    @NotEmpty(message = "Please supply your zipcode")
    @Size(min=5, max=5, message = "zipcode must be 5 numbers long")
    private String zipcode;
    @NotEmpty(message = "Please supply an item type")
    private String itemType;
    @NotNull(message = "Please supply an item ID")
    @Min(value = 0, message = "Item ID must be positive or 0")
    private int itemId;
    @NotNull(message = "Please supply a quantity to purchase")
    @Min(value = 1, message = "You must purchase at least 1 item")
    private int quantity;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return getItemId() == order.getItemId() &&
                getQuantity() == order.getQuantity() &&
                Objects.equals(getName(), order.getName()) &&
                Objects.equals(getStreet(), order.getStreet()) &&
                Objects.equals(getCity(), order.getCity()) &&
                Objects.equals(getState(), order.getState()) &&
                Objects.equals(getZipcode(), order.getZipcode()) &&
                Objects.equals(getItemType(), order.getItemType());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getStreet(), getCity(), getState(), getZipcode(), getItemType(), getItemId(), getQuantity());
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public String getItemType() {
        return itemType;
    }

    public void setItemType(String itemType) {
        this.itemType = itemType;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
