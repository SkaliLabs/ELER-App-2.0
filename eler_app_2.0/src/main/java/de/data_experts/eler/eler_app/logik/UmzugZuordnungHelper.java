package de.data_experts.eler.eler_app.logik;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import de.data_experts.eler.eler_app.model.Konfiguration;
import de.data_experts.eler.eler_app.model.Mitarbeiter;
import de.data_experts.eler.eler_app.model.Platzzuordnung;

/**
 * Erzeugt den Umzugsalgorithmus anhand einer einer alten und einer neuen Konfiguration in Form eines Strings.
 */
public class UmzugZuordnungHelper {

  // -- Attribute --------------------------------------------------------------

  private static final int KEIN_PLATZ = -1;

  // -- Konstruktoren ----------------------------------------------------------

  // -- Getter/Setter ----------------------------------------------------------

  // -- public Methoden --------------------------------------------------------

  public String erstelleUmzugZuordnungen( Konfiguration konfigurationAlt, Konfiguration konfigurationNeu ) {
    List<UmzugZuordnung> umzugZuordnungen = new ArrayList<>();
    fuelleUmzugZuordnungenMitMitarbeiter( umzugZuordnungen, konfigurationAlt );
    fuelleUmzugZuordnungenMitMitarbeiter( umzugZuordnungen, konfigurationNeu );

    fuelleUmzugZuordnungenMitPlaetzen( umzugZuordnungen, konfigurationAlt, true );
    fuelleUmzugZuordnungenMitPlaetzen( umzugZuordnungen, konfigurationNeu, false );

    bereinigeListe( umzugZuordnungen );

    UmzugStrategie strategie = new UmzugStrategie();
    return strategie.erstelleUmzug( umzugZuordnungen );
  }

  // -- private Methoden -------------------------------------------------------

  private void fuelleUmzugZuordnungenMitMitarbeiter( List<UmzugZuordnung> umzugZuordnungen,
      Konfiguration konfiguration ) {
    for ( Platzzuordnung p : konfiguration.getPlatzzuordnungen() ) {
      Mitarbeiter m = p.getMitarbeiter();
      if ( m == null )
        continue;
      if ( !hatListeElementZuMitarbeiter( umzugZuordnungen, m ) )
        umzugZuordnungen.add( new UmzugZuordnung( m, KEIN_PLATZ, KEIN_PLATZ ) );
    }
  }

  private boolean hatListeElementZuMitarbeiter( List<UmzugZuordnung> umzugZuordnungen, Mitarbeiter m ) {
    if ( m == null )
      return false;

    for ( UmzugZuordnung u : umzugZuordnungen )
      if ( u.getMitarbeiterId() == m.getId() )
        return true;
    return false;
  }

  private void fuelleUmzugZuordnungenMitPlaetzen( List<UmzugZuordnung> umzugZuordnungen, Konfiguration konfiguration,
      boolean alterPlatz ) {
    for ( Platzzuordnung p : konfiguration.getPlatzzuordnungen() ) {
      Mitarbeiter m = p.getMitarbeiter();
      fuellPlatzzuordnungAlt( umzugZuordnungen, m, p.getPlatz().getId(), alterPlatz );
    }

  }

  private void fuellPlatzzuordnungAlt( List<UmzugZuordnung> umzugZuordnungen, Mitarbeiter m, long platzId,
      boolean alterPlatz ) {
    for ( UmzugZuordnung u : umzugZuordnungen ) {
      if ( m == null )
        continue;

      if ( u.getMitarbeiterId() == m.getId() )
        if ( alterPlatz )
          u.setAlterPlatzId( platzId );
        else
          u.setNeuerPlatzId( platzId );
    }
  }

  private void bereinigeListe( List<UmzugZuordnung> umzugZuordnungen ) {
    Iterator<UmzugZuordnung> it = umzugZuordnungen.iterator();
    while ( it.hasNext() ) {
      UmzugZuordnung u = it.next();
      if ( u.getNeuerPlatzId() == KEIN_PLATZ ) {
        it.remove();
      }
      else if ( u.getAlterPlatzId() == u.getNeuerPlatzId() ) {
        it.remove();
      }
    }
  }

}
