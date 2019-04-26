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
		H4 h1 = new H4("Wann wird gewürfelt?");
		h1.getStyle().set("color", DUNKEL);
		add(h1);
		Label text1 = new Label("Am ersten Arbeitstag nach Ablauf eines Zyklus wird beim Frühstücken eine neue Konfiguration erstellt.");
		text1.getStyle().set("color", DUNKEL);
		text1.getStyle().set("margin-top", "0%");
		add(text1);
		
		H4 h2 = new H4("Welche Änderungen dürfen im Nachhinein an der Konfiguration vorgenommen werden?");
		h2.getStyle().set("color", DUNKEL);
		add(h2);
		Label text2 = new Label("Vor dem Umzug dürfen innerhalb eines Raums die Sitzplätze im gegenseitigen Einverständnis beliebig gewechselt werden. Änderungen sind in die Konfiguration einzupflegen.");
		text2.getStyle().set("color", DUNKEL);
		text2.getStyle().set("margin-top", "0%");
		add(text2);
		
		H4 h3 = new H4("Wann wird umgezogen?");
		h3.getStyle().set("color", DUNKEL);
		add(h3);
		Label text3 = new Label("An dem Tag, an dem eine neue Konfiguration erstellt wird, wird im Anschluss an die Mittagspause umgezogen.");
		text3.getStyle().set("color", DUNKEL);
		text3.getStyle().set("margin-top", "0%");
		add(text3);

		H4 h4 = new H4("Wie lange dauert ein Zyklus?");
		h4.getStyle().set("color", DUNKEL);
		add(h4);
		Label text4 = new Label("Ein Zyklus beginnt einen Tag nach Ablauf des vorherigen Zyklus und endet am letzten Tag des darauf folgenden Kalendermonats.\r\n Bsp.: alter Zyklus vom 1.4. bis 31.5. => neues Würfeln am 3.6. => neuer Zyklus vom 1.6. bis 31.7.");
		text4.getStyle().set("color", DUNKEL);
		text4.getStyle().set("margin-top", "0%");
		add(text4);
	}
	
	private static final long serialVersionUID = -7516539944724374197L;

}
