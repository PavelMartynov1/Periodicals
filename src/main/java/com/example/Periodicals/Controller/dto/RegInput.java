package com.example.Periodicals.Controller.dto;

import com.example.Periodicals.model.entity.Gender;

public class RegInput {
    private Gender gender;
    private String firstName;
    private String lastName;
    private String email;

    private String firstPassword;
    private String secondPassword;
    public Gender getGender() {
        return gender;
    }
    public void setGender(Gender gender) {
        this.gender = gender;
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



    public String getFirstPassword() {
        return firstPassword;
    }
    public void setFirstPassword(String firstPassword) {
        this.firstPassword = firstPassword;
    }
    public String getSecondPassword() {
        return secondPassword;
    }
    public void setSecondPassword(String secondPassword) {
        this.secondPassword = secondPassword;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public static class Builder{
        private RegInput instance = new RegInput();
        public Builder setGender(Gender gender) {
            instance.gender=gender;
            return this;
        }
        public Builder setFirstName(String firstName) {
            instance.firstName=firstName.trim();
            return this;
        }
        public Builder setLastName(String lastName) {
            instance.lastName=lastName.trim();
            return this;
        }
        public Builder setEmail(String email) {
            instance.email=email.trim();
            return this;
        }
        public Builder setFirstPassword(String firstPassword) {
            instance.firstPassword=firstPassword.trim();
            return this;
        }
        public Builder setSecondPassword(String secondPassword) {
            instance.secondPassword=secondPassword.trim();
            return this;
        }
        public RegInput build(){
            return instance;
        }
    }
}
