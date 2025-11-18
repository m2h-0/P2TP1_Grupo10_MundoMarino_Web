-- Inserciones respetando el orden de constraints
SET FOREIGN_KEY_CHECKS = 0;

-- ZONAS
INSERT INTO zonas (id_zona, nombre) VALUES
  (1, 'Zona Azul'),
  (2, 'Zona Morada');

-- HABITATS
INSERT INTO habitats (id_habitat, nombre) VALUES
  (3, 'Oceano Abierto'),
  (4, 'Costas Rocosas'),
  (5, 'Aguas Profundas');

-- EMPLEADOS (tabla base)
INSERT INTO empleados (id_empleado, nombre, direccion, telefono, tipo) VALUES
  ('15', 'Jose Martinez', 'Calle del Mar 123', '555100200', 'GUIA'),
  ('16', 'Rocio Sanchez', 'Avenida Oceanica 45', '555300400', 'GUIA'),
  ('17', 'Gaston Lopez', 'Playa Principal 78', '555500600', 'CUIDADOR'),
  ('18', 'Mariana Diaz', 'Bahia Escondida 22', '555700800', 'CUIDADOR'),
  ('19', 'Pedro Gonzalez', 'Puerto Principal 10', '555900100', 'ADMINISTRADOR');

-- SUBTABLAS DE EMPLEADOS (JOINED)
INSERT INTO guias (id_empleado) VALUES
  ('15'), ('16');

INSERT INTO cuidadores (id_empleado) VALUES
  ('17'), ('18');

INSERT INTO administradores (id_empleado) VALUES
  ('19');

-- CREDENCIALES (si se desean crear entradas vacías de ejemplo)
INSERT INTO credenciales (id_empleado, password_hash, tipo) VALUES
  ('15', 'changeme_hash_15', 'GUIA'),
  ('16', 'changeme_hash_16', 'GUIA'),
  ('17', 'changeme_hash_17', 'CUIDADOR'),
  ('18', 'changeme_hash_18', 'CUIDADOR'),
  ('19', 'changeme_hash_19', 'ADMINISTRADOR');

-- ESPECIES
-- nota: la especie 7 referenciaba zona 3 en tus datos Java, aquí la dejo NULL porque no existe zona 3
INSERT INTO especies (id_especie, nombre, nombre_cientifico, descripcion, id_zona) VALUES
  (6, 'Delfin', 'Delphinus delphis', 'Mamifero marino inteligente y jugueton', 1),
  (7, 'Orca', 'Orcinus orca', 'El mayor miembro de la familia de los delfines', NULL),
  (8, 'Pinguino Sudamericano', 'Spheniscus magellanicus', 'Ave marina no voladora de plumaje blanco y negro', 2);

-- ANIMALES
-- Asigno cuidadores disponibles (17 y 18) alternando para ejemplificar.
INSERT INTO animales (id_animal, nombre, descripcion, id_especie, id_habitat, id_zona, id_cuidador) VALUES
  (9,  'Flipper', NULL, 6, 3, 1, '17'),
  (10, 'Marina',  NULL, 6, 3, 1, '17'),
  (11, 'Orki',    NULL, 7, 5, NULL, '18'),
  (12, 'Blanca',  NULL, 7, 5, NULL, '18'),
  (13, 'Pingu',   NULL, 8, 4, 2, '17'),
  (14, 'Lola',    NULL, 8, 4, 2, '17');

-- ITINERARIOS
INSERT INTO itinerarios (id_itinerario, desc_recorrido, longitud, max_visitantes, num_especies) VALUES
  (1, 'Recorrido Zona Azul - Observación de Delfines', 2500, 30, NULL),
  (2, 'Expedición Aguas Profundas - Encuentro con Orcas', 4000, 20, NULL),
  (3, 'Tour Costas Rocosas - Pingüinos Sudamericanos', 1800, 25, NULL),
  (4, 'Circuito Completo Marina - Todas las Zonas', 6000, 40, NULL),
  (5, 'Aventura Nocturna - Vida Marina Nocturna', 3200, 15, NULL);

-- ITINERARIO_ZONA (asociar itinerarios con zonas)
INSERT INTO itinerario_zona (id_itinerario, id_zona) VALUES
  (1, 1),  -- Recorrido Zona Azul
  (2, 1),  -- Expedición Aguas Profundas
  (3, 2),  -- Tour Costas Rocosas
  (4, 1),  -- Circuito Completo (Zona Azul)
  (4, 2),  -- Circuito Completo (Zona Morada)
  (5, 1);  -- Aventura Nocturna

SET FOREIGN_KEY_CHECKS = 1;