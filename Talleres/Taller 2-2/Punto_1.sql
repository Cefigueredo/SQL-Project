SELECT all_tables.table_name as NOMBRETABLA, NUMCOLUMNAS, NUMCOLUMNAS-NOTNULL as NUMCOLSNULL, COUNT(NUMCOLSFKS) AS NUMCOLSFKS
FROM all_tables INNER JOIN (SELECT table_name, COUNT(table_name) as NUMCOLUMNAS
                            FROM all_tab_columns
                            WHERE owner='PARRANDEROS'
                            GROUP BY table_name) USERTABCOL 
                ON all_tables.table_name = USERTABCOL.table_name
                INNER JOIN (SELECT b.table_name, COUNT(b.constraint_name) as NOTNULL
                            FROM all_constraints a, all_cons_columns b
                            WHERE a.constraint_name = b.constraint_name AND b.owner='PARRANDEROS' AND a.constraint_type = 'P'
                            GROUP BY b.table_name
                            ORDER BY b.table_name) TABNULL
                ON all_tables.table_name = TABNULL.table_name
                LEFT JOIN (SELECT b.table_name, COUNT(b.constraint_name) as NUMCOLSFKS
                            FROM all_constraints a, all_cons_columns b
                            WHERE a.constraint_name = b.constraint_name AND a.constraint_type = 'R' 
                            AND b.owner = 'PARRANDEROS' AND a.owner = 'PARRANDEROS'
                            GROUP BY b.table_name, b.constraint_name) TABFKS ON all_tables.table_name = TABFKS.table_name
WHERE owner='PARRANDEROS'
GROUP BY all_tables.table_name, NUMCOLUMNAS, NOTNULL, NUMCOLSFKS
ORDER BY all_tables.table_name;