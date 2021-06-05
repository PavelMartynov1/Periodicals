package com.example.Periodicals.model.service;

public class ServiceFactory {
    public static  UserService getUserService(){
        return new UserService();
    }
    public static MagazineService getMagazineService(){
        return new MagazineService();
    }
    public static CategoryService getCategoryService(){
        return new CategoryService();
    }
}
