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
package de.data_experts.eler.eler_app.db;

import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import de.data_experts.eler.eler_app.model.Konfiguration;

public interface KonfigurationRepository extends JpaRepository<Konfiguration, Long> {

  Konfiguration findByGueltigBis( Date gueltigBis );

  @Query( "SELECT konf FROM Konfiguration konf WHERE konf.gueltigBis = (SELECT MAX(gueltigBis) FROM Konfiguration)" )
  Konfiguration findAktuelle();

}
