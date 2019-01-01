package de.data_experts.eler.eler_app.logik;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import de.data_experts.eler.eler_app.db.KonfigurationRepository;
import de.data_experts.eler.eler_app.db.MitarbeiterRepository;
import de.data_experts.eler.eler_app.db.RaumRepository;
import de.data_experts.eler.eler_app.model.Konfiguration;

/**
 * Der RaumbelegunsService bietet Dienstleistungen zum Laden, Persistieren und Erzeugen von Konfigurationen an.
 */
@Component
public class RaumbelegungService {

  // -- Konstruktoren ----------------------------------------------------------

  public RaumbelegungService() {
  }

  // -- public Methoden --------------------------------------------------------

  /**
   * Erzeugt eine neue Konfiguration. Die eingentliche Zufallsverteilung wird durch die VerteilungStrategie erzeugt. Es
   * werden mehrere Zufallsverteilungen erzeugt, von denen die mit der geringsten Ähnlichkeit zur aktuellen
   * Konfiguration durch die BewertungStragegie ausgewählt wird.
   * <p>
   *
   * @return
   */
  public Konfiguration generiereKonfiguration() {
    VerteilungStrategie verteilungStrategie = new VerteilungStrategie();
    BewertungStrategie bewertungStrategie = new BewertungStrategie();

    Konfiguration result = null;
    int gesamtBewertung = Integer.MAX_VALUE;

    for ( int i = 0; i < ANZAHL_GENERIERUNGSLAEUFE; i++ ) {
      Konfiguration konf = verteilungStrategie.generiereVerteilung( raumRepository.findAll(),
          mitarbeiterRepository.findAll() );
      int bewertungKonfiguration = bewertungStrategie.bewerteKonfiguration( konfigurationenRepository.findAll(), konf );

      if ( bewertungKonfiguration < gesamtBewertung ) {
        gesamtBewertung = bewertungKonfiguration;
        result = konf;
      }
    }

    return result;
  }

  public String getUmzug( Konfiguration konfigurationNeu ) {
    return new UmzugZuordnungHelper().erstelleUmzugZuordnungen( konfigurationenRepository.findAktuelle(),
        konfigurationNeu );
  }

  // -- private Methoden ------------------------------------------------------

  // -- Getter/Setter ----------------------------------------------------------

  // -- Attribute --------------------------------------------------------------

  @Autowired
  KonfigurationRepository konfigurationenRepository;

  @Autowired
  MitarbeiterRepository mitarbeiterRepository;

  @Autowired
  RaumRepository raumRepository;

  private final int ANZAHL_GENERIERUNGSLAEUFE = 12;

}
