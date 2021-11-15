package com.homework.exception;

import lombok.Data;

@Data
public class BusinessCode extends RuntimeException{
    private static final long serialVersionUID = 1L;
    private String code;
    private String message;
    
    public BusinessCode(String code){
        this.code = code;
    }
}
