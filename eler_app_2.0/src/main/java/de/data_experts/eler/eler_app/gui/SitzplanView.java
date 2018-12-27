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

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;

import de.data_experts.eler.eler_app.db.KonfigurationRepository;
import de.data_experts.eler.eler_app.logik.RaumbelegungService;
import de.data_experts.eler.eler_app.model.Konfiguration;

@Route( value = "sitzplan" )
public class SitzplanView extends VerticalLayout {

  private final Konfiguration aktuelleKonfiguration;

  public SitzplanView( KonfigurationRepository konfigurationRepository, RaumbelegungService service ) {
    aktuelleKonfiguration = konfigurationRepository.findAktuelle();

    add( new H3( "Aktuelle Konfiguration gültig vom " + aktuelleKonfiguration.getGueltigVonAlsString() + " bis zum "
        + aktuelleKonfiguration.getGueltigBisAlsString() ) );

    HorizontalLayout raumreihe1 = new HorizontalLayout();
    raumreihe1.add( getRaum( 125, Fensterseite.LINKS ) );
    raumreihe1.add( getRaum( 121, Fensterseite.RECHTS ) );
    add( raumreihe1 );

    HorizontalLayout raumreihe2 = new HorizontalLayout();
    raumreihe2.add( getRaum( 126, Fensterseite.LINKS ) );
    add( raumreihe2 );

    add( new Button( "Würfeln!", e -> {
      konfigurationRepository.save( service.generiereKonfiguration() );
      UI.getCurrent().getPage().reload();
    } ) );
  }

  private Component getRaum( int raumnr, Fensterseite fensterseite ) {
    TextField platz1 = createPlatz( raumnr * 10 + 1 );
    platz1.getStyle().set( "border-right", "1px solid #000000" );
    platz1.getStyle().set( "border-bottom", "1px solid #000000" );
    TextField platz2 = createPlatz( raumnr * 10 + 2 );
    platz2.getStyle().set( "border-bottom", "1px solid #000000" );
    TextField platz3 = createPlatz( raumnr * 10 + 3 );
    platz3.getStyle().set( "border-right", "1px solid #000000" );
    TextField platz4 = createPlatz( raumnr * 10 + 4 );

    HorizontalLayout reihe1 = createReihe();
    reihe1.add( platz1 );
    reihe1.add( platz2 );
    HorizontalLayout reihe2 = createReihe();
    reihe2.add( platz3 );
    reihe2.add( platz4 );
    VerticalLayout raum = createRaum( fensterseite );
    raum.add( reihe1 );
    raum.add( reihe2 );
    return raum;
  }

  private VerticalLayout createRaum( Fensterseite fensterseite ) {
    VerticalLayout raum = new VerticalLayout();
    raum.setWidth( "25%" );
    raum.setMargin( false );
    raum.setPadding( true );
    raum.setSpacing( false );
    raum.getStyle().set( "border-" + fensterseite.bezeichnung, "2px solid #000000" );
    return raum;
  }

  private HorizontalLayout createReihe() {
    HorizontalLayout reihe = new HorizontalLayout();
    reihe.setPadding( false );
    reihe.setMargin( false );
    reihe.setSpacing( false );
    return reihe;
  }

  private TextField createPlatz( long platzId ) {
    TextField platz = new TextField();
    platz.setValue( aktuelleKonfiguration.getKuerzelZuPlatzId( platzId ) );
    platz.setEnabled( false );
    platz.setWidth( "20%" );
    platz.getStyle().set( "padding", "2%" );
    platz.getStyle().set( "text-align", "center" );
    return platz;
  }

  private enum Fensterseite {
    LINKS( "left" ), RECHTS( "right" );

    private Fensterseite( String bezeichnung ) {
      this.bezeichnung = bezeichnung;
    }

    String bezeichnung;
  }

  private static final long serialVersionUID = 1992137646139137487L;
}
