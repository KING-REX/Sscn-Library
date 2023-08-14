package com.sscn.library.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.io.Serializable;
import java.time.LocalDate;
@NoArgsConstructor
@Data
@Entity
public class BookIssuance implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(referencedColumnName = "isbn", nullable = false, name = "book")
    private Book book;

    @JsonProperty("book")
    @Transient
    private String bookIsbn;

    @Column(nullable = false)
//    @CreationTimestamp
    private LocalDate dateIssued;

    @Column(nullable = false)
    private LocalDate dateDue;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(nullable = false, referencedColumnName = "id")
    private Member issuedTo;

    @JsonProperty("issuedTo")
    @Transient
    private Integer memberId;

    @Column(nullable = false)
    private ReturnStatus returnStatus;

    public void fillInBookIsbn() {
        this.setBookIsbn(this.getBook().getIsbn());
    }

    public void fillInMemberId() {
        this.setMemberId(this.getIssuedTo().getId());
    }

    @JsonProperty("book")
    public String getIssuedBookIsbn() {
        this.fillInBookIsbn();
        return this.getBookIsbn();
    }

    @JsonProperty("issuedTo")
    public Integer getIssuedMemberId() {
        this.fillInMemberId();
        return this.getMemberId();
    }
}
