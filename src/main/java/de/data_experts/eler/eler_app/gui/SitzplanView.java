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

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.html.UnorderedList;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import de.data_experts.eler.eler_app.logik.SitzplanLogik;
import de.data_experts.eler.eler_app.logik.ValidierungService;
import de.data_experts.eler.eler_app.mail.EmailService;
import de.data_experts.eler.eler_app.model.Konfiguration;
import org.springframework.beans.factory.annotation.Value;

import java.util.List;

import static de.data_experts.eler.eler_app.gui.Styles.DUNKEL;
import static de.data_experts.eler.eler_app.gui.Styles.HELL;
import static de.data_experts.eler.eler_app.gui.Styles.MITTEL;

@PageTitle("Sitzplan")
@Route(value = "", layout = MainView.class)
public class SitzplanView extends VerticalLayout {

    public SitzplanView(SitzplanLogik sitzplanLogik, EmailService emailService, ValidierungService validierungsservice) {
        this.sitzplanLogik = sitzplanLogik;

        H3 titel = new H3(createTitel(sitzplanLogik.getTitel()));
        titel.getStyle().set("color", DUNKEL);
        add(titel);

        HorizontalLayout raumreihe1 = new HorizontalLayout();
        raumreihe1.add(getRaum(125, Fensterseite.LINKS));
        raumreihe1.add(getRaum(121, Fensterseite.RECHTS));
        add(raumreihe1);

        HorizontalLayout raumreihe2 = new HorizontalLayout();
        raumreihe2.add(getRaum(126, Fensterseite.LINKS));
        add(raumreihe2);

        boolean isKonfigurationAbgelaufen = sitzplanLogik.isKonfigurationAbgelaufen();
        Button wuerfelnButton = createButton("Shuffle!", isKonfigurationAbgelaufen, shuffle(validierungsservice, emailService));
        wuerfelnButton.setEnabled(isKonfigurationAbgelaufen);
        add(wuerfelnButton);

        Button umzugButton = createButton("Umzugsplan anzeigen!", true, e -> umzugsDialogOeffnen());
        add(umzugButton);
    }

    private ComponentEventListener<ClickEvent<Button>> shuffle(ValidierungService validierungsservice, EmailService emailService) {
        List<String> fehler = validierungsservice.validiere();
        return fehler.isEmpty() ? e -> createSicherheitsabfrageDialog(sitzplanLogik, emailService) : e -> createNichtValideDialog(fehler);
    }

    private void createNichtValideDialog(List<String> fehlertexte) {
        Dialog dialog = new Dialog();
        VerticalLayout layout = new VerticalLayout();
        layout.add(createTitel("Nicht valide Angaben"));
        UnorderedList fehlerliste = new UnorderedList();
        fehlertexte.forEach(fehler -> fehlerliste.add(new Text(fehler)));
        layout.add(fehlerliste);
        layout.add(createButton("OK", true, e -> dialog.close()));
        dialog.add(layout);
        dialog.open();
    }

    private void createSicherheitsabfrageDialog(SitzplanLogik sitzplanLogik, EmailService emailService) {
        Dialog dialog = new Dialog();
        VerticalLayout layout = new VerticalLayout();
        layout.add(createTitel("Sicherheitsabfrage"));
        TextField passwortfeld = new TextField("Passwort:");
        passwortfeld.setErrorMessage("Falsches Passwort!");
        passwortfeld.getElement().getStyle().set("color", DUNKEL);
        layout.add(passwortfeld);
        layout.add(createButton("Bestätigen", true, e -> {
            if (passwortfeld.getValue().equals(passwortWuerfeln)) {
                Konfiguration neueKonfiguration = sitzplanLogik.erzeugeNeueKonfiguration();
                emailService.sendeMailMitKonfiguration(neueKonfiguration);
                UI.getCurrent().getPage().reload();
            } else {
                passwortfeld.setInvalid(true);
            }
        }));
        dialog.add(layout);
        dialog.open();
    }

    private H3 createTitel(String text) {
        H3 titel = new H3(text);
        titel.getStyle().set("color", DUNKEL);
        titel.getStyle().set("padding-top", "0px");
        titel.getStyle().set("margin-top", "0px");
        return titel;
    }

