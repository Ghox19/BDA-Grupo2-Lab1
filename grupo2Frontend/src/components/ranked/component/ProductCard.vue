<template>
    <div class="product-card">
      <div class="card-header">
        <h3>{{ nombre }}</h3>
        <span class="category-badge">{{ categoria }}</span>
      </div>
      <div class="card-content">
        <div class="info-row">
          <span class="label">ID Producto:</span>
          <span class="value">#{{ idProducto }}</span>
        </div>
        <div class="info-row">
          <span class="label">Stock:</span>
          <span class="value">{{ stock }} unidades</span>
        </div>
        <div class="info-row">
          <span class="label">Vendidos:</span>
          <span class="value">{{ totalVendido }}</span>
        </div>
        <div class="info-row">
          <span class="label">Ingresos:</span>
          <span class="value">${{ ingresoTotal.toFixed(2) }}</span>
        </div>
        <div class="info-row">
          <span class="label">% Ventas:</span>
          <span class="value">{{ porcentajeVentas.toFixed(2) }}%</span>
        </div>
      </div>
    </div>
  </template>
  
  <script setup>
  defineProps({
    nombre: {
      type: String,
      required: true
    },
    categoria: {
      type: String,
      required: true
    },
    idProducto: {
      type: Number,
      required: true
    },
    stock: {
      type: Number,
      required: true
    },
    totalVendido: {
      type: Number,
      required: true
    },
    ingresoTotal: {
      type: Number,
      required: true
    },
    porcentajeVentas: {
      type: Number,
      required: true
    }
  });
  </script>
  
  <style scoped>
  .product-card {
    background: white;
    border-radius: 8px;
    box-shadow: 0 2px 4px rgba(0,0,0,0.1);
    padding: 16px;
    width: 100%;
    max-width: 350px;
  }
  
  .card-header {
    display: flex;
    justify-content: space-between;
    align-items: center;
    margin-bottom: 16px;
  }
  
  .card-header h3 {
    margin: 0;
    font-size: 1.25rem;
    color: #333;
  }
  
  .category-badge {
    background: #e9ecef;
    padding: 4px 8px;
    border-radius: 4px;
    font-size: 0.875rem;
    color: #495057;
  }
  
  .info-row {
    display: flex;
    justify-content: space-between;
    padding: 8px 0;
    border-bottom: 1px solid #eee;
  }
  
  .info-row:last-child {
    border-bottom: none;
  }
  
  .label {
    color: #666;
    font-weight: 500;
  }
  
  .value {
    color: #333;
    font-weight: 600;
  }
  </style>