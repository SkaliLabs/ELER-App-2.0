package de.data_experts.eler.eler_app.logik;

import de.data_experts.eler.eler_app.db.MitarbeiterRepository;
import de.data_experts.eler.eler_app.db.PlatzRepository;
import de.data_experts.eler.eler_app.model.Mitarbeiter;
import de.data_experts.eler.eler_app.model.Platz;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ValidierungsService {

    public boolean validiere(){
        List<Mitarbeiter> aktiveOfficer = mitarbeiterRepository.findByAktivAndIsHomie(true, false);
        List<Platz> aktivePlaetze = platzRepository.findByAktiv(true);
        if ( aktiveOfficer.size() > aktivePlaetze.size())
            return false;

        List<Mitarbeiter> aktiveMitarbeiter = mitarbeiterRepository.findByAktiv(true);
        List<Platz> allePlaetze = platzRepository.findAll();
        return aktiveMitarbeiter.size() <= allePlaetze.size();
    }

    @Autowired
    private MitarbeiterRepository mitarbeiterRepository;

    @Autowired
    private PlatzRepository platzRepository;

}
