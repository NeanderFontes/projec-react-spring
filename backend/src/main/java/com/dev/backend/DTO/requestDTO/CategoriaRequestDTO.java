package com.dev.backend.DTO.requestDTO;

import com.dev.backend.model.Categoria;
import lombok.Data;
import org.springframework.beans.BeanUtils;

@Data
public class CategoriaRequestDTO {
    //TODO add validantion @NotBlank
    private Long id;
    private String nome;

    // Método para converter DTO em dados Model
    public Categoria converterCategoriDTOParaModel(CategoriaRequestDTO categoriaRequestDTO) {
        Categoria categoriaModel = new Categoria();
        BeanUtils.copyProperties(categoriaRequestDTO, categoriaModel);
        return categoriaModel;
    }
}
