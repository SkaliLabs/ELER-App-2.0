package de.data_experts.eler.eler_app.logik;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import de.data_experts.eler.eler_app.db.KonfigurationRepository;
import de.data_experts.eler.eler_app.model.Konfiguration;

/**
 * Erzeugt den Umzugsalgorithmus anhand einer einer alten und einer neuen Konfiguration in Form eines Strings.
 */
@Component
public class UmzugZuordnungHelper {

  // -- Konstruktoren ----------------------------------------------------------

  // -- public Methoden --------------------------------------------------------

  public Map<Integer, List<UmzugZuordnung>> erstelleUmzugZuordnungen( Konfiguration konfigurationNeu ) {
    if ( konfigurationNeu.getVorgaengerKonfId() == null )
      return Collections.emptyMap();

    final List<UmzugZuordnung> umzugZuordnungen = createUmzugZuordnungen( konfigurationNeu );

    Konfiguration konfigurationAlt = konfigurationenRepository.findById( konfigurationNeu.getVorgaengerKonfId() ).get();
    altePlaetzeSetzen( konfigurationAlt, umzugZuordnungen );

    List<UmzugZuordnung> bereinigteZuordnungen = umzugZuordnungen.stream()
        .filter( zuordnung -> zuordnung.getAlterPlatzId() != zuordnung.getNeuerPlatzId() )
        .collect( Collectors.toList() );

    UmzugStrategie strategie = new UmzugStrategie();
    return strategie.erstelleUmzug( bereinigteZuordnungen );
  }

  // -- private Methoden -------------------------------------------------------

  private void altePlaetzeSetzen( Konfiguration konfigurationAlt, final List<UmzugZuordnung> umzugZuordnungen ) {
    konfigurationAlt.getPlatzzuordnungen().forEach( alterPlatz -> {
      long mitarbeiterId = alterPlatz.getMitarbeiter().getId();
      for ( UmzugZuordnung zuordnung : umzugZuordnungen ) {
        if ( zuordnung.getMitarbeiterId() == mitarbeiterId )
          zuordnung.setAlterPlatzId( alterPlatz.getPlatz().getId() );
      }
    } );
  }

  private List<UmzugZuordnung> createUmzugZuordnungen( Konfiguration konfigurationNeu ) {
    final List<UmzugZuordnung> umzugZuordnungen = new ArrayList<>();
    konfigurationNeu.getPlatzzuordnungen().forEach( zuordnung -> umzugZuordnungen
        .add( new UmzugZuordnung( zuordnung.getMitarbeiter(), KEIN_PLATZ, zuordnung.getPlatz().getId() ) ) );
    return umzugZuordnungen;
  }

  // -- Getter/Setter ----------------------------------------------------------

  // -- Attribute --------------------------------------------------------------

  @Autowired
  KonfigurationRepository konfigurationenRepository;

  private static final int KEIN_PLATZ = -1;

}
