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

export const getOrderByIdUser = async (idUser) => {
    try {
        const response = await httpClient.get(`/orden/cliente/${idUser}`);
        return response.data;
    }
    catch (error) {
        console.error("Error en la respuesta del servidor:", error.response.data);
        throw error;
    }
}

export const getOrderById = async (idOrden) => {
    try {
        const response = await httpClient.get(`/orden/${idOrden}`);
        return response.data;
    } catch (error) {
        console.error("Error en la respuesta del servidor:", error.response.data);
        throw error;
    }
}

export const calculateTotalOrden = async (idOrden) => {
    try {
        const response = await httpClient.put(`/orden/calcularTotalOrden/${idOrden}`);
        return response.data;
    } catch (error) {
        console.error("Error en la respuesta del servidor:", error.response.data);
        throw error;
    }

}

export const PayOrder = async (idOrden) => {
    try {
        const response = await httpClient.put(`/cliente/actualizarEstadoOrden/${idOrden}`);
        return response.data;
    } catch (error) {
        console.error("Error en la respuesta del servidor:", error.response.data);
        throw error;
    }
}