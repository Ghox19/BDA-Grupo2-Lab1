-- Generar un create que dropea todas las tablas existentes --

-- DROP TABLE IF EXISTS detalle_orden;
-- DROP TABLE IF EXISTS orden;
-- DROP TABLE IF EXISTS cliente;
-- DROP TABLE IF EXISTS producto;
-- DROP TABLE IF EXISTS categoria;

CREATE TABLE IF NOT EXISTS categoria (
     id_categoria SERIAL PRIMARY KEY,
     nombre VARCHAR(100) NOT NULL
    );

CREATE TABLE IF NOT EXISTS producto (
    id_producto SERIAL PRIMARY KEY,
    nombre VARCHAR(255) NOT NULL,
    descripcion TEXT,
    precio DECIMAL(10, 2),
    stock INT,
    estado VARCHAR(50),
    id_categoria INTEGER,
    FOREIGN KEY (id_categoria) REFERENCES categoria (id_categoria) ON DELETE CASCADE
    );

CREATE TABLE IF NOT EXISTS cliente (
   id_cliente SERIAL PRIMARY KEY,
   nombre VARCHAR(255) NOT NULL,
    direccion VARCHAR(255),
    email VARCHAR(100),
    telefono VARCHAR(20)
    );

CREATE TABLE IF NOT EXISTS orden (
     id_orden SERIAL PRIMARY KEY,
     fecha_orden TIMESTAMP,
     estado VARCHAR(50),
    id_cliente INTEGER,
    total DECIMAL(10, 2),
    FOREIGN KEY (id_cliente) REFERENCES cliente (id_cliente) ON DELETE CASCADE
    );

CREATE TABLE IF NOT EXISTS detalle_orden (
     id_detalle SERIAL PRIMARY KEY,
     id_orden INTEGER,
     id_producto INTEGER,
     cantidad INT,
     precio_unitario DECIMAL(10, 2),
    FOREIGN KEY (id_orden) REFERENCES orden (id_orden) ON DELETE CASCADE,
    FOREIGN KEY (id_producto) REFERENCES producto (id_producto) ON DELETE CASCADE
    );