use apuestasbd;

delete from t_apuestas;
delete from t_apuestas_virtuales;
delete from t_movimientos;
delete from t_torneos;

drop table if exists t_apuestas_virtuales;
drop table if exists t_apuestas;
drop table if exists t_estados;
drop table if exists t_tipos;
drop table if exists t_tipsters;
drop table if exists t_torneos;
drop table if exists t_movimientos;
drop table if exists t_casas;
drop table if exists t_deportes;

CREATE TABLE `t_casas` (
  `id` int(2) NOT NULL,
  `descripcion` varchar(32) NOT NULL,
  `url` varchar(128) NOT NULL,
  `cantidad` decimal(9,3) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO t_casas
VALUES (0,'VIRTUAL','http:\\vir2al.es',500);


CREATE TABLE `t_deportes` (
  `id` int(2) NOT NULL,
  `descripcion` varchar(32) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO t_deportes
VALUES (0,'OTROS');


CREATE TABLE `t_estados` (
  `id` int(2) NOT NULL,
  `descripcion` varchar(32) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO t_estados
VALUES (1,'PENDIENTE');

INSERT INTO t_estados
VALUES (2,'EN CURSO');

INSERT INTO t_estados
VALUES (3,'GANADA');

INSERT INTO t_estados
VALUES (4,'PERDIDA');

INSERT INTO t_estados
VALUES (5,'SUSPENDIDA');

INSERT INTO t_estados
VALUES (6,'CANCELADA');

INSERT INTO t_estados
VALUES (7,'PARCIAL');

INSERT INTO t_estados
VALUES (8,'PUSH');

CREATE TABLE `t_tipos` (
  `id` int(3) NOT NULL,
  `descripcion` varchar(32) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO t_tipos
VALUES (0,'OTROS');

INSERT INTO t_tipos
VALUES (1,'RESULTADO');

INSERT INTO t_tipos
VALUES (2,'PUNTOS/GOLES');

INSERT INTO t_tipos
VALUES (3,'TARJETAS');

INSERT INTO t_tipos
VALUES (4,'AMBOS MARCAN');

INSERT INTO t_tipos
VALUES (5,'PORTERÍA A CERO');

INSERT INTO t_tipos
VALUES (6,'CORNERS');

INSERT INTO t_tipos
VALUES (7,'APUESTA CREADA');

INSERT INTO t_tipos
VALUES (8,'FUNBET');

INSERT INTO t_tipos
VALUES (9,'COMBINADA');

CREATE TABLE `t_tipsters` (
  `id` int(4) NOT NULL,
  `descripcion` varchar(64) NOT NULL,
  `url` varchar(256) DEFAULT NULL,
  `telegram` varchar(64) DEFAULT NULL,
  `activo` tinyint(4) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO t_tipsters 
VALUES (0,'OTROS',null,null,1);


CREATE TABLE `t_torneos` (
  `id` int(4) NOT NULL,
  `descripcion` varchar(32) NOT NULL,
  `deporte_id` int(2) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_torneos_deporte` (`deporte_id`),
  CONSTRAINT `fk_torneos_deporte` FOREIGN KEY (`deporte_id`) REFERENCES `t_deportes` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO t_torneos
VALUES (0,'OTROS',0);


CREATE TABLE `t_movimientos` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `observacion` varchar(64) DEFAULT NULL,
  `casa_id` int(2) NOT NULL,
  `fecha` date NOT NULL,
  `importe` decimal(8,3) NOT NULL,
  `tipo` varchar(16) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_movimientos_casa` (`casa_id`),
  CONSTRAINT `fk_movimientos_casa` FOREIGN KEY (`casa_id`) REFERENCES `t_casas` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

CREATE TABLE `t_apuestas` (
  `id` int(8) NOT NULL AUTO_INCREMENT,
  `fecha_alta` date NOT NULL,
  `fecha_evento` date NOT NULL,
  `casa_id` int(2) NOT NULL,
  `torneo_id` int(4) NOT NULL,
  `tipo_id` int(3) NOT NULL,
  `tipster_id` int(4) NOT NULL,
  `estado_id` int(2) NOT NULL,
  `descripcion` varchar(128) NOT NULL,
  `apuesta` varchar(64) NOT NULL,
  `cuota` decimal(5,3) NOT NULL,
  `importe` decimal(8,3) NOT NULL,
  `ganancia` decimal(8,3) DEFAULT NULL,
  `stake` decimal(3,1) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_apuestas_casa` (`casa_id`),
  KEY `fk_apuestas_tipo` (`tipo_id`),
  KEY `fk_apuestas_tipster` (`tipster_id`),
  KEY `fk_apuestas_estado` (`estado_id`),
  KEY `fk_apuestas_torneo` (`torneo_id`),
  CONSTRAINT `fk_apuestas_casa` FOREIGN KEY (`casa_id`) REFERENCES `t_casas` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_apuestas_estado` FOREIGN KEY (`estado_id`) REFERENCES `t_estados` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_apuestas_tipo` FOREIGN KEY (`tipo_id`) REFERENCES `t_tipos` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_apuestas_tipster` FOREIGN KEY (`tipster_id`) REFERENCES `t_tipsters` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_apuestas_torneo` FOREIGN KEY (`torneo_id`) REFERENCES `t_torneos` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=252 DEFAULT CHARSET=utf8;

CREATE TABLE `t_apuestas_virtuales` (
  `id` int(8) NOT NULL AUTO_INCREMENT,
  `fecha_alta` date NOT NULL,
  `fecha_evento` date NOT NULL,
  `torneo_id` int(4) NOT NULL,
  `tipo_id` int(3) NOT NULL,
  `tipster_id` int(4) NOT NULL,
  `estado_id` int(2) NOT NULL,
  `descripcion` varchar(128) NOT NULL,
  `apuesta` varchar(64) NOT NULL,
  `cuota` decimal(5,3) NOT NULL,
  `importe` decimal(8,3) NOT NULL,
  `ganancia` decimal(8,3) DEFAULT NULL,
  `stake` decimal(3,1) DEFAULT NULL,
  `apuesta_id` int(8) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_apuestas_virt_tipo` (`tipo_id`),
  KEY `fk_apuestas_virt_tipster` (`tipster_id`),
  KEY `fk_apuestas_virt_estado` (`estado_id`),
  KEY `fk_apuestas_virt_torneo` (`torneo_id`),
  CONSTRAINT `fk_apuestas_virt_estado` FOREIGN KEY (`estado_id`) REFERENCES `t_estados` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_apuestas_virt_tipo` FOREIGN KEY (`tipo_id`) REFERENCES `t_tipos` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_apuestas_virt_tipster` FOREIGN KEY (`tipster_id`) REFERENCES `t_tipsters` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_apuestas_virt_torneo` FOREIGN KEY (`torneo_id`) REFERENCES `t_torneos` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;

