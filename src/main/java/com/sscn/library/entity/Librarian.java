package com.sscn.library.entity;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import java.io.Serializable;

@NoArgsConstructor
@Data
@Entity
public class Librarian implements Serializable, Cloneable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false, unique = true)
    @Email(message = "Email is invalid!", regexp = "[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,3}", flags = Pattern.Flag.CASE_INSENSITIVE)
    @NotNull(message = "Email Field is Mandatory")
    private String email;

    @NotNull(message = "Password is mandatory!")
    @Length(min = 6, max = 25, message = "Password must be between 6 and 25 characters")
    @Valid()
    @Column(nullable = false)
    private String password;

    @Override
    public Librarian clone() throws CloneNotSupportedException {
        return (Librarian) super.clone();
    }
}
