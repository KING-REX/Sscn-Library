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
public class BookReturns implements Cloneable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(referencedColumnName = "isbn", nullable = false, name = "book_returned")
    private Book bookReturned;

    @JsonProperty("bookReturned")
    @Transient
    private String bookIsbn;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(referencedColumnName = "id", nullable = false, name = "book_issuance")
    private BookIssuance bookIssuance;

    @JsonProperty("bookIssuance")
    @Transient
    private Integer bookIssuanceId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ReturnStatus returnStatus;

    @Column(nullable = false)
    private LocalDate dateReturned;

    public void fillInBookIsbn() {
        this.setBookIsbn(this.getBookReturned().getIsbn());
    }

    public void fillInBookIssuanceId() {
        this.setBookIssuanceId(this.getBookIssuance().getId());
    }

    @JsonProperty("bookReturned")
    public String getBookIssuedIsbn() {
        this.fillInBookIsbn();
        return this.getBookIsbn();
    }

    @JsonProperty("bookIssuance")
    public Integer getReturnsBookIssuanceId() {
        this.fillInBookIssuanceId();
        return this.getBookIssuanceId();
    }

    @Override
    public BookReturns clone() throws CloneNotSupportedException {
        return (BookReturns) super.clone();
    }

    //TODO: Make this class implement the Serializable interface. I just removed it to test whether jpa can do without serializing.
}
