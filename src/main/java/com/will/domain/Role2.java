package com.will.domain;


import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Role2 {
    ADMIN2("ROLE_ADMIN2"),
    LAWYER("ROLE_LAWYER");

    private String value;
}


