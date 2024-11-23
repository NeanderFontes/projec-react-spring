package com.dev.backend.repository;

import com.dev.backend.model.Permissao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PermissaoRepository extends JpaRepository<Permissao, Long> {
    List<Permissao> findByNome(String nome);
}
