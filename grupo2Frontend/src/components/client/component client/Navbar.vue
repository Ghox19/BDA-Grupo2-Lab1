<script setup>
import { ref, onMounted } from "vue";
import { verifyToken } from "../../../Services/authentication";
import NavPublic from './ViewNavbar/NavPublic.vue';
import NavClient from './ViewNavbar/NavClient.vue';

const tokenValid = ref(false); // Variable reactiva para controlar si el token es válido

// Función para verificar el token
const verify= async () => {
  try {
    const response = await verifyToken();
    tokenValid.value = response;
  } catch (error) {
    tokenValid.value = false; 
  }
};

// Verificar token cuando el componente se monta
onMounted(() => {
  verify();
});
</script>

<template>
    <div class="navbar-container">
        <div class="content">
            <h1>Ecommerce</h1>
            <div class="sub-content">
                <NavPublic v-if="!tokenValid"/>
                <NavClient v-else/>
            </div>
        </div>
    </div>
</template>

<style scoped>
.navbar-container {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 9vh;
  width: 100%;
  background-color: #4944b8;
  color: white;
  font-family: "Open Sans", sans-serif;
}

.content {
  display: flex;
  justify-content: space-between;
  align-items: center;
  width: 100%;
  margin: 0 2rem;
}

.sub-content {
  display: flex;
  justify-content: space-between;
  align-items: center;
  height: 100%;
  width: 16%;
}
</style>