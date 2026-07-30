package com.example.system_pagos.service;


import com.example.system_pagos.model.Usuario;
import com.example.system_pagos.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UsuarioService {
  
  private final UsuarioRepository usuarioRepository;

  public UsuarioService(UsuarioRepository usuarioRepository) {
    this.usuarioRepository = usuarioRepository;
  }

  public Usuario guardarUsuario(Usuario usuario){
    if (usuarioRepository.existsByEmail(usuario.getEmail())){
      throw new RuntimeException("El correo ya se encuentra registrado");
    }
    return usuarioRepository.save(usuario);
  }

  public List<Usuario> obtenerTodosLosUsuarios(){
    return usuarioRepository.findAll();
  }

  public Usuario buscarPorId(Long id){
    return usuarioRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("usuario no encontrado con este id: "+ id));
  }

}
