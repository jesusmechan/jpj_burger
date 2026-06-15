/* =============================================================================
   JPJ Burgers - Script completo para SQL Server
   Base de datos para el sistema de pedidos de hamburguesería
   Compatible con la aplicación Java (MVC + patrones de diseño)
   ============================================================================= */

-- 1. CREAR BASE DE DATOS
IF DB_ID(N'JPJ_Burgers') IS NOT NULL
BEGIN
    ALTER DATABASE JPJ_Burgers SET SINGLE_USER WITH ROLLBACK IMMEDIATE;
    DROP DATABASE JPJ_Burgers;
END
GO

CREATE DATABASE JPJ_Burgers;
GO

USE JPJ_Burgers;
GO

-- 2. TABLAS DE CATÁLOGO

CREATE TABLE EstadoPedido (
    id_estado       INT IDENTITY(1,1) NOT NULL,
    codigo          VARCHAR(20)       NOT NULL,
    nombre          VARCHAR(50)       NOT NULL,
    orden           INT               NOT NULL,
    CONSTRAINT PK_EstadoPedido PRIMARY KEY (id_estado),
    CONSTRAINT UQ_EstadoPedido_Codigo UNIQUE (codigo)
);
GO

CREATE TABLE MetodoPago (
    id_metodo_pago  INT IDENTITY(1,1) NOT NULL,
    nombre          VARCHAR(30)       NOT NULL,
    activo          BIT               NOT NULL DEFAULT 1,
    CONSTRAINT PK_MetodoPago PRIMARY KEY (id_metodo_pago),
    CONSTRAINT UQ_MetodoPago_Nombre UNIQUE (nombre)
);
GO

CREATE TABLE Hamburguesa (
    id_hamburguesa  INT IDENTITY(1,1) NOT NULL,
    nombre          VARCHAR(80)       NOT NULL,
    descripcion     VARCHAR(300)      NOT NULL,
    precio_base     DECIMAL(10,2)     NOT NULL,
    activo          BIT               NOT NULL DEFAULT 1,
    CONSTRAINT PK_Hamburguesa PRIMARY KEY (id_hamburguesa),
    CONSTRAINT UQ_Hamburguesa_Nombre UNIQUE (nombre),
    CONSTRAINT CK_Hamburguesa_Precio CHECK (precio_base > 0)
);
GO

CREATE TABLE Extra (
    id_extra        INT IDENTITY(1,1) NOT NULL,
    nombre          VARCHAR(50)       NOT NULL,
    precio          DECIMAL(10,2)     NOT NULL,
    activo          BIT               NOT NULL DEFAULT 1,
    CONSTRAINT PK_Extra PRIMARY KEY (id_extra),
    CONSTRAINT UQ_Extra_Nombre UNIQUE (nombre),
    CONSTRAINT CK_Extra_Precio CHECK (precio >= 0)
);
GO

-- 3. TABLAS TRANSACCIONALES

CREATE TABLE Pedido (
    id_pedido       VARCHAR(8)        NOT NULL,
    nombre_cliente  VARCHAR(100)      NOT NULL,
    fecha_creacion  DATETIME2         NOT NULL DEFAULT SYSDATETIME(),
    id_estado       INT               NOT NULL,
    id_metodo_pago  INT               NULL,
    total           DECIMAL(10,2)     NOT NULL DEFAULT 0,
    CONSTRAINT PK_Pedido PRIMARY KEY (id_pedido),
    CONSTRAINT FK_Pedido_Estado FOREIGN KEY (id_estado)
        REFERENCES EstadoPedido (id_estado),
    CONSTRAINT FK_Pedido_MetodoPago FOREIGN KEY (id_metodo_pago)
        REFERENCES MetodoPago (id_metodo_pago),
    CONSTRAINT CK_Pedido_Total CHECK (total >= 0)
);
GO

CREATE TABLE DetallePedido (
    id_detalle      INT IDENTITY(1,1) NOT NULL,
    id_pedido       VARCHAR(8)        NOT NULL,
    descripcion     VARCHAR(500)      NOT NULL,
    precio_unitario DECIMAL(10,2)     NOT NULL,
    cantidad        INT               NOT NULL,
    subtotal        AS (precio_unitario * cantidad) PERSISTED,
    CONSTRAINT PK_DetallePedido PRIMARY KEY (id_detalle),
    CONSTRAINT FK_DetallePedido_Pedido FOREIGN KEY (id_pedido)
        REFERENCES Pedido (id_pedido) ON DELETE CASCADE,
    CONSTRAINT CK_DetallePedido_Cantidad CHECK (cantidad > 0),
    CONSTRAINT CK_DetallePedido_Precio CHECK (precio_unitario > 0)
);
GO

