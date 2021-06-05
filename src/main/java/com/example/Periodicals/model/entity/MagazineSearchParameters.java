package com.example.Periodicals.model.entity;

public class MagazineSearchParameters {
    private String name;
    private String category_id;
    private boolean price_asc;
    private boolean price_oder;
    private double rating;
    private int recordsPerPage;
    private int currentPage;
    public void setPrice_oder(boolean val){
        price_oder=val;
    }
    public String getPriceOrder(){
        if(price_asc){
            return "ASC";
        } else {
            return "DESC";
        }
    }
    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getRecordsPerPage() {
        return recordsPerPage;
    }

    public void setRecordsPerPage(int recordsPerPage) {
        this.recordsPerPage = recordsPerPage;
    }

    public boolean hasCategory() {
        return category_id != null;
    }

    public boolean hasWhere() {
        return hasName() || hasCategory();
    }

    public boolean hasPriceOrder() {
        return price_oder;
    }

    public boolean hasName() {
        return name != null;
    }

    public static class Builder {
        private MagazineSearchParameters instance = new MagazineSearchParameters();

        public Builder setName(String name) {
            instance.name = name;
            return this;
        }

        public Builder setCategory(String category) {
            instance.category_id = category;
            return this;
        }

        public Builder setPrice_asc(boolean value) {
            instance.price_asc = value;
            return this;
        }

        public Builder setRating(double rating) {
            instance.rating = rating;
            return this;
        }
        public Builder setRecordsPerPage(int num) {
            instance.recordsPerPage = num;
            return this;
        }

        public MagazineSearchParameters build() {
            return instance;
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category_id;
    }

    public void setCategory(String category) {
        this.category_id = category;
    }

    public boolean isPrice_asc() {
        return price_asc;
    }

    public void setPrice_asc(boolean price_asc) {
        this.price_asc = price_asc;
        price_oder=true;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    @Override
    public String toString() {
        return "MagazineSearchParameters{" +
                "name='" + name + '\'' +
                ", category_id='" + category_id + '\'' +
                ", price_asc=" + price_asc +
                ", price_oder=" + price_oder +
                ", rating=" + rating +
                ", recordsPerPage=" + recordsPerPage +
                ", currentPage=" + currentPage +
                '}';
    }
}
