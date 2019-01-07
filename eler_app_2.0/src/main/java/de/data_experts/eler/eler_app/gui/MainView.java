package de.data_experts.eler.eler_app.gui;

import static de.data_experts.eler.eler_app.gui.Styles.HELL;
import static de.data_experts.eler.eler_app.gui.Styles.MITTEL;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.html.Anchor;
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
    h2.getStyle().set( "color", MITTEL );
    h2.getStyle().set( "width", "100%" );
    h2.getStyle().set( "padding-bottom", "2%" );
    ueberschrift.add( h2 );
    ueberschrift.getStyle().set( "background-color", HELL );
    add( ueberschrift );

    HorizontalLayout navigation = new HorizontalLayout();
    navigation.add( createRouterLink( "Sitzplan", SitzplanView.class ) );
    navigation.add( createRouterLink( "Mitarbeiter", MitarbeiterView.class ) );
    navigation.add( createRouterLink( "Plätze", PlatzView.class ) );
    navigation.add( createAnchor() );
    navigation.getStyle().set( "background-color", MITTEL );
    navigation.getStyle().set( "text-align", "center" );
    add( navigation );
  }

  private Anchor createAnchor() {
    Anchor link = new Anchor( "/tuerschilder", "Türschilder" );
    link.getStyle().set( "color", HELL );
    link.getStyle().set( "text-align", "center" );
    link.getStyle().set( "margin", "1%" );
    link.getStyle().set( "padding-left", "2%" );
    link.setTarget( "_blank" );
    return link;
  }

  private RouterLink createRouterLink( String label, Class<? extends Component> klazz ) {
    RouterLink link = new RouterLink( label, klazz );
    link.getStyle().set( "color", HELL );
    link.getStyle().set( "text-align", "center" );
    link.getStyle().set( "margin", "1%" );
    link.getStyle().set( "padding-left", "2%" );
    return link;
  }

  private static final long serialVersionUID = 7526207307181015512L;
}
