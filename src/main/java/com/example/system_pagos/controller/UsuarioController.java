package com.example.system_pagos.controller;

import com.example.system_pagos.model.Usuario;
import com.example.system_pagos.service.UsuarioService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

  private final UsuarioService usuarioService;

  public UsuarioController(UsuarioService usuarioService) {
    this.usuarioService = usuarioService;
  }

  @PostMapping
  public Usuario crearUsuario(@RequestBody Usuario usuario){
    return usuarioService.guardarUsuario(usuario);
  }

  @GetMapping
  public List<Usuario> obtenerUsuarios(){
    return usuarioService.obtenerTodosLosUsuarios();
  }

  @GetMapping("/{id}")
  public ResponseEntity<Usuario> Id(@PathVariable Long id){
    return ResponseEntity.ok(usuarioService.buscarPorId(id));
  }

}
