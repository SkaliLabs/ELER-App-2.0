package de.data_experts.eler.eler_app.logik;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import de.data_experts.eler.eler_app.model.Konfiguration;
import de.data_experts.eler.eler_app.model.Mitarbeiter;
import de.data_experts.eler.eler_app.model.Platzzuordnung;
import de.data_experts.eler.eler_app.model.Raum;

/**
 * Aufgabe der BewertungStrategie ist es den Algorithmus zur Bestimmung der Ähnlichkeit von Konfigurationen zu kapseln.
 */
public class BewertungStrategie {

  // -- Konstruktoren ----------------------------------------------------------

  // -- public Methoden --------------------------------------------------------

  public int bewerteKonfiguration( List<Konfiguration> alteKonfigurationen, Konfiguration neueKonfiguration ) {
    if ( alteKonfigurationen == null || alteKonfigurationen.isEmpty() || neueKonfiguration == null )
      return 0;

    int result = 0;

    Map<Mitarbeiter, Set<Mitarbeiter>> nachbarnNeu = ermittleNachbarn( neueKonfiguration );
    Map<Mitarbeiter, Set<Mitarbeiter>> nachbarnAlt = ermittleNachbarn( alteKonfigurationen.get( 0 ) );

    for ( Mitarbeiter mitarbeiter : nachbarnNeu.keySet() ) {
      Set<Mitarbeiter> nachbarn = new HashSet<>();
      nachbarn.addAll( nachbarnNeu.get( mitarbeiter ) );

      // Sonderfall: falls ein Mitarbeiter neu ist und noch keine Nachbarn hatte
      if ( nachbarnAlt.get( mitarbeiter ) != null ) {
        nachbarn.retainAll( nachbarnAlt.get( mitarbeiter ) );
        result += FAKTOR_GLEICHER_NACHBAR * nachbarn.size();
      }
    }

    return result;
  }

  // -- private Methoden -------------------------------------------------------

  /*
   * Ermittelt zu jedem Mitarbeiter die Nachbarn im selben Raum. In diesem Fall ist jeder Mitarbeiter auch immmer
   * Nachbar zu sich selbst.
   */
  private Map<Mitarbeiter, Set<Mitarbeiter>> ermittleNachbarn( Konfiguration konfiguration ) {
    Map<Mitarbeiter, Set<Mitarbeiter>> nachbarn = new HashMap<>();

    Map<Raum, List<Platzzuordnung>> platzzuordnungenJeRaum = konfiguration.getPlatzzuordnungenJeRaum();
    for ( Raum raum : platzzuordnungenJeRaum.keySet() ) {
      List<Platzzuordnung> platzPlatzzuordnungen = platzzuordnungenJeRaum.get( raum );
      for ( Platzzuordnung zuordnung : platzPlatzzuordnungen )
        if ( zuordnung.getMitarbeiter() != null )
          nachbarn.put( zuordnung.getMitarbeiter(), ermittleMitarbeiterAusPlatzzuordnungen( platzPlatzzuordnungen ) );
    }
    return nachbarn;
  }

  /*
   * Es werden die Mitarbeiter ermittelt und als Menge zurückgegeben, die durch die übergebene Liste der
   * Platzzuordnungen Büroplätzen zugeordnet wurden.
   */
  private Set<Mitarbeiter> ermittleMitarbeiterAusPlatzzuordnungen( List<Platzzuordnung> zuordnungen ) {
    Set<Mitarbeiter> result = new HashSet<>();

    for ( Platzzuordnung platzzuordnung : zuordnungen )
      if ( platzzuordnung.getMitarbeiter() != null )
        result.add( platzzuordnung.getMitarbeiter() );

    return result;
  }

  // -- Getter/Setter ----------------------------------------------------------

  // -- Attribute --------------------------------------------------------------

  private final int FAKTOR_GLEICHER_NACHBAR = 5;

}
