package com.dev.backend.repository;

import com.dev.backend.model.Pessoa;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PessoaRepository extends JpaRepository<Pessoa, Long> {
    Pessoa findByEmail(String email);
    Pessoa findByEmailAndCodigoRecuperarSenha(String email, String codigoRecuperarSenha);

}