-- Tabla opcional: relación detalle-extras (normalización adicional)
CREATE TABLE DetallePedidoExtra (
    id_detalle_extra INT IDENTITY(1,1) NOT NULL,
    id_detalle       INT               NOT NULL,
    id_extra         INT               NOT NULL,
    CONSTRAINT PK_DetallePedidoExtra PRIMARY KEY (id_detalle_extra),
    CONSTRAINT FK_DetallePedidoExtra_Detalle FOREIGN KEY (id_detalle)
        REFERENCES DetallePedido (id_detalle) ON DELETE CASCADE,
    CONSTRAINT FK_DetallePedidoExtra_Extra FOREIGN KEY (id_extra)
        REFERENCES Extra (id_extra),
    CONSTRAINT UQ_DetallePedidoExtra UNIQUE (id_detalle, id_extra)
);
GO

-- 4. ÍNDICES

CREATE INDEX IX_Pedido_FechaCreacion ON Pedido (fecha_creacion DESC);
CREATE INDEX IX_Pedido_Estado ON Pedido (id_estado);
CREATE INDEX IX_DetallePedido_Pedido ON DetallePedido (id_pedido);
GO

-- 5. DATOS INICIALES (CATÁLOGO)

INSERT INTO EstadoPedido (codigo, nombre, orden) VALUES
('PENDIENTE',      'PENDIENTE',       1),
('EN_PREPARACION', 'EN PREPARACIÓN',  2),
('LISTO',          'LISTO',           3),
('ENTREGADO',      'ENTREGADO',       4);
GO

INSERT INTO MetodoPago (nombre) VALUES
('Efectivo'),
('Tarjeta'),
('Yape');
GO

INSERT INTO Hamburguesa (nombre, descripcion, precio_base) VALUES
('Clásica JPJ', 'Carne 150g, lechuga, tomate, cebolla y salsa especial', 15.90),
('Veggie JPJ',  'Medallón de lentejas, aguacate, rúcula y mayo vegana', 14.50),
('BBQ JPJ',     'Doble carne, bacon, cebolla caramelizada y salsa BBQ', 18.90);
GO

INSERT INTO Extra (nombre, precio) VALUES
('Queso extra', 2.00),
('Bacon',       3.00),
('Aguacate',    2.50);
GO

-- 6. VISTAS

CREATE VIEW vw_PedidosResumen
AS
SELECT
    p.id_pedido,
    p.nombre_cliente,
    p.fecha_creacion,
    e.nombre AS estado,
    e.codigo AS codigo_estado,
    mp.nombre AS metodo_pago,
    p.total,
    (SELECT COUNT(*) FROM DetallePedido d WHERE d.id_pedido = p.id_pedido) AS cantidad_items
FROM Pedido p
INNER JOIN EstadoPedido e ON e.id_estado = p.id_estado
LEFT JOIN MetodoPago mp ON mp.id_metodo_pago = p.id_metodo_pago;
GO

CREATE VIEW vw_DetallePedidoCompleto
AS
SELECT
    d.id_detalle,
    d.id_pedido,
    d.descripcion,
    d.precio_unitario,
    d.cantidad,
    d.subtotal
FROM DetallePedido d;
GO

-- 7. FUNCIONES AUXILIARES

CREATE FUNCTION dbo.fn_CalcularTotalPedido (@id_pedido VARCHAR(8))
RETURNS DECIMAL(10,2)
AS
BEGIN
    DECLARE @total DECIMAL(10,2);

    SELECT @total = ISNULL(SUM(subtotal), 0)
    FROM DetallePedido
    WHERE id_pedido = @id_pedido;

    RETURN @total;
END;
GO

-- 8. PROCEDIMIENTOS ALMACENADOS

-- Registrar un pedido completo (cabecera + detalle)
CREATE PROCEDURE dbo.sp_RegistrarPedido
    @id_pedido      VARCHAR(8),
    @nombre_cliente VARCHAR(100),
    @metodo_pago    VARCHAR(30),
    @detalle_json   NVARCHAR(MAX) -- JSON: [{"descripcion":"...","precio_unitario":15.90,"cantidad":2}]
AS
BEGIN
    SET NOCOUNT ON;
    SET XACT_ABORT ON;

    BEGIN TRY
        BEGIN TRANSACTION;

        DECLARE @id_estado INT = (SELECT id_estado FROM EstadoPedido WHERE codigo = 'PENDIENTE');
        DECLARE @id_metodo_pago INT = (SELECT id_metodo_pago FROM MetodoPago WHERE nombre = @metodo_pago);

        IF @id_metodo_pago IS NULL
            THROW 50001, 'Método de pago no válido.', 1;

        IF @nombre_cliente IS NULL OR LTRIM(RTRIM(@nombre_cliente)) = ''
            THROW 50002, 'El nombre del cliente es obligatorio.', 1;

        IF ISJSON(@detalle_json) = 0 OR (SELECT COUNT(*) FROM OPENJSON(@detalle_json)) = 0
            THROW 50003, 'Debe incluir al menos un ítem en el pedido.', 1;

        INSERT INTO Pedido (id_pedido, nombre_cliente, id_estado, id_metodo_pago, total)
        VALUES (@id_pedido, LTRIM(RTRIM(@nombre_cliente)), @id_estado, @id_metodo_pago, 0);

        INSERT INTO DetallePedido (id_pedido, descripcion, precio_unitario, cantidad)
        SELECT
            @id_pedido,
            JSON_VALUE(value, '$.descripcion'),
            CAST(JSON_VALUE(value, '$.precio_unitario') AS DECIMAL(10,2)),
            CAST(JSON_VALUE(value, '$.cantidad') AS INT)
        FROM OPENJSON(@detalle_json);

        UPDATE Pedido
        SET total = dbo.fn_CalcularTotalPedido(@id_pedido)
        WHERE id_pedido = @id_pedido;

        COMMIT TRANSACTION;

        SELECT * FROM vw_PedidosResumen WHERE id_pedido = @id_pedido;
    END TRY
    BEGIN CATCH
        IF @@TRANCOUNT > 0 ROLLBACK TRANSACTION;
        THROW;
    END CATCH
