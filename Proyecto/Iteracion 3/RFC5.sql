--RFC5 MOSTRAR EL PROCESO DE VACUNACI�N DE UN CIUDADANO

SELECT c.id, c.nombre, e.estado, e.comentario_descripcion
FROM ciudadanos c INNER JOIN estado_proceso e ON e.id_ciudadano = c.id
WHERE c.id = 1;