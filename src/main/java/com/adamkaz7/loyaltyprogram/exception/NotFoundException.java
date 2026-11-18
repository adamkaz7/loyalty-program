package com.adamkaz7.loyaltyprogram.exception;

import org.springframework.http.HttpStatus;

public class NotFoundException extends LoyaltyException {
    public NotFoundException(String message) {
        super(message, HttpStatus.NOT_FOUND);
    }
}
