package com.management.bookstore.mapper.jwt;

import lombok.Data;

import java.io.Serializable;
@Data
public class JwtResponseMapper implements Serializable {

    private static final long serialVersionUID = -8091879091924046844L;
    private final String jwtToken;

}