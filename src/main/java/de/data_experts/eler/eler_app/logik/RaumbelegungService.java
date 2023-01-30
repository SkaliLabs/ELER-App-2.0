package de.data_experts.eler.eler_app.logik;

import de.data_experts.eler.eler_app.db.PlatzRepository;
import de.data_experts.eler.eler_app.model.Mitarbeiter;
import de.data_experts.eler.eler_app.model.Platz;
import de.data_experts.eler.eler_app.model.Platzzuordnung;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import de.data_experts.eler.eler_app.db.KonfigurationRepository;
import de.data_experts.eler.eler_app.db.MitarbeiterRepository;
import de.data_experts.eler.eler_app.db.RaumRepository;
import de.data_experts.eler.eler_app.model.Konfiguration;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
     */
    public Konfiguration generiereKonfiguration(Konfiguration vorherigeKonfiguration) {
        VerteilungStrategie verteilungStrategie = new VerteilungStrategie();
        BewertungStrategie bewertungStrategie = new BewertungStrategie();

        Konfiguration result = null;
        int gesamtBewertung = Integer.MAX_VALUE;

        for (int i = 0; i < ANZAHL_GENERIERUNGSLAEUFE; i++) {
            Konfiguration konf = verteilungStrategie.generiereVerteilung(raumRepository.findAll(),
                    mitarbeiterRepository.findAll(), vorherigeKonfiguration);
            int bewertungKonfiguration = bewertungStrategie.bewerteKonfiguration(konfigurationenRepository.findAll(), konf);

            if (bewertungKonfiguration < gesamtBewertung) {
                gesamtBewertung = bewertungKonfiguration;
                result = konf;
            }
        }

        fuelleMitHomiesAuf(result);

        return result;
    }

    // -- private Methoden -------------------------------------------------------

    private void fuelleMitHomiesAuf(Konfiguration konfiguration) {
        List<Mitarbeiter> homies = mitarbeiterRepository.findByAktivAndIsHomie(true, true);
        List<Platz> plaetze = platzRepository.findAll();

        List<Platz> belegtePlaetze = konfiguration.getPlatzzuordnungen().stream().map(Platzzuordnung::getPlatz).collect(Collectors.toList());
        List<Platz> uebrigePlaetze = plaetze.stream().filter(platz -> !belegtePlaetze.contains(platz)).collect(Collectors.toCollection(ArrayList::new));

        homies.forEach(homie -> {
            Platz platz = uebrigePlaetze.get(0);
            konfiguration.getPlatzzuordnungen().add(new Platzzuordnung(platz, homie));
            uebrigePlaetze.remove(platz);
        });
    }

    // -- Getter/Setter ----------------------------------------------------------

    // -- Attribute --------------------------------------------------------------

    @Autowired
    KonfigurationRepository konfigurationenRepository;

    @Autowired
    MitarbeiterRepository mitarbeiterRepository;

    @Autowired
    RaumRepository raumRepository;

    @Autowired
    PlatzRepository platzRepository;

    private final int ANZAHL_GENERIERUNGSLAEUFE = 12;

}
