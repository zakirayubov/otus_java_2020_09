package ru.otus.model;

import javax.persistence.*;

@Entity
@Table(name = "phones")
public class PhoneDataSet implements Unique<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "number")
    private String number;

    @ManyToOne
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;

    public PhoneDataSet() {
    }

    public PhoneDataSet(String number, Client client) {
        this.number = number;
        this.client = client;
    }

    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String street) {
        this.number = street;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    @Override
    public String toString() {
        return "PhoneDataSet{" +
                       "id=" + id +
                       ", number='" + number + '\'' +
                       '}';
    }
}
