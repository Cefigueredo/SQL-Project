--@Author ce.figueredo@uniandes.edu.co
--@Author jm.rivera@uniandes.edu.co
--Punto 3 Los diez (10) bebedores con más apariciones en Parranderos
SELECT bebedores.id, bebedores.nombre, APARICIONES, bebedores.ciudad
FROM bebedores
    INNER JOIN 
        (SELECT id_bebedor, count(id_bebedor) AS apariciones
        FROM (SELECT frecuentan.id_bebedor
                FROM frecuentan 
                    UNION ALL
                SELECT bebedores.id
                FROM bebedores
                
                UNION ALL
                
                SELECT gustan.id_bebedor
                FROM gustan)
        GROUP BY id_bebedor)
    ON bebedores.id=id_bebedor
ORDER BY APARICIONES DESC, bebedores.ciudad
FETCH FIRST 10 ROW ONLY;