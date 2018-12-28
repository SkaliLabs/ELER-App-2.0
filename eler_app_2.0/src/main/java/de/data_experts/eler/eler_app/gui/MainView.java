package de.data_experts.eler.eler_app.gui;

import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.router.RouterLayout;
import com.vaadin.flow.router.RouterLink;

public class MainView extends Div implements RouterLayout {

  public MainView() {
    add( new H2( "ELER-App 2.0" ) );
    add( new RouterLink( "Sitzplan", SitzplanView.class ) );
    add( new RouterLink( "Mitarbeiter", MitarbeiterView.class ) );
    add( new RouterLink( "Räume", RaumView.class ) );
  }

  private static final long serialVersionUID = 7526207307181015512L;
}
