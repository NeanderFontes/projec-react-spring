package com.dev.backend.repository;

import com.dev.backend.model.Cidade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CidadeRepository extends JpaRepository<Cidade, Long> {
    boolean existsByNome(String nome);
}
