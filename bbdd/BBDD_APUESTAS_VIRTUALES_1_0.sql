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
  `apuesta_id` int(8),
  PRIMARY KEY (`id`),
  KEY `fk_apuestas_virt_tipo` (`tipo_id`),
  KEY `fk_apuestas_virt_tipster` (`tipster_id`),
  KEY `fk_apuestas_virt_estado` (`estado_id`),
  KEY `fk_apuestas_virt_torneo` (`torneo_id`),
  CONSTRAINT `fk_apuestas_virt_estado` FOREIGN KEY (`estado_id`) REFERENCES `t_estados` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_apuestas_virt_tipo` FOREIGN KEY (`tipo_id`) REFERENCES `t_tipos` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_apuestas_virt_tipster` FOREIGN KEY (`tipster_id`) REFERENCES `t_tipsters` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_apuestas_virt_torneo` FOREIGN KEY (`torneo_id`) REFERENCES `t_torneos` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;
