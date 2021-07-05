--@Author ce.figueredo@uniandes.edu.co
--@Author jm.rivera@uniandes.edu.co
--Punto 2 Informacion de los bares y horarios
SELECT nombre, horario, num_horario, tipo_de_bebida
FROM (
SELECT bares.nombre AS nombre,frecuentan.horario AS horario,COUNT(frecuentan.horario) AS NUM_HORARIO, tipo_bebida.nombre tipo_de_bebida, ROW_NUMBER()
    OVER(ORDER BY bares.nombre ASC, tipo_bebida.nombre, COUNT(frecuentan.horario) DESC) NUMERO_FILA
    FROM ((((bares
        INNER JOIN frecuentan ON bares.id = frecuentan.id_bar)
        INNER JOIN bebedores ON bebedores.id=frecuentan.id_bebedor)
        INNER JOIN gustan
            ON bebedores.id=gustan.id_bebedor)
        INNER JOIN bebidas
            ON bebidas.id=gustan.id_bebida)
        INNER JOIN tipo_bebida
            ON tipo_bebida.id=bebidas.tipo
        WHERE bebedores.ciudad <> bares.ciudad AND
            frecuentan.horario NOT LIKE 'todos'
    GROUP BY frecuentan.horario, bares.nombre, tipo_bebida.nombre
    ORDER BY bares.nombre ASC, tipo_bebida.nombre, COUNT(frecuentan.horario) DESC)
WHERE MOD(NUMERO_FILA,2)=1;