package de.data_experts.eler.eler_app.gui;

import static de.data_experts.eler.eler_app.gui.Styles.DUNKEL;

import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@PageTitle( "Regeln" )
@Route( value = "regeln", layout = MainView.class )
public class RegelView extends VerticalLayout {

  public RegelView() {
    createAndAddUeberschrift( "Wann wird gewürfelt?" );
    createAndAddLabel(
        "Am ersten Arbeitstag nach Ablauf eines Zyklus wird beim Frühstücken eine neue Konfiguration erstellt." );

    createAndAddUeberschrift( "Welche Änderungen dürfen im Nachhinein an der Konfiguration vorgenommen werden?" );
    createAndAddLabel(
        "Vor dem Umzug dürfen innerhalb eines Raums die Sitzplätze im gegenseitigen Einverständnis beliebig gewechselt werden." );
    createAndAddLabel( "Änderungen sind in die Konfiguration einzupflegen." );

    createAndAddUeberschrift( "Wann wird umgezogen?" );
    createAndAddLabel(
        "An dem Tag, an dem eine neue Konfiguration erstellt wird, wird im Anschluss an die Mittagspause umgezogen." );

    createAndAddUeberschrift( "Wie lange dauert ein Zyklus?" );
    createAndAddLabel(
        "Ein Zyklus beginnt einen Tag nach Ablauf des vorherigen Zyklus und endet am letzten Tag des darauf folgenden Kalendermonats." );
    createAndAddLabel(
        "Bsp.: alter Zyklus vom 1.4. bis 31.5. => neues Würfeln am 3.6. => neuer Zyklus vom 1.6. bis 31.7." );
  }

  private void createAndAddUeberschrift( String ueberschrift ) {
    H4 header = new H4( ueberschrift );
    header.getStyle().set( "color", DUNKEL );
    add( header );
  }

  private void createAndAddLabel( String text ) {
    Label label = new Label( text );
    label.getStyle().set( "color", DUNKEL );
    label.getStyle().set( "margin-top", "0%" );
    add( label );
  }

  private static final long serialVersionUID = -7516539944724374197L;

}
