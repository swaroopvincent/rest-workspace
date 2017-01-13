package com.swarup.learning.restful.orderentrysystem.model;

import javax.persistence.*;

/**
 * Created by swaroop on 10/01/17.
 */
@Entity
@SequenceGenerator(name = "productSequence")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "productSequence")
    private int id;
    private String name;
    private double cost;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }
}
