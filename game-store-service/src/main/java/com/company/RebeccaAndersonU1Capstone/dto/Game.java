package com.company.RebeccaAndersonU1Capstone.dto;

import java.math.BigDecimal;
import java.util.Objects;

public class Game extends Item{
    private int id;
    private String title;
    private String esrbRating;
    private String description;
    private String studio;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Game game = (Game) o;
        return getId() == game.getId() &&
                getQuantity() == game.getQuantity() &&
                Objects.equals(getTitle(), game.getTitle()) &&
                Objects.equals(getEsrbRating(), game.getEsrbRating()) &&
                Objects.equals(getDescription(), game.getDescription()) &&
                Objects.equals(getPrice(), game.getPrice()) &&
                Objects.equals(getStudio(), game.getStudio());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getTitle(), getEsrbRating(), getDescription(), getPrice(), getStudio(), getQuantity());
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getEsrbRating() {
        return esrbRating;
    }

    public void setEsrbRating(String esrbRating) {
        this.esrbRating = esrbRating;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public String getStudio() {
        return studio;
    }

    public void setStudio(String studio) {
        this.studio = studio;
    }

}
