<template>
    <div class="card">
      <div class="card-content">
        <div class="user-section">
          <h3>Objeto: {{ idObjeto }}</h3>
        </div>
        <div class="divider"></div>
        <div class="operation-info">
          <p><strong>Tipo de Operación:</strong> {{ tipoOperacion }}</p>
        </div>
        <div class="divider"></div>
        <div class="total-section">
          <p><strong>Tabla:</strong> {{ nombreTabla }}</p>
        </div>
        <div class="divider"></div>
        <div class="total-section">
          <p><strong>Fecha:</strong> {{ fecha }}</p>
        </div>
      </div>
    </div>
  </template>
  
  <script setup>
  import { defineProps } from 'vue'
  
  const props = defineProps({
    tipoOperacion: {
      type: String,
      required: true
    },
    nombreTabla: {
      type: String,
      required: true
    },
    fecha: {
      type: String,
      required: true
    },
    idObjeto: {
      type: Number,
      required: true
    }
  })
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