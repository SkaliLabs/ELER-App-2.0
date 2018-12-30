package de.data_experts.eler.eler_app.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.OneToMany;

/**
 * Eine Konfiguration repräsentiert die Platzzuordnungen, die für einen bestimmten Zeitraum gelten sollen.
 */
@Entity
public class Konfiguration {

  // -- Konstruktoren ----------------------------------------------------------

  /**
   * Für JPA
   */
  protected Konfiguration() {
  }

  public Konfiguration( List<Platzzuordnung> platzzuordnungen ) {
    this.gueltigVon = LocalDateTime.now();
    this.gueltigBis = LocalDateTime.now().plusMonths( 2 );
    this.platzzuordnungen = platzzuordnungen;
    this.tuerschilder = erstelleTuerschilder();
  }

  // -- public Methoden --------------------------------------------------------

  public String getKuerzelZuPlatzId( long platzId ) {
    for ( Platzzuordnung zuordnung : platzzuordnungen ) {
      if ( zuordnung.getPlatz().getId() == platzId ) {
        Mitarbeiter mitarbeiter = zuordnung.getMitarbeiter();
        return mitarbeiter == null ? "" : mitarbeiter.getKuerzel();
      }
    }
    return "";
  }

  public Map<Raum, List<Platzzuordnung>> getPlatzzuordnungenJeRaum() {
    Map<Raum, List<Platzzuordnung>> result = new HashMap<>();

    for ( Platzzuordnung platzzuordnung : platzzuordnungen ) {
      if ( result.get( platzzuordnung.getPlatz().getRaum() ) == null )
        result.put( platzzuordnung.getPlatz().getRaum(), new ArrayList<Platzzuordnung>() );
      result.get( platzzuordnung.getPlatz().getRaum() ).add( platzzuordnung );
    }

    // die Platzzuordnungen je Raum sortieren
    for ( Raum raum : result.keySet() )
      Collections.sort( result.get( raum ) );

    return result;
  }

  @Override
  public boolean equals( Object o ) {
    if ( o instanceof Konfiguration )
      return id == ( (Konfiguration) o ).getId();
    return false;
  }

  // -- private Methoden -------------------------------------------------------

