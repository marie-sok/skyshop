package org.skypro.skyshop.exceptions;


public class BestResultNotFound extends Exception {

    private final String name;

    public BestResultNotFound(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "For Searching " + name + " is not found" + '}';
    }
}
