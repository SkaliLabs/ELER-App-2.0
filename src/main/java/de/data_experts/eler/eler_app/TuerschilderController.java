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

import de.data_experts.eler.eler_app.db.KonfigurationRepository;
import de.data_experts.eler.eler_app.logik.TuerschildErzeugungStrategie;
import de.data_experts.eler.eler_app.model.Konfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class TuerschilderController {

    @GetMapping("/tuerschilder")
    @ResponseBody
    public String erzeugeTuerschilder() {
        Konfiguration konfiguration = konfigurationRepository.findAktuelle();
        return new TuerschildErzeugungStrategie().erstelleTuerschilder(konfiguration);
    }

    @Autowired
    KonfigurationRepository konfigurationRepository;

}
