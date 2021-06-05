package com.example.Periodicals.model.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class User {

    private String firstName;
    private String lastName;
    private long id;
    private String email;
    private Gender gender;
    private Role role;
    private String password;
    private BigDecimal balance;
    private Status status;
    private List<Subscription> subs=new ArrayList<>();
    LocalDate singInDate;

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public List<Subscription> getSubs() {
        return subs;
    }

    public void setSubs(List<Subscription> subs) {
        this.subs = subs;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public static class Builder {
        private User instance = new User();

        public Builder setStatus(Status status) {
            instance.status = status;
            return this;
        }
        public Builder setBalance(BigDecimal balance) {
            instance.balance = balance;
            return this;
        }
        public Builder setId(int id) {
            instance.id = id;
            return this;
        }
        public Builder setSingInDate(LocalDate date){

            instance.singInDate=date;
            return this;
        }
        public Builder setFirstName(String name){
            instance.firstName=name;
            return this;
        }
        public Builder setLastName(String name){
            instance.lastName=name;
            return this;
        }
        public Builder setGender(Gender gender){
            instance.gender=gender;
            return this;
        }

        public Builder setEmail(String email) {
            instance.email = email;
            return this;
        }

        public Builder setPassword(String password) {
            instance.password = password;
            return this;
        }

        public Builder setRole(Role role) {
            instance.role = role;
            return this;
        }

        public User build() {
            return instance;
        }
    }
    public void setSingInDate(LocalDate date) {
        this.singInDate = date;
    }
    public LocalDate getSingInDate() {
        return this.singInDate;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
    public int getRoleId(){
        return role.getId();
    }
    public void setRoleId(int i){
         role.setId(i);
    }
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "User{" +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", id=" + id +
                ", email='" + email + '\'' +
                ", gender=" + gender +
                ", role=" + role +
                ", password='" + password + '\'' +
                '}';
    }
}
