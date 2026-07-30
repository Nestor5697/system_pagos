package com.example.system_pagos.controller;


import com.example.system_pagos.dto.CrearPagoRequest;
import com.example.system_pagos.dto.PagoResponse;
import com.example.system_pagos.model.Pago;
import com.example.system_pagos.service.PagoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.system_pagos.service.JwtService;

import java.util.List;


@RestController
@RequestMapping("/api/v1/pagos")
public class PagoController {

  private final PagoService pagoService;
  private final JwtService jwtService;

  public PagoController(PagoService pagoService, JwtService jwtService) {
    this.pagoService = pagoService;
    this.jwtService = jwtService;
  }

  @PostMapping
  public ResponseEntity<PagoResponse> crearPago(@RequestHeader("Authorization") String bearerToken, @RequestBody CrearPagoRequest request) {
    String token = bearerToken.replace("Bearer ", "");
    String email = jwtService.obtenerEmailDelToken(token);
    System.out.println("Pago realizado por el usuario autenticado: " + email);
    PagoResponse respuesta = pagoService.crearPagoSimulado(request);
    return ResponseEntity.status(HttpStatus.CREATED).body(respuesta);
  }

  @GetMapping
  public ResponseEntity<List<PagoResponse>> obtenerPagos() {
    return ResponseEntity.ok(pagoService.obtenerTodosLosPagos());
  }
}
