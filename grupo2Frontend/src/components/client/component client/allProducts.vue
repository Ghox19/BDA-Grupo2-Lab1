<template>
    <div v-if="loading">Cargando productos...</div>
      <div v-if="errorMessage" class="error">{{ errorMessage }}</div>
      <div class="product-list">
        <div v-if="nullmessage">
          No hay productos disponibles en este momento.
        </div>
        <div v-for="product in productos" :key="product.id_producto" class="product-card" @click="ViewProduct(product.id_producto)">
          <h3>{{ product.nombre }}</h3>
          <p>Precio: {{ product.precio }}</p>
          <p>Stock: {{ product.stock }}</p>
        </div>
      </div>
</template>

<script setup>
  import { ref, onMounted } from 'vue'; // Importar funciones reactivas y ciclo de vida
  import { getProducts } from '../../../Services/ProductService';

// Variables reactivas
const productos = ref([]);
const loading = ref(false);
const nullmessage = ref(false);
const errorMessage = ref('');

// Función para obtener los productos

const getAllProducts = async () => {
  loading.value = true;
  try {
    const response = await getProducts(); // Llamar a la función del servicio
    productos.value = response; // Asignar datos a la referencia reactiva
  } catch (error) {
    errorMessage.value = 'Error al cargar los productos';
  } finally {
    loading.value = false;
    if(productos.value == null && errorMessage.value == '') {
      nullmessage.value = true;
    }
  }
};

const ViewProduct = (id) => {
  console.log('Ver producto:', id);
};

// Llamar a la función cuando el componente se monte
onMounted(() => {
  getAllProducts();
});

</script>

<style scoped>
.product-list {
  display: flex;
  flex-wrap: wrap;
  justify-content: center;
  width: 100%;
  gap: 16px;
  height: 100%;
  overflow-y: auto;
}

.product-card {
  width: 300px;
  height: 300px;
  margin: 16px;
  padding: 16px;
  border: 1px solid #ccc;
  border-radius: 8px;
  box-sizing: border-box;
  cursor: pointer;
}
</style>