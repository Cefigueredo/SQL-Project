--RF13 - REGISTRAR CAMBIO DE ESTADO DE UN PUNTO DE VACUNACIÓN
INSERT INTO CAMBIOS_PUNTO_VACUNACION (ID, DISPONIBLE,FECHACAMBIO,COMENTARIOS,ID_PUNTO_VACUNACION)
values (3, 0,'15/01/2021','Por sospecha de contagio de uno de los miembros del personal se cierra el punto por una semana. Si tenía una cita agendada, esta será reprogramada', 1);

INSERT INTO CAMBIOS_PUNTO_VACUNACION (ID, DISPONIBLE,FECHACAMBIO,COMENTARIOS,ID_PUNTO_VACUNACION)
values (4, 1,'22/01/2021','Luego de que las pruebas salieran negativas, se vuelve a abrir el punto de vacunación', 1);

--Para informar de los cambios, un ciuadadano puede consultar los cambios hechos en su punto de vacunación
SELECT * 
FROM CAMBIOS_PUNTO_VACUNACION
WHERE ID_PUNTO_VACUNACION=(SELECT IDPUNTOVACUNACION AS IDPV
    FROM CIUDADANOS
    WHERE ID=1);