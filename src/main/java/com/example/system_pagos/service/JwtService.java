package com.example.system_pagos.service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;

@Service
public class JwtService {

  // Clave secreta para firmar los tokens (mínimo 256 bits)
  private final Key SECRET_KEY = Keys.hmacShaKeyFor("clave_secreta_super_segura_sistema_pagos_123456".getBytes());

  // Genera un token JWT que expira en 10 minutos
  public String generarToken(String email) {
    long tiempoExpiracion = 10 * 60 * 1000; // 10 minutos en milisegundos

    return Jwts.builder()
            .setSubject(email)
            .setIssuedAt(new Date(System.currentTimeMillis()))
            .setExpiration(new Date(System.currentTimeMillis() + tiempoExpiracion))
            .signWith(SECRET_KEY, SignatureAlgorithm.HS256)
            .compact();
  }

  // Extrae el email del token y valida si la firma es auténtica
  public String obtenerEmailDelToken(String token) {
    return Jwts.parserBuilder()
            .setSigningKey(SECRET_KEY)
            .build()
            .parseClaimsJws(token)
            .getBody()
            .getSubject();
  }
}