import httpClient from "../http-common";

export const getDetalleOrden = async (idOrden, idProducto) => {
    try {
        const response = await httpClient.get(`/detalleOrden/findByProductoAndOrden/${idProducto}/${idOrden}`);
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

export const createDetalleOrden = async (detalleOrden) => {
    try {
        const response = await httpClient.post("/detalleOrden", detalleOrden);
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

export const updateDetalleOrden = async (detalleOrden) => {
    try {
        const response = await httpClient.put("/detalleOrden", detalleOrden);
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