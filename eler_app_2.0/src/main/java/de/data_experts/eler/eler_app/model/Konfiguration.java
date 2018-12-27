package de.data_experts.eler.eler_app.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
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
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ( ( gueltigBis == null ) ? 0 : gueltigBis.hashCode() );
    result = prime * result + ( ( gueltigVon == null ) ? 0 : gueltigVon.hashCode() );
    result = prime * result + ( ( platzzuordnungen == null ) ? 0 : platzzuordnungen.hashCode() );
    return result;
  }

  @Override
  public boolean equals( Object o ) {

    if ( o == null || o.getClass() != getClass() )
      return false;

    if ( o == this )
      return true;

    Konfiguration other = (Konfiguration) o;

    if ( !gueltigVon.equals( other.gueltigVon ) )
      return false;

    if ( !gueltigBis.equals( other.gueltigBis ) )
      return false;

    if ( platzzuordnungen == null && other.platzzuordnungen == null )
      return true;

    if ( platzzuordnungen == null || other.platzzuordnungen == null )
      return false;

    if ( platzzuordnungen.size() != other.platzzuordnungen.size() )
      return false;

    for ( Platzzuordnung platzzuordnung : platzzuordnungen )
      if ( !other.platzzuordnungen.contains( platzzuordnung ) )
        return false;

    return true;
  }

  /**
   * Liefert die Stringrepräsentation der Konfiguration. Diese wird zum Füllen der Infomail genutzt.
   */
  @Override
  public String toString() {
    StringBuilder result = new StringBuilder();
    result.append( "Gültigkeit von " + getGueltigVonAlsString() + " bis " + getGueltigBisAlsString() + "\n" );

    Map<Raum, List<Platzzuordnung>> zuordnungenJeRaum = getPlatzzuordnungenJeRaum();
    for ( Raum raum : zuordnungenJeRaum.keySet() ) {
      result.append( "\n" + raum.getBezeichnung() + "\n" );
      for ( Platzzuordnung zuordnung : zuordnungenJeRaum.get( raum ) ) {
        if ( zuordnung.getMitarbeiter() != null )
          result.append(
              zuordnung.getPlatz().getBezeichnung() + ": " + zuordnung.getMitarbeiter().getName() + "\n" );
        else
          result.append( zuordnung.getPlatz().getBezeichnung() + ":\n" );
      }
    }
    return result.toString();

  }

  public String toHtml() {
    StringBuilder result = new StringBuilder();

    result.append( "Gültigkeit von " + getGueltigVonAlsString() + " bis " + getGueltigBisAlsString() + "\n" );
    result.append( "		<table border='1' style='margin-left:7px; margin-top:5px'>" );
    result.append( "		<tr>" );
    result.append( "			<th> <b>Platz</b> </th>" );
    result.append( "			<th> <b>Name</b> </th>" );
    result.append( "		</tr>)" );

    Map<Raum, List<Platzzuordnung>> zuordnungenJeRaum = getPlatzzuordnungenJeRaum();
    for ( Raum raum : zuordnungenJeRaum.keySet() ) {
      result.append( "		<tr> " );
      result.append( "			<td colspan = '3'> <b>" + raum.getBezeichnung() + "</b> </td>" );
      result.append( "		</tr>" );

      for ( Platzzuordnung zuordnung : zuordnungenJeRaum.get( raum ) ) {
        if ( zuordnung.getMitarbeiter() != null ) {
          result.append( "		<tr>" );
          result.append( "			<td>" + zuordnung.getPlatz().getBezeichnung() + ": " + "</td> " );
          result.append( "			<td>" + zuordnung.getMitarbeiter().getName() + ": " + "</td> " );
          result.append( "		</tr>" );
        }
        else {
          result.append( "		<tr>" );
          result.append( "			<td>" + zuordnung.getPlatz().getBezeichnung() + "</td> " );
          result.append( "			<td></td> " );
          result.append( "			<td></td> " );
          result.append( "		</tr>" );

        }
      }
    }
    result.append( "</table>" );
    return result.toString();
  }

  // -- private Methoden -------------------------------------------------------

  // -- Attribute --------------------------------------------------------------

  @Id
  @GeneratedValue
  private long id;

  private LocalDateTime gueltigVon;

  private LocalDateTime gueltigBis;

  @OneToMany( cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER )
  @JoinColumn( name = "konfiguration" )
  private List<Platzzuordnung> platzzuordnungen;

  private transient DateTimeFormatter formatter = DateTimeFormatter.ofPattern( "dd. MMMM yyyy", Locale.GERMANY );
}
