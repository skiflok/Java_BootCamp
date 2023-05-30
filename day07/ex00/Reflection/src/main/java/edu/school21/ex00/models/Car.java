package edu.school21.ex00.models;


import java.math.BigDecimal;

public class Car {
    private String brand;
    private BigDecimal coast;
    private String color;
    private double weight;

    public Car () {
        brand = "Volga";
        coast = new BigDecimal("99.99");
        color = "red";
        weight = 1000.01;
    }

    public Car(String brand, BigDecimal coast, String color, double weight) {
        this.brand = brand;
        this.coast = coast;
        this.color = color;
        this.weight = weight;
    }

    public void bip () {
        System.out.println("bip");
    }

    private void start () {
        System.out.println("Ehay rjavor korito yje");
    }
}