END;
GO

-- Listar todos los pedidos
CREATE PROCEDURE dbo.sp_ListarPedidos
AS
BEGIN
    SET NOCOUNT ON;

    SELECT *
    FROM vw_PedidosResumen
    ORDER BY fecha_creacion DESC;
END;
GO

-- Buscar pedido por ID con su detalle
CREATE PROCEDURE dbo.sp_BuscarPedidoPorId
    @id_pedido VARCHAR(8)
AS
BEGIN
    SET NOCOUNT ON;

    SELECT * FROM vw_PedidosResumen WHERE id_pedido = @id_pedido;
    SELECT * FROM vw_DetallePedidoCompleto WHERE id_pedido = @id_pedido;
END;
GO

-- Avanzar estado del pedido (patrón State)
CREATE PROCEDURE dbo.sp_AvanzarEstadoPedido
    @id_pedido VARCHAR(8)
AS
BEGIN
    SET NOCOUNT ON;

    DECLARE @codigo_actual VARCHAR(20);
    DECLARE @id_estado_nuevo INT;
    DECLARE @nombre_estado_nuevo VARCHAR(50);

    SELECT @codigo_actual = e.codigo
    FROM Pedido p
    INNER JOIN EstadoPedido e ON e.id_estado = p.id_estado
    WHERE p.id_pedido = @id_pedido;

    IF @codigo_actual IS NULL
        THROW 50004, 'Pedido no encontrado.', 1;

    IF @codigo_actual = 'ENTREGADO'
        THROW 50005, 'El pedido ya fue entregado.', 1;

    SELECT
        @id_estado_nuevo = id_estado,
        @nombre_estado_nuevo = nombre
    FROM EstadoPedido
    WHERE codigo = CASE @codigo_actual
        WHEN 'PENDIENTE'      THEN 'EN_PREPARACION'
        WHEN 'EN_PREPARACION' THEN 'LISTO'
        WHEN 'LISTO'          THEN 'ENTREGADO'
        ELSE @codigo_actual
    END;

    UPDATE Pedido
    SET id_estado = @id_estado_nuevo
    WHERE id_pedido = @id_pedido;

    SELECT
        p.id_pedido,
        p.nombre_cliente,
        @nombre_estado_nuevo AS estado,
        p.total
    FROM Pedido p
    WHERE p.id_pedido = @id_pedido;
END;
GO

-- Actualizar total de un pedido (por si se modifica el detalle)
CREATE PROCEDURE dbo.sp_ActualizarTotalPedido
    @id_pedido VARCHAR(8)
AS
BEGIN
    SET NOCOUNT ON;

    UPDATE Pedido
    SET total = dbo.fn_CalcularTotalPedido(@id_pedido)
    WHERE id_pedido = @id_pedido;
END;
GO

-- 9. DATOS DE EJEMPLO (OPCIONAL)

DECLARE @detalle NVARCHAR(MAX) = N'[
    {"descripcion":"Clásica JPJ (Carne 150g, lechuga, tomate, cebolla y salsa especial + Queso extra)","precio_unitario":17.90,"cantidad":2},
    {"descripcion":"BBQ JPJ (Doble carne, bacon, cebolla caramelizada y salsa BBQ)","precio_unitario":18.90,"cantidad":1}
]';

EXEC dbo.sp_RegistrarPedido
    @id_pedido = 'A1B2C3D4',
    @nombre_cliente = 'Juan Pérez',
    @metodo_pago = 'Yape',
    @detalle_json = @detalle;
GO

EXEC dbo.sp_AvanzarEstadoPedido @id_pedido = 'A1B2C3D4';
GO

-- 10. CONSULTAS DE VERIFICACIÓN

PRINT '=== RESUMEN DE PEDIDOS ===';
SELECT * FROM vw_PedidosResumen;

PRINT '=== DETALLE DEL PEDIDO DE EJEMPLO ===';
SELECT * FROM vw_DetallePedidoCompleto WHERE id_pedido = 'A1B2C3D4';

PRINT '=== CATÁLOGO DE HAMBURGUESAS ===';
SELECT * FROM Hamburguesa WHERE activo = 1;

PRINT '=== CATÁLOGO DE EXTRAS ===';
SELECT * FROM Extra WHERE activo = 1;

PRINT 'Base de datos JPJ_Burgers creada correctamente.';
GO
