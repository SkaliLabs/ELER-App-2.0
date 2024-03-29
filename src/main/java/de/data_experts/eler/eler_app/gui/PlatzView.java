package de.data_experts.eler.eler_app.gui;

import static de.data_experts.eler.eler_app.gui.Styles.DUNKEL;

import java.util.ArrayList;
import java.util.List;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.renderer.TemplateRenderer;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import de.data_experts.eler.eler_app.db.PlatzRepository;
import de.data_experts.eler.eler_app.db.RaumRepository;
import de.data_experts.eler.eler_app.model.Platz;
import de.data_experts.eler.eler_app.model.Raum;

@PageTitle( "Plätze" )
@Route( value = "plaetze", layout = MainView.class )
public class PlatzView extends VerticalLayout {

  public PlatzView( PlatzRepository platzRepo, RaumRepository raumRepo ) {
    List<Platz> allePlaetze = new ArrayList<>();
    allePlaetze.addAll( addPlaetzeGrid( platzRepo, raumRepo, 121 ) );
    allePlaetze.addAll( addPlaetzeGrid( platzRepo, raumRepo, 125 ) );
    allePlaetze.addAll( addPlaetzeGrid( platzRepo, raumRepo, 126 ) );
  }

  private List<Platz> addPlaetzeGrid( PlatzRepository platzRepo, RaumRepository raumRepo, Integer raumNr ) {
    Raum raum = raumRepo.findById( raumNr ).get();
    H3 raumName = new H3( raum.getBezeichnung() );
    raumName.getStyle().set( "color", DUNKEL );
    add( raumName );
    Grid<Platz> grid = new Grid<>( Platz.class );
    List<Platz> plaetze = platzRepo.findByRaum( raum );
    grid.setItems( plaetze );
    grid.getColumns().forEach( column -> grid.removeColumn( column ) );
    grid.addColumn( TemplateRenderer.<Platz> of(
        "<input type='checkbox' name='[[item.id]]' value='[[item.id]]' on-change='handleChange' checked='[[item.checked]]'/>" )
        .withProperty( "id", Platz::getId )
        .withProperty( "checked", platz -> platz.isAktiv() ? "checked" : "" )
        .withEventHandler( "handleChange", platz -> {
          platz.setAktiv( !platz.isAktiv() );
          platzRepo.save( platz );
        } ) )
        .setHeader( "Status" );
    grid.addColumn( Platz::getBezeichnung ).setHeader( "Bezeichnung" );
    grid.getStyle().set( "color", DUNKEL );
    grid.setHeightByRows( true );
    grid.setWidth( "75%" );
    add( grid );
    return plaetze;
  }

  private static final long serialVersionUID = -739058150069479001L;
}
