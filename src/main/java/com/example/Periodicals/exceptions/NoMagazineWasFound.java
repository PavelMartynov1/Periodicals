package com.example.Periodicals.exceptions;

public class NoMagazineWasFound extends RuntimeException{
    private long id;

    public NoMagazineWasFound(String message, Throwable cause, long id) {
        super(message, cause);
        this.id = id;
    }
    public long getId(){
        return id;
    }
}
