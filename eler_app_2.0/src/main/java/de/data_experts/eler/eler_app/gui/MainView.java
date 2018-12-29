package de.data_experts.eler.eler_app.gui;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.router.RouterLayout;
import com.vaadin.flow.router.RouterLink;

public class MainView extends Div implements RouterLayout {

  public MainView() {
    HorizontalLayout ueberschrift = new HorizontalLayout();
    H2 h2 = new H2( "ELER-App 2.0" );
    h2.getStyle().set( "text-align", "center" );
    h2.getStyle().set( "color", "#20B2AA" );
    h2.getStyle().set( "width", "100%" );
    h2.getStyle().set( "padding-bottom", "2%" );
    ueberschrift.add( h2 );
    ueberschrift.getStyle().set( "background-color", "#AEFAF5" );
    add( ueberschrift );

    HorizontalLayout navigation = new HorizontalLayout();
    RouterLink sitzplanLink = createLink( "Sitzplan", SitzplanView.class );
    navigation.add( sitzplanLink );
    RouterLink mitarbeiterLink = createLink( "Mitarbeiter", MitarbeiterView.class );
    navigation.add( mitarbeiterLink );
    RouterLink raeumeLink = createLink( "Räume", RaumView.class );
    navigation.add( raeumeLink );
    navigation.getStyle().set( "background-color", "#20B2AA" );
    navigation.getStyle().set( "text-align", "center" );
    add( navigation );
    // getStyle().set( "background-color", "#E6FFFF" );
  }

  private RouterLink createLink( String label, Class<? extends Component> klazz ) {
    RouterLink sitzplanLink = new RouterLink( label, klazz );
    // sitzplanLink.getStyle().set( "width", "20%" );
    sitzplanLink.getStyle().set( "color", "#AEFAF5" );
    sitzplanLink.getStyle().set( "text-align", "center" );
    sitzplanLink.getStyle().set( "margin", "1%" );
    sitzplanLink.getStyle().set( "padding-left", "2%" );
    return sitzplanLink;
  }

  private static final long serialVersionUID = 7526207307181015512L;
}
