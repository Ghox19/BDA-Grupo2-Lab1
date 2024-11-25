<template>
    <div class="ranked-container">
      <div v-if="loading" class="loading">Cargando datos...</div>
      <div v-else-if="errorMessage" class="error">{{ errorMessage }}</div>
      <div v-else class="cards-grid">
        <div v-for="producto in ranked" :key="producto.id_producto" class="card-wrapper">
          <ProductCard
            :nombre="producto.nombre"
            :categoria="producto.categoria"
            :idProducto="producto.id_producto"
            :stock="producto.stock"
            :totalVendido="producto.total_vendido"
            :ingresoTotal="producto.ingreso_total"
            :porcentajeVentas="producto.porcentaje_ventas"
          />
        </div>
      </div>
    </div>
  </template>
  
  <script setup>
  import { ref, onMounted } from 'vue';
  import axios from 'axios';
  import ProductCard from './component/ProductCard.vue';
  
  const ranked = ref([]);
  const loading = ref(false);
  const errorMessage = ref('');
  
  const getRankedProducts = async () => {
    loading.value = true;
    try {
      const response = await axios.get('http://localhost:8080/public/prod/top', {
        headers: {
          'Content-Type': 'application/json',
          'Accept': 'application/json'
        }
      });
      ranked.value = response.data;
    } catch (error) {
      errorMessage.value = error.response?.status === 403 
        ? 'No tienes autorización para ver los productos' 
        : 'Error al cargar los productos';
    } finally {
      loading.value = false;
    }
  };
  
  onMounted(() => {
    getRankedProducts();
  });
  </script>
  
  <style scoped>
  .ranked-container {
    padding: 20px;
    width: 100%;
  }
  
  .cards-grid {
    display: grid;
    grid-template-columns: repeat(auto-fit, minmax(300px, 1fr));
    gap: 20px;
    padding: 16px;
  }
  
  .card-wrapper {
    display: flex;
    justify-content: center;
  }
  
  .loading {
    text-align: center;
    padding: 20px;
    font-size: 1.2rem;
    color: #666;
  }
  
  .error {
    text-align: center;
    padding: 20px;
    color: #dc3545;
    font-weight: bold;
  }
  </style>