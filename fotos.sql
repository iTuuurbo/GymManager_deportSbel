-- ============================================================
--  Fotos de perfil (deportSbeltDB)
--  La columna directorioFotoPerfil se CREA SOLA al reiniciar la app
--  (la entidad la agrega via ddl-auto=update). Solo corre estos UPDATE
--  para poner las URLs. Ejecutar como SCRIPT (Alt+X en DBeaver).
--
--  Si la app aun no la creo, puedes crearla manual (una sola vez):
--    ALTER TABLE Cliente      ADD COLUMN directorioFotoPerfil VARCHAR(255);
--    ALTER TABLE Instructor   ADD COLUMN directorioFotoPerfil VARCHAR(255);
--    ALTER TABLE Nutricionista ADD COLUMN directorioFotoPerfil VARCHAR(255);
-- ============================================================

UPDATE Cliente SET directorioFotoPerfil='https://randomuser.me/api/portraits/women/65.jpg' WHERE dni='72458913';
UPDATE Cliente SET directorioFotoPerfil='https://randomuser.me/api/portraits/men/32.jpg'   WHERE dni='70125896';
UPDATE Cliente SET directorioFotoPerfil='https://randomuser.me/api/portraits/women/68.jpg' WHERE dni='73654120';
UPDATE Cliente SET directorioFotoPerfil='https://randomuser.me/api/portraits/men/45.jpg'   WHERE dni='71203654';
UPDATE Cliente SET directorioFotoPerfil='https://randomuser.me/api/portraits/women/12.jpg' WHERE dni='74859612';

UPDATE Instructor SET directorioFotoPerfil='https://randomuser.me/api/portraits/men/11.jpg'   WHERE dni='80011122';
UPDATE Instructor SET directorioFotoPerfil='https://randomuser.me/api/portraits/women/22.jpg' WHERE dni='80033344';
UPDATE Instructor SET directorioFotoPerfil='https://randomuser.me/api/portraits/men/33.jpg'   WHERE dni='80055566';

UPDATE Nutricionista SET directorioFotoPerfil='https://randomuser.me/api/portraits/women/41.jpg' WHERE dni='90011122';
UPDATE Nutricionista SET directorioFotoPerfil='https://randomuser.me/api/portraits/men/52.jpg'   WHERE dni='90033344';
