package com.example.Periodicals.model.entity;

import java.math.BigDecimal;

public class Magazine {
    private long id;
    private String name;
    private String description;
    private BigDecimal price;
    private  Category category;
    private String publisher;

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public static class Builder{
        private Magazine instance=new Magazine();
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
        public Builder setPrice(BigDecimal price){
            instance.price=price;
            return this;
        }
        public Builder setCategory(Category category){
            instance.category=category;
            return this;
        }
        public Magazine build(){
            return instance;
        }
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

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Magazine{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", category=" + category +
                ", publisher='" + publisher + '\'' +
                '}';
    }
}
