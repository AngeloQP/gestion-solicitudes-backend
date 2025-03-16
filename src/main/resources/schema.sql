DROP TABLE IF EXISTS contactos;
DROP TABLE IF EXISTS solicitudes;
DROP TABLE IF EXISTS marca;
DROP TABLE IF EXISTS tipo_solicitud;

CREATE TABLE IF NOT EXISTS marca (
    id SERIAL PRIMARY KEY,
    descripcion VARCHAR(100) NOT NULL
);

CREATE TABLE IF NOT EXISTS tipo_solicitud (
    id SERIAL PRIMARY KEY,
    descripcion VARCHAR(100) NOT NULL
);

CREATE TABLE IF NOT EXISTS solicitudes (
    id SERIAL PRIMARY KEY,
    codigo VARCHAR(36) UNIQUE NOT NULL,
    marca_id INT NOT NULL,
    tipo_solicitud_id INT NOT NULL,
    nombre_contacto VARCHAR(255) NOT NULL,
    numero_contacto VARCHAR(20) NOT NULL,
    fecha_envio TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (marca_id) REFERENCES marca(id) ON DELETE CASCADE,
    FOREIGN KEY (tipo_solicitud_id) REFERENCES tipo_solicitud(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS contactos (
    id SERIAL PRIMARY KEY,
    solicitud_id INT NOT NULL,
    nombre_contacto VARCHAR(255) NOT NULL,
    numero_contacto VARCHAR(20) NOT NULL,
    FOREIGN KEY (solicitud_id) REFERENCES solicitudes(id) ON DELETE CASCADE
);