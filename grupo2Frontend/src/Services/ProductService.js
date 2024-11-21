import httpClient from "../http-common";

export const getProducts = async () => {
    try {
        const response = await httpClient.get("/public/prod");
        return response.data;
    } catch (error) {
        console.error("Error en la respuesta del servidor:", error.response.data);
        throw error;
    }
}