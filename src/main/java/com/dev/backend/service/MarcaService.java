package com.dev.backend.service;

import com.dev.backend.DTO.requestDTO.MarcaRequestDTO;
import com.dev.backend.model.Marca;
import com.dev.backend.repository.MarcaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class MarcaService {
    @Autowired
    private MarcaRepository marcaRepository;

    public List<Marca> listAll() {
        return marcaRepository.findAll();
    }

    public Marca criarMarca(MarcaRequestDTO marcaRequestDTO) {
        // Instanciar um novo Objeto Marca para converter DTO em Model
        Marca criarModelMarca = new MarcaRequestDTO().converterCategoriDTOParaModel(marcaRequestDTO);

        // Verificar se o tipo de Marca já existe
        validarMarcaPorNome(criarModelMarca);

        return marcaRepository.saveAndFlush(criarModelMarca);
    }

    public Marca atualizarMarca(MarcaRequestDTO marcaRequestDTO) {
        // Instanciar novo Objeto Marca para converter DTO em Model
        Marca atualizarModelMarca = new MarcaRequestDTO().converterCategoriDTOParaModel(marcaRequestDTO);

        // Verificar se já existe Marca através do ID para Atualizar
        validarMarcaPorIdParaAtt(atualizarModelMarca);

        return marcaRepository.saveAndFlush(atualizarModelMarca);
    }

    public void eliminarMarca(Long eliminarMarcaPorId) {
        Marca eliminarModel = marcaRepository.findById(eliminarMarcaPorId)
                .orElseThrow(() -> new IllegalArgumentException("Parâmetro de "
                        + eliminarMarcaPorId + " inválido para atualizar da Categoria."));
        marcaRepository.deleteById(eliminarModel.getId());
    }

    private Marca validarMarcaPorNome(Marca validarDadoMarca) {
        // tirar os espaços para criar em DB
        validarDadoMarca.getNome().trim();

        // Verificar em DB por boolean para indentificar sem nome já existe
        boolean nomeMarcaExist = marcaRepository.findByNome(validarDadoMarca.getNome());
        if (nomeMarcaExist) {
            throw new IllegalArgumentException("Marca " + validarDadoMarca.getNome()
                    + " já existe!");
        }
        validarDadoMarca.setDataCriacao(new Date());
        return validarDadoMarca;
    }

    private Marca validarMarcaPorIdParaAtt(Marca atualizarModelMarcaPorID) {
        // Instanciar novo Obejto para verificar se á existe por ID e att dados
        Marca idMarcaModelEmDB = marcaRepository.findById(atualizarModelMarcaPorID.getId())
                .orElseThrow(() -> new IllegalArgumentException("Parâmetro de "
                        + atualizarModelMarcaPorID.getId() + " inválido para atualizar Marca."));

        // Caso ID seja válido, alterar os dados em DB e retornar para salvar
        idMarcaModelEmDB.setNome(atualizarModelMarcaPorID.getNome());
        idMarcaModelEmDB.setDataAtualizacao(new Date());

        return idMarcaModelEmDB;
    }
}
