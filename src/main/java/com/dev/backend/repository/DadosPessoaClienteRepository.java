package com.dev.backend.repository;

import com.dev.backend.model.Pessoa;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DadosPessoaClienteRepository extends JpaRepository<Pessoa, Long> {

}
