package de.data_experts.eler.eler_app.logik;

import java.time.LocalDate;
import java.time.temporal.IsoFields;

public class Kuechenplan {

  public boolean hatRaumKuechendienst( int raumnr ) {
    return hatRaumKuechendienst( raumnr, LocalDate.now().get( IsoFields.WEEK_OF_WEEK_BASED_YEAR ) );
  }

  boolean hatRaumKuechendienst( int raumnr, int kalenderwoche ) {
    int rest = kalenderwoche % 9;
    switch ( rest ) {
      case 1:
      case 5:
        return raumnr == 121;
      case 2:
      case 7:
        return raumnr == 125;
      case 4:
      case 8:
        return raumnr == 126;
      default:
        return false;
    }
  }
}
