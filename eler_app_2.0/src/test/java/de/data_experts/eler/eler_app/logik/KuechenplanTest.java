package de.data_experts.eler.eler_app.logik;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class KuechenplanTest {

  @Test
  public void test_hatRaumKuechendienst_Kalenderwoche_1() {
    assertTrue( new Kuechenplan().hatRaumKuechendienst( 121, 1 ) );
    assertFalse( new Kuechenplan().hatRaumKuechendienst( 125, 1 ) );
    assertFalse( new Kuechenplan().hatRaumKuechendienst( 126, 1 ) );
  }

  @Test
  public void test_hatRaumKuechendienst_Kalenderwoche_2() {
    assertFalse( new Kuechenplan().hatRaumKuechendienst( 121, 2 ) );
    assertFalse( new Kuechenplan().hatRaumKuechendienst( 125, 2 ) );
    assertFalse( new Kuechenplan().hatRaumKuechendienst( 126, 2 ) );
  }

  @Test
  public void test_hatRaumKuechendienst_Kalenderwoche_3() {
    assertFalse( new Kuechenplan().hatRaumKuechendienst( 121, 3 ) );
    assertTrue( new Kuechenplan().hatRaumKuechendienst( 125, 3 ) );
    assertFalse( new Kuechenplan().hatRaumKuechendienst( 126, 3 ) );
  }

  @Test
  public void test_hatRaumKuechendienst_Kalenderwoche_4() {
    assertFalse( new Kuechenplan().hatRaumKuechendienst( 121, 4 ) );
    assertFalse( new Kuechenplan().hatRaumKuechendienst( 125, 4 ) );
    assertFalse( new Kuechenplan().hatRaumKuechendienst( 126, 4 ) );
  }

  @Test
  public void test_hatRaumKuechendienst_Kalenderwoche_5() {
	assertFalse( new Kuechenplan().hatRaumKuechendienst( 121, 5 ) );
    assertFalse( new Kuechenplan().hatRaumKuechendienst( 125, 5 ) );
    assertTrue( new Kuechenplan().hatRaumKuechendienst( 126, 5 ) );
  }

  @Test
  public void test_hatRaumKuechendienst_Kalenderwoche_6() {
    assertFalse( new Kuechenplan().hatRaumKuechendienst( 121, 6 ) );
    assertFalse( new Kuechenplan().hatRaumKuechendienst( 125, 6 ) );
    assertFalse( new Kuechenplan().hatRaumKuechendienst( 126, 6 ) );
  }

  @Test
  public void test_hatRaumKuechendienst_Kalenderwoche_7() {
	assertTrue( new Kuechenplan().hatRaumKuechendienst( 121, 7 ) );
	assertFalse( new Kuechenplan().hatRaumKuechendienst( 125, 7 ) );
    assertFalse( new Kuechenplan().hatRaumKuechendienst( 126, 7 ) );
  }

  @Test
  public void test_hatRaumKuechendienst_Kalenderwoche_8() {
    assertFalse( new Kuechenplan().hatRaumKuechendienst( 121, 8 ) );
    assertFalse( new Kuechenplan().hatRaumKuechendienst( 125, 8 ) );
    assertFalse( new Kuechenplan().hatRaumKuechendienst( 126, 8 ) );
  }

  @Test
  public void test_hatRaumKuechendienst_Kalenderwoche_9() {
    assertFalse( new Kuechenplan().hatRaumKuechendienst( 121, 9 ) );
    assertTrue( new Kuechenplan().hatRaumKuechendienst( 125, 9 ) );
    assertFalse( new Kuechenplan().hatRaumKuechendienst( 126, 9 ) );
  }

  @Test
  public void test_hatRaumKuechendienst_Kalenderwoche_10() {
	assertFalse( new Kuechenplan().hatRaumKuechendienst( 121, 10 ) );
    assertFalse( new Kuechenplan().hatRaumKuechendienst( 125, 10 ) );
    assertFalse( new Kuechenplan().hatRaumKuechendienst( 126, 10 ) );
  }

  @Test
  public void test_hatRaumKuechendienst_Kalenderwoche_11() {
    assertFalse( new Kuechenplan().hatRaumKuechendienst( 121, 11 ) );
    assertFalse( new Kuechenplan().hatRaumKuechendienst( 125, 11 ) );
    assertTrue( new Kuechenplan().hatRaumKuechendienst( 126, 11 ) );
  }

  @Test
  public void test_hatRaumKuechendienst_Kalenderwoche_12() {
    assertFalse( new Kuechenplan().hatRaumKuechendienst( 121, 12 ) );
    assertFalse( new Kuechenplan().hatRaumKuechendienst( 125, 12 ) );
    assertFalse( new Kuechenplan().hatRaumKuechendienst( 126, 12 ) );
  }
}
