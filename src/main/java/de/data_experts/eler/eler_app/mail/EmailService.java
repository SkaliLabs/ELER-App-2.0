package de.data_experts.eler.eler_app.mail;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import de.data_experts.eler.eler_app.model.Konfiguration;
import de.data_experts.eler.eler_app.model.Platzzuordnung;
import de.data_experts.eler.eler_app.model.Raum;

@Service
public class EmailService {

  public void sendeMailMitKonfiguration( Konfiguration konfiguration ) {
    SimpleMailMessage message = new SimpleMailMessage();
    message.setTo( "t_sekretariat@data-experts.de", "t_profil_eler@data-experts.de" );
    message.setFrom( adminMailadresse );
    message.setSubject( "ELER ist wieder umgezogen" );
    message.setText( erzeugeNachricht( konfiguration ) );
    sendeMail( message );
  }

  private String erzeugeNachricht( Konfiguration konfiguration ) {
    List<String> zeilen = new ArrayList<>();
    Map<Raum, List<Platzzuordnung>> platzzuordnungenJeRaum = konfiguration.getPlatzzuordnungenJeRaum();

    for ( Entry<Raum, List<Platzzuordnung>> entry : platzzuordnungenJeRaum.entrySet() ) {
      zeilen.add( entry.getKey().getBezeichnung() + ":" );
      entry.getValue().stream().filter( platzZuordnung -> platzZuordnung.getMitarbeiter() != null ).forEach( platzZuordnung -> zeilen.add( platzZuordnung.getMitarbeiter().getName() ) );
      zeilen.add( "" );
    }
    return String.join( "\n", zeilen );
  }

  private void sendeMail( SimpleMailMessage message ) {
    if ( isAktiv )
      emailSender.send( message );
    else {
      System.out.println( "Mail nicht versendet an: " + String.join( ",", message.getTo() ) );
      System.out.println( "Nachricht: " + message.getText() );
    }
  }

  @Value( "${emailservice.isaktiv}" )
  private boolean isAktiv;

  private static final String adminMailadresse = "t_profil_eler@data-experts.de";

  @Autowired
  private JavaMailSender emailSender;
}