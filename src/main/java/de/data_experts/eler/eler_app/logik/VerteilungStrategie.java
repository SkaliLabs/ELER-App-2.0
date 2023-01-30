package de.data_experts.eler.eler_app.logik;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import de.data_experts.eler.eler_app.model.Konfiguration;
import de.data_experts.eler.eler_app.model.Mitarbeiter;
import de.data_experts.eler.eler_app.model.Platz;
import de.data_experts.eler.eler_app.model.Platzzuordnung;
import de.data_experts.eler.eler_app.model.Raum;

/**
 * Erzeugt eine neue Konfiguration durch Verteilung von Mitarbeitern auf Räume.
 */
public class VerteilungStrategie {

  // -- Konstruktoren ----------------------------------------------------------

  // -- public Methoden --------------------------------------------------------

  public Konfiguration generiereVerteilung( List<Raum> raeume, List<Mitarbeiter> mitarbeiter,
      Konfiguration vorherigeKonfiguration ) {
    List<Platzzuordnung> zuordnungenVerteilt = new ArrayList<>();
    Random zufallsGenerator = new Random( new Date().getTime() );

    // sammle die Plätze ein, die in die Zufallsverteilung eingehen sollen
    List<Platz> belegbarerPlaetze = new ArrayList<>();
    for ( Raum raum : raeume )
      for ( Platz platz : raum.getPlaetze() )
        if ( platz.isAktiv() )
          belegbarerPlaetze.add( platz );

    // sammle die Mitarbeiter ein, die in die Zufallsverteilung eingehen sollen
    List<Mitarbeiter> zuVerteilendeMitarbeiter = new ArrayList<>();
    for ( Mitarbeiter arbeiter : mitarbeiter )
      if ( arbeiter.isAktiv() && !arbeiter.isHomie())
        zuVerteilendeMitarbeiter.add( arbeiter );

    // zufällige Zuordnungen zwischen Mitarbeitern und Plätzen schaffen
    for ( Mitarbeiter arbeiter : zuVerteilendeMitarbeiter ) {
      int zufallsIndex = zufallsGenerator.nextInt( belegbarerPlaetze.size() );
      zuordnungenVerteilt.add( new Platzzuordnung( belegbarerPlaetze.get( zufallsIndex ), arbeiter ) );
      belegbarerPlaetze.remove( zufallsIndex );
    }

    // absichern, dass kein Raum mit nur einem Mitarbeiter belegt ist
    sichereMindestanzahlMitarbeiterJeRaumAb( zuordnungenVerteilt, belegbarerPlaetze );

    // Platzzuordnungen für leere Plätze erstellen
    for ( Platz platz : belegbarerPlaetze )
      zuordnungenVerteilt.add( new Platzzuordnung( platz, null ) );

    return new Konfiguration( zuordnungenVerteilt, vorherigeKonfiguration );
  }

  // -- private Methoden -------------------------------------------------------

  /*
   * Wenn möglich werden die Platzzuordnungen so verschoben, dass in jedem Raum die MINDESTZAHL_MITARBEIETR_JE_RAUM
   * eingehalten wird. Dazu werden Räume entweder aufgefüllt (ein Mitarbeiter aus einem Raum mit vielen Mitarbeitern
   * wird in den betreffenden Raum versetzt) oder geleert (der Mitarbeiter aus dem betroffenen Raum wird in einen noch
   * nicht voll besetzten Raum versetzt).
   */
  private void sichereMindestanzahlMitarbeiterJeRaumAb( List<Platzzuordnung> zuordnungen,
      List<Platz> belegbarePlaetze ) {
    Map<Raum, List<Mitarbeiter>> mitarbeiterJeRaum = getMitarbeiterJeRaum( zuordnungen );

    if ( isMindestanzahlMitarbeiterJeRaumUnterschritten( mitarbeiterJeRaum ) ) {

      // Raum mit nur einem Mitarbeiter auffüllen oder leeren?
      if ( kannAufgefuelltWerden( mitarbeiterJeRaum ) ) {
        // Raum auffüllen
        Platz platz = getFreienPlatzAusRaum( getUnterbesetztenRaum( mitarbeiterJeRaum ), belegbarePlaetze );
        Platzzuordnung zuordnung = getPlatzzuordnungAusRaum( getRaumMitHoherBelegung( mitarbeiterJeRaum ),
            zuordnungen );
        belegbarePlaetze.add( zuordnung.getPlatz() );
        belegbarePlaetze.remove( platz );
        zuordnung.setPlatz( platz );
      }
      else {
        // Raum leeren
        Raum raum = getUnterbesetztenRaum( mitarbeiterJeRaum );
        Platz platz = getBelegtenPlatzAusRaum( raum, zuordnungen );
        Platz platzb = getFreienPlatzNichtInRaum( belegbarePlaetze, raum );

        Platzzuordnung zuordnung = getPlatzzuordnungZuPlatz( platz, zuordnungen );
        belegbarePlaetze.add( platz );
        zuordnung.setPlatz( platzb );
        belegbarePlaetze.remove( platzb );
      }
    }
  }

