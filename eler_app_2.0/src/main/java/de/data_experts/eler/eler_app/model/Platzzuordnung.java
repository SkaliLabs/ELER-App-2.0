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

  // -- public Methoden --------------------------------------------------------

  @Override
  public int hashCode() {
    return (int) id;
  }

  @Override
  public boolean equals( Object o ) {
    if ( o instanceof Platzzuordnung )
      return getId() == ( (Platzzuordnung) o ).getId();
    return false;
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

  // -- Attribute --------------------------------------------------------------

  @Id
  @GeneratedValue
  private long id;

  @ManyToOne( fetch = FetchType.EAGER )
  @JoinColumn( name = "platz" )
  private Platz platz;

  @ManyToOne( fetch = FetchType.EAGER )
  @JoinColumn( name = "mitarbeiter" )
  private Mitarbeiter mitarbeiter;

}
