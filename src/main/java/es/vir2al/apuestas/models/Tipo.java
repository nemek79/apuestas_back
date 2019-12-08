package es.vir2al.apuestas.models;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Representa los tipos de apuestas (Ganador, combinada, Over goles...)
 */

@Entity
@Table(name="t_tipos")
public class Tipo implements Serializable{

  private static final long serialVersionUID = 1L;
 
  @Id
  private Long id;

  @NotNull
  @Size(min=4, max=32)
  @Column(name="descripcion",nullable = false,unique = true)
  private String descripcion;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getDescripcion() {
    return descripcion;
  }

  public void setDescripcion(String descripcion) {
    this.descripcion = descripcion;
  }

  
}
