--RFC12 Consultar funcionamiento. Por cada semana del año, los puntos de vacunacion con más afluencia
--Version Admin Plan de Vacunacion
SELECT SEMANA, MIN(NumVisitas) AS MIN
FROM (SELECT Semana, idPuntoVacunacion, count(idPuntoVacunacion) AS NumVisitas
    FROM (SELECT to_number(to_char(FECHAYHORA,'ww'))AS Semana,idpuntovacunacion AS idPuntoVacunacion
        FROM CITAS
        WHERE estadocita='ASISTIDA')
    GROUP BY Semana,idPuntoVacunacion
    ORDER BY Semana)
GROUP BY SEMANA
ORDER BY SEMANA;

SELECT Semana, idPuntoVacunacion, count(idPuntoVacunacion) AS NumVisitas
    FROM (SELECT to_number(to_char(FECHAYHORA,'ww'))AS Semana,idpuntovacunacion AS idPuntoVacunacion
        FROM CITAS
        WHERE estadocita='ASISTIDA')
    GROUP BY Semana,idPuntoVacunacion
    ORDER BY Semana;
    
    
    
SELECT MINSEMANA.SEMANA, MINIMO, IDPUNTOVACUNACION
FROM (SELECT SEMANA, MIN(NumVisitas) AS MINIMO
        FROM (SELECT Semana, idPuntoVacunacion, count(idPuntoVacunacion) AS NumVisitas
                FROM (SELECT to_number(to_char(FECHAYHORA,'ww'))AS Semana,idpuntovacunacion AS idPuntoVacunacion
                        FROM CITAS
                        WHERE estadocita='ASISTIDA')
                GROUP BY Semana,idPuntoVacunacion
                ORDER BY Semana)
        GROUP BY SEMANA
        ORDER BY SEMANA) MINSEMANA 
INNER JOIN
    (SELECT Semana, idPuntoVacunacion, count(idPuntoVacunacion) AS NumVisitas
        FROM (SELECT to_number(to_char(FECHAYHORA,'ww'))AS Semana,idpuntovacunacion AS idPuntoVacunacion
                FROM CITAS
                WHERE estadocita='ASISTIDA')
        GROUP BY Semana,idPuntoVacunacion
        ORDER BY Semana) PUNTOSSEMANA ON MINSEMANA.semana = PUNTOSSEMANA.semana AND minsemana.MINIMO = puntossemana.numvisitas;
