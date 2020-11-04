package ohtu.ohtuvarasto;

import org.junit.*;
import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class VarastoTest {

    Varasto varasto;
    Varasto kayttokelvoton;
    Varasto saldovarasto;
    Varasto kayttokelvotonSaldoMiinuksella;
    Varasto ylivuoto;
    double vertailuTarkkuus = 0.0001;

    @Before
    public void setUp() {
        varasto = new Varasto(10);
        kayttokelvoton = new Varasto(-1);
        saldovarasto = new Varasto(12, 6);
        kayttokelvotonSaldoMiinuksella = new Varasto(-1, -3);
        ylivuoto = new Varasto(9, 11);
    }

    @Test
    public void konstruktoriLuoTyhjanVaraston() {
        assertEquals(0, varasto.getSaldo(), vertailuTarkkuus);
    }
    
    @Test
    public void uudellaVarastollaOikeaTilavuus() {
        assertEquals(10, varasto.getTilavuus(), vertailuTarkkuus);
    }
    
    @Test
    public void konstruktoriLuoKelvottomanVarastonJosVirheellinenTilavuus() {
        assertEquals(0, kayttokelvoton.getTilavuus(), vertailuTarkkuus);
    }

    @Test
    public void konstruktoriLuoAlkusaldollisenVaraston() {
        assertEquals(6, saldovarasto.getSaldo(), vertailuTarkkuus);
    }
    
    @Test
    public void alkusaldollisellaVarastollaOikeaTilavuus() {
        assertEquals(12, saldovarasto.getTilavuus(), vertailuTarkkuus);
    }
    
    @Test
    public void konstruktoriLuoKelvottomanAlkusaldollisenVarastonJosVirheellinenTilavuus() {
        assertEquals(0, kayttokelvotonSaldoMiinuksella.getTilavuus(), vertailuTarkkuus);
    }
    
//    @Test
//    public void uudenVarastonSaldoNollaJosAlkusaldoMiinuksella() {
//        assertEquals(0, kayttokelvotonSaldoMiinuksella.getSaldo(), vertailuTarkkuus);
//    }
    
    @Test
    public void uudenVarastonYlimääräVuotanutHukkaan() {
        assertEquals(9, ylivuoto.getSaldo(), vertailuTarkkuus);
    }

    @Test
    public void lisaysLisaaSaldoa() {
        varasto.lisaaVarastoon(8);

        // saldon pitäisi olla sama kun lisätty määrä
        assertEquals(8, varasto.getSaldo(), vertailuTarkkuus);
    }
    
    @Test
    public void liianSuuriLisaysVuotaaYli() {
        varasto.lisaaVarastoon(12);
        // saldon pitäisi olla sama kuin tilavuus
        assertEquals(varasto.getTilavuus(), varasto.getSaldo(), vertailuTarkkuus);
    }
    
    @Test
    public void negatiivinenLisaysEiVahennaSaldoa() {
        varasto.lisaaVarastoon(-2);
        assertEquals(0, varasto.getSaldo(), vertailuTarkkuus);
    }
    
    @Test
    public void lisaysLisaaPienentaaVapaataTilaa() {
        varasto.lisaaVarastoon(8);

        // vapaata tilaa pitäisi vielä olla tilavuus-lisättävä määrä eli 2
        assertEquals(2, varasto.paljonkoMahtuu(), vertailuTarkkuus);
    }

    @Test
    public void ottaminenPalauttaaOikeanMaaran() {
        varasto.lisaaVarastoon(8);

        double saatuMaara = varasto.otaVarastosta(2);

        assertEquals(2, saatuMaara, vertailuTarkkuus);
    }
    
    @Test
    public void negatiivinenOttaminenEiOtaMitaan() {
        assertEquals(0, saldovarasto.otaVarastosta(-2), vertailuTarkkuus);
    }

    @Test
    public void otetaanKaikkiMitaVoidaan() {
        assertEquals(6, saldovarasto.otaVarastosta(10), vertailuTarkkuus);
    }
    
    @Test
    public void otetaanKaikkiMitaVoidaanTyhjentaaSaldon() {
        saldovarasto.otaVarastosta(10);
        assertEquals(0, saldovarasto.getSaldo(), vertailuTarkkuus);
    }
    
    @Test
    public void ottaminenLisääTilaa() {
        varasto.lisaaVarastoon(8);

        varasto.otaVarastosta(2);

        // varastossa pitäisi olla tilaa 10 - 8 + 2 eli 4
        assertEquals(4, varasto.paljonkoMahtuu(), vertailuTarkkuus);
    }
    
    @Test
    public void merkkijonoesitysOikein() {
        String merkkijono = saldovarasto.toString();
        assertEquals("saldo = 6.0, vielä tilaa 6.0", merkkijono);
    }

}