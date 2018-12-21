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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;

import de.data_experts.eler.eler_app.db.KonfigurationRepository;
import de.data_experts.eler.eler_app.db.MitarbeiterRepository;
import de.data_experts.eler.eler_app.db.PlatzRepository;
import de.data_experts.eler.eler_app.db.RaumRepository;

//@Controller
public class VerteilungController {

  @Autowired
  MitarbeiterRepository mitarbeiterRepository;

  @Autowired
  RaumRepository raumRepository;

  @Autowired
  PlatzRepository platzRepository;

  @Autowired
  KonfigurationRepository konfigurationRepository;

  // @GetMapping( "/" )
  // @ResponseBody
  // public String konfig() {
  // RaumbelegungService service = new RaumbelegungService();
  // return service.generiereZeichnung( konfigurationRepository.findAktuelle() );
  // }

  @GetMapping( "/" )
  public String sitzplan() {
    // RaumbelegungService service = new RaumbelegungService();
    // Konfiguration konfiguration = service.generiereKonfiguration();
    // konfigurationRepository.save( konfiguration );
    return "sitzplan";
  }

}
