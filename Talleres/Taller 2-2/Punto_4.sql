SELECT a.table_name AS NOMBRETABLA, a.column_name AS NOMBRECOLUMNA, a.data_type AS TIPODEDATO,
        b.constraint_name AS NOMBRERESTRICCION, a.nullable AS PERMITENULOS
FROM all_tab_columns a INNER JOIN all_cons_columns b ON a.table_name = b.table_name AND a.column_name = b.column_name
WHERE a.owner = 'PARRANDEROS' AND b.owner = 'PARRANDEROS'
GROUP BY a.table_name, a.column_name, a.data_type, b.constraint_name, a.nullable
ORDER BY a.table_name, a.column_name, b.constraint_name;