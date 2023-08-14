package com.sscn.library.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
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
    private Integer id;

    @JsonIgnore
    @OneToOne
    @JoinColumn(referencedColumnName = "isbn", nullable = false)
    private Book bookIssued;

    @JsonProperty("bookIssued")
    @Transient
    private String bookIsbn;

    @JsonIgnore
    @OneToOne
    @JoinColumn(referencedColumnName = "id", nullable = false)
    private BookIssuance bookIssuance;

    @JsonProperty("bookIssuance")
    @Transient
    private Integer bookIssuanceId;

    @Column(nullable = false)
    private LocalDate dateReturned;

    public void fillInBookIsbn() {
        this.setBookIsbn(this.getBookIssued().getIsbn());
    }

    public void fillInBookIssuanceId() {
        this.setBookIssuanceId(this.getBookIssuance().getId());
    }

    @JsonProperty("bookIssued")
    public String getBookIssuedIsbn() {
        this.fillInBookIsbn();
        return this.getBookIsbn();
    }

    @JsonProperty("bookIssuance")
    public Integer getReturnsBookIssuanceId() {
        this.fillInBookIssuanceId();
        return this.getBookIssuanceId();
    }
}
