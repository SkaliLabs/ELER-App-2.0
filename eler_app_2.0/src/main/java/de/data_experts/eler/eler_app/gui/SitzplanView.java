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

import java.util.List;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.page.Page;
import com.vaadin.flow.router.Route;

import de.data_experts.eler.eler_app.db.KonfigurationRepository;
import de.data_experts.eler.eler_app.db.MitarbeiterRepository;
import de.data_experts.eler.eler_app.db.RaumRepository;
import de.data_experts.eler.eler_app.logik.VerteilungStrategie;
import de.data_experts.eler.eler_app.model.Konfiguration;
import de.data_experts.eler.eler_app.model.Mitarbeiter;
import de.data_experts.eler.eler_app.model.Raum;

@Route( value = "sitzplan" )
public class SitzplanView extends VerticalLayout {

  private final Konfiguration aktuelleKonfiguration;
  
  public SitzplanView(KonfigurationRepository konfigurationRepository,RaumRepository raumRepository,MitarbeiterRepository mitarbeiterRepository,VerteilungStrategie strategie) {
    aktuelleKonfiguration = konfigurationRepository.findAktuelle();
    add( getRaum() );
    add( new Button( "Begrüßen!", e -> Notification.show( "Hallo!" ) ) );
    List<Raum> raeume = raumRepository.findAll();
    List<Mitarbeiter> mitarbeiter = mitarbeiterRepository.findAll();
    add( new Button( "Würfeln!", e -> 
    {
    konfigurationRepository.save(strategie.generiereVerteilung(raeume, mitarbeiter) );
    	UI.getCurrent().getPage().reload();
    }));
  }

  private Component getRaum() {
    Label platz1 = createPlatz( 1261 );
    platz1.getStyle().set( "border-right", "1px solid #000000" );
    platz1.getStyle().set( "border-bottom", "1px solid #000000" );
    Label platz2 = createPlatz( 1262 );
    platz2.getStyle().set( "border-bottom", "1px solid #000000" );
    Label platz3 = createPlatz( 1263 );
    platz3.getStyle().set( "border-right", "1px solid #000000" );
    Label platz4 = createPlatz( 1264 );

    HorizontalLayout reihe1 = createReihe();
    reihe1.add( platz1 );
    reihe1.add( platz2 );
    HorizontalLayout reihe2 = createReihe();
    reihe2.add( platz3 );
    reihe2.add( platz4 );
    VerticalLayout raum = createRaum();
    raum.add( reihe1 );
    raum.add( reihe2 );
    return raum;
  }

  private VerticalLayout createRaum() {
    VerticalLayout raum = new VerticalLayout();
    raum.setWidth( "100px" );
    raum.setMargin( false );
    raum.setPadding( false );
    raum.setSpacing( false );
    raum.getStyle().set( "border-right", "2px solid #000000" );
    return raum;
  }

  private HorizontalLayout createReihe() {
    HorizontalLayout reihe = new HorizontalLayout();
    reihe.setPadding( false );
    reihe.setMargin( false );
    reihe.setSpacing( false );
    return reihe;
  }

  private Label createPlatz( long platzId ) {
    Label platz = new Label( aktuelleKonfiguration.getKuerzelZuPlatzId( platzId ) );
    platz.setWidth( "40px" );
    platz.getStyle().set( "padding", "2px" );
    platz.getStyle().set( "text-align", "center" );
    return platz;
  }

  private static final long serialVersionUID = 1992137646139137487L;
}
