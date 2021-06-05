package com.example.Periodicals.model.entity;

public enum Role {
    USER(1),
    ADMIN(2);
    private int id;
      Role(int i) {
        id=i;
    }
    int getId(){
        return id;
    }
    void setId(int i){
     id=i;
    }

}
