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
    -- Tomando en cuenta que los usuarios se van a ir añadiendo conforme se registren en el sistema, y que los productos
    -- y categoría de estos no los maneja el usuario directamente en el front
    IF (TG_TABLE_NAME = 'orden') THEN
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

CREATE OR REPLACE FUNCTION actualizar_estado_orden(idorden INTEGER)
    RETURNS VARCHAR AS $$
DECLARE
    cantidadp INT;
    stockproducto INT;
    idproducto INT;
    stockinsuficiente BOOLEAN := FALSE;
    estadoorden VARCHAR;
BEGIN
    FOR idproducto, cantidadp IN
        SELECT detalle.id_producto, detalle.cantidad
        FROM detalle_orden detalle
        WHERE detalle.id_orden = idorden
        LOOP
            SELECT prod.stock INTO stockproducto
            FROM producto prod
            WHERE prod.id_producto = idproducto;

            IF stockproducto < cantidadp THEN
                stockinsuficiente := TRUE;
                EXIT;
            END IF;
        END LOOP;

    IF stockinsuficiente THEN
        UPDATE orden
        SET estado = 'pendiente'
        WHERE id_orden = idorden;
        estadoorden := 'pendiente';
    ELSE
        FOR idproducto, cantidadp IN
            SELECT detalle.id_producto, detalle.cantidad
            FROM detalle_orden detalle
            WHERE detalle.id_orden = idorden
            LOOP
                UPDATE producto
                SET stock = stock - cantidadp
                WHERE id_producto = idproducto;

                -- Actualizar el estado del producto si el stock es 0
                IF (SELECT prod.stock FROM producto prod WHERE prod.id_producto = idproducto LIMIT 1) = 0 THEN
                    UPDATE producto
                    SET estado = 'agotado'
                    WHERE id_producto = idproducto;
                END IF;
            END LOOP;

        UPDATE orden
        SET estado = 'enviada'
        WHERE id_orden = idorden;
        estadoorden := 'enviada';
    END IF;

    RETURN estadoorden;
END;
$$ LANGUAGE plpgsql;
/

CREATE OR REPLACE FUNCTION calcular_total_orden(p_id_orden BIGINT)
       RETURNS DECIMAL(10,2) AS $BODY$
DECLARE
    v_total DECIMAL(10,2);
BEGIN
    SELECT SUM(d.cantidad * d.precio_unitario)
    INTO v_total
    FROM detalle_orden d
    WHERE d.id_orden = p_id_orden;

    UPDATE orden
    SET total = v_total
    where id_orden = p_id_orden;

    RETURN v_total;
END;
$BODY$ LANGUAGE plpgsql;
/

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