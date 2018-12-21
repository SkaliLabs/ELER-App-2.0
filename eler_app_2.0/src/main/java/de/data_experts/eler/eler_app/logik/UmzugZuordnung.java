package de.data_experts.eler.eler_app.logik;

import de.data_experts.eler.eler_app.model.Mitarbeiter;

/**
 * Die Klasse UmzugZuordnung repräsentiert einen Mitarbeiter mit seinem Sitzplatz in der aktuellen, sowie vorrangehenden
 * Konfiguration.
 */
public class UmzugZuordnung {

  // -- Attribute --------------------------------------------------------------

  private Mitarbeiter mitarbeiter;

  private long alterPlatzId;

  private long neuerPlatzId;

  // -- Konstruktoren ----------------------------------------------------------

  public UmzugZuordnung( Mitarbeiter mitarbeiter, long alterPlatzId, long neuerPlatzId ) {
    this.mitarbeiter = mitarbeiter;
    this.alterPlatzId = alterPlatzId;
    this.neuerPlatzId = neuerPlatzId;
  }

  // -- Getter/Setter ----------------------------------------------------------

  public long getMitarbeiterId() {
    return mitarbeiter.getId();
  }

  public String getMitarbeiterKuerzel() {
    return mitarbeiter.getKuerzel();
  }

  public long getAlterPlatzId() {
    return alterPlatzId;
  }

  public long getNeuerPlatzId() {
    return neuerPlatzId;
  }

  public void setAlterPlatzId( long alterPlatzId ) {
    this.alterPlatzId = alterPlatzId;
  }

  public void setNeuerPlatzId( long neuerPlatzId ) {
    this.neuerPlatzId = neuerPlatzId;
  }

}
