package com.atividade1.atividade1.repository;

import com.atividade1.atividade1.model.livroModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface livroRepository extends JpaRepository<livroModel, Long> {
}
