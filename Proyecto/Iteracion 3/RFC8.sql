SELECT c.nombre
FROM ciudadanos c INNER JOIN estado_de_salud e ON c.idestado_salud = e.id INNER JOIN punto_vacunacion p ON p.id = c.idpunto_vacunacion
        INNER JOIN oficina_eps_regional eps ON eps.id = c.idoficina_eps
WHERE c.ciudad = 'Bogotá' OR c.fecha_nacimiento BETWEEN TO_DATE('05/01/00','DD/MM/YY') AND TO_DATE('05/01/00','DD/MM/YY')
    OR e.descripcion = 'Otros/Sin comorbilidad' OR p.id = 1 OR eps.id = 1
GROUP BY c.nombre;
