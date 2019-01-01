package de.data_experts.eler.eler_app.gui;

import java.util.List;
import java.util.Map;

import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

import de.data_experts.eler.eler_app.logik.UmzugZuordnung;

public class UmzugView extends VerticalLayout {

  public UmzugView( Map<Integer, List<UmzugZuordnung>> map ) {
    add( new H3( "Umzug-Reihenfolge:" ) );
    for ( Integer i : map.keySet() ) {
      List<UmzugZuordnung> umzugZuordnungen = map.get( i );
      for ( UmzugZuordnung u : umzugZuordnungen ) {
        if ( umzugZuordnungen.size() > 1 ) {
          if ( umzugZuordnungen.get( 0 ).equals( u ) ) {
            add( createLabel( u.getMitarbeiterKuerzel() + " --> Flur" ) );
          }
          else
            add( createLabel( u.getMitarbeiterKuerzel() ) );
        }
        else
          add( createLabel( u.getMitarbeiterKuerzel() ) );
      }
      if ( umzugZuordnungen.size() > 1 ) {
        add( createLabel( umzugZuordnungen.get( 0 ).getMitarbeiterKuerzel() ) );
      }
    }
  }

  private Label createLabel( String text ) {
    Label label = new Label( text );
    label.getStyle().set( "padding", "0px" );
    label.getStyle().set( "margin", "0px" );
    return label;
  }

  private static final long serialVersionUID = 4486763312683074885L;
}
