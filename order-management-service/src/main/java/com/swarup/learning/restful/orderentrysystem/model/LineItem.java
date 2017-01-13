package com.swarup.learning.restful.orderentrysystem.model;

import javax.persistence.*;

/**
 * Created by swaroop on 10/01/17.
 */
@Entity
@SequenceGenerator(name = "lineItemSequence")
public class LineItem {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "lineItemSequence")
    private int id;
    private int quantity;
    private Product product;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
