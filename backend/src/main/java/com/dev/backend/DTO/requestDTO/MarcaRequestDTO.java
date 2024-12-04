package com.dev.backend.DTO.requestDTO;

import com.dev.backend.model.Marca;
import lombok.Data;
import org.springframework.beans.BeanUtils;

@Data
public class MarcaRequestDTO {
    //TODO add validantion @NotBlank
    private Long id;
    private String nome;

    // Método para converter DTO em dados Model
    public Marca converterCategoriDTOParaModel(MarcaRequestDTO marcaRequestDTO) {
        Marca marcaModel = new Marca();
        BeanUtils.copyProperties(marcaRequestDTO, marcaModel);
        return marcaModel;
    }
}
