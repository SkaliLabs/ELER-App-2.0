package de.data_experts.eler.eler_app.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.OneToMany;

/**
 * Eine Konfiguration repräsentiert die Platzzuordnungen, die für einen bestimmten Zeitraum gelten sollen.
 */
@Entity
public class Konfiguration {

  // -- Konstruktoren ----------------------------------------------------------

  /**
   * Für JPA
   */
  protected Konfiguration() {
  }

  public Konfiguration( List<Platzzuordnung> platzzuordnungen ) {
    this.gueltigVon = LocalDateTime.now();
    this.gueltigBis = LocalDateTime.now().plusMonths( 2 ).minusDays( 1 );
    this.platzzuordnungen = platzzuordnungen;
  }

  // -- public Methoden --------------------------------------------------------

  public String getKuerzelZuPlatzId( long platzId ) {
    for ( Platzzuordnung zuordnung : platzzuordnungen ) {
      if ( zuordnung.getPlatz().getId() == platzId ) {
        Mitarbeiter mitarbeiter = zuordnung.getMitarbeiter();
        return mitarbeiter == null ? "" : mitarbeiter.getKuerzel();
      }
    }
    return "";
  }

  public Map<Raum, List<Platzzuordnung>> getPlatzzuordnungenJeRaum() {
    Map<Raum, List<Platzzuordnung>> result = new HashMap<>();

    for ( Platzzuordnung platzzuordnung : platzzuordnungen ) {
      if ( result.get( platzzuordnung.getPlatz().getRaum() ) == null )
        result.put( platzzuordnung.getPlatz().getRaum(), new ArrayList<Platzzuordnung>() );
      result.get( platzzuordnung.getPlatz().getRaum() ).add( platzzuordnung );
    }

    // die Platzzuordnungen je Raum sortieren
    for ( Raum raum : result.keySet() )
      Collections.sort( result.get( raum ) );

    return result;
  }

  @Override
  public boolean equals( Object o ) {
    if ( o instanceof Konfiguration )
      return id == ( (Konfiguration) o ).getId();
    return false;
  }

  // -- private Methoden -------------------------------------------------------

  // -- Getter/Setter ----------------------------------------------------------

  public long getId() {
    return id;
  }

  public LocalDateTime getGueltigVon() {
    return gueltigVon;
  }

  public String getGueltigVonAlsString() {
    return getGueltigVon().format( formatter );
  }

  public void setGueltigVon( LocalDateTime gueltigVon ) {
    this.gueltigVon = gueltigVon;
  }

  public LocalDateTime getGueltigBis() {
    return gueltigBis;
  }

  public String getGueltigBisAlsString() {
    return getGueltigBis().format( formatter );
  }

  public void setGueltigBis( LocalDateTime gueltigBis ) {
    this.gueltigBis = gueltigBis;
  }

  public List<Platzzuordnung> getPlatzzuordnungen() {
    return platzzuordnungen;
  }

  public String getTuerschilder() {
    return tuerschilder;
  }

  // -- Attribute --------------------------------------------------------------

  @Id
  @GeneratedValue
  private long id;

  private LocalDateTime gueltigVon;

  private LocalDateTime gueltigBis;

  @OneToMany( cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER )
  @JoinColumn( name = "konfiguration" )
  private List<Platzzuordnung> platzzuordnungen;

  @Lob
  private String tuerschilder;

  private transient DateTimeFormatter formatter = DateTimeFormatter.ofPattern( "dd. MMMM yyyy", Locale.GERMANY );

}
