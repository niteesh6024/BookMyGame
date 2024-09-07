import axios from "axios";

export const apiClient = axios.create(
    {
        baseURL:"http://localhost:8080" ,
        Origin: "http://localhost:3000"
    }
)