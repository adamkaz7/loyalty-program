package com.adamkaz7.loyaltyprogram.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public abstract class LoyaltyException extends RuntimeException {
    private final HttpStatus status;

    public LoyaltyException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }
}
