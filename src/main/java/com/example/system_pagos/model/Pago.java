package com.example.system_pagos.model;


import jakarta.persistence.*;

@Entity
@Table(name = "pagos")
public class Pago {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private Double valor;
  private String estado;
  private String wompiTransactionId;

  @ManyToOne
  @JoinColumn(name = "usuario_id", nullable = false)
  private Usuario usuario;

  public Pago() {
  }

  public Pago(Double valor, String estado, String wompiTransactionId, Usuario usuario) {
    this.valor = valor;
    this.estado = estado;
    this.wompiTransactionId = wompiTransactionId;
    this.usuario = usuario;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Double getValor() {
    return valor;
  }

  public void setValor(Double valor) {
    this.valor = valor;
  }

  public String getEstado() {
    return estado;
  }

  public void setEstado(String estado) {
    this.estado = estado;
  }

  public String getWompiTransactionId() {
    return wompiTransactionId;
  }

  public void setWompiTransactionId(String wompiTransactionId) {
    this.wompiTransactionId = wompiTransactionId;
  }

  public Usuario getUsuario() {
    return usuario;
  }

  public void setUsuario(Usuario usuario) {
    this.usuario = usuario;
  }
}