  private String erstelleTuerschilder() {
    String result = vorlage;
    Map<Raum, List<Platzzuordnung>> platzzuordnungenJeRaum = getPlatzzuordnungenJeRaum();
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

  private String ersetzeUmlaute( String mitUmlauten ) {
    String ohneUmlaute = mitUmlauten.replace( "Ö", "&Ouml;" );
    ohneUmlaute = ohneUmlaute.replace( "Ü", "&Uuml;" );
    ohneUmlaute = ohneUmlaute.replace( "Ä", "&Auml;" );
    ohneUmlaute = ohneUmlaute.replace( "ö", "&ouml;" );
    ohneUmlaute = ohneUmlaute.replace( "ü", "&uuml;" );
    ohneUmlaute = ohneUmlaute.replace( "ä", "&auml;" );
    return ohneUmlaute;
  }

  // -- Getter/Setter ----------------------------------------------------------

  public long getId() {
    return id;
  }

  public LocalDateTime getGueltigVon() {
    return gueltigVon;
  }

  public String getGueltigVonAlsString() {
    return getGueltigVon().format( formatter );
  }

  public void setGueltigVon( LocalDateTime gueltigVon ) {
    this.gueltigVon = gueltigVon;
  }

  public LocalDateTime getGueltigBis() {
    return gueltigBis;
  }

  public String getGueltigBisAlsString() {
    return getGueltigBis().format( formatter );
  }

  public void setGueltigBis( LocalDateTime gueltigBis ) {
    this.gueltigBis = gueltigBis;
  }

  public List<Platzzuordnung> getPlatzzuordnungen() {
    return platzzuordnungen;
  }

  public String getTuerschilder() {
    return tuerschilder;
  }

  // -- Attribute --------------------------------------------------------------

  @Id
  @GeneratedValue
  private long id;

  private LocalDateTime gueltigVon;

  private LocalDateTime gueltigBis;

  @OneToMany( cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER )
  @JoinColumn( name = "konfiguration" )
  private List<Platzzuordnung> platzzuordnungen;

  @Lob
  private String tuerschilder;

  private transient DateTimeFormatter formatter = DateTimeFormatter.ofPattern( "dd. MMMM yyyy", Locale.GERMANY );

  private transient String vorlage = "<html><head><meta charset=\"utf-8\"/></head><body>\r\n" +
      "<table border=3 bordercolor=black style=\"width:14.5cm; height:14.5cm; font-family:PT Sans; background-position:center\" rules=none\r\n"
      +
      "background=\"///m:/USER.ALL/ELER/Wald.png\">\r\n" +
      "<tr style=\"height:3cm\"><td colspan=2 align=center style=\"font-size:80\"><b><u>1.26</u></b></td></tr>\r\n" +
      "<tr style=\"height:1cm\"><td colspan=2 align=center style=\"font-size:45\"><b>ELER-Wald</b></td></tr>\r\n" +
      "<tr style=\"height:1cm\"><td colspan=2></td></tr>\r\n" +
      "<tr style=\"height:1cm; font-size:30\"><td style=\"width:3cm\"></td><td><!--#1261--></td></tr>\r\n" +
      "<tr style=\"height:1cm; font-size:30\"><td style=\"width:3cm\"></td><td><!--#1262--></td></tr>\r\n" +
      "<tr style=\"height:1cm; font-size:30\"><td style=\"width:3cm\"></td><td><!--#1263--></td></tr>\r\n" +
      "<tr style=\"height:1cm; font-size:30\"><td style=\"width:3cm\"></td><td><!--#1264--></td></tr>\r\n" +
      "<tr style=\"height:1.2cm\"><td colspan=2></td></tr>\r\n" +
      "<tr style=\"max-height:2.5cm\"><td colspan=2 align=right><img src=\"///m:/USER.ALL/_data_experts/Bilder/logo.png\" style=\"height:2.4cm; padding-right:0.5cm\"></td></tr></table>\r\n"
      +
      "<table border=3 bordercolor=black style=\"width:14.5cm; height:14.5cm; font-family:PT Sans; page-break-before:always; background-position:center\" rules=none\r\n"
      +
      "background=\"///m:/USER.ALL/ELER/Wiese.png\">\r\n" +
      "<tr style=\"height:3cm\"><td colspan=2 align=center style=\"font-size:80\"><b><u>1.21</u></b></td></tr>\r\n" +
      "<tr style=\"height:1cm\"><td colspan=2 align=center style=\"font-size:45\"><b>ELER-Wiese</b></td></tr>\r\n" +
      "<tr style=\"height:1cm\"><td colspan=2></td></tr>\r\n" +
      "<tr style=\"height:1cm; font-size:30\"><td style=\"width:3cm\"></td><td><!--#1211--></td></tr>\r\n" +
      "<tr style=\"height:1cm; font-size:30\"><td style=\"width:3cm\"></td><td><!--#1212--></td></tr>\r\n" +
      "<tr style=\"height:1cm; font-size:30\"><td style=\"width:3cm\"></td><td><!--#1213--></td></tr>\r\n" +
      "<tr style=\"height:1cm; font-size:30\"><td style=\"width:3cm\"></td><td><!--#1214--></td></tr>\r\n" +
      "<tr style=\"height:1.2cm\"><td colspan=2></td></tr>\r\n" +
      "<tr style=\"max-height:2.5cm\"><td colspan=2 align=right><img src=\"///m:/USER.ALL/_data_experts/Bilder/logo.png\" style=\"height:2.4cm; padding-right:0.5cm\"></td></tr></table>\r\n"
      +
      "<table border=3 bordercolor=black style=\"width:14.5cm; height:14.5cm; font-family:PT Sans; page-break-before:always; background-position:center\" rules=none\r\n"
      +
      "background=\"///m:/USER.ALL/ELER/Strand.png\">\r\n" +
      "<tr style=\"height:3cm\"><td colspan=2 align=center style=\"font-size:80\"><b><u>1.25</u></b></td></tr>\r\n" +
      "<tr style=\"height:1cm\"><td colspan=2 align=center style=\"font-size:45\"><b>ELER-Strand</b></td></tr>\r\n" +
      "<tr style=\"height:1cm\"><td colspan=2></td></tr>\r\n" +
      "<tr style=\"height:1cm; font-size:30\"><td style=\"width:3cm\"></td><td><!--#1251--></td></tr>\r\n" +
      "<tr style=\"height:1cm; font-size:30\"><td style=\"width:3cm\"></td><td><!--#1252--></td></tr>\r\n" +
      "<tr style=\"height:1cm; font-size:30\"><td style=\"width:3cm\"></td><td><!--#1253--></td></tr>\r\n" +
      "<tr style=\"height:1cm; font-size:30\"><td style=\"width:3cm\"></td><td><!--#1254--></td></tr>\r\n" +
      "<tr style=\"height:1.2cm\"><td colspan=2></td></tr>\r\n" +
      "<tr style=\"max-height:2.5cm\"><td colspan=2 align=right><img src=\"///m:/USER.ALL/_data_experts/Bilder/logo.png\" style=\"height:2.4cm; padding-right:0.5cm\"></td></tr></table></body></html>";
}
