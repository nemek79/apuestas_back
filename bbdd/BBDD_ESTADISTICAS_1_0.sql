CREATE TABLE t_est_apuestas_dia (

	id					INT 			NOT NULL AUTO_INCREMENT,
	fecha				DATE			NOT NULL,
	num_apuestas		INT			NOT NULL,
	num_encurso			INT			NOT NULL,
	num_ganadas			INT			NOT NULL,
	num_perdidas		INT			NOT NULL,
	num_pendientes		INT			NOT NULL,
	num_suspendidas		INT			NOT NULL,
	num_canceladas		INT			NOT NULL,
	num_parciales		INT			NOT NULL,
	num_push				INT			NOT NULL,
	importe_apostado	DECIMAL(8,3) 	NOT NULL,
	importe_potencial	DECIMAL(8,3) 	NOT NULL,
	importe_ganado		DECIMAL(8,3) 	NOT NULL,
	importe_perdido		DECIMAL(8,3) 	NOT NULL,
	importe_total		DECIMAL(8,3) 	NOT NULL,
	yield				DECIMAL(8,3) 	NOT NULL,

	PRIMARY KEY(id),
	UNIQUE (fecha)

) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE t_est_apuestas_semana (

	id					INT 			NOT NULL AUTO_INCREMENT,
	semana				INT(2)			NOT NULL,
	ano					INT(4)			NOT NULL,
	num_apuestas		INT			NOT NULL,
	num_encurso			INT			NOT NULL,
	num_ganadas			INT			NOT NULL,
	num_perdidas		INT			NOT NULL,
	num_pendientes		INT			NOT NULL,
	num_suspendidas		INT			NOT NULL,
	num_canceladas		INT			NOT NULL,
	num_parciales		INT			NOT NULL,
	num_push				INT			NOT NULL,
	importe_apostado	DECIMAL(8,3) 	NOT NULL,
	importe_potencial	DECIMAL(8,3) 	NOT NULL,
	importe_ganado		DECIMAL(8,3) 	NOT NULL,
	importe_perdido		DECIMAL(8,3) 	NOT NULL,
	importe_total		DECIMAL(8,3) 	NOT NULL,
	yield				DECIMAL(8,3) 	NOT NULL,

	PRIMARY KEY(id),
	UNIQUE (semana,ano)

) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE t_est_apuestas_mes (

	id					INT 			NOT NULL AUTO_INCREMENT,
	mes					INT(2)			NOT NULL,
	ano					INT(4)			NOT NULL,
	num_apuestas		INT			NOT NULL,
	num_encurso			INT			NOT NULL,
	num_ganadas			INT			NOT NULL,
	num_perdidas		INT			NOT NULL,
	num_pendientes		INT		NOT NULL,
	num_suspendidas		INT			NOT NULL,
	num_canceladas		INT		NOT NULL,
	num_parciales		INT			NOT NULL,
	num_push				INT			NOT NULL,
	importe_apostado	DECIMAL(8,3) 	NOT NULL,
	importe_potencial	DECIMAL(8,3) 	NOT NULL,
	importe_ganado		DECIMAL(8,3) 	NOT NULL,
	importe_perdido		DECIMAL(8,3) 	NOT NULL,
	importe_total		DECIMAL(8,3) 	NOT NULL,
	yield				DECIMAL(8,3) 	NOT NULL,

	PRIMARY KEY(id),
	UNIQUE (mes,ano)

) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE t_est_apuestas_ano (

	id					INT 			NOT NULL AUTO_INCREMENT,
	ano					INT(4)			NOT NULL,
	num_apuestas		INT		NOT NULL,
	num_encurso			INT		NOT NULL,
	num_ganadas			INT			NOT NULL,
	num_perdidas		INT			NOT NULL,
	num_pendientes		INT			NOT NULL,
	num_suspendidas		INT			NOT NULL,
	num_canceladas		INT			NOT NULL,
	num_parciales		INT			NOT NULL,
	num_push				INT			NOT NULL,
	importe_apostado	DECIMAL(8,3) 	NOT NULL,
	importe_potencial	DECIMAL(8,3) 	NOT NULL,
	importe_ganado		DECIMAL(8,3) 	NOT NULL,
	importe_perdido		DECIMAL(8,3) 	NOT NULL,
	importe_total		DECIMAL(8,3) 	NOT NULL,
	yield				DECIMAL(8,3) 	NOT NULL,

	PRIMARY KEY(id),
	UNIQUE (ano)

) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE t_est_apuestas_total (

	id					INT 			NOT NULL AUTO_INCREMENT,
	num_apuestas		INT			NOT NULL,
	num_ganadas			INT			NOT NULL,
	num_perdidas		INT			NOT NULL,
	num_pendientes		INT			NOT NULL,
	num_otras			INT			NOT NULL,
	num_push				INT			NOT NULL,
	importe_apostado	DECIMAL(8,3) 	NOT NULL,
	importe_potencial	DECIMAL(8,3) 	NOT NULL,
	importe_ganado		DECIMAL(8,3) 	NOT NULL,
	importe_perdido		DECIMAL(8,3) 	NOT NULL,
	importe_total		DECIMAL(8,3) 	NOT NULL,
	yield				DECIMAL(8,3) 	NOT NULL,

	PRIMARY KEY(id)

) ENGINE=InnoDB DEFAULT CHARSET=utf8;
