<script setup>
import { ref, onMounted } from 'vue';
import {useStore} from "vuex";
import { getOrderByIdUser } from "../../../Services/OrdenService.js";
import { useRouter } from 'vue-router';

const store = useStore();
const route = useRouter();
const User = store.getters.getUser;
const Ordenes = ref([]);

onMounted(async () => {
  console.log('User:', User.id_user);
  try {
    const response = await getOrderByIdUser(User.id_user);
    console.log('Response:', response);
    if (response) {
      Ordenes.value = [...response].sort((a, b) => {
        return a.estado === 'en_proceso' ? -1 : 1;
      });

      console.log('Ordenes:', Ordenes.value);
    } else {
      console.log('No data found in response');
    }
  } catch (error) {
    console.log('Error:', error);
  }
});

const fecha = (time) => {
  const options = time.split("T");
  return options[0];
};

const detalle = (id) => {
  console.log('ID:', id);
  route.push({ name: "order" ,params: { id } });
};


</script>

<template>
  <div class="container">
    <h1>Ordenes</h1>
    <div class="content">
      <div class="order-list">
        <div v-for="(order, index) in Ordenes" :key="order.id_orden" class="content-list">
          <div class="order-card">
            <h2>{{ index + 1 }}</h2>
            <p>{{ fecha(order.fecha_orden) }}</p>
            <p>{{ order.estado }}</p>
            <button class="boton" @click="detalle(order.id_orden)">Ver Detalles</button>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
.container {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  width: 100%;
  height: 100%;

}
.container h1 {
  font-size: 4rem;
  margin: 2rem;
  top: 0;
}

.container .content {
  display: flex;
  flex-direction: column;
  padding: 1rem;
  align-items: center;
  background-color: white;
  width: 100%;
  height: 100%;
}

.content .order-list {
  display: flex;
  flex-direction: column;
  width: 100%;
  height: 100%;
  overflow-y: auto;
  overflow-x: hidden;
}

::-webkit-scrollbar {
  width: 0.7rem;
}


::-webkit-scrollbar-track {
  background: #f0f0f0; 
  border-radius: 1rem; 
}


::-webkit-scrollbar-thumb {
  background-color: #0000009b; 
  border-radius: 1rem; 
  border: 2px solid #f0f0f0; 
}

/* Hover del thumb */
::-webkit-scrollbar-thumb:hover {
  background-color: black;
}

.order-card {
  display: flex;
  flex-direction: row;
  justify-content: space-between;
  align-items: center;
  border: 1px solid #000;
  border-radius: 8px;
  padding: 40px;
  width: calc(100% - 7rem);
  margin: 1rem;
  height: 1px;
}

.content {
  display: flex;
  width: 80vw;
}

</style>