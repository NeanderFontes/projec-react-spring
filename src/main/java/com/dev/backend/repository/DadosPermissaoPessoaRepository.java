package com.dev.backend.repository;

import com.dev.backend.model.Permissao;
import com.dev.backend.model.PermissaoPessoa;
import com.dev.backend.model.Pessoa;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DadosPermissaoPessoaRepository extends JpaRepository<PermissaoPessoa, Long> {
    boolean existsByPessoaAndPermissao(Pessoa pessoa, Permissao permissao);
}
