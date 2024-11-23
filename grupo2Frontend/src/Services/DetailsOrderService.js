import httpClient from "../http-common";

export const getDetailsOrderbyOrder = async (idOrden) => {
    try {
        const response = await httpClient.get(`/detalleOrden/orden/${idOrden}`);
        return response.data;
    } catch (error) {
        console.error("Error en la respuesta del servidor:", error.response.data);
        throw error;
    }
}