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
import static de.data_experts.eler.eler_app.gui.Styles.HELL;

import java.util.List;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import de.data_experts.eler.eler_app.db.RaumRepository;
import de.data_experts.eler.eler_app.model.Raum;

@PageTitle( "Räume" )
@Route( value = "raeume", layout = MainView.class )
public class RaumView extends VerticalLayout {

  public RaumView( RaumRepository repo ) {
    Grid<Raum> grid = new Grid<>( Raum.class );
    List<Raum> alleRaeume = repo.findAll();
    grid.setItems( alleRaeume );
    grid.getStyle().set( "color", DUNKEL );
    add( grid );

    Button button = new Button( "Speichern!", e -> repo.saveAll( alleRaeume ) );
    button.getStyle().set( "color", DUNKEL );
    button.getStyle().set( "background-color", HELL );
    add( button );
  }

  private static final long serialVersionUID = -2081451884485555973L;
}