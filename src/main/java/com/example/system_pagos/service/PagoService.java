package com.example.system_pagos.service;

import com.example.system_pagos.dto.CrearPagoRequest;
import com.example.system_pagos.dto.PagoResponse;
import com.example.system_pagos.model.Pago;
import com.example.system_pagos.model.Usuario;
import com.example.system_pagos.repository.PagoRepository;
import com.example.system_pagos.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PagoService {

  private final PagoRepository pagoRepository;
  private final UsuarioRepository usuarioRepository;
  private final WompiService wompiService; // <--- INYECTAMOS WOMPI SERVICE

  public PagoService(PagoRepository pagoRepository,
                     UsuarioRepository usuarioRepository,
                     WompiService wompiService) {
    this.pagoRepository = pagoRepository;
    this.usuarioRepository = usuarioRepository;
    this.wompiService = wompiService;
  }

  public PagoResponse crearPagoSimulado(CrearPagoRequest request) {
    // 1. Validar que el usuario exista
    Usuario usuario = usuarioRepository.findById(request.usuarioId())
            .orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + request.usuarioId()));

    // 2. Llamada real a la API Sandbox de Wompi mediante HttpClient
    String respuestaWompi = wompiService.procesarPagoEnWompi(request.valor());

    // 3. Creamos la entidad Pago con el valor y la respuesta de Wompi
    Pago pago = new Pago();
    pago.setUsuario(usuario);
    pago.setValor(request.valor().doubleValue());
    pago.setEstado("PENDIENTE");
    // Asignamos una muestra o la respuesta directa recibida por Wompi
    pago.setWompiTransactionId("WOMPI-RESP-OK");

    // 4. Guardamos en PostgreSQL
    Pago pagoGuardado = pagoRepository.save(pago);

    // 5. Devolvemos el DTO
    return new PagoResponse(
            pagoGuardado.getId(),
            request.valor(),
            pagoGuardado.getEstado(),
            pagoGuardado.getWompiTransactionId(),
            pagoGuardado.getUsuario().getId()
    );
  }

  public List<PagoResponse> obtenerTodosLosPagos() {
    return pagoRepository.findAll().stream()
            .map(pago -> new PagoResponse(
                    pago.getId(),
                    java.math.BigDecimal.valueOf(pago.getValor()),
                    pago.getEstado(),
                    pago.getWompiTransactionId(),
                    pago.getUsuario().getId()
            ))
            .toList();
  }
}