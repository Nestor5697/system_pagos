package com.example.system_pagos.dto;

import java.math.BigDecimal;

public record PagoResponse(
        Long id,
        BigDecimal valor,
        String estado,
        String transaccionId,
        Long usuario
) {
}
