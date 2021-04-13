package ru.otus.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "account")
public class Account implements Unique<String>{

    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "type")
    private String type;

    @Column(name = "rest")
    private double rest;

    public Account() {
    }

    public Account(String id, String type, double rest) {
        this.id = id;
        this.type = type;
        this.rest = rest;
    }

    @Override
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getRest() {
        return rest;
    }

    public void setRest(double rest) {
        this.rest = rest;
    }

    @Override
    public String toString() {
        return "Account{" +
                       "id='" + id + '\'' +
                       ", type='" + type + '\'' +
                       ", rest=" + rest +
                       '}';
    }
}
