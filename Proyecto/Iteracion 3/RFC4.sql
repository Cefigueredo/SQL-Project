SELECT DISP.id, DISP.localizacion, count(CIUDNO) as INYECCIONES, DOSISDISPONIBLES
FROM (SELECT p.id, p.localizacion, count(d.disponible) as DOSISDISPONIBLES
    FROM punto_vacunacion p INNER JOIN dosis d ON p.id = d.idpuntovacunacion 
    WHERE d.disponible = 1
    GROUP BY p.id, p.localizacion, d.disponible)DISP INNER JOIN
     (SELECT count(c.idciudadano) as ASISTENCIAS, c.fechayhora as FECHAHORA, c.idciudadano AS CIUDNO, c.idpuntovacunacion AS PUNTOV
    FROM citas c
    WHERE c.estadocita = 'AGENDADA'
    GROUP BY c.idCiudadano, c.fechayhora, c.idpuntovacunacion)CITAASISTIDA ON DISP.id = citaasistida.puntov
WHERE FECHAHORA BETWEEN TO_TIMESTAMP('2021-04-11 09:14:00.0', 'YYYY-MM-DD HH24:MI:SS.FF')
                        AND TO_TIMESTAMP('2021-07-11 23:59:00.0', 'YYYY-MM-DD HH24:MI:SS.FF')
GROUP BY DISP.id, DISP.localizacion, DOSISDISPONIBLES;