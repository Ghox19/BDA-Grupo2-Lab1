<script setup>
import {onMounted, ref} from 'vue';
import { useRouter } from 'vue-router';
import { getProductById} from '../../../Services/ProductService';
import { getDetalleOrden } from "../../../Services/DetalleOrden";
import { createDetalleOrden } from "../../../Services/DetalleOrden";
import { updateDetalleOrden } from "../../../Services/DetalleOrden";
import { verifyToken } from "../../../Services/authentication";
import {useStore} from "vuex";

const router = useRouter();
const nombre = ref('')
const precio = ref('')
const stock = ref('')
const descripcion = ref('')
const estado = ref('')
const cantidad = ref(1)
const store = useStore();
const orden = store.state.order;
const idProducto = router.currentRoute.value.params.id;



onMounted(async () => {
  const response = await getProductById(idProducto);
  nombre.value = response.data.nombre;
  precio.value = response.data.precio;
  stock.value = response.data.stock;
  descripcion.value = response.data.descripcion;
  estado.value = response.data.estado;
});

const carrito = async () => {
  console.log("Cantidad",cantidad.value);
  const response = await getDetalleOrden(orden, idProducto);

  console.log(response);

  const response1 = await verifyToken();
  console.log(response1);
  if (!response1) {
     alert("Debe iniciar sesión para agregar productos al carrito");
     router.push({ name: 'login' });
     return;
   }



  if (response.data) {
    const aux = response.data.cantidad + cantidad.value;

    const data1 = {
      id_detalle: response.data.id_detalle,
      id_orden: orden,
      id_producto: idProducto,
      cantidad: aux,
      precio_unitario: response.data.precio_unitario
    }

    console.log(data1);
    const response1 = await updateDetalleOrden(data1);
    alert("Se aumento la cantidad de productos en el carrito");
    router.push({  name: 'allproducts', params: { id: 1 }});

  } else {
    console.log("No existe");
    const data2 = {
      id_orden: orden,
      id_producto: idProducto,
      cantidad: cantidad.value,
      precio_unitario: precio.value
    }

    console.log(data2);

    const response2 = await createDetalleOrden(data2);
    console.log(response2);
    alert("Producto agregado al carrito");
    router.push({ name: 'allproducts', params: { id: 1 }});
  }
}

</script>

<template>
  <div class="fondo">
    <div class="formato">
      <div class="contenedor1">
        <h2 class="titulo1">{{nombre}}</h2>
        <div class="caracteristicas">
          <p class="caligrafia"><strong>Precio: </strong> ${{precio}}</p>
          <p class="caligrafia"><strong>Stock:  </strong>{{stock}} unidades</p>
          <p class="caligrafia"><strong>Estado: </strong>{{estado}}</p>
        </div>
        <div class="cantidad-container">
          <button class="boton" @click="carrito">Agregar al Carrito</button>
          <input type="number" v-model="cantidad" class="input-cantidad" :max="stock" :min="1" />
        </div>
      </div>
      <div class="contenedor2">
        <h2 class="titulo2" >Descripcion del producto</h2>
        <p class="descripcion">{{descripcion}}</p>
      </div>
    </div>
  </div>
</template>

<style scoped>
.fondo {
  height: 100%;
  width: 100%;
  background-color: white;
  overflow: hidden;
}

.formato{
  height: 100%;
  display: flex;
}

.contenedor1 {
  width: 40%;
  height: 100%;
  background: #5EA9DC52;
}

.contenedor2{
  width: 60%;
  height: 100%;
  background: white;
  border: 1px solid white;
  color:black;
}

.caligrafia{
  color: black;
  text-align: justify;
  margin-left: 190px;
}

.descripcion{
  color: black;
  text-align: justify;
  margin-left: 50px;
  margin-right: 50px;
}

.titulo1{
  color: black;
  text-align: center;
  margin-top: 150px;
}

.titulo2{
  color: black;
  text-align: center;
  margin-top: 20px;
}

.boton{
  background: #4944b8;
  color: white;
}

.input-cantidad {
  width: 80px;
  height: 30px;
  margin-left: 10px;
  text-align: center;
  background: white;
  color: #1a1a1a;
}
</style>