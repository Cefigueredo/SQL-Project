SELECT table_name AS NOMBRETABLA, data_type AS TIPODEDATO, COUNT(data_type) AS NUMCOLSTIPODATO , TRUNC(AVG(avg_col_len),2) AS PROMEDIOLONGITUDCOL
FROM all_tab_columns
WHERE owner = 'PARRANDEROS'
GROUP BY table_name, data_type
ORDER BY table_name;