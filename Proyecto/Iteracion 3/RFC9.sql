--@ENCONTRAR LOS CIUDADANOS QUE ESTUVIERON CONTACTO CON OTRO CIUDADANO DADO
SELECT cd.nombre
FROM ciudadanos cd INNER JOIN citas ct ON cd.id = ct.idciudadano 
        INNER JOIN (SELECT p.id as punto
                    FROM ciudadanos cd INNER JOIN punto_vacunacion p ON cd.idpuntovacunacion = p.id
                    WHERE cd.id = 1)PUNTOVAC ON ct.idpuntovacunacion = punto
WHERE ct.fechayhora BETWEEN TO_TIMESTAMP('2021-05-01 09:14:00.0', 'YYYY-MM-DD HH24:MI:SS.FF')
                    AND TO_TIMESTAMP('2021-05-11 23:59:00.0', 'YYYY-MM-DD HH24:MI:SS.FF')
        AND ct.estadocita = 'ASISTIDA' 
GROUP BY cd.nombre;