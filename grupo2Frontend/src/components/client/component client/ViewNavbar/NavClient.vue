<template>
    <div class="contentNavClient">
        <button class="btn-cart" @click="ViewCart">
            <svg xmlns="http://www.w3.org/2000/svg" height="24px" viewBox="0 -960 960 960" width="24px" fill="#4944b8"><path d="M280-80q-33 0-56.5-23.5T200-160q0-33 23.5-56.5T280-240q33 0 56.5 23.5T360-160q0 33-23.5 56.5T280-80Zm400 0q-33 0-56.5-23.5T600-160q0-33 23.5-56.5T680-240q33 0 56.5 23.5T760-160q0 33-23.5 56.5T680-80ZM246-720l96 200h280l110-200H246Zm-38-80h590q23 0 35 20.5t1 41.5L692-482q-11 20-29.5 31T622-440H324l-44 80h480v80H280q-45 0-68-39.5t-2-78.5l54-98-144-304H40v-80h130l38 80Zm134 280h280-280Z"/></svg>
        </button>
        <button class="btn-logout" @click="logoutUser">
            Cerrar Sesión
        </button>
    </div>
</template>

<script setup>
import { logout } from '../../../../Services/UserService';
import { useRoute } from 'vue-router';
import { useStore } from 'vuex';

const store = useStore();
const route = useRoute();

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

const ViewCart = () => {
    console.log('Ver carrito');
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