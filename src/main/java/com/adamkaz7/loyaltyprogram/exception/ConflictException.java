package com.adamkaz7.loyaltyprogram.exception;

import org.springframework.http.HttpStatus;

public class ConflictException extends LoyaltyException {
    public ConflictException(String message) {
        super(message, HttpStatus.CONFLICT);
    }
}
