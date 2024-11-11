package com.dev.backend.repository;

import com.dev.backend.model.ProdutoImagens;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProdutoImgRepository extends JpaRepository<ProdutoImagens, Long> {
    boolean existsByProduto_Id(Long idProduto);
}
