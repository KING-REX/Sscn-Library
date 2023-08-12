package com.sscn.library.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor
@Data
@Entity
public class BookReturns {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @JoinColumn(referencedColumnName = "id", nullable = false)
    private Book bookIssued;

    @JoinColumn(referencedColumnName = "id", nullable = false)
    private BookIssuance bookIssuance;

    @Column(nullable = false)
    private LocalDate dateReturned;
}
