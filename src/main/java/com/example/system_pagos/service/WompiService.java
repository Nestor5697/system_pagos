package com.example.system_pagos.service;

import com.example.system_pagos.dto.WompiTransactionRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Service
public class WompiService {

  private final HttpClient httpClient;
  private final ObjectMapper objectMapper;

  // Instanciamos los dos clientes directamente en el constructor
  public WompiService() {
    this.httpClient = HttpClient.newHttpClient();
    this.objectMapper = new ObjectMapper(); // <--- AQUÍ INSTANCIAMOS EL OBJECTMAPPER
  }

  public String procesarPagoEnWompi(BigDecimal valor) {
    try {
      String url = "https://sandbox.wompi.co/v1/transactions";

      long montoCentavos = valor.multiply(new BigDecimal("100")).longValue();

      WompiTransactionRequest wompiReq = new WompiTransactionRequest(
              montoCentavos,
              "COP",
              "CARD"
      );

      String jsonBody = objectMapper.writeValueAsString(wompiReq);

      HttpRequest request = HttpRequest.newBuilder()
              .uri(URI.create(url))
              .header("Content-Type", "application/json")
              .header("Authorization", "Bearer pub_test_Q5y14286283y4514286283y4514286")
              .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
              .build();

      HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

      return response.body();

    } catch (Exception e) {
      throw new RuntimeException("Error al comunicarse con Wompi: " + e.getMessage());
    }
  }
}