--@INFORMACIÓN DE LAS PERSONAS VACUNADAS EN UN RANGO DE FECHAS

--@Administrador del Plan de Vacunación-----------------------------------------------------------------------------------------------------
--@Ordenado por nombre de ciudadano
SELECT ciudadanos.nombre, ciudadanos.documento, ciudadanos.fechanacimiento, ciudadanos.ciudad, ciudadanos.numerocontacto, eps.nombre as EPS
FROM ciudadanos INNER JOIN citas ON ciudadanos.id = citas.idciudadano  INNER JOIN oficina_eps_regional eps ON eps.id = ciudadanos.idoficinaeps
WHERE citas.estadocita = 'ASISTIDA' AND citas.fechayhora  BETWEEN TO_TIMESTAMP('2021-05-01 09:14:00.0', 'YYYY-MM-DD HH24:MI:SS.FF')
                    AND TO_TIMESTAMP('2021-07-11 23:59:00.0', 'YYYY-MM-DD HH24:MI:SS.FF')
GROUP BY ciudadanos.nombre, ciudadanos.documento, ciudadanos.fechanacimiento, ciudadanos.ciudad, ciudadanos.numerocontacto, citas.estadocita, eps.nombre
ORDER BY ciudadanos.nombre;
--@Ordenado por nombre de EPS
SELECT ciudadanos.nombre, ciudadanos.documento, ciudadanos.fechanacimiento, ciudadanos.ciudad, ciudadanos.numerocontacto, eps.nombre as EPS
FROM ciudadanos INNER JOIN citas ON ciudadanos.id = citas.idciudadano  INNER JOIN oficina_eps_regional eps ON eps.id = ciudadanos.idoficinaeps
WHERE citas.estadocita = 'ASISTIDA' AND citas.fechayhora  BETWEEN TO_TIMESTAMP('2021-05-01 09:14:00.0', 'YYYY-MM-DD HH24:MI:SS.FF')
                    AND TO_TIMESTAMP('2021-07-11 23:59:00.0', 'YYYY-MM-DD HH24:MI:SS.FF')
GROUP BY ciudadanos.nombre, ciudadanos.documento, ciudadanos.fechanacimiento, ciudadanos.ciudad, ciudadanos.numerocontacto, citas.estadocita, eps.nombre
ORDER BY EPS;

--@Administrador de un punto de vacunación---------------------------------------------------------------------------------------------------
--@Ordenado por nombre de ciudadano
SELECT ciudadanos.nombre, ciudadanos.documento, ciudadanos.fechanacimiento, ciudadanos.ciudad, ciudadanos.numerocontacto, eps.nombre as EPS
FROM ciudadanos INNER JOIN citas ON ciudadanos.id = citas.idciudadano  INNER JOIN oficina_eps_regional eps ON eps.id = ciudadanos.idoficinaeps
    INNER JOIN punto_vacunacion p ON p.id = ciudadanos.idpuntovacunacion
WHERE citas.estadocita = 'ASISTIDA' AND citas.fechayhora  BETWEEN TO_TIMESTAMP('2021-05-01 09:14:00.0', 'YYYY-MM-DD HH24:MI:SS.FF')
                    AND TO_TIMESTAMP('2021-07-11 23:59:00.0', 'YYYY-MM-DD HH24:MI:SS.FF') AND p.id = 1
GROUP BY ciudadanos.nombre, ciudadanos.documento, ciudadanos.fechanacimiento, ciudadanos.ciudad, ciudadanos.numerocontacto, citas.estadocita, eps.nombre
ORDER BY ciudadanos.nombre;
--@Ordenado por nombre de EPS
SELECT ciudadanos.nombre, ciudadanos.documento, ciudadanos.fechanacimiento, ciudadanos.ciudad, ciudadanos.numerocontacto, eps.nombre as EPS
FROM ciudadanos INNER JOIN citas ON ciudadanos.id = citas.idciudadano  INNER JOIN oficina_eps_regional eps ON eps.id = ciudadanos.idoficinaeps
    INNER JOIN punto_vacunacion p ON p.id = ciudadanos.idpuntovacunacion
WHERE citas.estadocita = 'ASISTIDA' AND citas.fechayhora  BETWEEN TO_TIMESTAMP('2021-05-01 09:14:00.0', 'YYYY-MM-DD HH24:MI:SS.FF')
                    AND TO_TIMESTAMP('2021-07-11 23:59:00.0', 'YYYY-MM-DD HH24:MI:SS.FF') AND p.id = 1
GROUP BY ciudadanos.nombre, ciudadanos.documento, ciudadanos.fechanacimiento, ciudadanos.ciudad, ciudadanos.numerocontacto, citas.estadocita, eps.nombre
ORDER BY EPS;
