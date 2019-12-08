package es.vir2al.apuestas.constantes;

public class ConstApp {

  // Tipos de movimiento
  public static final String MOV_RETIRADA = "retirada";
  public static final String MOV_INGRESO = "ingreso";

  // TIPOS DE FILTRADO

  public static final String FILTER_ALL_MOVIMIENTOS = "all";
  public static final String FILTER_BY_CASA = "casa";
  public static final String FILTER_BY_FECHAS = "fechas";
  public static final String FILTER_BY_BOTH = "both";
  public static final String FILTER_BY_FECHAEVENTO = "fechaevento";

  // FORMATO FECHAS
  public static final String INPUT_DATE_FORMAT = "yyyyMMdd";

  // ESTADOS DE APUESTA
  public static final Integer ESTADO_PENDIENTE = 1;
  public static final Integer ESTADO_ENCURSO = 2;
  public static final Integer ESTADO_GANADA = 3;
  public static final Integer ESTADO_PERDIDA = 4;
  public static final Integer ESTADO_SUSPENDIDA = 5;
  public static final Integer ESTADO_CANCELADA = 6;
  public static final Integer ESTADO_PARCIAL = 7;
  public static final Integer ESTADO_PUSH = 8;

}
