<script setup>
import axios from 'axios';
import { ref, onMounted } from 'vue';
import rankedCard from './component/rankedCard.vue';

// Variables reactivas
const ranked = ref([]);
const loading = ref(false);
const errorMessage = ref('');

const getRanked = async () => {
  loading.value = true;
  try {
    const response = await axios.get('http://localhost:8080/auditoria', {
      headers: {
        'Content-Type': 'application/json',
        'Accept': 'application/json'
      }
    });
    ranked.value = response.data;
  } catch (error) {
    if (error.response?.status === 403) {
      errorMessage.value = 'No tienes autorización para ver la auditoria';
    } else {
      errorMessage.value = 'Error al cargar la auditoria';
    }
  } finally {
    loading.value = false;
  }
};

// Llamar a la función cuando el componente se monte
onMounted(() => {
  getRanked();
});
</script>

<template>
  <div class="container-home">
    <div class="content-home">
      <div v-if="loading">Cargando productos...</div>
      <div v-if="errorMessage" class="error">{{ errorMessage }}</div>
      <div class="product-list">
        <div v-for="user in ranked" :key="ranked.id_usuario" class="product-card">
            <p class="title"> Operacion {{ user.tipo_operacion }}</p>
            <rankedCard :idUsuario="user.id_usuario" :tipoOperacion="user.tipo_operacion" :totalOperaciones="user.total_operaciones" />
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
.container-home {
  height: 100%;
  width: 100%;
  background-color: white;
  display: flex;
  flex-direction: column;
  overflow-y: auto;
}

.content-home {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100%;
  width: 100%;
  overflow: hidden; /* Evita el desbordamiento */
  padding: 16px;
  box-sizing: border-box;
  overflow-y: auto;
}

.title {
  font-size: 1.5rem;
  font-weight: bold;
  color: black;
  margin: 0;
}  
</style>