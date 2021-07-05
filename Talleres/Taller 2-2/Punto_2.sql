SELECT a.table_name, b.column_name, a.data_type
FROM all_tab_columns a LEFT JOIN all_cons_columns b ON a.table_name = b.table_name AND a.column_name = b.column_name
                        LEFT JOIN all_constraints c ON b.constraint_name = c.constraint_name
WHERE a.owner = 'PARRANDEROS' AND b.owner = 'PARRANDEROS' AND c.owner = 'PARRANDEROS'
    AND c.constraint_type = 'P'
GROUP BY a.table_name, b.column_name, a.data_type
ORDER BY a.table_name, b.column_name;