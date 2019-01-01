package de.data_experts.eler.eler_app.gui;

import java.util.List;
import java.util.Map;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

import de.data_experts.eler.eler_app.logik.UmzugZuordnung;
import de.data_experts.eler.eler_app.logik.UmzugZuordnungHelper;
import de.data_experts.eler.eler_app.model.Konfiguration;

public class UmzugView extends VerticalLayout {

  public UmzugView( Konfiguration konfiguration ) {
    Map<Integer, List<UmzugZuordnung>> map = new UmzugZuordnungHelper().erstelleUmzugZuordnungen( konfiguration );
    add( new H2( "Umzug-Reihenfolge:" ) );
    for ( Integer i : map.keySet() ) {
      List<UmzugZuordnung> umzugZuordnungen = map.get( i );
      for ( UmzugZuordnung u : umzugZuordnungen ) {
        if ( umzugZuordnungen.size() > 1 ) {
          if ( umzugZuordnungen.get( 0 ).equals( u ) ) {
            add( new Text( u.getMitarbeiterKuerzel() + " --> Flur" ) );
          }
          else
            add( new Text( u.getMitarbeiterKuerzel() ) );
        }
        else
          add( new Text( u.getMitarbeiterKuerzel() ) );
      }
      if ( umzugZuordnungen.size() > 1 ) {
        add( new Text( umzugZuordnungen.get( 0 ).getMitarbeiterKuerzel() ) );
      }
    }
  }

  private static final long serialVersionUID = 4486763312683074885L;
}
