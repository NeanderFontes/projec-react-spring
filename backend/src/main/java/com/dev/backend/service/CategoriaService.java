package com.dev.backend.service;

import com.dev.backend.DTO.requestDTO.CategoriaRequestDTO;
import com.dev.backend.model.Categoria;
import com.dev.backend.repository.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class CategoriaService {

    @Autowired
    private CategoriaRepository categoriaRepository;

    public List<Categoria> listAll() {

        return categoriaRepository.findAll();
    }

    public Categoria criarCategoria(CategoriaRequestDTO criarCategoriaDTO) {
        // Instanciar novo objeto Categoria para converter o DTO para Model
        Categoria criarCategoriaModel = new CategoriaRequestDTO().converterCategoriDTOParaModel(criarCategoriaDTO);

        // Validação para verificar se o tipo de categoria já existe
        validacaoPorNomeCategoria(criarCategoriaModel);

        return categoriaRepository.saveAndFlush(criarCategoriaModel);
    }

    public Categoria atualizarCategoria(CategoriaRequestDTO atualizarCategoriaDTO) {
        // Instanciar novo objeto Categoria para converter o DTO para Model
        Categoria atualizarCategoriaModel = new CategoriaRequestDTO().converterCategoriDTOParaModel(atualizarCategoriaDTO);

        // Validar objeto Categoria para atualizar
        validarCategoriaParaAtt(atualizarCategoriaModel);

        return categoriaRepository.save(atualizarCategoriaModel);
    }

    public void eliminarCategoria(Long eliminarCategoriaPorId) {
        Categoria eliminarCategoriaDB = categoriaRepository.findById(eliminarCategoriaPorId)
                .orElseThrow(() -> new IllegalArgumentException("Parâmetro de "
                        + eliminarCategoriaPorId + " inválido para atualizar da Categoria."));
        categoriaRepository.deleteById(eliminarCategoriaDB.getId());
    }

    private Categoria validacaoPorNomeCategoria(Categoria categoriaValidacao) {
        // tirar os espaços da Categoria para inserir em DB
        categoriaValidacao.getNome().trim();

        // Verificação em DB através de boolean para verificar se nome da categoria ja existe
        boolean categoriaValidacaoExist = categoriaRepository.findByNome(categoriaValidacao);
        if (categoriaValidacaoExist) {
            throw new IllegalArgumentException("Categoria " + categoriaValidacao.getNome()
                    + " já existe!");
        }

        // Criar nova data para a nova Categoria inserida
        categoriaValidacao.setDataCriacao(new Date());

        return categoriaValidacao;
    }

    private Categoria validarCategoriaParaAtt(Categoria validarCategoriaId) {
        // Nova instancia da Categoria para validar através do ID
        Categoria idCategoriaDB = categoriaRepository.findById(validarCategoriaId.getId())
                .orElseThrow(() -> new IllegalArgumentException("Parâmetro de "
                        + validarCategoriaId.getId() + " inválido para atualizar da Categoria."));

        // Manter dados de existentes do objeto já existente
        idCategoriaDB.setDataCriacao(idCategoriaDB.getDataCriacao());
        idCategoriaDB.setDataAtualizacao(new Date());
        idCategoriaDB.setNome(validarCategoriaId.getNome());

        // Duvida se retorno o obeto do DB ou Att
        return idCategoriaDB;
    }
}
