package com.example.system_pagos.dto;
import java.math.BigDecimal;

public record CrearPagoRequest(
  Long usuarioId,
  BigDecimal valor
){}
