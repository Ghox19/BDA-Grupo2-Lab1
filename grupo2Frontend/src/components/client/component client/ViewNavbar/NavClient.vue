<template>
    <div class="contentNavClient">
        <button class="btn-cart" @click="ViewOrder()">
            <svg xmlns="http://www.w3.org/2000/svg" height="24px" viewBox="0 -960 960 960" width="24px" fill="#4944b8"><path d="M320-280q17 0 28.5-11.5T360-320q0-17-11.5-28.5T320-360q-17 0-28.5 11.5T280-320q0 17 11.5 28.5T320-280Zm0-160q17 0 28.5-11.5T360-480q0-17-11.5-28.5T320-520q-17 0-28.5 11.5T280-480q0 17 11.5 28.5T320-440Zm0-160q17 0 28.5-11.5T360-640q0-17-11.5-28.5T320-680q-17 0-28.5 11.5T280-640q0 17 11.5 28.5T320-600Zm120 320h240v-80H440v80Zm0-160h240v-80H440v80Zm0-160h240v-80H440v80ZM200-120q-33 0-56.5-23.5T120-200v-560q0-33 23.5-56.5T200-840h560q33 0 56.5 23.5T840-760v560q0 33-23.5 56.5T760-120H200Zm0-80h560v-560H200v560Zm0-560v560-560Z"/></svg>
            Ordenes
        </button>
        <button class="btn-logout" @click="logoutUser">
            Cerrar Sesión
        </button>
    </div>
</template>

<script setup>
import { logout } from '../../../../Services/UserService';
import { useRouter } from 'vue-router';
import { useStore } from 'vuex';

const store = useStore();
const route = useRouter();

const logoutUser = async () => {
    try{
        const response = logout();
        console.log('Response:', response);

        store.commit('logout');
        store.commit('clearUser');
        store.commit('clearOrder');
        alert('Sesión cerrada exitosamente');
        window.location.reload();
    }catch(error){
        alert('Error al cerrar sesión');
    }
}

const ViewOrder = () => {
    route.push({name: 'ListOrder'});
}


</script>

<style scoped>
.contentNavClient{
    display: flex;
    justify-content: space-between;
    align-items: center;
    width: 100%;
}

.btn-cart{
    padding: 0.8rem;
    background: linear-gradient(to left, white 50%, #4944b8 50%);
    background-size: 200% 100%;
    background-position: right bottom;
    color: #4944b8;
    border-radius: 0.5rem;
    border: 1px solid white;
    cursor: pointer;
    font-weight: bold;
    justify-content: center;
    align-items: center;
    display: flex;
    transition: background-position 0.4s ease, color 0.4s ease;
}

.btn-cart:hover{
    background-position: left bottom;
    color: white;
}

.btn-cart:hover svg{
    fill: white;
}

.btn-logout{
    padding: 1rem;
    background: linear-gradient(to left, white 50%, #4944b8 50%);
    background-size: 200% 100%;
    background-position: right bottom;
    color: #4944b8;
    border-radius: 0.5rem;
    border: 1px solid white;
    cursor: pointer;
    font-weight: bold;
    justify-content: center;
    align-items: center;
    display: flex;
    transition: background-position 0.4s ease, color 0.4s ease;
}

.btn-logout:hover{
    background-position: left bottom;
    color: white;
}

</style>