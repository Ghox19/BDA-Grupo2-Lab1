<script setup>
import { ref, onMounted } from 'vue';
import {useStore} from "vuex";
import { getOrderByIdUser } from "../../../Services/OrdenService.js";

const store = useStore();
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

const detalle = () => {
  console.log('Detalle');
  //Colocar ruta
};


</script>

<template>
  <div class="container">
    <h1>Ordenes</h1>
    <div class="content">
      <div class="order-list">
        <div v-for="(order, index) in Ordenes" :key="order.id_order">
          <div class="order-card">
            <h2>{{ index + 1 }}</h2>
            <p>{{ fecha(order.fecha_orden) }}</p>
            <p>{{ order.estado }}</p>
            <button class="boton" @click="detalle">Ver Detalles</button>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>

.order-card {
  display: flex;
  flex-direction: row;
  justify-content: space-between;
  align-items: center;
  border: 1px solid #000;
  border-radius: 8px;
  padding: 40px;
  margin-bottom: 15px;
  width: 244%;
  height: 1px;
}

.content {
  display: flex;
  width: 80vw;
}

</style>