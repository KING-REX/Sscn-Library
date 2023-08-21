package com.sscn.library.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Builder
public class BookIssuance implements Serializable, Cloneable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(referencedColumnName = "isbn", nullable = false, name = "book_issued")
    private Book bookIssued;

    @JsonProperty("bookIssued")
    @Transient
    private String bookIsbn;

    @Column(nullable = false)
//    @CreationTimestamp
    private LocalDate dateIssued;

    @Column(nullable = false)
    private LocalDate dateDue;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(nullable = false, referencedColumnName = "id", name = "issued_to")
    private Member issuedTo;

    @JsonProperty("issuedTo")
    @Transient
    private Integer memberId;

    @Column(nullable = false)
    private Integer copiesIssued;

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    private BorrowStatus borrowStatus;

    //TODO: Edit this particular field. I made json ignore it because it'll end up printing the all the bookReturns in the array as objects, instead of using
    // their ids. When you've done the workaround(like how you did in Book for the authors attribute and how you did in BookIssuance for the book attribute),
    // you can then remove it.
    @JsonIgnore
    @OneToMany(mappedBy = "bookIssuance", cascade = CascadeType.ALL)
    private List<BookReturns> bookReturns;

    public void fillInBookIsbn() {
        this.setBookIsbn(this.getBookIssued().getIsbn());
    }

    public void fillInMemberId() {
        this.setMemberId(this.getIssuedTo().getId());
    }

    @JsonProperty("bookIssued")
    public String getIssuedBookIsbn() {
        this.fillInBookIsbn();
        return this.getBookIsbn();
    }

    @JsonProperty("issuedTo")
    public Integer getIssuedMemberId() {
        this.fillInMemberId();
        return this.getMemberId();
    }

    @Override
    public BookIssuance clone() throws CloneNotSupportedException {
        return (BookIssuance) super.clone();
    }
}
