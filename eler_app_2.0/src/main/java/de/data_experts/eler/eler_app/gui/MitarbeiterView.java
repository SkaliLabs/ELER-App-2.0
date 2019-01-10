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

import static de.data_experts.eler.eler_app.gui.Styles.DUNKEL;

import java.util.List;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.renderer.TemplateRenderer;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import de.data_experts.eler.eler_app.db.MitarbeiterRepository;
import de.data_experts.eler.eler_app.model.Mitarbeiter;

@PageTitle( "Mitarbeiter" )
@Route( value = "mitarbeiter", layout = MainView.class )
public class MitarbeiterView extends VerticalLayout {

  public MitarbeiterView( MitarbeiterRepository repo ) {
    Grid<Mitarbeiter> grid = new Grid<>( Mitarbeiter.class );
    List<Mitarbeiter> alleMitarbeiter = repo.findAll();
    grid.setItems( alleMitarbeiter );
    grid.getColumns().forEach( column -> grid.removeColumn( column ) );
    grid.addColumn( TemplateRenderer
        .<Mitarbeiter> of(
            "<input type='checkbox' name='[[item.id]]' value='[[item.id]]' on-change='handleChange' checked='[[item.checked]]'/>" )
        .withProperty( "id", Mitarbeiter::getId )
        .withProperty( "checked", mitarbeiter -> mitarbeiter.isAktiv() ? "checked" : "" )
        .withEventHandler( "handleChange", mitarbeiter -> {
          mitarbeiter.setAktiv( !mitarbeiter.isAktiv() );
          repo.save( mitarbeiter );
        } ) )
        .setHeader( "Status" );
    grid.addColumn( Mitarbeiter::getName ).setHeader( "Name" );
    grid.setHeightByRows( true );
    grid.getStyle().set( "color", DUNKEL );
    grid.setWidth( "75%" );
    add( grid );
  }

  private static final long serialVersionUID = -2081451884485555973L;
}
