--RFC6 INFORME DE DOSIS
--Devuelve el número de dosis aplicadas y de las dosis no disponibles

SELECT vacunacion.dosis_aplicadas as DOSIS_APLICADAS, no_disponible as DOSIS_NO_DISPONIBLES
FROM vacunacion, (SELECT count(d.disponible) as no_disponible
                FROM dosis d
                WHERE d.disponible=0);