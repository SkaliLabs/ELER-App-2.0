package de.data_experts.eler.eler_app.logik;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import de.data_experts.eler.eler_app.db.KonfigurationRepository;
import de.data_experts.eler.eler_app.model.Konfiguration;

@Component
public class SitzplanLogik {

  // -- Konstruktoren ----------------------------------------------------------

  public SitzplanLogik() {
  }

  // -- public Methoden --------------------------------------------------------

  public String getTitel() {
    return getAktuelleKonfiguration().getGueltigVonAlsString() + " - " + getAktuelleKonfiguration().getGueltigBisAlsString();
  }

  public String getTitelUmzugsdialog() {
    Map<Integer, List<UmzugZuordnung>> umzugZuordnungen = umzugZuordnungHelper
        .erstelleUmzugZuordnungen( getAktuelleKonfiguration() );
    if ( umzugZuordnungen.isEmpty() )
      return "Es gibt keine Umzugs-Reihenfolge.";
    return "Umzug-Reihenfolge:";
  }

  public String getPlatzbezeichnung( long platzId ) {
    return getAktuelleKonfiguration().getKuerzelZuPlatzId( platzId );
  }

  public boolean hatRaumKuechendienst( int raumNr ) {
    return new Kuechenplan().hatRaumKuechendienst( raumNr );
  }

  public Konfiguration erzeugeNeueKonfiguration() {
    return konfigurationRepository.save( raumbelegungService.generiereKonfiguration( getAktuelleKonfiguration() ) );
  }

  public List<String> getUmzugZuordnungen() {
    Map<Integer, List<UmzugZuordnung>> map = umzugZuordnungHelper
        .erstelleUmzugZuordnungen( getAktuelleKonfiguration() );
    return extracted( map );
  }

  public boolean isKonfigurationAbgelaufen() {
    return getAktuelleKonfiguration().getGueltigBis().isBefore( LocalDateTime.now() );
  }

  // -- private Methoden -------------------------------------------------------

  private List<String> extracted( Map<Integer, List<UmzugZuordnung>> map ) {
    List<String> labelList = new ArrayList<>();
    for ( Integer i : map.keySet() ) {
      List<UmzugZuordnung> umzugZuordnungen = map.get( i );
      for ( UmzugZuordnung u : umzugZuordnungen ) {
        if ( umzugZuordnungen.size() > 1 ) {
          if ( umzugZuordnungen.get( 0 ).equals( u ) )
            labelList.add( u.getMitarbeiterKuerzel() + " --> Flur" );
          else
            labelList.add( u.getMitarbeiterKuerzel() );
        }
        else
          labelList.add( u.getMitarbeiterKuerzel() );
      }
      if ( umzugZuordnungen.size() > 1 )
        labelList.add( umzugZuordnungen.get( 0 ).getMitarbeiterKuerzel() );
    }
    return labelList;
  }

  // -- Getter/Setter ----------------------------------------------------------

  public Konfiguration getAktuelleKonfiguration() {
      return konfigurationRepository.findAktuelle();
  }

  // -- Attribute --------------------------------------------------------------

  @Autowired
  private RaumbelegungService raumbelegungService;

  @Autowired
  private KonfigurationRepository konfigurationRepository;

  @Autowired
  private UmzugZuordnungHelper umzugZuordnungHelper;
}
