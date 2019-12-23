package es.vir2al.apuestas.dtos.Responses;

import java.io.Serializable;

/**
 * InfoDiaResponse
 */

public class InfoDiaResponse implements Serializable {

  private static final long serialVersionUID = 5528332572164662150L;

  protected String fecha;
  protected Integer numApuestasTotal;
  protected Integer numApuestasPendientes;
  protected Integer numApuestasGanadas;
  protected Integer numApuestasPerdidas;
  protected Integer numApuestasOtras;
  protected Float importeTotal;
  protected Float importeGanado;
  protected Float importePerdido;
  protected Float importePotencial;
  protected Float importeGanancia;

  public InfoDiaResponse(){

    this.fecha = null;
    this.numApuestasTotal = 0;
    this.numApuestasPendientes = 0;
    this.numApuestasGanadas = 0;
    this.numApuestasPerdidas = 0;
    this.numApuestasOtras = 0;
    this.importeTotal = 0f;
    this.importeGanado = 0f;
    this.importePerdido = 0f;
    this.importePotencial = 0f;
    this.importeGanado = 0f;

  }

  public String getFecha() {
    return fecha;
  }

  public void setFecha(String fecha) {
    this.fecha = fecha;
  }

  public Integer getNumApuestasTotal() {
    return numApuestasTotal;
  }

  public void setNumApuestasTotal(Integer numApuestasTotal) {
    this.numApuestasTotal = numApuestasTotal;
  }

  public Integer getNumApuestasPendientes() {
    return numApuestasPendientes;
  }

  public void setNumApuestasPendientes(Integer numApuestasPendientes) {
    this.numApuestasPendientes = numApuestasPendientes;
  }

  public Integer getNumApuestasGanadas() {
    return numApuestasGanadas;
  }

  public void setNumApuestasGanadas(Integer numApuestasGanadas) {
    this.numApuestasGanadas = numApuestasGanadas;
  }

  public Integer getNumApuestasPerdidas() {
    return numApuestasPerdidas;
  }

  public void setNumApuestasPerdidas(Integer numApuestasPerdidas) {
    this.numApuestasPerdidas = numApuestasPerdidas;
  }

  public Integer getNumApuestasOtras() {
    return numApuestasOtras;
  }

  public void setNumApuestasOtras(Integer numApuestasOtras) {
    this.numApuestasOtras = numApuestasOtras;
  }

  public Float getImporteTotal() {
    return importeTotal;
  }

  public void setImporteTotal(Float importeTotal) {
    this.importeTotal = importeTotal;
  }

  public Float getImporteGanado() {
    return importeGanado;
  }

  public void setImporteGanado(Float importeGanado) {
    this.importeGanado = importeGanado;
  }

  public Float getImportePerdido() {
    return importePerdido;
  }

  public void setImportePerdido(Float importePerdido) {
    this.importePerdido = importePerdido;
  }

  public Float getImportePotencial() {
    return importePotencial;
  }

  public void setImportePotencial(Float importePotencial) {
    this.importePotencial = importePotencial;
  }

  public Float getImporteGanancia() {
    return importeGanancia;
  }

  public void setImporteGanancia(Float importeGanancia) {
    this.importeGanancia = importeGanancia;
  }

  // ganancia / total * 100
  public Float getYield() {

    if (importeTotal == 0) return 0f;

    return (this.importeGanancia / this.importeTotal) * 100;

  }

}
