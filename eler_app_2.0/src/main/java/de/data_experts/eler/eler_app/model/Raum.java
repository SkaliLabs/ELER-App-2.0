package de.data_experts.eler.eler_app.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

/**
 * Ein Raum dient als "Behälter" für Plätze.
 */
@Entity
public class Raum {

  // -- Konstruktoren ----------------------------------------------------------

  /**
   * Für JPA
   */
  protected Raum() {
  }

  public Raum( Integer raumNr, String bezeichnung ) {
    this.raumNr = raumNr;
    this.bezeichnung = bezeichnung;
  }

  // -- Getter/Setter ----------------------------------------------------------

  public Integer getRaumNr() {
    return raumNr;
  }

  public String getBezeichnung() {
    return bezeichnung;
  }

  public List<Platz> getPlaetze() {
    if ( plaetze == null )
      plaetze = new ArrayList<>();
    return plaetze;
  }

  public void addPlatz( Platz platz ) {
    getPlaetze().add( platz );
  }

  // -- public Methoden --------------------------------------------------------

  @Override
  public boolean equals( Object o ) {
    return o instanceof Raum ? raumNr.equals( ( (Raum) o ).raumNr ) : false;
  }

  @Override
  public int hashCode() {
    return raumNr.hashCode();
  }

  // -- private Methoden -------------------------------------------------------

  // -- Attribute --------------------------------------------------------------

  @Id
  private Integer raumNr;

  private String bezeichnung;

  @OneToMany( mappedBy = "raum", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER )
  private List<Platz> plaetze = new ArrayList<>();

}
