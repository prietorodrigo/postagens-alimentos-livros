package com.atividade1.atividade1.service;

import com.atividade1.atividade1.model.alimentoModel;
import java.util.List;

public interface alimentoService {
    List<alimentoModel> findAll();
    alimentoModel findById(long id);
    alimentoModel save(alimentoModel alimentoModel);
    List<alimentoModel> findAlimentosByCalorias(int calorias);
    List<alimentoModel> findAlimentosByNomeLike(String nome);
}
