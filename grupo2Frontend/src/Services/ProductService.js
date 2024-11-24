import httpClient from "../http-common";

export const getProducts = async (id) => {
    try {
        const response = await httpClient.get(`/public/prod?page=${id}`);
        return response.data;
    } catch (error) {
        console.error("Error en la respuesta del servidor:", error.response.data);
        throw error;
    }
}

export const getProductById = async (productId) => {
    try {
        const response = await httpClient.get(`/public/prod/${productId}`);
        return { data: response.data, status: response.status };
    } catch (error) {
        if (error.response) {
            console.error("Error en la respuesta del servidor:", error.response.data);
            return { data: error.response.data, status: error.response.status };
        } else if (error.request) {
            console.error("No se recibió respuesta del servidor:", error.request);
        } else {
            console.error("Error al hacer la solicitud:", error.message);
        }
        throw error;
    }
}