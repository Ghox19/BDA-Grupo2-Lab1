<script setup>
import { ref, onMounted } from 'vue'; // Importar funciones reactivas y ciclo de vida
import NavbarClient from './component client/Navbar.vue';
import { getProducts } from '../../Services/ProductService';

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

// Llamar a la función cuando el componente se monte
onMounted(() => {
  getAllProducts();
});

</script>

<template>
  <div class="container-home">
    <NavbarClient />
    <div class="content-home">
      <router-view />
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
  color: #333;
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100%;
  width: 100%;
  overflow: hidden;
  box-sizing: border-box;
}
</style>