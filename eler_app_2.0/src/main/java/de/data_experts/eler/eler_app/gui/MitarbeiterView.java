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
package de.data_experts.eler.eler_app.gui;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

import de.data_experts.eler.eler_app.db.MitarbeiterRepository;
import de.data_experts.eler.eler_app.model.Mitarbeiter;

@Route( value = "mitarbeiter" )
public class MitarbeiterView extends VerticalLayout {
  private static final long serialVersionUID = -2081451884485555973L;

  public MitarbeiterView( MitarbeiterRepository repo ) {
    Grid<Mitarbeiter> grid = new Grid<>( Mitarbeiter.class );
    grid.setItems( repo.findAll() );
    grid.getColumns().forEach( column -> grid.removeColumn( column ) );
    grid.addColumn( Mitarbeiter::getName ).setHeader( "Name" );
    grid.addColumn( Mitarbeiter::isAktiv ).setHeader( "Status" );
    grid.setHeightByRows( true );
    add( grid );
  }

}
