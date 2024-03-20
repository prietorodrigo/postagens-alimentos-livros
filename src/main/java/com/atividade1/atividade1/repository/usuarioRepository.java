package com.atividade1.atividade1.repository;

import com.atividade1.atividade1.model.usuarioModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface usuarioRepository extends JpaRepository<usuarioModel, Long> {
    Optional<usuarioModel> findByEmail(String email);
    Optional<usuarioModel> findByCelular(String celular);
}
