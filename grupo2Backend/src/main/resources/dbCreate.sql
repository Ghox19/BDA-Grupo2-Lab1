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
                                        id_cliente INTEGER,
                                        fecha TIMESTAMP
);


CREATE OR REPLACE FUNCTION auditar_operacion()
    RETURNS TRIGGER AS $BODY$
DECLARE
    id_valor INTEGER;
    id_cliente INTEGER;
BEGIN
    -- Tomando en cuenta que los usuarios se van a ir añadiendo conforme se registren en el sistema, y que los productos
    -- y categoría de estos no los maneja el usuario directamente en el front
    IF (TG_TABLE_NAME = 'orden') THEN
        IF (TG_OP = 'DELETE') THEN
            SELECT OLD.id_orden INTO id_valor;
            SELECT OLD.id_cliente INTO id_cliente;
        ELSE
            SELECT NEW.id_orden INTO id_valor;
            SELECT NEW.id_cliente INTO id_cliente;
        END IF;
    ELSIF (TG_TABLE_NAME = 'detalle_orden') THEN
        IF (TG_OP = 'DELETE') THEN
            SELECT OLD.id_detalle INTO id_valor;
            -- Un detalle orden contiene un id_orden, por lo que se puede obtener el id_cliente
            SELECT o.id_cliente INTO id_cliente
            FROM orden o
            WHERE OLD.id_orden = o.id_orden;
        ELSE
            SELECT NEW.id_detalle INTO id_valor;
            SELECT o.id_cliente INTO id_cliente
            FROM orden o
            WHERE NEW.id_orden = o.id_orden;
        END IF;
    END IF;

    INSERT INTO auditoria (id_objeto, nombre_tabla, operacion, id_cliente, fecha)
    VALUES (id_valor, TG_TABLE_NAME, TG_OP, id_cliente, current_timestamp);

    RETURN NULL;
END
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

CREATE OR REPLACE FUNCTION get_user_most_operations()
RETURNS TABLE (
    id_usuario INTEGER,
    tipo_operacion VARCHAR(200),
    total_operaciones BIGINT
) AS $BODY$
BEGIN
RETURN QUERY
    WITH ranked_operations AS (
        SELECT
            id_cliente,
            operacion,
            COUNT(*) as cantidad,
            RANK() OVER (PARTITION BY operacion ORDER BY COUNT(*) DESC) as rank
        FROM auditoria
        WHERE operacion IN ('INSERT', 'DELETE', 'UPDATE')
        GROUP BY id_cliente, operacion
    )
SELECT
    id_cliente,
    operacion,
    cantidad
FROM ranked_operations
WHERE rank = 1;

RETURN;
END;
$BODY$
LANGUAGE plpgsql;
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