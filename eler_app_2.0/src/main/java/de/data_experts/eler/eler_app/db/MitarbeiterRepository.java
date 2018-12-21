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

import org.springframework.data.jpa.repository.JpaRepository;

import de.data_experts.eler.eler_app.model.Mitarbeiter;

public interface MitarbeiterRepository extends JpaRepository<Mitarbeiter, Long> {

  Mitarbeiter findByKuerzel( String kuerzel );

}
