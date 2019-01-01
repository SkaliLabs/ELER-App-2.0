package de.data_experts.eler.eler_app.gui;

import static de.data_experts.eler.eler_app.gui.Styles.DUNKEL;

import java.util.List;
import java.util.Map;

import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

import de.data_experts.eler.eler_app.logik.UmzugZuordnung;

public class UmzugView extends VerticalLayout {

  public UmzugView( Map<Integer, List<UmzugZuordnung>> map ) {
    for ( Integer i : map.keySet() ) {
      List<UmzugZuordnung> umzugZuordnungen = map.get( i );
      for ( UmzugZuordnung u : umzugZuordnungen ) {
        if ( umzugZuordnungen.size() > 1 ) {
          if ( umzugZuordnungen.get( 0 ).equals( u ) ) {
            Label label = createLabel( u.getMitarbeiterKuerzel() + " --> Flur" );
            label.getStyle().set( "margin-top", "5%" );
            label.getStyle().set( "padding-top", "5%" );
            add( label );
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
    label.getStyle().set( "color", DUNKEL );
    label.getStyle().set( "padding", "0px" );
    label.getStyle().set( "margin", "0px" );
    return label;
  }

  private static final long serialVersionUID = 4486763312683074885L;
}
