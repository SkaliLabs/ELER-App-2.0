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

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import de.data_experts.eler.eler_app.db.KonfigurationRepository;
import de.data_experts.eler.eler_app.logik.RaumbelegungService;
import de.data_experts.eler.eler_app.logik.UmzugZuordnungHelper;
import de.data_experts.eler.eler_app.model.Konfiguration;

@PageTitle( "Sitzplan" )
@Route( value = "", layout = MainView.class )
public class SitzplanView extends VerticalLayout {

  public SitzplanView( KonfigurationRepository konfigurationRepository, RaumbelegungService service, UmzugZuordnungHelper umzugZuordnungHelper ) {
    aktuelleKonfiguration = konfigurationRepository.findAktuelle();

    H3 ueberschrift = new H3( "Aktuelle Konfiguration gültig vom " + aktuelleKonfiguration.getGueltigVonAlsString()
        + " bis zum " + aktuelleKonfiguration.getGueltigBisAlsString() );
    ueberschrift.getStyle().set( "color", DUNKEL );
    add( ueberschrift );

    HorizontalLayout raumreihe1 = new HorizontalLayout();
    raumreihe1.add( getRaum( 125, Fensterseite.LINKS ) );
    raumreihe1.add( getRaum( 121, Fensterseite.RECHTS ) );
    add( raumreihe1 );

    HorizontalLayout raumreihe2 = new HorizontalLayout();
    raumreihe2.add( getRaum( 126, Fensterseite.LINKS ) );
    add( raumreihe2 );

    Button wuerfelnButton = new Button( "Würfeln!", e -> {
      konfigurationRepository.save( service.generiereKonfiguration( aktuelleKonfiguration ) );
      UI.getCurrent().getPage().reload();
    } );
    wuerfelnButton.getStyle().set( "color", DUNKEL );
    wuerfelnButton.getStyle().set( "background-color", HELL );
    add( wuerfelnButton );

    Button umzugButton = new Button( "Umzugsplan anzeigen!", e -> {
      Dialog dialog = new Dialog();
      dialog.add( new UmzugView( umzugZuordnungHelper.erstelleUmzugZuordnungen( aktuelleKonfiguration ) ) );
      dialog.open();
    } );
    umzugButton.getStyle().set( "color", DUNKEL );
    umzugButton.getStyle().set( "background-color", HELL );
    add( umzugButton );
  }

  private Component getRaum( int raumnr, Fensterseite fensterseite ) {
    TextField platz1 = createPlatz( raumnr * 10 + 1 );
    platz1.getStyle().set( "border-right", "1px solid " + DUNKEL );
    platz1.getStyle().set( "border-bottom", "1px solid " + DUNKEL );
    TextField platz2 = createPlatz( raumnr * 10 + 2 );
    platz2.getStyle().set( "border-bottom", "1px solid " + DUNKEL );
    TextField platz3 = createPlatz( raumnr * 10 + 3 );
    platz3.getStyle().set( "border-right", "1px solid " + DUNKEL );
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
    raum.getStyle().set( "border-" + fensterseite.bezeichnung, "2px solid " + DUNKEL );
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
    platz.setReadOnly( true );
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

  private final Konfiguration aktuelleKonfiguration;

  private static final long serialVersionUID = 1992137646139137487L;
}
