package de.data_experts.eler.eler_app.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Die Klasse Mitarbeiter repräsentiert einen einzelnen Mitarbeiter, der einem Arbeitsplatz in einem Raum zugewiesen
 * wird.
 */
@Entity
public class Mitarbeiter {

  // -- Konstruktoren ----------------------------------------------------------

  /**
   * Für JPA
   */
  protected Mitarbeiter() {
  }

  public Mitarbeiter( String vorname, String nachname, String kuerzel ) {
    this( vorname, nachname, kuerzel, true );
  }

  public Mitarbeiter( String vorname, String nachname, String kuerzel, boolean aktiv ) {
    this.vorname = vorname;
    this.nachname = nachname;
    this.kuerzel = kuerzel;
    this.aktiv = aktiv;
  }

  // -- Getter/Setter ----------------------------------------------------------

  public long getId() {
    return id;
  }

  public String getVorname() {
    return vorname;
  }

  public String getNachname() {
    return nachname;
  }

  public String getKuerzel() {
    return kuerzel;
  }

  public String getName() {
    return vorname + " " + nachname;
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

    Mitarbeiter other = (Mitarbeiter) o;

    if ( vorname != null ^ other.vorname != null )
      return false;

    if ( vorname != null && other.vorname != null && !vorname.equals( other.vorname ) )
      return false;

    if ( nachname != null ^ other.nachname != null )
      return false;

    if ( nachname != null && other.nachname != null && !nachname.equals( other.nachname ) )
      return false;

    if ( kuerzel != null ^ other.kuerzel != null )
      return false;

    if ( kuerzel != null && other.kuerzel != null && !kuerzel.equals( other.kuerzel ) )
      return false;

    return true;
  }

  @Override
  public int hashCode() {
    return (int) id;
  }

  @Override
  public String toString() {
    return vorname + " " + nachname + " (" + kuerzel + ")";
  }

  // -- private Methoden -------------------------------------------------------

  // -- Attribute --------------------------------------------------------------

  @Id
  @GeneratedValue
  private long id;

  private String vorname;

  private String nachname;

  private String kuerzel;

  private boolean aktiv;

}
