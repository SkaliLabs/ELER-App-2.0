package de.data_experts.eler.eler_app.logik;

import de.data_experts.eler.eler_app.db.MitarbeiterRepository;
import de.data_experts.eler.eler_app.db.PlatzRepository;
import de.data_experts.eler.eler_app.model.Mitarbeiter;
import de.data_experts.eler.eler_app.model.Platz;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ValidierungService {

    public List<String> validiere() {
        List<String> result = new ArrayList<>();
        List<Mitarbeiter> aktiveOfficer = mitarbeiterRepository.findByAktivAndIsHomie(true, false);
        List<Platz> aktivePlaetze = platzRepository.findByAktiv(true);
        if (aktiveOfficer.size() > aktivePlaetze.size())
            result.add("Es wurden mehr Officers ausgewählt als Plätze.");

        List<Mitarbeiter> aktiveMitarbeiter = mitarbeiterRepository.findByAktiv(true);
        List<Platz> allePlaetze = platzRepository.findAll();
        if (aktiveMitarbeiter.size() > allePlaetze.size())
            result.add("Es wurden mehr Mitarbeiter ausgewählt als Plätze verfügbar sind.");

        return result;
    }

    @Autowired
    private MitarbeiterRepository mitarbeiterRepository;

    @Autowired
    private PlatzRepository platzRepository;

}
