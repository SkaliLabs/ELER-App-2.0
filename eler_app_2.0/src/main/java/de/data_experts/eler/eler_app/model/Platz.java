package de.data_experts.eler.eler_app.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 * Die Klasse Platz repräsentiert einen Arbeitsplatz eines Mitarbeiters.
 */
@Entity
public class Platz {

  // -- Konstruktoren ----------------------------------------------------------

  /**
   * Für JPA
   */
  protected Platz() {
  }

  public Platz( long id, String bezeichnung, Raum raum ) {
    this( id, bezeichnung, raum, true );
  }

  public Platz( long id, String bezeichnung, Raum raum, boolean aktiv ) {
    this.id = id;
    this.bezeichnung = bezeichnung;
    this.raum = raum;
    this.aktiv = aktiv;
  }

  // -- public Methoden --------------------------------------------------------

  @Override
  public boolean equals( Object o ) {
    if ( o instanceof Platz )
      return getId() == ( (Platz) o ).getId();
    return false;
  }

  @Override
  public int hashCode() {
    return (int) id;
  }

  // -- private Methoden -------------------------------------------------------

  // -- Getter/Setter ----------------------------------------------------------

  public long getId() {
    return id;
  }

  public String getBezeichnung() {
    return bezeichnung;
  }

  public Raum getRaum() {
    return raum;
  }

  public boolean isAktiv() {
    return aktiv;
  }

  public void setAktiv( boolean aktiv ) {
    this.aktiv = aktiv;
  }

  // -- Attribute --------------------------------------------------------------

  @Id
  private long id;

  private String bezeichnung;

  @ManyToOne( fetch = FetchType.EAGER )
  @JoinColumn( name = "raum" )
  private Raum raum;

  private boolean aktiv;

}
