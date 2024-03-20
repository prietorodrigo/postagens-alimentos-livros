package com.atividade1.atividade1.service;

import com.atividade1.atividade1.model.postagemModel;

import java.util.List;

public interface postagemService {
    List<postagemModel> findAll();
    postagemModel findById(long id);
    postagemModel save(postagemModel postagemModel);
}
