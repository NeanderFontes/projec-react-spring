import axios from "axios";

export const axiosInstance = axios.create({
    baseURL: "http://localhost:8080/api/marca"
});

export class MarcaService {

    listAll() {
        return axiosInstance.get("/");
    }

    criarMarca(marca : Projeto.Marca) {
        return axiosInstance.post("/", marca);
    }

    atualizarMarca(marca : Projeto.Marca) {
        return axiosInstance.put("/", marca);
    }

    eliminarMarca(id : number) {
        return axiosInstance.delete("/" + id);
    }
}