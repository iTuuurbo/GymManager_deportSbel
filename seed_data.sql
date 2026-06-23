-- ============================================================
--  GymManager - Datos de prueba (deportSbeltDB)
--  Respeta columnas camelCase y valores de enum de las entidades.
--  Las FKs se resuelven con subconsultas (no dependen de los IDs autogenerados).
--  Ejecutar UNA vez. Reabrir/re-ejecutar puede duplicar filas en tablas sin clave unica.
-- ============================================================

-- ---------- 1) Roles (nombre es UNIQUE -> INSERT IGNORE evita duplicados) ----------
INSERT IGNORE INTO Rol (nombre, estado) VALUES
('ADMINISTRADOR', 1),
('INSTRUCTOR',    1),
('NUTRICIONISTA', 1),
('CLIENTE',       1);

-- ---------- 2) Usuarios para instructores y nutricionistas (username UNIQUE) ----------
INSERT IGNORE INTO Usuario (idRol, username, password, estado) VALUES
((SELECT idRol FROM Rol WHERE nombre='INSTRUCTOR'),    'instructor_pedro', '1234', 1),
((SELECT idRol FROM Rol WHERE nombre='INSTRUCTOR'),    'instructor_lucia', '1234', 1),
((SELECT idRol FROM Rol WHERE nombre='INSTRUCTOR'),    'instructor_marco', '1234', 1),
((SELECT idRol FROM Rol WHERE nombre='NUTRICIONISTA'), 'nutri_sofia',      '1234', 1),
((SELECT idRol FROM Rol WHERE nombre='NUTRICIONISTA'), 'nutri_diego',      '1234', 1);

-- ---------- 3) Instructores (dni UNIQUE; estado = enum EstadoRegistro) ----------
INSERT IGNORE INTO Instructor (idUsuario, dni, nombres, apellidos, especialidad, telefono, estado) VALUES
((SELECT idUsuario FROM Usuario WHERE username='instructor_pedro'), '80011122', 'Pedro', 'Gomez Salas',   'Musculacion',  '988111222', 'ACTIVO'),
((SELECT idUsuario FROM Usuario WHERE username='instructor_lucia'), '80033344', 'Lucia', 'Ramirez Cruz',  'Funcional',    '988333444', 'ACTIVO'),
((SELECT idUsuario FROM Usuario WHERE username='instructor_marco'), '80055566', 'Marco', 'Diaz Pineda',   'Crossfit',     '988555666', 'ACTIVO');

-- ---------- 4) Nutricionistas (dni UNIQUE; estado = TINYINT) ----------
INSERT IGNORE INTO Nutricionista (idUsuario, dni, nombres, apellidos, colegiatura, telefono, estado) VALUES
((SELECT idUsuario FROM Usuario WHERE username='nutri_sofia'), '90011122', 'Sofia', 'Vargas Leon',  'CNP-1023', '977111222', 1),
((SELECT idUsuario FROM Usuario WHERE username='nutri_diego'), '90033344', 'Diego', 'Castro Rios',  'CNP-2045', '977333444', 1);

-- ---------- 5) Tipos de membresia (nombre UNIQUE; estado = TINYINT) ----------
INSERT IGNORE INTO TipoMembresia (nombre, duracionDias, precio, estado) VALUES
('Mensual',    30,  80.00, 1),
('Trimestral', 90, 210.00, 1),
('Anual',     365, 720.00, 1);

-- ---------- 6) Membresias de los 5 clientes existentes (estado = enum EstadoMembresia) ----------
INSERT INTO Membresia (idCliente, idTipoMembresia, fechaInicio, fechaFin, estado) VALUES
((SELECT idCliente FROM Cliente WHERE dni='72458913'), (SELECT idTipoMembresia FROM TipoMembresia WHERE nombre='Mensual'),    '2026-06-01', '2026-07-01', 'ACTIVA'),
((SELECT idCliente FROM Cliente WHERE dni='70125896'), (SELECT idTipoMembresia FROM TipoMembresia WHERE nombre='Trimestral'), '2026-04-15', '2026-07-14', 'ACTIVA'),
((SELECT idCliente FROM Cliente WHERE dni='73654120'), (SELECT idTipoMembresia FROM TipoMembresia WHERE nombre='Anual'),      '2026-01-10', '2027-01-10', 'ACTIVA'),
((SELECT idCliente FROM Cliente WHERE dni='71203654'), (SELECT idTipoMembresia FROM TipoMembresia WHERE nombre='Mensual'),    '2026-05-20', '2026-06-19', 'VENCIDA'),
((SELECT idCliente FROM Cliente WHERE dni='74859612'), (SELECT idTipoMembresia FROM TipoMembresia WHERE nombre='Mensual'),    '2026-06-18', '2026-07-18', 'PROXIMA_A_VENCER');

