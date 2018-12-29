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

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import de.data_experts.eler.eler_app.model.Platz;
import de.data_experts.eler.eler_app.model.Raum;

public interface PlatzRepository extends JpaRepository<Platz, Long> {

  @Query( "SELECT platz FROM Platz platz WHERE platz.raum = ?1" )
  List<Platz> findByRaum( Raum raum );

}
