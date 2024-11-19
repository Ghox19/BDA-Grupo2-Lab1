import httpClient from "./http-common";

export const auth = async (to, from, next) => {
  try {
    const response = await httpClient.post("/auth/verify", {
      withCredentials: true,
    });

    if (response.status === 200) {
      console.log("Usuario autenticado");
      next();
    } else {
      console.log("No autenticado");
      next("/");
    }
  } catch (error) {
    console.log("Error de autenticación:", error);
    next("/");
  }
};