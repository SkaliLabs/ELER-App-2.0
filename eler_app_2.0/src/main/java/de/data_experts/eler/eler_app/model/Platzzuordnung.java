package de.data_experts.eler.eler_app.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 * Eine Platzzuordnung stellt die Verbindung zwischen einem Mitarbeiter und seinem Arbeitsplatz dar.
 */
@Entity
public class Platzzuordnung implements Comparable<Platzzuordnung> {

  // -- Konstruktoren ----------------------------------------------------------

  /**
   * Für JPA
   */
  public Platzzuordnung() {
  }

  public Platzzuordnung( Platz platz, Mitarbeiter mitarbeiter ) {
    this.platz = platz;
    this.mitarbeiter = mitarbeiter;
  }

  // -- Getter/Setter ----------------------------------------------------------

  public long getId() {
    return id;
  }

  public Platz getPlatz() {
    return platz;
  }

  public void setPlatz( Platz platz ) {
    this.platz = platz;
  }

  public Mitarbeiter getMitarbeiter() {
    return mitarbeiter;
  }

  // -- public Methoden --------------------------------------------------------

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ( ( mitarbeiter == null ) ? 0 : mitarbeiter.hashCode() );
    result = prime * result + ( ( platz == null ) ? 0 : platz.hashCode() );
    return result;
  }

  @Override
  public boolean equals( Object o ) {

    if ( o == null || o.getClass() != getClass() )
      return false;

    if ( o == this )
      return true;

    Platzzuordnung other = (Platzzuordnung) o;

    if ( platz == null && other.platz == null ) {
      if ( mitarbeiter == null && other.mitarbeiter == null )
        return true;

      if ( mitarbeiter == null ^ other.mitarbeiter == null )
        return false;

      return mitarbeiter.equals( other.mitarbeiter );
    }

    if ( platz == null ^ other.platz == null )
      return false;

    return platz.equals( other.platz );
  }

  @Override
  public String toString() {
    if ( mitarbeiter == null )
      return platz.getBezeichnung() + " : <nicht belegt>";
    return platz.getBezeichnung() + " : " + mitarbeiter.getName();
  }

  @Override
  public int compareTo( Platzzuordnung another ) {
    return this.platz.getBezeichnung().compareTo( another.getPlatz().getBezeichnung() );
  }

  // -- private Methoden -------------------------------------------------------

  // -- Attribute --------------------------------------------------------------

  @Id
  @GeneratedValue
  private long id;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn( name = "platz" )
  private Platz platz;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn( name = "mitarbeiter" )
  private Mitarbeiter mitarbeiter;

}
