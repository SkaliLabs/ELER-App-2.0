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
    this( platzzuordnungen, null );
  }

  public Konfiguration( List<Platzzuordnung> platzzuordnungen, Konfiguration vorgaengerKonfig ) {
    this.gueltigVon = vorgaengerKonfig == null ? LocalDateTime.now(): vorgaengerKonfig.getGueltigBis().plusDays( 1 );
    this.gueltigBis = gueltigVon.plusMonths( 2 ).minusDays( 1 );
    this.platzzuordnungen = platzzuordnungen;
    this.vorgaengerKonfId = vorgaengerKonfig == null ? null : vorgaengerKonfig.getId();
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
      result.computeIfAbsent(platzzuordnung.getPlatz().getRaum(), key -> new ArrayList<>());
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

  public LocalDateTime getGueltigBis() {
    return gueltigBis;
  }

  public String getGueltigBisAlsString() {
    return getGueltigBis().format( formatter );
  }

  public List<Platzzuordnung> getPlatzzuordnungen() {
    return platzzuordnungen;
  }

  public Long getVorgaengerKonfId() {
    return vorgaengerKonfId;
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

  private Long vorgaengerKonfId;

  private final transient DateTimeFormatter formatter = DateTimeFormatter.ofPattern( "dd. MMMM yyyy", Locale.GERMANY );

}
