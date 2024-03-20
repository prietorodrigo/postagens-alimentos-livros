package com.atividade1.atividade1.repository;

import com.atividade1.atividade1.model.alimentoModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface alimentoRepository extends JpaRepository<alimentoModel, Long> {
    List<alimentoModel> findAlimentosByCalorias(int calorias);
    List<alimentoModel> findAlimentosByNomeLike(String nome);
}
