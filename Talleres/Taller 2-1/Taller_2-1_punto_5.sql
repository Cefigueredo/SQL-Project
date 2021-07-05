--@Author ce.figueredo@uniandes.edu.co
--@Author jm.rivera@uniandes.edu.co
--Punto 5 2.5.	Las diez (10) bebidas alcohólicas que más gustan a los bebedores “Viajeros”
SELECT bebidas.id, COUNT(bebidas.id), bebidas.nombre, tipo_bebida.nombre
FROM ((bebedores
    INNER JOIN gustan ON bebedores.id=gustan.id_bebedor)
    INNER JOIN bebidas ON bebidas.id=gustan.id_bebida)
    INNER JOIN tipo_bebida ON bebidas.tipo=tipo_bebida.id
WHERE bebedores.id IN (SELECT bebedores.id
    FROM (((bebedores
        INNER JOIN frecuentan ON frecuentan.id_bebedor=bebedores.id)
        INNER JOIN bares ON frecuentan.id_bar=bares.id)
        INNER JOIN (SELECT bebedores.id AS beb_id, COUNT(bebedores.id)AS VIAJERO
                FROM (bebedores
                    INNER JOIN frecuentan ON frecuentan.id_bebedor=bebedores.id)
                    INNER JOIN bares ON frecuentan.id_bar=bares.id
                WHERE bares.ciudad<>bebedores.ciudad
                GROUP BY bebedores.id
                ORDER BY bebedores.id) ON beb_id=bebedores.id)
        INNER JOIN (SELECT bebedores.id AS beb_id2, COUNT(bebedores.id)AS LOCAL
                FROM (bebedores
                    INNER JOIN frecuentan ON frecuentan.id_bebedor=bebedores.id)
                    INNER JOIN bares ON frecuentan.id_bar=bares.id
                WHERE bares.ciudad=bebedores.ciudad
                GROUP BY bebedores.id
                ORDER BY bebedores.id) ON beb_id2=bebedores.id
    WHERE VIAJERO>LOCAL
    GROUP BY bebedores.id) AND
    bebidas.grado_alcohol>0
GROUP BY bebidas.id, bebidas.nombre, tipo_bebida.nombre
ORDER BY COUNT(bebidas.id)DESC
FETCH FIRST 10 ROWS ONLY;