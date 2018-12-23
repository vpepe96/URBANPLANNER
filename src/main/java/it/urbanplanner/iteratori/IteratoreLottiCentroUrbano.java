package it.urbanplanner.iteratori;

import it.urbanplanner.CentroUrbano;
import it.urbanplanner.settore.Lotto;

import java.util.Iterator;

/**
 * Iteratore che permette di iterare tutti i lotti di tutti i settori
 * del CentroUrbano.
 */
public class IteratoreLottiCentroUrbano implements Iterator<Lotto> {
    /**
     * CentroUrbano di riferimento.
     */
    private CentroUrbano centroUrbano;

    /**
     * Indice colonna del settore corrente.
     */
    private int indiceSettoreColonna;

    /**
     * Indice riga del settore corrente.
     */
    private int indiceSettoreRiga;

    /**
     * Indice colonna del lotto corrente.
     */
    private int indiceLottoColonna;

    /**
     * Indice riga del lotto corrente.
     */
    private int indiceLottoRiga;

    /**
     * Numero di settori per riga.
     */
    private int settoriPerRiga;

    /**
     * Numero di settori per colonna.
     */
    private int settoriPerColonna;

    /**
     * Numero di lotti per colonna in un settore.
     */
    private int lottiPerColonna;

    /**
     * Numero di lotti per riga in un settore.
     */
    private int lottiPerRiga;

    /**
     * Costruttore IteratoreLottiCentroUrbano.
     *
     * @param centroUrbano
     */
    public IteratoreLottiCentroUrbano(CentroUrbano centroUrbano) {
        this.centroUrbano = centroUrbano;
        indiceSettoreColonna = 0;
        indiceSettoreRiga = 0;
        indiceLottoColonna = 0;
        indiceLottoRiga = 0;
        settoriPerColonna = centroUrbano.getNumeroDiSettoriPerColonna();
        settoriPerRiga = centroUrbano.getNumeroDiSettoriPerRiga();
        lottiPerColonna = centroUrbano.getNumeroDiLottiPerColonna();
        lottiPerRiga = centroUrbano.getNumeroDiLottiPerRiga();
    }

    /**
     * {@inheritDoc}
     */
    public boolean hasNext() {
        return indiceSettoreRiga < settoriPerColonna;
    }

    /**
     * {@inheritDoc}
     */
    public Lotto next() {
        Lotto risultato = centroUrbano.ottieni(
                indiceSettoreRiga,
                indiceSettoreColonna
        ).ottieni(
                indiceLottoRiga,
                indiceLottoColonna++
        );

        if ((indiceLottoColonna %= lottiPerRiga) == 0) {
            indiceLottoRiga++;

        }

        if (indiceLottoRiga != 0 && (indiceLottoRiga %= lottiPerColonna) == 0) {
            indiceSettoreColonna++;
            if ((indiceSettoreColonna %= settoriPerRiga) == 0) {
                indiceSettoreRiga++;
            }
        }
        return risultato;
    }
}
