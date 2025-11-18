SET FOREIGN_KEY_CHECKS = 0;
DROP TABLE IF EXISTS itinerario_especie;
DROP TABLE IF EXISTS itinerario_zona;
DROP TABLE IF EXISTS animales;
DROP TABLE IF EXISTS especies;
DROP TABLE IF EXISTS habitats;
DROP TABLE IF EXISTS zonas;
DROP TABLE IF EXISTS itinerarios;
DROP TABLE IF EXISTS credenciales;
DROP TABLE IF EXISTS administradores;
DROP TABLE IF EXISTS guias;
DROP TABLE IF EXISTS cuidadores;
DROP TABLE IF EXISTS empleados;
DROP TABLE IF EXISTS contactos;
DROP TABLE IF EXISTS usuarios;
DROP TABLE IF EXISTS lista_datos;
DROP TABLE IF EXISTS dato;
SET FOREIGN_KEY_CHECKS = 1;

-- Tabla empleados (base para JOINED inheritance)
CREATE TABLE empleados (
  id_empleado VARCHAR(20) NOT NULL,
  nombre VARCHAR(100) NOT NULL,
  direccion VARCHAR(200),
  telefono VARCHAR(20),
  fecha_ingreso DATE,
  tipo VARCHAR(20) NOT NULL, -- calculado en la app (GUIA, CUIDADOR, ADMINISTRADOR)
  PRIMARY KEY (id_empleado),
  INDEX idx_empleados_tipo (tipo)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Tablas para subclases (JOINED strategy)
CREATE TABLE guias (
  id_empleado VARCHAR(20) NOT NULL,
  -- campos exclusivos de Guia (si los agregas)
  PRIMARY KEY (id_empleado),
  CONSTRAINT fk_guias_empleado FOREIGN KEY (id_empleado) REFERENCES empleados(id_empleado) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE cuidadores (
  id_empleado VARCHAR(20) NOT NULL,
  -- campos exclusivos de Cuidador (si los agregas)
  PRIMARY KEY (id_empleado),
  CONSTRAINT fk_cuidadores_empleado FOREIGN KEY (id_empleado) REFERENCES empleados(id_empleado) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE administradores (
  id_empleado VARCHAR(20) NOT NULL,
  -- campos exclusivos de Administrador (si los agregas)
  PRIMARY KEY (id_empleado),
  CONSTRAINT fk_admin_empleado FOREIGN KEY (id_empleado) REFERENCES empleados(id_empleado) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Credencial (presente en el UML)
CREATE TABLE credenciales (
  id_empleado VARCHAR(20) NOT NULL,
  password_hash VARCHAR(255) NOT NULL,
  tipo VARCHAR(50),
  PRIMARY KEY (id_empleado),
  CONSTRAINT fk_credenciales_empleado FOREIGN KEY (id_empleado) REFERENCES empleados(id_empleado) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Usuarios (separado para login API en tu código)
CREATE TABLE usuarios (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  username VARCHAR(150) NOT NULL UNIQUE,
  password VARCHAR(255) NOT NULL,
  rol VARCHAR(50) NOT NULL,
  INDEX idx_usuarios_rol (rol)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Zonas
CREATE TABLE zonas (
  id_zona INT AUTO_INCREMENT PRIMARY KEY,
  nombre VARCHAR(100) NOT NULL UNIQUE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Habitats
CREATE TABLE habitats (
  id_habitat INT AUTO_INCREMENT PRIMARY KEY,
  nombre VARCHAR(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Especies
CREATE TABLE especies (
  id_especie INT AUTO_INCREMENT PRIMARY KEY,
  nombre VARCHAR(100) NOT NULL,
  nombre_cientifico VARCHAR(150),
  descripcion VARCHAR(500),
  id_zona INT,
  CONSTRAINT fk_especie_zona FOREIGN KEY (id_zona) REFERENCES zonas(id_zona) ON DELETE SET NULL ON UPDATE CASCADE,
  INDEX idx_especies_nombre (nombre)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Itinerarios
CREATE TABLE itinerarios (
  id_itinerario INT AUTO_INCREMENT PRIMARY KEY,
  desc_recorrido VARCHAR(500),
  longitud INT,
  max_visitantes INT,
  num_especies INT,
  INDEX idx_itinerario_num_especies (num_especies)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Tabla de unión Itinerario <-> Zona con constraint de unicidad (ItinerarioZona)
CREATE TABLE itinerario_zona (
  id_itinerario_zona BIGINT AUTO_INCREMENT PRIMARY KEY,
  id_itinerario INT NOT NULL,
  id_zona INT NOT NULL,
  UNIQUE KEY uq_itinerario_zona (id_itinerario, id_zona),
  CONSTRAINT fk_itinerariozona_itinerario FOREIGN KEY (id_itinerario) REFERENCES itinerarios(id_itinerario) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT fk_itinerariozona_zona FOREIGN KEY (id_zona) REFERENCES zonas(id_zona) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Tabla de unión Itinerario <-> Especie con constraint de unicidad (ItinerarioEspecie)
CREATE TABLE itinerario_especie (
  id_itinerario_especie BIGINT AUTO_INCREMENT PRIMARY KEY,
  id_itinerario INT NOT NULL,
  id_especie INT NOT NULL,
  UNIQUE KEY uq_itinerario_especie (id_itinerario, id_especie),
  CONSTRAINT fk_itinerario_especie_itinerario FOREIGN KEY (id_itinerario) REFERENCES itinerarios(id_itinerario) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT fk_itinerario_especie_especie FOREIGN KEY (id_especie) REFERENCES especies(id_especie) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Animales
CREATE TABLE animales (
  id_animal INT AUTO_INCREMENT PRIMARY KEY,
  nombre VARCHAR(100) NOT NULL,
  descripcion VARCHAR(500),
  id_especie INT,
  id_habitat INT,
  id_zona INT,
  id_cuidador VARCHAR(20),
  CONSTRAINT fk_animal_especie FOREIGN KEY (id_especie) REFERENCES especies(id_especie) ON DELETE SET NULL ON UPDATE CASCADE,
  CONSTRAINT fk_animal_habitat FOREIGN KEY (id_habitat) REFERENCES habitats(id_habitat) ON DELETE SET NULL ON UPDATE CASCADE,
  CONSTRAINT fk_animal_zona FOREIGN KEY (id_zona) REFERENCES zonas(id_zona) ON DELETE SET NULL ON UPDATE CASCADE,
  CONSTRAINT fk_animal_cuidador FOREIGN KEY (id_cuidador) REFERENCES cuidadores(id_empleado) ON DELETE SET NULL ON UPDATE CASCADE,
  INDEX idx_animales_nombre (nombre)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Tablas auxiliares para DTOs / herramientas del UML (ListaDatos y Dato)
CREATE TABLE lista_datos (
  id_lista BIGINT AUTO_INCREMENT PRIMARY KEY,
  nombre VARCHAR(150) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE dato (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  id_lista BIGINT,
  contenido TEXT,
  CONSTRAINT fk_dato_lista FOREIGN KEY (id_lista) REFERENCES lista_datos(id_lista) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=UTF8MB4;


-- -----------------------------------
-- -----------------------------------
-- -----------------------------------
-- -----------------------------------

ALTER TABLE empleados
  ADD COLUMN usuario_id BIGINT NULL,
  ADD INDEX idx_empleados_usuario_id (usuario_id);
  
UPDATE empleados e
JOIN usuarios u ON u.username = e.id_empleado
SET e.usuario_id = u.id;

ALTER TABLE empleados
  ADD CONSTRAINT fk_empleados_usuario
  FOREIGN KEY (usuario_id) REFERENCES usuarios(id)
  ON DELETE SET NULL ON UPDATE CASCADE;
  
-- Asegurar que usuarios.username no tiene caracteres problemáticos y que coincide con empleados.id_empleado
ALTER TABLE usuarios
  ADD COLUMN empleado_id VARCHAR(20) NULL;

UPDATE usuarios u
JOIN empleados e ON u.username = e.id_empleado
SET u.empleado_id = e.id_empleado;

ALTER TABLE usuarios
  ADD CONSTRAINT fk_usuarios_empleado FOREIGN KEY (empleado_id) REFERENCES empleados(id_empleado);

-- ------------------------

ALTER TABLE itinerarios
  ADD COLUMN id_empleado_guia VARCHAR(20) NULL;

ALTER TABLE itinerarios
  ADD CONSTRAINT fk_itinerario_guia FOREIGN KEY (id_empleado_guia)
  REFERENCES empleados(id_empleado)
  ON DELETE SET NULL ON UPDATE CASCADE;