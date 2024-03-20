package com.atividade1.atividade1.service;

import com.atividade1.atividade1.model.usuarioModel;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface usuarioService {
    public void saveUser(usuarioModel usuario);
    public List<Object> isUserPresent(usuarioModel usuario);
}
