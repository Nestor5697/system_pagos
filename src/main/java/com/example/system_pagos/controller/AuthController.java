package com.example.system_pagos.controller;

import java.util.Optional;
import com.example.system_pagos.dto.LoginRequest;
import com.example.system_pagos.model.Usuario;
import com.example.system_pagos.repository.UsuarioRepository;
import com.example.system_pagos.service.JwtService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

  private final UsuarioRepository usuarioRepository;
  private final JwtService jwtService;

  public AuthController(UsuarioRepository usuarioRepository, JwtService jwtService) {
    this.usuarioRepository = usuarioRepository;
    this.jwtService = jwtService;
  }

  @PostMapping("/login")
  public ResponseEntity<?> login(@RequestBody LoginRequest request) {
    // 1. Buscamos el usuario por email
    Usuario usuario = usuarioRepository.findByEmail(request.email())
            .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

    // 2. Validamos la contraseña (simulada)
    if (!usuario.getPassword().equals(request.password())) {
      return ResponseEntity.status(401).body("Contraseña incorrecta");
    }

    // 3. Generamos el Token JWT usando nuestro JwtService
    String token = jwtService.generarToken(usuario.getEmail());

    // 4. Retornamos el Token al cliente (Postman)
    return ResponseEntity.ok(Map.of("token", token));
  }
}