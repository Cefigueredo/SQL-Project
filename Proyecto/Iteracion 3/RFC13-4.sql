--RFC13 Consultar a los l�deres en gesti�n del proceso de vacunaci�n
--IV EPS que hayan vacunado con dosis a la mayor proporci�n de afiliados
    
SELECT IDOFICINAEPS, COUNT(*)
FROM CIUDADANOS
GROUP BY IDOFICINAEPS
ORDER BY IDOFICINAEPS;

SELECT IDOFICINAEPS, COUNT(*)
FROM CIUDADANOS
WHERE ID IN (
    SELECT IDCIUDADANO
    FROM(
        SELECT IDCIUDADANO, COUNT(*) AS NumeroDosis
        FROM CITAS
        WHERE estadocita='ASISTIDA'
        GROUP BY IDCIUDADANO)
    WHERE NumeroDosis=2)
GROUP BY IDOFICINAEPS
ORDER BY IDOFICINAEPS;