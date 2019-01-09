package de.data_experts.eler.eler_app.logik;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import de.data_experts.eler.eler_app.db.KonfigurationRepository;
import de.data_experts.eler.eler_app.model.Konfiguration;

public class SitzplanLogik {

  public SitzplanLogik( KonfigurationRepository konfigurationRepository, RaumbelegungService service,
      UmzugZuordnungHelper umzugZuordnungHelper ) {
    this.raumbelegungService = service;
    this.umzugZuordnungHelper = umzugZuordnungHelper;
    this.aktuelleKonfiguration = konfigurationRepository.findAktuelle();
  }

  public String getTitel() {
    return aktuelleKonfiguration.getGueltigVonAlsString() + " - " + aktuelleKonfiguration.getGueltigBisAlsString();
  }

  public String getTitelUmzugsdialog() {
    Map<Integer, List<UmzugZuordnung>> umzugZuordnungen = umzugZuordnungHelper
        .erstelleUmzugZuordnungen( aktuelleKonfiguration );
    if ( umzugZuordnungen.isEmpty() )
      return "Es gibt keine Umzugs-Reihenfolge.";
    return "Umzug-Reihenfolge:";
  }

  public String getPlatzbezeichnung( long platzId ) {
    return aktuelleKonfiguration.getKuerzelZuPlatzId( platzId );
  }

  public boolean hatRaumKuechendienst( int raumNr ) {
    return new Kuechenplan().hatRaumKuechendienst( raumNr );
  }

  public Konfiguration erzeugeNeueKonfiguration() {
    return raumbelegungService.generiereKonfiguration( aktuelleKonfiguration );
  }

  public List<String> getUmzugZuordnungen() {
    Map<Integer, List<UmzugZuordnung>> map = umzugZuordnungHelper.erstelleUmzugZuordnungen( aktuelleKonfiguration );
    return extracted( map );
  }

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

  private RaumbelegungService raumbelegungService;

  private UmzugZuordnungHelper umzugZuordnungHelper;

  private Konfiguration aktuelleKonfiguration;
}
