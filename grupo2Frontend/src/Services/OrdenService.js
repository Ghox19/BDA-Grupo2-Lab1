import httpClient from "../http-common";

export const getOrderIdCliente = async (idCliente) => {
    try {
        const response = await httpClient.get(`/orden/ordencliente/${idCliente}`);
        return response.data;
    } catch (error) {
        console.error("Error en la respuesta del servidor:", error.response.data);
        throw error;
    }
}

export const CreateOrder = async (order) => {
    try {
        const response = await httpClient.post("/orden", order);
        return response.data;
    } catch (error) {
        console.error("Error en la respuesta del servidor:", error.response.data);
        throw error;
    }
}