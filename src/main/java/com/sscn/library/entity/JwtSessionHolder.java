package com.sscn.library.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
//@Entity
public class JwtSessionHolder {

    @Column(nullable = false)
    private Integer jwtToken;

    @Column(nullable = false)
    private String secretKey;
}
