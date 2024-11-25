<template>
    <div class="container-order">
        <div class="container-content">
            <h1>Detalles de la orden</h1>
            <div class="content-order">
                <p>Estado:</p>
                <p class="text-order">{{ Order.estado }}</p>
                <p>Fecha:</p>
                <p class="text-order">{{ Order.date }}</p>
                <p>Total:</p>
                <p class="text-order">${{ total }}</p>
            </div>
            <button v-if="Order.estado != 'enviada'" @click="handlepayOrder()" class="btn-pay">pagar</button>
        </div>
        <div class="container-listOrder">
            <div v-for="detailOrder in ListDetailsOrder" :key="detailOrder.id_detailorden" class="order-card">
                <div class="card-content">
                    <h3>{{ detailOrder.nombre }}</h3>
                    <p>Precio unitario: {{ detailOrder.precio }}</p>
                    <p>Cantidad: {{ detailOrder.cantidad }}</p>
                </div>
            </div>
        </div>
    </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import { getDetailsOrderbyOrder} from '../../../../Services/DetailsOrderService';
import { getOrderById, calculateTotalOrden } from '../../../../Services/OrdenService';   
import { getProductById} from '../../../../Services/ProductService';
import { PayOrder, CreateOrder } from '../../../../Services/OrdenService';
import { useStore } from 'vuex';
import { useRouter } from 'vue-router';

const router = useRouter();
const idOrder = router.currentRoute.value.params.id;
const store = useStore();
const User = store.getters.getUser;
const Order = ref({});
const ListDetailsOrder = ref([]);
const total = ref(0);

const getOrderAndDetailsOrder = async () => {
    const responseOrder = await getOrderById(idOrder);
    total.value = await calculateTotalOrden(idOrder);
    Order.value = {
        date: responseOrder.fecha_orden.split("T")[0],
        estado: responseOrder.estado
    }

    const response = await getDetailsOrderbyOrder(idOrder);
    const List = response;
    
    for(const detail of List){
        const responseProduct = await getProductById(detail.id_producto);
        ListDetailsOrder.value.push({
            ...responseProduct,
            cantidad: detail.cantidad,
            precio: detail.precio_unitario,
            nombre: responseProduct.data.nombre
        })
    }

    console.log('ListDetailsOrder:', ListDetailsOrder.value);

};

onMounted(() => {
    getOrderAndDetailsOrder();
});

const handlepayOrder = async () => {
    console.log(User);
    const response = await PayOrder(idOrder);
    if(response){
        const responseOrden = await CreateOrder({
            fecha_orden: new Date(),
            id_cliente: User.id_user,
            estado: "en_proceso",
            total: 0
        });

        store.commit('setOrder', responseOrden);

        alert('Orden pagada correctamente');

        router.push({ name: 'ListOrder' });

    }
}

</script>

<style scoped>
.container-order {
    height: 100%;
    width: 100%;
    background-color: white;
    display: flex;
    justify-content: space-between;
    align-items: center;
}

.container-order h1 {
    font-size: 4rem;
    margin: 2rem;
    top: 0;

}

.container-order .content-order {
    display: flex;
    flex-direction: column;
    justify-content: space-between;
    align-items: center;
    width: 50%;
    height: 50%;
    margin: 2rem;
}

.content-order .text-order{
    font-size: 1.2rem;
    font-weight: bold;
}

.btn-pay{
    width: 50%;
    height: 4rem;
    padding: 1rem;
    background-color: #4944b8;
    color: white;
    border-radius: 0.5rem;
    border: solid 2px #4944b8;
    cursor: pointer;
    font-size: 1rem;
    font-weight: bold;
    justify-content: center;
    align-items: center;
    display: flex;
    transition: background-color 0.3s ease, color 0.3s ease;
}

.btn-pay:hover {
    background-color: white;
    color: #4944b8;
    border: solid 2px #4944b8;
}

.container-content {
    height: 100%;
    width: 50%;
    background-color: #5EA9DC52;
    border-right: 1px solid #5EA9DC52;
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
}

.container-listOrder {
  height: 100%;
  width: 50%;
  overflow-y: auto;
  overflow-x: hidden;
  display: flex;
  flex-direction: column;
  background-color: white;
  box-sizing: border-box;
}

::-webkit-scrollbar {
  width: 0.7rem;
}


::-webkit-scrollbar-track {
  background: #f0f0f0; 
  border-radius: 1rem; 
}


::-webkit-scrollbar-thumb {
  background-color: #4a44b89b; 
  border-radius: 1rem; 
  border: 2px solid #f0f0f0; 
}


::-webkit-scrollbar-thumb:hover {
  background-color: #4944b8;
}


.order-card {
    height: 6rem;
    width: calc(100% - 2rem);
    display: flex;
    align-items: center;
    border: 1px solid #5EA9DC52;
    border-radius: 0.5rem;
    margin: 1rem;
    box-shadow: 0px 2px 4px rgba(0, 0, 0, 0.1);
}

.order-card .card-content {
    height: 100%;
    width: 100%;
    display: flex;
    justify-content: space-between;
    align-items: center;
    margin: 2rem;
}
</style>