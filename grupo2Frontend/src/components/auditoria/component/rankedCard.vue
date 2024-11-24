<template>
    <div>
      <!-- Main user card -->
      <div class="card">
        <div class="card-content">
          <div class="user-section">
            <h3>Usuario: {{ userData.nombre || 'Cargando...' }}</h3>
          </div>
          <div class="divider"></div>
          <div class="operation-info">
            <p><strong>Tipo de Operación:</strong> {{ tipoOperacion }}</p>
          </div>
          <div class="divider"></div>
          <div class="total-section">
            <p><strong>Total:</strong> {{ totalOperaciones }}</p>
          </div>
        </div>
      </div>
  
      <!-- Mapped operation cards -->
      <div class="operations-container">
        <logCard
          v-for="operation in userOperations"
          :key="operation.id"
          :tipo-operacion="operation.operacion"
          :nombre-tabla="operation.nombre_tabla"
          :fecha="operation.fecha"
          :idObjeto="operation.id_objeto"
        />
      </div>
    </div>
  </template>
  
  <script setup>
  import { defineProps, ref, watch } from 'vue'
  import axios from 'axios'
  import logCard from './logCard.vue';
  
  const props = defineProps({
    idUsuario: {
      type: Number,
      required: true
    },
    tipoOperacion: {
      type: String,
      required: true
    },
    totalOperaciones: {
      type: Number,
      required: true,
      default: 0
    }
  })
  
  const userData = ref({});
  const userOperations = ref([]);
  const errorMessage = ref('');
  
  const getUserData = async (id) => {
    if (!id) return;
    
    try {
      const response = await axios.get(`http://localhost:8080/public/client/${id}`, {
        headers: {
          'Content-Type': 'application/json',
          'Accept': 'application/json'
        }
      });
      userData.value = response.data;
    } catch (error) {
      if (error.response?.status === 403) {
        errorMessage.value = 'No tienes autorización para ver la auditoria';
      } else {
        errorMessage.value = 'Error al cargar la auditoria';
      }
    }
  };

  const getAuditarUser = async (id) => {
    if (!id) return;
    
    try {
      const response = await axios.get(`http://localhost:8080/auditoria/${id}`, {
        headers: {
          'Content-Type': 'application/json',
          'Accept': 'application/json'
        }
      });
      userOperations.value = response.data;
    } catch (error) {
      if (error.response?.status === 403) {
        errorMessage.value = 'No tienes autorización para ver la auditoria';
      } else {
        errorMessage.value = 'Error al cargar la auditoria';
      }
    }
  };
  
  // Watch for changes in idUsuario
  watch(() => props.idUsuario, (newId) => {
    if (newId) {
      getUserData(newId);
      getAuditarUser(newId);
    }
  }, { immediate: true });
  </script>
  
  <style scoped>
  .card {
    border: 1px solid #ddd;
    border-radius: 8px;
    color: black;
    padding: 12px 20px;
    margin: 16px;
    box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
    background-color: white;
  }
  
  .card-content {
    display: flex;
    align-items: center;
    justify-content: space-between;
    gap: 20px;
  }
  
  .user-section, .operation-info, .total-section {
    flex: 1;
    text-align: center;
  }
  
  .user-section h3 {
    margin: 0;
    font-size: 1.1rem;
  }
  
  .operation-info p, .total-section p {
    margin: 0;
    font-size: 1rem;
  }
  
  .divider {
    width: 1px;
    height: 40px;
    background-color: #ddd;
  }
  
  @media (max-width: 768px) {
    .card-content {
      flex-direction: column;
      gap: 10px;
    }
    
    .divider {
      width: 100%;
      height: 1px;
    }
  }
  </style>