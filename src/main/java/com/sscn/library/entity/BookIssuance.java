package com.sscn.library.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;
@NoArgsConstructor
@Data
@Entity
public class BookIssuance implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @JoinColumn(name = "BookId", nullable = false)
    private Book book;

    @Column(nullable = false)
    private LocalDate dateIssued;

    @Column(nullable = false)
    private LocalDate dateDue;

    @JoinColumn(name = "IssuedTo", nullable = false, referencedColumnName = "id")
    private Member issuedTo;

    @Column(nullable = false)
    private ReturnStatus returnStatus;
}
