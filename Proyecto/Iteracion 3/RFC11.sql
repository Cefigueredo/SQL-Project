--@INFORMACIÓN DE LAS PERSONAS NO VACUNADAS EN UN RANGO DE FECHAS
--@Administrador del Plan de Vacunación-----------------------------------------------------------------------------------------------------
--@Ordenado por nombre de ciudadano
CREATE INDEX ciudadano_nombre_i
ON Ciudadano(nombre);
SELECT CIUDADANO.id, CIUDADANO.nombre, CIUDADANO.documento, CIUDADANO.fechanacimiento, CIUDADANO.numerocontacto, OFIEPS.nombre as EPS
FROM ((SELECT id, nombre, fechanacimiento, documento, direccion, numerocontacto, idoficinaeps, idpuntovacunacion
    FROM ciudadanos) CIUDADANO INNER JOIN (SELECT id, nombre
    FROM oficina_eps_regional)OFIEPS ON OFIEPS.id = CIUDADANO.idoficinaeps) LEFT JOIN (SELECT citas.fechayhora, citas.estadocita, citas.idciudadano
    FROM citas
    WHERE citas.estadocita != 'ASISTIDA'  AND citas.fechayhora  BETWEEN TO_TIMESTAMP('2021-05-01 09:14:00.0', 'YYYY-MM-DD HH24:MI:SS.FF')
                    AND TO_TIMESTAMP('2021-07-11 23:59:00.0', 'YYYY-MM-DD HH24:MI:SS.FF')) CITASENFECHA ON CIUDADANO.id = CITASENFECHA.idciudadano  
WHERE CITASENFECHA.estadocita != 'ASISTIDA' OR CITASENFECHA.estadocita is null
GROUP BY CIUDADANO.id, CIUDADANO.nombre, CIUDADANO.documento, CIUDADANO.fechanacimiento, CIUDADANO.numerocontacto, OFIEPS.nombre
ORDER BY CIUDADANO.nombre;

--@Ordenado por nombre de EPS
SELECT CIUDADANO.nombre, CIUDADANO.documento, CIUDADANO.fechanacimiento, CIUDADANO.numerocontacto, OFIEPS.nombre
FROM ((SELECT id, nombre, fechanacimiento, documento, direccion, numerocontacto, idoficinaeps, idpuntovacunacion
    FROM ciudadanos) CIUDADANO INNER JOIN (SELECT id, nombre
    FROM oficina_eps_regional)OFIEPS ON OFIEPS.id = CIUDADANO.idoficinaeps) LEFT JOIN (SELECT citas.fechayhora, citas.estadocita, citas.idciudadano
    FROM citas
    WHERE citas.estadocita != 'ASISTIDA'  AND citas.fechayhora  BETWEEN TO_TIMESTAMP('2021-05-01 09:14:00.0', 'YYYY-MM-DD HH24:MI:SS.FF')
                    AND TO_TIMESTAMP('2021-07-11 23:59:00.0', 'YYYY-MM-DD HH24:MI:SS.FF')) CITASENFECHA ON CIUDADANO.id = CITASENFECHA.idciudadano  
WHERE (CITASENFECHA.estadocita != 'ASISTIDA' OR CITASENFECHA.estadocita is null)
GROUP BY CIUDADANO.nombre, CIUDADANO.documento, CIUDADANO.fechanacimiento, CIUDADANO.numerocontacto, OFIEPS.nombre
ORDER BY OFIEPS.nombre;

--@Administrador de un punto de vacunación---------------------------------------------------------------------------------------------------
--@Ordenado por nombre de ciudadano
SELECT CIUDADANO.nombre, CIUDADANO.documento, CIUDADANO.fechanacimiento, CIUDADANO.numerocontacto, OFIEPS.nombre
FROM ((SELECT id, nombre, fechanacimiento, documento, direccion, numerocontacto, idoficinaeps, idpuntovacunacion
    FROM ciudadanos) CIUDADANO INNER JOIN (SELECT id, nombre
    FROM oficina_eps_regional)OFIEPS ON OFIEPS.id = CIUDADANO.idoficinaeps) LEFT JOIN (SELECT citas.fechayhora, citas.estadocita, citas.idciudadano
    FROM citas
    WHERE citas.estadocita != 'ASISTIDA'  AND citas.fechayhora  BETWEEN TO_TIMESTAMP('2021-05-01 09:14:00.0', 'YYYY-MM-DD HH24:MI:SS.FF')
                    AND TO_TIMESTAMP('2021-07-11 23:59:00.0', 'YYYY-MM-DD HH24:MI:SS.FF')) CITASENFECHA ON CIUDADANO.id = CITASENFECHA.idciudadano
    INNER JOIN (SELECT p.id
                    FROM punto_vacunacion p
                        WHERE id=1) PUNTO ON PUNTO.id = CIUDADANO.idpuntovacunacion
WHERE CITASENFECHA.estadocita != 'ASISTIDA' OR CITASENFECHA.estadocita is null
GROUP BY CIUDADANO.nombre, CIUDADANO.documento, CIUDADANO.fechanacimiento, CIUDADANO.numerocontacto, OFIEPS.nombre
ORDER BY CIUDADANO.nombre;

--@Ordenado por nombre de EPS
SELECT CIUDADANO.nombre, CIUDADANO.documento, CIUDADANO.fechanacimiento, CIUDADANO.numerocontacto, OFIEPS.nombre
FROM ((SELECT id, nombre, fechanacimiento, documento, direccion, numerocontacto, idoficinaeps, idpuntovacunacion
    FROM ciudadanos) CIUDADANO INNER JOIN (SELECT id, nombre
    FROM oficina_eps_regional)OFIEPS ON OFIEPS.id = CIUDADANO.idoficinaeps) LEFT JOIN (SELECT citas.fechayhora, citas.estadocita, citas.idciudadano
    FROM citas
    WHERE citas.estadocita != 'ASISTIDA'  AND citas.fechayhora  BETWEEN TO_TIMESTAMP('2021-05-01 09:14:00.0', 'YYYY-MM-DD HH24:MI:SS.FF')
                    AND TO_TIMESTAMP('2021-07-11 23:59:00.0', 'YYYY-MM-DD HH24:MI:SS.FF')) CITASENFECHA ON CIUDADANO.id = CITASENFECHA.idciudadano
    INNER JOIN (SELECT p.id
                    FROM punto_vacunacion p
                        WHERE id=1) PUNTO ON PUNTO.id = CIUDADANO.idpuntovacunacion
WHERE CITASENFECHA.estadocita != 'ASISTIDA' OR CITASENFECHA.estadocita is null
GROUP BY CIUDADANO.nombre, CIUDADANO.documento, CIUDADANO.fechanacimiento, CIUDADANO.numerocontacto, OFIEPS.nombre
ORDER BY OFIEPS.nombre;
