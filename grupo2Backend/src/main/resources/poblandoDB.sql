TRUNCATE TABLE detalle_orden RESTART IDENTITY CASCADE;
TRUNCATE TABLE orden RESTART IDENTITY CASCADE;
TRUNCATE TABLE producto RESTART IDENTITY CASCADE;
TRUNCATE TABLE categoria RESTART IDENTITY CASCADE;
TRUNCATE TABLE cliente RESTART IDENTITY CASCADE;
TRUNCATE TABLE auditoria RESTART IDENTITY CASCADE;
/

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
                                                               (current_timestamp, 'en_proceso', 1, 1220.00),
                                                               (current_timestamp, 'enviado', 2, 320.00),
                                                               (current_timestamp, 'en_proceso', 3, 45.00),
                                                               (current_timestamp, 'enviado', 4, 40.00);

INSERT INTO detalle_orden (id_orden, id_producto, cantidad, precio_unitario) VALUES
                                                                                 (1, 1, 1, 1200.00),
                                                                                 (2, 2, 2, 20.00),
                                                                                 (2, 3, 1, 300.00),
                                                                                 (3, 4, 1, 45.00),
                                                                                 (4, 6, 2, 20.00);