-- ---------- 7) Clases grupales (estado = enum EstadoGrupal: PROGRAMADA/CANCELADA/FINALIZADA) ----------
INSERT INTO ClaseGrupal (idInstructor, nombre, descripcion, capacidad, cuposDisponibles, fecha, horaInicio, horaFin, estado) VALUES
((SELECT idInstructor FROM Instructor WHERE dni='80011122'), 'Spinning matutino', 'Cardio intenso en bicicleta', 20, 18, '2026-06-25', '07:00:00', '08:00:00', 'PROGRAMADA'),
((SELECT idInstructor FROM Instructor WHERE dni='80033344'), 'Funcional tarde',   'Entrenamiento funcional',     15, 15, '2026-06-25', '18:00:00', '19:00:00', 'PROGRAMADA'),
((SELECT idInstructor FROM Instructor WHERE dni='80055566'), 'Crossfit WOD',      'Workout of the day',          12, 10, '2026-06-26', '19:00:00', '20:00:00', 'PROGRAMADA');

-- ---------- 8) Reservas (fechaReserva NOT NULL -> se setea explicito; estado = enum EstadoReserva) ----------
INSERT INTO Reserva (idClase, idCliente, fechaReserva, estado) VALUES
((SELECT idClase FROM ClaseGrupal WHERE nombre='Spinning matutino'), (SELECT idCliente FROM Cliente WHERE dni='72458913'), NOW(), 'CONFIRMADA'),
((SELECT idClase FROM ClaseGrupal WHERE nombre='Spinning matutino'), (SELECT idCliente FROM Cliente WHERE dni='70125896'), NOW(), 'CONFIRMADA'),
((SELECT idClase FROM ClaseGrupal WHERE nombre='Crossfit WOD'),      (SELECT idCliente FROM Cliente WHERE dni='73654120'), NOW(), 'LISTA_ESPERA');

-- ---------- 9) Asistencias ----------
INSERT INTO Asistencia (idCliente, fecha, horaIngreso, horaSalida) VALUES
((SELECT idCliente FROM Cliente WHERE dni='72458913'), '2026-06-22', '07:05:00', '08:30:00'),
((SELECT idCliente FROM Cliente WHERE dni='70125896'), '2026-06-22', '18:10:00', '19:40:00'),
((SELECT idCliente FROM Cliente WHERE dni='73654120'), '2026-06-23', '06:55:00', NULL);

-- ---------- 10) Citas nutricionales (estado = enum EstadoCita: PENDIENTE/CONFIRMADA/ATENDIDA/CANCELADA) ----------
INSERT INTO CitaNutricional (idCliente, idNutricionista, fecha, hora, estado, observaciones) VALUES
((SELECT idCliente FROM Cliente WHERE dni='72458913'), (SELECT idNutricionista FROM Nutricionista WHERE dni='90011122'), '2026-06-27', '10:00:00', 'PENDIENTE',  'Primera evaluacion'),
((SELECT idCliente FROM Cliente WHERE dni='71203654'), (SELECT idNutricionista FROM Nutricionista WHERE dni='90033344'), '2026-06-28', '11:30:00', 'CONFIRMADA', 'Plan de bajada de peso');

-- ============================================================
--  Verificacion rapida
-- ============================================================
-- SELECT COUNT(*) FROM Instructor;
-- SELECT COUNT(*) FROM Nutricionista;
-- SELECT COUNT(*) FROM TipoMembresia;
-- SELECT COUNT(*) FROM Membresia;
-- SELECT COUNT(*) FROM ClaseGrupal;
-- SELECT COUNT(*) FROM Reserva;
-- SELECT COUNT(*) FROM Asistencia;
-- SELECT COUNT(*) FROM CitaNutricional;
