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

  // -- public Methoden --------------------------------------------------------

  @Override
  public boolean equals( Object o ) {

    if ( o == null || o.getClass() != getClass() )
      return false;

    if ( o == this )
      return true;

    Platz other = (Platz) o;

    if ( bezeichnung != null ^ other.bezeichnung != null )
      return false;

    if ( bezeichnung != null && other.bezeichnung != null && !bezeichnung.equals( other.bezeichnung ) )
      return false;

    return true;
  }

  @Override
  public int hashCode() {
    return (int) id;
  }

  // -- private Methoden -------------------------------------------------------

  // -- Attribute --------------------------------------------------------------

  @Id
  private long id;

  private String bezeichnung;

  @ManyToOne( fetch = FetchType.LAZY )
  @JoinColumn( name = "raum" )
  private Raum raum;

  private boolean aktiv;

}
