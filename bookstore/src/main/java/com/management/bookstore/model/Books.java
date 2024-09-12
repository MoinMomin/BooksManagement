package com.management.bookstore.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "Books")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Books {
    @Id
    @Column(name = "ISBN")
    @JsonProperty("ISBN")
    private String ISBN;
    private String title;

    private String author;
private double price;
private int stockQuantity;
}
