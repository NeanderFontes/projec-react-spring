declare namespace Projeto {
    type Estados = {
        id: number; // O ID do estado não deve ser opcional, pois é a chave primária
        nome: string;
        sigla: string;
    };

    type Cidade = {
        id: number; // O ID da cidade não deve ser opcional, pois é a chave primária
        nome: string;
        estado: Estados; // Relacionamento com FK dos Estados
    };

    type Marca = {
        id: number;
        nome: string;
    }
}