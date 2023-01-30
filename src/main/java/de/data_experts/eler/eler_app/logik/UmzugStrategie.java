package de.data_experts.eler.eler_app.logik;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Erzeugt den Umzugsalgorithmus anhand einer UmzugZuordnung in Form eines Strings.
 */
public class UmzugStrategie {

  // -- Konstruktoren ----------------------------------------------------------

  // -- public Methoden --------------------------------------------------------

  public Map<Integer, List<UmzugZuordnung>> erstelleUmzug( List<UmzugZuordnung> umzugZuordnungen ) {
    Map<Integer, List<UmzugZuordnung>> map = new HashMap<>();
    Integer zyklus = 1;

    while ( !umzugZuordnungen.isEmpty() ) {
      List<UmzugZuordnung> zuordnungen = findeZyklus( umzugZuordnungen );
      map.put( zyklus++, zuordnungen );
      umzugZuordnungen.removeAll( zuordnungen );
    }
    return map;
  }

  // -- private Methoden -------------------------------------------------------

  private List<UmzugZuordnung> findeZyklus( List<UmzugZuordnung> umzugZuordnungen ) {
    List<UmzugZuordnung> zuordnungen = new ArrayList<>();
    boolean abgeschlossen = false;
    while ( !abgeschlossen ) {
      // erstes Element stellt den Beginn des Zyklus dar
      UmzugZuordnung elementVorwaerts = umzugZuordnungen.get( 0 );
      UmzugZuordnung elementRueckwaerts = umzugZuordnungen.get( 0 );
      zuordnungen.add( elementVorwaerts );

      // vorwärts gehen
      long neuerPlatz = elementVorwaerts.getNeuerPlatzId();
      long alterPlatz = -1;
      while ( alterPlatz != neuerPlatz ) {
        UmzugZuordnung u = getZuordnungMitNeuemPlatz( umzugZuordnungen, elementVorwaerts.getAlterPlatzId() );
        if ( u == null )
          break;

        if ( !zuordnungen.contains( u ) )
          zuordnungen.add( u ); // Mitarbeiter in Liste einfügen
        elementVorwaerts = u;
        alterPlatz = u.getAlterPlatzId();
      }

      // rückwärts gehen
      neuerPlatz = elementRueckwaerts.getNeuerPlatzId();
      alterPlatz = -1;
      while ( alterPlatz != neuerPlatz ) {
        UmzugZuordnung u = getZuordnungMitAltemPlatz( umzugZuordnungen, elementRueckwaerts.getNeuerPlatzId() );
        if ( u == null )
          break;

        if ( !zuordnungen.contains( u ) )
          zuordnungen.add( 0, u ); // Mitarbeiter an erste Stelle der Liste einfügen
        elementRueckwaerts = u;
        alterPlatz = u.getNeuerPlatzId();
      }

      abgeschlossen = true;
    }
    return zuordnungen;
  }

  /**
   * Holt Zuordnung, die den gegebenen neuen Platz als alten Platz besitzt
   */
  private UmzugZuordnung getZuordnungMitAltemPlatz( List<UmzugZuordnung> umzugZuordnungen, long platznummer ) {
    for ( UmzugZuordnung z : umzugZuordnungen )
      if ( z.getAlterPlatzId() == platznummer )
        return z;
    return null;
  }

  /**
   * Holt Zuordnung die den gegebenen alten Platz als neuen Platz besitzt
   */
  private UmzugZuordnung getZuordnungMitNeuemPlatz( List<UmzugZuordnung> umzugZuordnungen, long platznummer ) {
    for ( UmzugZuordnung z : umzugZuordnungen )
      if ( z.getNeuerPlatzId() == platznummer )
        return z;
    return null;
  }

  // -- Getter/Setter ----------------------------------------------------------

  // -- Attribute --------------------------------------------------------------

}
