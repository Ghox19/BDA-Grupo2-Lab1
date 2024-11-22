-- IDEALMENTE EJECUTAR LO SIGUIENTE POR MEDIO DE PGADMIN, O EJECUTAR SOLAMENTE LO RELACIONADO AL PROCEDIMIENTO EN ADELANTE, USANDO SPRING BOOT SE CAE POR LAS $$

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
    telefono VARCHAR(20),
    clave VARCHAR(20)
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

CREATE TABLE IF NOT EXISTS auditoria(
                                        id SERIAL PRIMARY KEY,
                                        id_objeto INTEGER,
                                        nombre_tabla VARCHAR(100),
    operacion VARCHAR(200),
    fecha TIMESTAMP
    );

-- DESCOMENTAR SI ES QUE SE QUIERE PROBAR MANUALMENTE
CREATE OR REPLACE FUNCTION auditar_operacion()
    RETURNS TRIGGER AS $BODY$
DECLARE
id_valor INTEGER;
BEGIN
    IF (TG_TABLE_NAME = 'categoria') THEN
        IF (TG_OP = 'DELETE') THEN
SELECT OLD.id_categoria INTO id_valor;
ELSE
SELECT NEW.id_categoria INTO id_valor;
END IF;
    ELSIF (TG_TABLE_NAME = 'producto') THEN
        IF (TG_OP = 'DELETE') THEN
SELECT OLD.id_producto INTO id_valor;
ELSE
SELECT NEW.id_producto INTO id_valor;
END IF;
    ELSIF (TG_TABLE_NAME = 'cliente') THEN
        IF (TG_OP = 'DELETE') THEN
SELECT OLD.id_cliente INTO id_valor;
ELSE
SELECT NEW.id_cliente INTO id_valor;
END IF;
    ELSIF (TG_TABLE_NAME = 'orden') THEN
        IF (TG_OP = 'DELETE') THEN
SELECT OLD.id_orden INTO id_valor;
ELSE
SELECT NEW.id_orden INTO id_valor;
END IF;
    ELSIF (TG_TABLE_NAME = 'detalle_orden') THEN
        IF (TG_OP = 'DELETE') THEN
SELECT OLD.id_detalle INTO id_valor;
ELSE
SELECT NEW.id_detalle INTO id_valor;
END IF;
END IF;

INSERT INTO auditoria (id_objeto, nombre_tabla, operacion, fecha)
VALUES (id_valor, TG_TABLE_NAME, TG_OP, current_timestamp);

RETURN NULL;
END;
$BODY$ LANGUAGE plpgsql;
/

DROP TRIGGER IF EXISTS trigger_auditoria_categoria ON categoria;
CREATE TRIGGER trigger_auditoria_categoria
    AFTER INSERT OR UPDATE OR DELETE ON categoria
    FOR EACH ROW
EXECUTE FUNCTION auditar_operacion();

DROP TRIGGER IF EXISTS trigger_auditoria_producto ON producto;
CREATE TRIGGER trigger_auditoria_producto
    AFTER INSERT OR UPDATE OR DELETE ON producto
    FOR EACH ROW
EXECUTE FUNCTION auditar_operacion();

DROP TRIGGER IF EXISTS trigger_auditoria_cliente ON cliente;
CREATE TRIGGER trigger_auditoria_cliente
    AFTER INSERT OR UPDATE OR DELETE ON cliente
    FOR EACH ROW
EXECUTE FUNCTION auditar_operacion();

DROP TRIGGER IF EXISTS trigger_auditoria_orden ON orden;
CREATE TRIGGER trigger_auditoria_orden
    AFTER INSERT OR UPDATE OR DELETE ON orden
    FOR EACH ROW
EXECUTE FUNCTION auditar_operacion();

DROP TRIGGER IF EXISTS trigger_auditoria_detalle_orden ON detalle_orden;
CREATE TRIGGER trigger_auditoria_detalle_orden
    AFTER INSERT OR UPDATE OR DELETE ON detalle_orden
    FOR EACH ROW
EXECUTE FUNCTION auditar_operacion();

-- DATOS DE EJEMPLO PARA POBLAR LA BASE DE DATOS
-- Se eliminan los elementos previamente almacenados
TRUNCATE TABLE detalle_orden RESTART IDENTITY CASCADE;
TRUNCATE TABLE orden RESTART IDENTITY CASCADE;
TRUNCATE TABLE producto RESTART IDENTITY CASCADE;
TRUNCATE TABLE categoria RESTART IDENTITY CASCADE;
TRUNCATE TABLE cliente RESTART IDENTITY CASCADE;
TRUNCATE TABLE auditoria RESTART IDENTITY CASCADE;

-- Se insertan los datos de ejemplo
INSERT INTO categoria (nombre) VALUES ('Electrónica'), ('Ropa'), ('Hogar'), ('Deportes'), ('Ocio');
INSERT INTO producto (nombre, descripcion, precio, stock, estado, id_categoria) VALUES
('Laptop', 'Laptop de alta gama', 1200.00, 10, 'disponible', 1),
('Camiseta', 'Camiseta de algodón', 20.00, 50, 'disponible', 2),
('Sofá', 'Sofá de 3 plazas', 300.00, 5, 'disponible', 3),
('Libro de Java', 'Guía completa de Java', 45.00, 20, 'disponible', 5),
('Pelota de fútbol', 'Pelota de fútbol profesional', 25.00, 30, 'disponible', 4),
('Muñeca', 'Muñeca de colección', 15.00, 40, 'disponible', 5);

INSERT INTO cliente (nombre, direccion, email, telefono, clave) VALUES
('Carlos Ruiz', 'Calle Luna 789', 'carlos.ruiz@example.com', '123123123', 'clave789'),
('Ana Torres', 'Avenida Sol 101', 'ana.torres@example.com', '321321321', 'clave101'),
('Juan Perez', 'Calle Falsa 123', 'juan.perez@example.com', '123456789', 'clave123'),
('Maria Gomez', 'Avenida Siempre Viva 456', 'maria.gomez@example.com', '987654321', 'clave456');

INSERT INTO orden (fecha_orden, estado, id_cliente, total) VALUES
(current_timestamp, 'pendiente', 1, 1220.00),
(current_timestamp, 'enviado', 2, 320.00),
(current_timestamp, 'pendiente', 3, 45.00),
(current_timestamp, 'enviado', 4, 40.00);

INSERT INTO detalle_orden (id_orden, id_producto, cantidad, precio_unitario) VALUES
(1, 1, 1, 1200.00),
(2, 2, 2, 20.00),
(2, 3, 1, 300.00),
(3, 4, 1, 45.00),
(4, 6, 2, 20.00);
