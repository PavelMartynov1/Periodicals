package com.example.Periodicals.model.entity;

import java.util.Objects;

public class Category {
    private int id;
    private String name;
    private String description;

    public Category(int id , String name){
        this.id=id;
        this.name=name;
    }

    public Category(){

    }
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Category{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Category category = (Category) o;
        return id == category.id && name.equals(category.name) && description.equals(category.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description);
    }
}
