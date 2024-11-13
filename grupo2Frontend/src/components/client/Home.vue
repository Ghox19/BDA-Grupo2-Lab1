<script setup>
import { ref, onMounted } from 'vue'; // Importar funciones reactivas y ciclo de vida
import NavbarClient from './component client/Navbar.vue';
import axios from 'axios';

// Variables reactivas
const productos = ref([]);
const loading = ref(false);
const errorMessage = ref('');

// Función para obtener los productos
const getProductos = async () => {
  loading.value = true;
  try {
    const response = await axios.get('http://localhost:8080/producto');
    productos.value = response.data; // Asignar datos a la referencia reactiva
  } catch (error) {
    errorMessage.value = 'Error al cargar los productos';
  } finally {
    loading.value = false;
  }
};

// Llamar a la función cuando el componente se monte
onMounted(() => {
  getProductos();
});
</script>

<template>
  <div class="container-home">
    <NavbarClient />
    <div class="content-home">
      <div v-if="loading">Cargando productos...</div>
      <div v-if="errorMessage" class="error">{{ errorMessage }}</div>
      <div class="product-list">
        <div v-for="product in productos" :key="product.id" class="product-card">
          <h3>{{ product.name }}</h3>
          <p>{{ product.description }}</p>
          <p>Precio: {{ product.price }}</p>
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
}

.product-list {
  display: flex;
  flex-wrap: wrap;
  justify-content: center;
}

.product-card {
  width: 300px;
  height: 300px;
  margin: 16px;
  padding: 16px;
  border: 1px solid #ccc;
  border-radius: 8px;
  box-sizing: border-box;
}
</style>