load data into table citas
insert
fields terminated by ","
(
	id,
	fechayhora TIMESTAMP "DD/MM/YYYY HH24:MI",
	duracioncita,
	estadocita,
	idciudadano,
	idpuntovacunacion
)