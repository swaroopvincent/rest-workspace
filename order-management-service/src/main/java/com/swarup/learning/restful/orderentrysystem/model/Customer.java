package com.swarup.learning.restful.orderentrysystem.model;

import javax.persistence.*;

/**
 * Created by swaroop on 10/01/17.
 */
@Entity
@SequenceGenerator(name = "customSequence")
public class Customer {

    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "customSequence"
    )
    private int id;
    private String firstName;
    private String lastName;
    private String city;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
