import axios from "axios";

export const axiosInstance = axios.create({
    baseURL: "http://localhost:8080/api/cidade"
})

export class CidadeService {

    listAll() {
        return axiosInstance.get("/");
    }

    criar(cidade : Projeto.Cidade) {
        return axiosInstance.post("/", cidade);
    }

    atulizar(cidade : Projeto.Cidade) {
        return axiosInstance.put("/", cidade);
    }

    eliminar(id : number) {
        return axiosInstance.delete("/" + id);
    }

}