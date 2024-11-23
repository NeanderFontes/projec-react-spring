package com.dev.backend.repository;

import com.dev.backend.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {
    Optional<Produto> findByUuid(UUID uuid);

    boolean existsByDescricaoCurtaAndCategoriaId(String trim, Long id);

    boolean existsByDescricaoCurtaAndMarcaId(String trim, Long id);

}
