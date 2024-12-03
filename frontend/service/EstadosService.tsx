import axios from "axios";

export const axiosInstance = axios.create({
    baseURL: "http://localhost:8080/api/estado"
})

export class EstadosService { 

    listAll() {
        return axiosInstance.get("/"); 
    }

    criar(estado : Projeto.Estados) {
        return axiosInstance.post("/", estado);
    }

    atulizar(estado : Projeto.Estados) {
        return axiosInstance.put("/", estado);
    }

    eliminar(id : number) {
        return axiosInstance.delete("/" + id);
    }
}