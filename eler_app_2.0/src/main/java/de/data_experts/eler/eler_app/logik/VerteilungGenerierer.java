package de.data_experts.eler.eler_app.logik;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;

import de.data_experts.eler.eler_app.db.KonfigurationRepository;
import de.data_experts.eler.eler_app.model.Konfiguration;

/**
 * Auszuführende Klasse zur Erzeugung einer neuen Konfiguration und der damit einhergehenden Dateien.
 */

public class VerteilungGenerierer {

  // @Autowired
  // static PlatzRepository platzRepository;
  //
  // @Autowired
  // static MitarbeiterRepository mitarbeiterRepository;

  @Autowired
  static KonfigurationRepository konfigurationRepository;

  public static void main( String[] args ) throws IOException {
    RaumbelegungService service = new RaumbelegungService();
    Konfiguration konfiguration = service.generiereKonfiguration();
    konfigurationRepository.save( konfiguration );
    // Konfiguration konfiguration = getWunschKonfiguration();

    try ( FileOutputStream os = new FileOutputStream( new File( "Konfiguration.html" ) ) ) {
      try ( OutputStreamWriter writer = new OutputStreamWriter( os ) ) {
        writer.write( service.generiereZeichnung( konfiguration ) );
      }
    }

    try ( FileOutputStream os = new FileOutputStream( new File( "Umzug.txt" ) ) ) {
      try ( OutputStreamWriter writer = new OutputStreamWriter( os ) ) {
        writer.write( service.getUmzug( konfiguration ) );
      }
    }

    try ( FileOutputStream os = new FileOutputStream( new File( "Konfiguration.txt" ) ) ) {
      try ( OutputStreamWriter writer = new OutputStreamWriter( os ) ) {
        writer.write( konfiguration.toString() );
      }
    }

    try ( FileOutputStream os = new FileOutputStream( new File( "Tuerschilder.html" ) ) ) {
      try ( OutputStreamWriter writer = new OutputStreamWriter( os ) ) {
        writer.write( new TuerschildErzeugungsStrategie().erstelleTuerschilder( konfiguration ) );
      }
    }

    String datum = new SimpleDateFormat( "dd.MM.yyyy", Locale.GERMANY ).format( new Date() );
    String uhrzeit = new SimpleDateFormat( "HH:mm", Locale.GERMANY ).format( new Date() );
    System.out.println( "Verteilung wurde erfolgreich erstellt am: " + datum + " um " + uhrzeit );
  }

  // @SuppressWarnings( "deprecation" )
  // private static Konfiguration getWunschKonfiguration() {
  // List<Platzzuordnung> platzzuordnungen = new ArrayList<>();
  // platzzuordnungen.add( new Platzzuordnung( platz( 1261L ), mitarbeiter( "skl" ) ) );
  // platzzuordnungen.add( new Platzzuordnung( platz( 1262L ), mitarbeiter( "sta" ) ) );
  // platzzuordnungen.add( new Platzzuordnung( platz( 1263L ), mitarbeiter( "jk" ) ) );
  // platzzuordnungen.add( new Platzzuordnung( platz( 1264L ), mitarbeiter( "ali" ) ) );
  // platzzuordnungen.add( new Platzzuordnung( platz( 1251L ), mitarbeiter( "cp" ) ) );
  // platzzuordnungen.add( new Platzzuordnung( platz( 1252L ), mitarbeiter( "mu" ) ) );
  // platzzuordnungen.add( new Platzzuordnung( platz( 1211L ), mitarbeiter( "mn" ) ) );
  // platzzuordnungen.add( new Platzzuordnung( platz( 1212L ), mitarbeiter( "dst" ) ) );
  // platzzuordnungen.add( new Platzzuordnung( platz( 1213L ), mitarbeiter( "gkn" ) ) );
  // platzzuordnungen.add( new Platzzuordnung( platz( 1214L ), mitarbeiter( "mare" ) ) );
  // return new Konfiguration( platzzuordnungen, new Date( 118, 8, 1 ), new Date( 118, 10, 1 ) );
  // }
  //
  // private static Mitarbeiter mitarbeiter( String kuerzel ) {
  // return mitarbeiterRepository.findByKuerzel( kuerzel );
  // }
  //
  // private static Platz platz( Long platzId ) {
  // return platzRepository.findById( platzId ).get();
  // }

}
