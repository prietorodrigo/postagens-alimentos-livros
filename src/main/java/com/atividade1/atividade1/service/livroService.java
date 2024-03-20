package com.atividade1.atividade1.service;

import com.atividade1.atividade1.model.livroModel;

import java.util.List;

public interface livroService {
    List<livroModel> findAll();
    livroModel findById(long id);
    livroModel save(livroModel livroModel);
}
