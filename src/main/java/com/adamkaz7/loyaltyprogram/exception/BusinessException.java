package com.adamkaz7.loyaltyprogram.exception;

import org.springframework.http.HttpStatus;

public class BusinessException extends LoyaltyException {
    public BusinessException(String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }
}
