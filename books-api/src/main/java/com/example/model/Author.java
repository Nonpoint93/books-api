package com.example.model;

/**
 * @author Jesús Jiménez Ocaña
 */
public class Author {
    private String name;
    private String firstSurname;
    private String bio;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFirstSurname() {
        return firstSurname;
    }

    public void setFirstSurname(String firstSurname) {
        this.firstSurname = firstSurname;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }
}
