--@Author ce.figueredo@uniandes.edu.co
--@Author jm.rivera@uniandes.edu.co
--Punto 6 Información de bares
SELECT *
FROM(SELECT bares.id, COUNT(bebidas.id) AS NUM_BEBIDAS,bares.ciudad, bares.nombre
    FROM (bares
        INNER JOIN sirven ON bares.id=sirven.id_bar)
        INNER JOIN bebidas ON sirven.id_bebida=bebidas.id
    WHERE bares.presupuesto='Alto' AND
    bebidas.grado_alcohol > 10
    GROUP BY bares.id, bares.ciudad, bares.nombre
    ORDER BY NUM_BEBIDAS)
WHERE num_bebidas BETWEEN 5 AND 10;