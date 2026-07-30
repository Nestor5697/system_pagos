package com.example.system_pagos.dto;

public record WompiTransactionRequest(
        Long amount_in_cents,
        String currency,
        String payment_method_type
) {
}
