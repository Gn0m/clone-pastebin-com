package com.example.clonepastebincom.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class Error {

    private int statusCode;
    private String message;


}
