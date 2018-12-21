package de.data_experts.eler.eler_app.logik;

import java.util.List;

import de.data_experts.eler.eler_app.model.Konfiguration;
import de.data_experts.eler.eler_app.model.Platzzuordnung;

/**
 * Erzeugt die Zeichnung zu einer Konfiguration in Form eines HTML-Strings.
 */
public class HTMLZeichnungsStrategie {

  // -- Attribute --------------------------------------------------------------

  // private static final String vorlage = "<HTML><HEAD><TITLE>ELER-App</TITLE><STYLE type=\"text/css\">td {height:
  // 30;width: 40;}table.raum {border: 3px solid gray;border-radius: 5px;rules: \'none\';width: 160;height:
  // 90;}</STYLE></HEAD><BODY><TABLE><TR><TD></TD><TD></TD><TD><TABLE class=\"raum\"><TR><TD align=right><TABLE
  // bordercolor=\'black\' rules=\'all\' border=0><TR><TD align=middle><!--#1264--></TD><TD
  // align=middle><!--#1261--></TD></TR><TR><TD align=middle><!--#1263--></TD><TD
  // align=middle><!--#1262--></TD></TR></TABLE></TD></TR></TABLE></TD><TD><FONT size=7>|</FONT></TD></TR><TR><TD
  // align=right><FONT size=7>|</FONT></TD><TD><TABLE class=\"raum\"><TR><TD><TABLE bordercolor=\'black\' rules=\'all\'
  // border=0><TR><TD align=middle><!--#1211--></TD><TD align=middle><!--#1212--></TD></TR><TR><TD
  // align=middle><!--#1214--></TD><TD align=middle><!--#1213--></TD></TR></TABLE></TD></TR></TABLE></TD><TD><TABLE
  // class=\"raum\"><TR><TD align=right><TABLE bordercolor=\'black\' rules=\'all\' border=0><TR><TD
  // align=middle><!--#1254--></TD><TD align=middle><!--#1251--></TD></TR><TR><TD align=middle><!--#1253--></TD><TD
  // align=middle><!--#1252--></TD></TR></TABLE></TD></TR></TABLE></TD><TD><FONT
  // size=7>|</FONT></TD></TR></TABLE></BODY></HTML>";

  private static final String vorlage = "<TABLE><TR><TD></TD><TD></TD><TD><TABLE class=\"raum\"><TR><TD align=right><TABLE bordercolor=\'black\' rules=\'all\' border=0><TR><TD align=middle><!--#1264--></TD><TD align=middle><!--#1261--></TD></TR><TR><TD align=middle><!--#1263--></TD><TD align=middle><!--#1262--></TD></TR></TABLE></TD></TR></TABLE></TD><TD><FONT size=7>|</FONT></TD></TR><TR><TD align=right><FONT size=7>|</FONT></TD><TD><TABLE class=\"raum\"><TR><TD><TABLE bordercolor=\'black\' rules=\'all\' border=0><TR><TD align=middle><!--#1211--></TD><TD align=middle><!--#1212--></TD></TR><TR><TD align=middle><!--#1214--></TD><TD align=middle><!--#1213--></TD></TR></TABLE></TD></TR></TABLE></TD><TD><TABLE class=\"raum\"><TR><TD align=right><TABLE bordercolor=\'black\' rules=\'all\' border=0><TR><TD align=middle><!--#1254--></TD><TD align=middle><!--#1251--></TD></TR><TR><TD align=middle><!--#1253--></TD><TD align=middle><!--#1252--></TD></TR></TABLE></TD></TR></TABLE></TD><TD><FONT size=7>|</FONT></TD></TR></TABLE>";

  // -- Konstruktoren ----------------------------------------------------------

  // -- Getter/Setter ----------------------------------------------------------

  // -- public Methoden --------------------------------------------------------

  public String erstelleZeichnung( Konfiguration konfiguration ) {
    String result = vorlage;
    List<Platzzuordnung> platzzuordnungen = konfiguration.getPlatzzuordnungen();
    for ( Platzzuordnung platzzuordnung : platzzuordnungen ) {
      if ( ( platzzuordnung.getMitarbeiter() != null ) ) {
        String kuerzel = platzzuordnung.getMitarbeiter().getKuerzel();
        String platzhalter = "<!--#" + String.valueOf( platzzuordnung.getPlatz().getId() ) + "-->";
        result = result.replace( platzhalter, kuerzel );
      }
    }
    return result;
  }

  // -- private Methoden -------------------------------------------------------

}
