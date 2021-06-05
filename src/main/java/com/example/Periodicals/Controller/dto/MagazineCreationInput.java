package com.example.Periodicals.Controller.dto;

import com.example.Periodicals.model.entity.Category;

import java.math.BigDecimal;

public class MagazineCreationInput {
    private long id;
    private String name;
    private String description;
    private String price;
    private  Category category;
    private String publisher;

    @Override
    public String toString() {
        return "MagazineCreationInput{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", category=" + category +
                ", publisher='" + publisher + '\'' +
                '}';
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
    public static  class Builder{

        private MagazineCreationInput instance=new MagazineCreationInput();
        public Builder setPublisher(String name){
            instance.publisher=name;
            return this;
        }
        public Builder setName(String name){
            instance.name=name;
            return this;
        }
        public Builder setId(long id){
            instance.id=id;
            return this;
        }
        public Builder setDescription(String description){
            instance.description=description;
            return this;
        }
        public Builder setPrice(String price){
            instance.price=price;
            return this;
        }
        public Builder setCategory(Category category){
            instance.category=category;
            return this;
        }
        public MagazineCreationInput build(){
            return instance;
        }
    }
}