    private Button createButton(String titel, boolean isButtonAktiv,
                                ComponentEventListener<ClickEvent<Button>> clickListener) {
        Button wuerfelnButton = new Button(titel, clickListener);
        if (isButtonAktiv) {
            wuerfelnButton.getStyle().set("color", HELL);
            wuerfelnButton.getStyle().set("background-color", MITTEL);
        } else {
            wuerfelnButton.getStyle().set("color", MITTEL);
            wuerfelnButton.getStyle().set("background-color", HELL);
        }
        return wuerfelnButton;
    }

    private void umzugsDialogOeffnen() {
        Dialog dialog = new Dialog();
        dialog.add(createTitel(sitzplanLogik.getTitelUmzugsdialog()));
        List<String> umzugZuordnungen = sitzplanLogik.getUmzugZuordnungen();
        if (!umzugZuordnungen.isEmpty()) {
            VerticalLayout umzugView = new VerticalLayout();
            umzugZuordnungen.forEach(zuordnung -> umzugView.add(createLabel(zuordnung)));
            dialog.add(umzugView);
        }
        dialog.add(createButton("Schließen", true, e -> dialog.close()));
        dialog.open();
    }

    private Label createLabel(String text) {
        Label label = new Label(text);
        label.getStyle().set("color", DUNKEL);
        label.getStyle().set("padding", "0px");
        label.getStyle().set("margin", "0px");
        return label;
    }

    private Component getRaum(int raumNr, Fensterseite fensterseite) {
        boolean hatRaumKuechendienst = sitzplanLogik.hatRaumKuechendienst(raumNr);
        String rahmenfarbe = hatRaumKuechendienst ? Styles.DUNKEL : Styles.SCHWARZ;
        TextField platz1 = createPlatz(raumNr * 10L + 1);
        platz1.getStyle().set("border-right", "1px solid " + rahmenfarbe);
        platz1.getStyle().set("border-bottom", "1px solid " + rahmenfarbe);
        TextField platz2 = createPlatz(raumNr * 10L + 2);
        platz2.getStyle().set("border-bottom", "1px solid " + rahmenfarbe);
        TextField platz3 = createPlatz(raumNr * 10L + 3);
        platz3.getStyle().set("border-right", "1px solid " + rahmenfarbe);
        TextField platz4 = createPlatz(raumNr * 10L + 4);

        HorizontalLayout reihe1 = createReihe();
        reihe1.add(platz1);
        reihe1.add(platz2);
        HorizontalLayout reihe2 = createReihe();
        reihe2.add(platz3);
        reihe2.add(platz4);
        VerticalLayout raum = createRaum(fensterseite, rahmenfarbe);
        raum.add(reihe1);
        raum.add(reihe2);
        return raum;
    }

    private VerticalLayout createRaum(Fensterseite fensterseite, String rahmenfarbe) {
        VerticalLayout raum = new VerticalLayout();
        raum.setWidth("25%");
        raum.setMargin(false);
        raum.setPadding(true);
        raum.setSpacing(false);
        raum.getStyle().set("border-" + fensterseite.bezeichnung, "2px solid " + rahmenfarbe);
        return raum;
    }

    private HorizontalLayout createReihe() {
        HorizontalLayout reihe = new HorizontalLayout();
        reihe.setPadding(false);
        reihe.setMargin(false);
        reihe.setSpacing(false);
        return reihe;
    }

    private TextField createPlatz(long platzId) {
        TextField platz = new TextField();
        platz.setValue(sitzplanLogik.getPlatzbezeichnung(platzId));
        platz.setReadOnly(true);
        platz.setWidth("20%");
        platz.getStyle().set("padding", "2%");
        platz.getStyle().set("text-align", "center");
        return platz;
    }

    private enum Fensterseite {
        LINKS("left"), RECHTS("right");

        Fensterseite(String bezeichnung) {
            this.bezeichnung = bezeichnung;
        }

        final String bezeichnung;
    }

    private final SitzplanLogik sitzplanLogik;

    @Value("${passwort.wuerfeln}")
    private String passwortWuerfeln;

    private static final long serialVersionUID = 1992137646139137487L;
}
