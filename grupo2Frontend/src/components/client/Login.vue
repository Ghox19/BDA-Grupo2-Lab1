<template>
    <div class="fondo-login">
        <div class="contenedor-login">
                <div class="div-login-form">
                    <form @submit.prevent="login">
                        <h1>Inicia sesión</h1>
                        <div class="div-inputs-login">
                            <label>Email</label>
                            <input type="email" v-model="data.email" placeholder="Ej: email@gmail.com" required>
                            <label>Password</label>
                            <input type="password" v-model="data.clave" placeholder="Contraseña" required>
                            <p>¿No estas registrado? <router-link to="/register">Registrate</Router-link></p>
                        </div>
                        <div class="div-button-login">
                            <button type="submit">Iniciar sesión</button>
                        </div>
                    </form>
                </div>
        </div>  
    </div>
</template>

<script setup>
import { ref } from 'vue';
import { Login } from '../../Services/UserService';
import { getOrderIdCliente, CreateOrder } from '../../Services/OrdenService';
import { useStore } from 'vuex';
import { useRouter } from 'vue-router';

const store = useStore();
const router = useRouter();
const data = ref({ email: '', clave: '' });

const login = async () => {
    const response = await Login(data.value);
    console.log('Response:', response);
    if (response.status === 200) {
        store.commit('setUser', response.data);
        store.commit('login');

        const idCliente = response.data.id_user;

        const responseOrden = await getOrderIdCliente(idCliente);
        let orderId = responseOrden || null;

        if(!orderId){
            const data = {
                fecha_orden: new Date(),
                id_cliente: idCliente,
                estado: "en_proceso",
                total: 0
            }

            const newOrden = await CreateOrder(data);
            orderId = newOrden;
        }
        
        console.log('Orden:', orderId);
        store.commit('setOrder', orderId);

        alert('Sesión iniciada correctamente');
        router.push({ name: 'allproducts' , params: { id: 1 }});
    } else {
        alert('Error al iniciar sesión');
    }
};
</script>

<style scoped>
.fondo-login {
    display: flex;
    justify-content: center;
    align-items: center;
    height: 100%;
    width: 100%;
    background-image: url('../../assets/0bdf1db1939da634b85d6dccf27c35fd.jpg');
    background-size: cover;
    background-position: center;
}

.contenedor-login {
    display: flex;
    justify-content: center;
    align-items: center;
    height: 75%;
    width: 35%;
    background-color:white;
    border-radius: 1rem;
    border: solid 1px white;
    box-shadow: rgba(0, 0, 0, 0.35) 0px 5px 15px;
}

.div-login-form {
    height: 100%;
    width: 100%;
    right: 0;
    border-radius: 1rem;
    margin: 1rem;
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
    font-family: "Open Sans", sans-serif;
}

.div-login-form h1 {
    font-size: 2.5rem;
    font-weight: bold;
    margin-bottom: 1rem;
    height: 20%;
    width: 100%;
    display: flex;
    justify-content: center;
    align-items: center;
    color: #721B65;
}

.div-login-form form {
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
    width: 100%;
    height: 100%;
}


.div-button-login {
    display: flex;
    justify-content: center;
    align-items: center;
    width: 100%;
    height: 20%;
}

.div-button-login button {
    width: 85%;
    height: 3rem;
    padding: 1rem;
    background-color: #721B65;
    color: white;
    border-radius: 0.5rem;
    border: solid 2px #721B65;
    cursor: pointer;
    font-size: 1rem;
    font-weight: bold;
    justify-content: center;
    align-items: center;
    display: flex;
    transition: background-color 0.3s ease, color 0.3s ease;
}

.div-button-login button:hover {
    background-color: white;
    color: #721B65;
    border: solid 2px #721B65;
}

.div-inputs-login {
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
    color: #721B65;
    width: 100%;
    height: 50%;
}

.div-inputs-login input{
    width: 80%;
    height: 2rem;
    margin: 1rem;
    padding: 1rem;
    font-size: 1rem;
    background-color: white;
    color: rgb(45, 44, 44);
    border-radius: 0.5rem;
    border: solid 1px #721B65;
}

.div-inputs-login input:focus{
    outline: none;
    border: solid 1px #721B65;
    box-shadow: 0 0 2.5px #721B65;
}

.div-inputs-login label{
    font-size: 1rem;
    font-weight: bold;
    margin: 0.5rem;
}
</style>