  /*
   * Liefert aus der übergebenen Map den ersten Raum, der die Mindestanzahl der Mitarbeiter pro Raum unterschreitet.
   */
  private Raum getUnterbesetztenRaum( Map<Raum, List<Mitarbeiter>> mitarbeiterJeRaum ) {
    for ( Raum raum : mitarbeiterJeRaum.keySet() )
      if ( mitarbeiterJeRaum.get( raum ).size() < MINDESTZAHL_MITARBEIETR_JE_RAUM )
        return raum;
    return null;
  }

  /*
   * Es wird der erste gefundene leere Platz geliefert. Gibt es keinen leeren Platz wird null zurückgegeben.
   */
  private Platz getFreienPlatzAusRaum( Raum raum, List<Platz> belegbarePlaetze ) {
    for ( Platz platz : belegbarePlaetze )
      if ( platz.getRaum().equals( raum ) )
        return platz;
    return null;
  }

  /*
   * Ermittelt die zum übergebenen Platz zugehörige Platzzuordnung aus der übergebenen Liste von Platzzuordnungen.
   */
  private Platzzuordnung getPlatzzuordnungZuPlatz( Platz platz, List<Platzzuordnung> platzzuordnungen ) {
    for ( Platzzuordnung zuordnung : platzzuordnungen )
      if ( zuordnung.getPlatz().equals( platz ) )
        return zuordnung;
    return null;
  }

  /*
   * Ermittelt den ersten in der übergebenen Liste belegten Platz aus dem übergebenen Raum.
   */
  private Platz getBelegtenPlatzAusRaum( Raum raum, List<Platzzuordnung> zuordnungen ) {
    for ( Platzzuordnung zuordnung : zuordnungen )
      if ( zuordnung.getPlatz().getRaum().equals( raum ) )
        return zuordnung.getPlatz();
    return null;
  }

  private Raum getRaumMitHoherBelegung( Map<Raum, List<Mitarbeiter>> mitarbeiterJeRaum ) {
    for ( Raum raum : mitarbeiterJeRaum.keySet() )
      if ( mitarbeiterJeRaum.get( raum ).size() > MINDESTZAHL_MITARBEIETR_JE_RAUM )
        return raum;
    return null;
  }

  private Platzzuordnung getPlatzzuordnungAusRaum( Raum raum, List<Platzzuordnung> zuordnungen ) {
    for ( Platzzuordnung zuordnung : zuordnungen )
      if ( zuordnung.getPlatz().getRaum().equals( raum ) )
        return zuordnung;
    return null;
  }

  private Platz getFreienPlatzNichtInRaum( List<Platz> plaetze, Raum raum ) {
    for ( Platz platz : plaetze )
      if ( platz.getRaum().equals( raum ) )
        return platz;
    return null;
  }

  private boolean isMindestanzahlMitarbeiterJeRaumUnterschritten( Map<Raum, List<Mitarbeiter>> mitarbeiterJeRaum ) {
    return getUnterbesetztenRaum( mitarbeiterJeRaum ) != null;
  }

  private boolean kannAufgefuelltWerden( Map<Raum, List<Mitarbeiter>> mitarbeiterJeRaum ) {
    return getDurchschnittlicheAnzahlMitarbeiterJeRaum( mitarbeiterJeRaum ) > MINDESTZAHL_MITARBEIETR_JE_RAUM;
  }

  private double getDurchschnittlicheAnzahlMitarbeiterJeRaum( Map<Raum, List<Mitarbeiter>> mitarbeiterJeRaum ) {
    int summe = 0;

    for ( Raum raum : mitarbeiterJeRaum.keySet() )
      summe += mitarbeiterJeRaum.get( raum ).size();

    return ( (double) summe ) / mitarbeiterJeRaum.size();
  }

  private Map<Raum, List<Mitarbeiter>> getMitarbeiterJeRaum( List<Platzzuordnung> platzzuordnungen ) {
    Map<Raum, List<Mitarbeiter>> mitarbeiterJeRaum = new HashMap<>();

    for ( Platzzuordnung zuordnung : platzzuordnungen ) {
      if ( mitarbeiterJeRaum.get( zuordnung.getPlatz().getRaum() ) == null )
        mitarbeiterJeRaum.put( zuordnung.getPlatz().getRaum(), new ArrayList<Mitarbeiter>() );
      mitarbeiterJeRaum.get( zuordnung.getPlatz().getRaum() ).add( zuordnung.getMitarbeiter() );
    }
    return mitarbeiterJeRaum;
  }

  // -- Getter/Setter ----------------------------------------------------------

  // -- Attribute --------------------------------------------------------------

  private final int MINDESTZAHL_MITARBEIETR_JE_RAUM = 2;
}
