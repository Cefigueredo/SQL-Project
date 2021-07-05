SELECT c.fechayhora
FROM punto_vacunacion p INNER JOIN citas c ON p.id = c.idpuntovacunacion
WHERE c.estadocita = 'ASISTIDA' AND p.id = 1
GROUP BY c.fechayhora;