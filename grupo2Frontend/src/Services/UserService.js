import httpClient from "../http-common";

export const createClient = async (user) => {
    try{
        const response = await httpClient.post("auth/register", user);  
        return {data: response.data, status: response.status};

    } catch (error) {
        if(error.response){
            console.log("error en respuesta del servidor: ", error.response.data);
            return {data: error.response.data, status: error.response.status};
        } else if(error.request){
            console.log("no se recibe respuesta del servidor: ", error.request);
        } else {
            console.log("Error al enviar la petición: ", error.message);
        }
    }
}

export const Login = async (userData) => {
    try {
      const response = await httpClient.post("auth/login", userData);
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

export const logout = async () => {
    try {
      const response = await httpClient.post("auth/logout");
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

export const getuser = async (userData) => {
    try {
      const response = await httpClient.get(`/cliente/${userData}`);
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