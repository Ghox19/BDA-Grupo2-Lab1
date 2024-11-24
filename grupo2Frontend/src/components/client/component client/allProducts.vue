<template>
  <div class="content-allproduct">
    <div class="content-listproduct">
      <div v-if="loading" class="content-message">Cargando productos...</div>
      <div v-if="errorMessage" class="content-message">{{ errorMessage }}</div>
      <div v-if="!loading && !errorMessage" class="product-list">
        <div v-if="nullmessage" class="content-message">
          No hay productos disponibles en este momento.
        </div>
        <div v-for="product in productos" :key="product.id_producto" class="product-card" @click="ViewProduct(product.id_producto)">
          <h3>{{ product.nombre }}</h3>
          <p>Precio: {{ product.precio }}</p>
          <p>Stock: {{ product.stock }}</p>
        </div>
      </div>
    </div>
  <div v-if="!loading && totalPages > 1" class="pagination">
      <button @click="changePage(currentPage - 1)" :disabled="currentPage === 1">Anterior</button>
      <span>Página {{ currentPage }} de {{ totalPages }}</span>
      <button @click="changePage(currentPage + 1)" :disabled="currentPage === totalPages">Siguiente</button>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted, watch } from 'vue';
import { getProducts } from '../../../Services/ProductService';
import { useRoute, useRouter } from 'vue-router';

// Variables reactivas
const productos = ref([]);
const loading = ref(false);
const nullmessage = ref(false);
const errorMessage = ref('');
const currentPage = ref(1); // Página actual
const totalPages = ref(0); // Número total de páginas
const route = useRoute();
const router = useRouter();

// Función para obtener los productos con paginación
const getAllProducts = async () => {
  loading.value = true;
  nullmessage.value = false;
  errorMessage.value = '';

  const page = Number(route.params.id) || 1; // Obtener el número de página desde la URL
  try {
    const response = await getProducts(page); // Llamar al servicio con el número de página
    console.log(response);
    productos.value = response.productos;
    currentPage.value = response.currentPage;
    totalPages.value = response.totalPages;

    // Mostrar mensaje si no hay productos
    if (productos.value.length === 0) {
      nullmessage.value = true;
    }
  } catch (error) {
    errorMessage.value = 'Error al cargar los productos';
  } finally {
    loading.value = false;
  }
};

// Cambiar de página y actualizar la URL
const changePage = (page) => {
  if (page >= 1 && page <= totalPages.value) {
    router.push({ name: 'allproducts', params: { id: page } });
  }
};

// Escuchar cambios en el parámetro de la ruta
watch(
  () => route.params.id,
  () => {
    getAllProducts(); // Actualizar los productos cuando cambie la página en la URL
  },
  { immediate: true }
);

const ViewProduct = (id) => {
  router.push({ name: 'product', params: { id } });
};

// Llamar a la función cuando el componente se monte
onMounted(() => {
  getAllProducts(); // Cargar los productos basados en la URL inicial
});
</script>

<style scoped>
.content-allproduct {
  height: 100%; /* Asegurar que ocupe toda la altura disponible */
  overflow: hidden; /* Evitar scroll en el contenedor principal */
  display: flex;
  flex-direction: column;
}

.product-list {
  display: flex;
  flex-wrap: wrap;
  justify-content: center;
  width: 100%;
  height: 100%;
  overflow-y: auto;
}

.content-listproduct {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100%;
  width: 100%;
  overflow-y: auto;
  overflow-x: hidden;
  scrollbar-width: thin;
  scrollbar-color: #4944b8 #eaeaea;
}

.content-listproduct::-webkit-scrollbar {
  width: 8px;
}

.content-listproduct::-webkit-scrollbar-thumb {
  background-color: #4944b8;
  border-radius: 8px;
}

.content-listproduct::-webkit-scrollbar-track {
  background-color: #eaeaea;
}

.content-message {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100%;
  width: 100%;
}

.product-card {
  width: 512px;
  height: 288px;
  margin: 16px;
  padding: 16px;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  border: 1px solid #4944b8;
  background-color: white;
  border-radius: 8px;
  box-sizing: border-box;
  cursor: pointer;
}

.product-card h3 {
  margin: 0;
  font-size: 1.5rem;
}

.pagination {
  display: flex;
  justify-content: center;
  align-items: center;
  margin: 16px 0;
}

.pagination button {
  margin: 0 8px;
  padding: 8px 16px;
  background-color: #4944b8;
  color: white;
  border: none;
  border-radius: 4px;
  cursor: pointer;
}

.pagination button:disabled {
  background-color: #ccc;
  cursor: not-allowed;
}

.pagination span {
  margin: 0 8px;
}
</style>
