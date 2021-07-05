--@Author ce.figueredo@uniandes.edu.co
--@Author jm.rivera@uniandes.edu.co
--Punto 4 La ciudad para lanzar una nueva bebida
SELECT CIUDAD_NO,  count(CIUDAD_NO)
FROM (SELECT bares.id, bares.nombre, bares.presupuesto, bares.cant_sedes, 
        CASE WHEN bares.ciudad = 'Bogotá' THEN 'Bogota'
            WHEN bares.ciudad = 'Medellín' THEN 'Medellin'
            ELSE bares.ciudad
        END AS CIUDAD_NO
    FROM bares)BAR_NO
    INNER JOIN sirven ON BAR_NO.id=sirven.id_bar
    INNER JOIN bebidas ON bebidas.id=sirven.id_bebida 
WHERE bebidas.tipo=1 AND bebidas.grado_alcohol BETWEEN 8 AND 12
GROUP BY CIUDAD_NO
ORDER BY count(CIUDAD_NO) DESC
FETCH FIRST 1 ROWS ONLY;