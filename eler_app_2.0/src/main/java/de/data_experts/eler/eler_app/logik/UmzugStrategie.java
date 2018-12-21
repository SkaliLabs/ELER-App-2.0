package de.data_experts.eler.eler_app.logik;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Erzeugt den Umzugsalgorithmus anhand einer UmzugZuordnung in Form eines Strings.
 */
public class UmzugStrategie {

  // -- Attribute --------------------------------------------------------------

  // -- Konstruktoren ----------------------------------------------------------

  // -- Getter/Setter ----------------------------------------------------------

  // -- public Methoden --------------------------------------------------------

  public String erstelleUmzug( List<UmzugZuordnung> umzugZuordnungen ) {
    Map<Integer, List<UmzugZuordnung>> map = new HashMap<>();
    Integer zyklus = 1;

    while ( !umzugZuordnungen.isEmpty() ) {
      List<UmzugZuordnung> zuordnungen = findeZyklus( umzugZuordnungen );
      map.put( zyklus++, zuordnungen );
      umzugZuordnungen.removeAll( zuordnungen );
    }
    return erstelleAusgabe( map );
  }

  // -- private Methoden -------------------------------------------------------

  private static List<UmzugZuordnung> findeZyklus( List<UmzugZuordnung> umzugZuordnungen ) {
    List<UmzugZuordnung> zuordnungen = new ArrayList<>();
    boolean abgeschlossen = false;
    while ( !abgeschlossen ) {
      // erstes Element stellt den Beginn des Zyklus dar
      UmzugZuordnung elementVorwaerts = umzugZuordnungen.get( 0 );
      UmzugZuordnung elementRueckwaerts = umzugZuordnungen.get( 0 );
      zuordnungen.add( elementVorwaerts );

      // vorw�rts gehen
      long neuerPlatz = elementVorwaerts.getNeuerPlatzId();
      long alterPlatz = -1;
      while ( alterPlatz != neuerPlatz ) {
        UmzugZuordnung u = getZuordnungMitNeuemPlatz( umzugZuordnungen, elementVorwaerts.getAlterPlatzId() );
        if ( u == null )
          break;

        if ( !zuordnungen.contains( u ) )
          zuordnungen.add( u ); // Mitarbeiter in Liste einf�gen
        elementVorwaerts = u;
        alterPlatz = u.getAlterPlatzId();
      }

      // r�ckw�rts gehen
      neuerPlatz = elementRueckwaerts.getNeuerPlatzId();
      alterPlatz = -1;
      while ( alterPlatz != neuerPlatz ) {
        UmzugZuordnung u = getZuordnungMitAltemPlatz( umzugZuordnungen, elementRueckwaerts.getNeuerPlatzId() );
        if ( u == null )
          break;

        if ( !zuordnungen.contains( u ) )
          zuordnungen.add( 0, u ); // Mitarbeiter an erste Stelle der Liste einf�gen
        elementRueckwaerts = u;
        alterPlatz = u.getNeuerPlatzId();
      }

      abgeschlossen = true;
    }
    return zuordnungen;
  }

  // Holt Zuordnung, die den gegebenen neuen Platz als alten Platz besitzt
  private static UmzugZuordnung getZuordnungMitAltemPlatz( List<UmzugZuordnung> umzugZuordnungen, long platznummer ) {
    for ( UmzugZuordnung z : umzugZuordnungen )
      if ( z.getAlterPlatzId() == platznummer )
        return z;
    return null;
  }

  // Holt Zuordnung die den gegebenen Alten Platz als Neuen platz besitzt
  private static UmzugZuordnung getZuordnungMitNeuemPlatz( List<UmzugZuordnung> umzugZuordnungen, long platznummer ) {
    for ( UmzugZuordnung z : umzugZuordnungen )
      if ( z.getNeuerPlatzId() == platznummer )
        return z;
    return null;
  }

  // Wandelt die Informationen in einen verst�ndlichen String um
  private static String erstelleAusgabe( Map<Integer, List<UmzugZuordnung>> map ) {
    StringBuffer buffer = new StringBuffer();
    buffer.append( "Umzug-Reihenfolge:" );

    for ( Integer i : map.keySet() ) {
      List<UmzugZuordnung> umzugZuordnungen = map.get( i );
      for ( UmzugZuordnung u : umzugZuordnungen ) {
        if ( umzugZuordnungen.size() > 1 ) {
          if ( umzugZuordnungen.get( 0 ).equals( u ) ) {
            buffer.append( "\n\n" + u.getMitarbeiterKuerzel() + " --> Flur" );
          }
          else
            buffer.append( "\n" + u.getMitarbeiterKuerzel() );
        }
        else
          buffer.append( "\n" + u.getMitarbeiterKuerzel() );
      }
      if ( umzugZuordnungen.size() > 1 ) {
        buffer.append( "\n" + umzugZuordnungen.get( 0 ).getMitarbeiterKuerzel() );
      }
    }
    return buffer.toString();
  }

}
