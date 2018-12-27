/*   $HeadURL$
 * ----------------------------------------------------------------------------
 *     (c) by data experts gmbh
 *            Woldegker Str. 12
 *            17033 Neubrandenburg
 * ----------------------------------------------------------------------------
 *     Dieses Dokument und die hierin enthaltenen Informationen unterliegen
 *     dem Urheberrecht und duerfen ohne die schriftliche Genehmigung des
 *     Herausgebers weder als ganzes noch in Teilen dupliziert, reproduziert
 *     oder manipuliert werden.
 * ----------------------------------------------------------------------------
 *     $Id$
 * ----------------------------------------------------------------------------
 */
package de.data_experts.eler.eler_app;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;

import de.data_experts.eler.eler_app.db.KonfigurationRepository;
import de.data_experts.eler.eler_app.db.MitarbeiterRepository;
import de.data_experts.eler.eler_app.db.PlatzRepository;
import de.data_experts.eler.eler_app.db.RaumRepository;
import de.data_experts.eler.eler_app.model.Konfiguration;
import de.data_experts.eler.eler_app.model.Mitarbeiter;
import de.data_experts.eler.eler_app.model.Platz;
import de.data_experts.eler.eler_app.model.Platzzuordnung;
import de.data_experts.eler.eler_app.model.Raum;

@Controller
public class DatenbankInitialisierenController {

  @Autowired
  MitarbeiterRepository mitarbeiterRepository;

  @Autowired
  RaumRepository raumRepository;

  @Autowired
  PlatzRepository platzRepository;

  @Autowired
  KonfigurationRepository konfigurationRepository;

  @Bean
  public void initialisiereDaten() {
    initialisiereMitarbeiter();
    initialisiereRaeume();
    initialisierePlaetze();
    initialisiereKonfiguration();
  }

  private void initialisiereMitarbeiter() {
    if ( !mitarbeiterRepository.findAll().isEmpty() )
      return;
    mitarbeiterRepository.save( new Mitarbeiter( "lede", "Ali", "ali" ) );
    mitarbeiterRepository.save( new Mitarbeiter( "lede", "Cp", "cp" ) );
    mitarbeiterRepository.save( new Mitarbeiter( "lede", "Dst", "dst" ) );
    mitarbeiterRepository.save( new Mitarbeiter( "lede", "Gkn", "gkn" ) );
    mitarbeiterRepository.save( new Mitarbeiter( "lede", "Jk", "jk" ) );
    mitarbeiterRepository.save( new Mitarbeiter( "lede", "Mare", "mare" ) );
    mitarbeiterRepository.save( new Mitarbeiter( "lede", "Mn", "mn" ) );
    mitarbeiterRepository.save( new Mitarbeiter( "lede", "Mu", "mu" ) );
    mitarbeiterRepository.save( new Mitarbeiter( "lede", "Skl", "skl" ) );
    mitarbeiterRepository.save( new Mitarbeiter( "lede", "Sta", "sta" ) );
  }

  private void initialisiereRaeume() {
    if ( !raumRepository.findAll().isEmpty() )
      return;
    raumRepository.save( new Raum( "1.26", "Raum 1.26 ELER-Wald" ) );
    raumRepository.save( new Raum( "1.21", "Raum 1.21 ELER-Wiese" ) );
    raumRepository.save( new Raum( "1.25", "Raum 1.25 ELER-Strand" ) );
  }

  private void initialisierePlaetze() {
    if ( !platzRepository.findAll().isEmpty() )
      return;

    Raum wald = raumRepository.findById( "1.26" ).get();
    platzRepository.save( new Platz( 1261, "Platz 1", wald ) );
    platzRepository.save( new Platz( 1262, "Platz 2", wald ) );
    platzRepository.save( new Platz( 1263, "Platz 3", wald ) );
    platzRepository.save( new Platz( 1264, "Platz 4", wald ) );

    Raum wiese = raumRepository.findById( "1.21" ).get();
    platzRepository.save( new Platz( 1211, "Platz 1", wiese ) );
    platzRepository.save( new Platz( 1212, "Platz 2", wiese ) );
    platzRepository.save( new Platz( 1213, "Platz 3", wiese ) );
    platzRepository.save( new Platz( 1214, "Platz 4", wiese ) );

    Raum strand = raumRepository.findById( "1.25" ).get();
    platzRepository.save( new Platz( 1251, "Platz 1", strand ) );
    platzRepository.save( new Platz( 1252, "Platz 2", strand ) );
    platzRepository.save( new Platz( 1253, "Platz 3", strand ) );
    platzRepository.save( new Platz( 1254, "Platz 4", strand ) );
  }

  @SuppressWarnings( "deprecation" )
  private void initialisiereKonfiguration() {
    if ( !konfigurationRepository.findAll().isEmpty() )
      return;

    List<Platzzuordnung> zuordnungen = new ArrayList<>();
    zuordnungen.add( new Platzzuordnung( platz( 1261L ), mitarbeiter( "cp" ) ) );
    zuordnungen.add( new Platzzuordnung( platz( 1262L ), mitarbeiter( "mu" ) ) );
    zuordnungen.add( new Platzzuordnung( platz( 1263L ), mitarbeiter( "mn" ) ) );
    zuordnungen.add( new Platzzuordnung( platz( 1264L ), mitarbeiter( "ali" ) ) );
    zuordnungen.add( new Platzzuordnung( platz( 1252L ), mitarbeiter( "gkn" ) ) );
    zuordnungen.add( new Platzzuordnung( platz( 1253L ), mitarbeiter( "sta" ) ) );
    zuordnungen.add( new Platzzuordnung( platz( 1211L ), mitarbeiter( "mare" ) ) );
    zuordnungen.add( new Platzzuordnung( platz( 1212L ), mitarbeiter( "jk" ) ) );
    zuordnungen.add( new Platzzuordnung( platz( 1213L ), mitarbeiter( "dst" ) ) );
    zuordnungen.add( new Platzzuordnung( platz( 1214L ), mitarbeiter( "skl" ) ) );
    konfigurationRepository.save( new Konfiguration( zuordnungen, new Date( 118, 8, 1 ), new Date( 118, 10, 1 ) ) );
  }

  private Mitarbeiter mitarbeiter( String kuerzel ) {
    return mitarbeiterRepository.findByKuerzel( kuerzel );
  }

  private Platz platz( Long platzId ) {
    return platzRepository.findById( platzId ).get();
  }
}
