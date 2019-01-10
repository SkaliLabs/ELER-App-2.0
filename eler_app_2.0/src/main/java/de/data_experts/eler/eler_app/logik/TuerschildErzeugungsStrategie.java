package de.data_experts.eler.eler_app.logik;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import de.data_experts.eler.eler_app.model.Konfiguration;
import de.data_experts.eler.eler_app.model.Mitarbeiter;
import de.data_experts.eler.eler_app.model.Platzzuordnung;
import de.data_experts.eler.eler_app.model.Raum;

/**
 * Erzeugt die Türschilder zu einer Konfiguration in Form eines HTML-Strings.
 */
public class TuerschildErzeugungsStrategie {

  // -- Konstruktoren ----------------------------------------------------------

  // -- public Methoden --------------------------------------------------------

  public String erstelleTuerschilder( Konfiguration konfiguration ) {
    String result = vorlage;
    Map<Raum, List<Platzzuordnung>> platzzuordnungenJeRaum = konfiguration.getPlatzzuordnungenJeRaum();
    for ( Raum raum : platzzuordnungenJeRaum.keySet() ) {
      List<Mitarbeiter> mitarbeiterImRaum = platzzuordnungenJeRaum.get( raum ).stream()
          .map( zuordnung -> zuordnung.getMitarbeiter() ).filter( mitarbeiter -> mitarbeiter != null )
          .collect( Collectors.toList() );
      long position = raum.getRaumNr() * 10 + 1;
      for ( Mitarbeiter mitarbeiter : mitarbeiterImRaum ) {
        String name = mitarbeiter.getNachname() + ", " + mitarbeiter.getVorname();
        String platzhalter = "<!--#" + position + "-->";
        result = result.replace( platzhalter, name );
        position++;
      }
    }
    result = ersetzeUmlaute( result );
    return result;
  }

  // -- private Methoden -------------------------------------------------------

  private String ersetzeUmlaute( String mitUmlauten ) {
    String ohneUmlaute = mitUmlauten.replace( "�", "&Ouml;" );
    ohneUmlaute = ohneUmlaute.replace( "�", "&Uuml;" );
    ohneUmlaute = ohneUmlaute.replace( "�", "&Auml;" );
    ohneUmlaute = ohneUmlaute.replace( "�", "&ouml;" );
    ohneUmlaute = ohneUmlaute.replace( "�", "&uuml;" );
    ohneUmlaute = ohneUmlaute.replace( "�", "&auml;" );
    return ohneUmlaute;
  }

  // -- Getter/Setter ----------------------------------------------------------

  // -- Attribute --------------------------------------------------------------

  private String vorlage = "<html><head><meta charset=\"utf-8\"/><title>Türschilder</title></head><body>\r\n"
      + "<table border=3 bordercolor=black style=\"width:14.5cm; height:14.5cm; font-family:PT Sans; background-position:center\""
      + "rules=none\r\n background=\"raumbilder/Wald.png\">\r\n" +
      "<tr style=\"height:3cm\"><td colspan=2 align=center style=\"font-size:80\"><b><u>1.26</u></b></td></tr>\r\n" +
      "<tr style=\"height:1cm\"><td colspan=2 align=center style=\"font-size:45\"><b>ELER-Wald</b></td></tr>\r\n" +
      "<tr style=\"height:1cm\"><td colspan=2></td></tr>\r\n" +
      "<tr style=\"height:1cm; font-size:30\"><td style=\"width:3cm\"></td><td><!--#1261--></td></tr>\r\n" +
      "<tr style=\"height:1cm; font-size:30\"><td style=\"width:3cm\"></td><td><!--#1262--></td></tr>\r\n" +
      "<tr style=\"height:1cm; font-size:30\"><td style=\"width:3cm\"></td><td><!--#1263--></td></tr>\r\n" +
      "<tr style=\"height:1cm; font-size:30\"><td style=\"width:3cm\"></td><td><!--#1264--></td></tr>\r\n" +
      "<tr style=\"height:1.2cm\"><td colspan=2></td></tr>\r\n" +
      "<tr style=\"max-height:2.5cm\"><td colspan=2 align=right><img src=\"raumbilder/logo.png\""
      + "style=\"height:2.4cm; padding-right:0.5cm\"></td></tr></table>\r\n" +
      "<table border=3 bordercolor=black style=\"width:14.5cm; height:14.5cm; font-family:PT Sans; page-break-before:always; background-position:center\""
      + "rules=none\r\n background=\"raumbilder/Wiese.png\">\r\n" +
      "<tr style=\"height:3cm\"><td colspan=2 align=center style=\"font-size:80\"><b><u>1.21</u></b></td></tr>\r\n" +
      "<tr style=\"height:1cm\"><td colspan=2 align=center style=\"font-size:45\"><b>ELER-Wiese</b></td></tr>\r\n" +
      "<tr style=\"height:1cm\"><td colspan=2></td></tr>\r\n" +
      "<tr style=\"height:1cm; font-size:30\"><td style=\"width:3cm\"></td><td><!--#1211--></td></tr>\r\n" +
      "<tr style=\"height:1cm; font-size:30\"><td style=\"width:3cm\"></td><td><!--#1212--></td></tr>\r\n" +
      "<tr style=\"height:1cm; font-size:30\"><td style=\"width:3cm\"></td><td><!--#1213--></td></tr>\r\n" +
      "<tr style=\"height:1cm; font-size:30\"><td style=\"width:3cm\"></td><td><!--#1214--></td></tr>\r\n" +
      "<tr style=\"height:1.2cm\"><td colspan=2></td></tr>\r\n" +
      "<tr style=\"max-height:2.5cm\"><td colspan=2 align=right><img src=\"raumbilder/logo.png\" "
      + "style=\"height:2.4cm; padding-right:0.5cm\"></td></tr></table>\r\n" +
      "<table border=3 bordercolor=black style=\"width:14.5cm; height:14.5cm; font-family:PT Sans; page-break-before:always; background-position:center\""
      + "rules=none\r\n background=\"raumbilder/Strand.png\">\r\n" +
      "<tr style=\"height:3cm\"><td colspan=2 align=center style=\"font-size:80\"><b><u>1.25</u></b></td></tr>\r\n" +
      "<tr style=\"height:1cm\"><td colspan=2 align=center style=\"font-size:45\"><b>ELER-Strand</b></td></tr>\r\n" +
      "<tr style=\"height:1cm\"><td colspan=2></td></tr>\r\n" +
      "<tr style=\"height:1cm; font-size:30\"><td style=\"width:3cm\"></td><td><!--#1251--></td></tr>\r\n" +
      "<tr style=\"height:1cm; font-size:30\"><td style=\"width:3cm\"></td><td><!--#1252--></td></tr>\r\n" +
      "<tr style=\"height:1cm; font-size:30\"><td style=\"width:3cm\"></td><td><!--#1253--></td></tr>\r\n" +
      "<tr style=\"height:1cm; font-size:30\"><td style=\"width:3cm\"></td><td><!--#1254--></td></tr>\r\n" +
      "<tr style=\"height:1.2cm\"><td colspan=2></td></tr>\r\n" +
      "<tr style=\"max-height:2.5cm\"><td colspan=2 align=right><img src=\"raumbilder/logo.png\" style=\"height:2.4cm; padding-right:0.5cm\"></td></tr></table></body></html>";

}
