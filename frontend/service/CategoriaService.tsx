import axios from "axios";

export const axiosInstance = axios.create({
    baseURL: "http://localhost:8080/api/categoria"
});

export class CategoriaService {

    listAll() {
        return axiosInstance.get("/");
    }

    criarCategoria(categoria : Projeto.Marca) {
        return axiosInstance.post("/", categoria);
    }

    atualizarCategoria(categoria : Projeto.Marca) {
        return axiosInstance.put("/", categoria);
    }

    eliminarCategoria(id : number) {
        return axiosInstance.delete("/" + id);
    }
}