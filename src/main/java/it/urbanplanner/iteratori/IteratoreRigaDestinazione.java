package it.urbanplanner.iteratori;

import it.urbanplanner.CentroUrbano;
import it.urbanplanner.settore.Lotto;

import java.util.Iterator;

/**
 * Iteratore che permette di iterare i lotti di una data riga specificando
 * inizio e destinazione.
 */
public class IteratoreRigaDestinazione implements Iterator<Lotto> {
    /**
     * CentroUrbano di riferimento.
     */
    private CentroUrbano centroUrbano;

    /**
     * Indice colonna del lotto corrente.
     */
    private int indiceColonnaLotto;

    /**
     * Indice colonna del settore corrente.
     */
    private int indiceColonnaSettore;

    /**
     * Indice riga del settore corrente.
     */
    private int indiceRigaSettori;

    /**
     * Indice riga del lotto corrente.
     */
    private int indiceRigaLotti;

    /**
     * Indice colonna del lotto destinazione.
     */
    private int indiceColonnaLottoDestinazione;

    /**
     * Indice colonna del settore destinazione.
     */
    private int indiceColonnaSettoreDestinazione;

    /**
     * Numero di settori per riga.
     */
    private int lottiPerRiga;

    /**
     * Costruttore IteratoreRigaDestinazione.
     *
     * @param centroUrbano
     * @param indiceColonnaSettore
     * @param indiceColonnaLotto
     * @param indiceColonnaSettoreDestinazione
     * @param indiceColonnaLottoDestinazione
     * @param indiceRigaSettori
     * @param indiceRigaLotti
     */
    public IteratoreRigaDestinazione(CentroUrbano centroUrbano,
                        int indiceColonnaSettore,
                        int indiceColonnaLotto,
                        int indiceColonnaSettoreDestinazione,
                        int indiceColonnaLottoDestinazione,
                        int indiceRigaSettori,
                        int indiceRigaLotti){

        this.centroUrbano = centroUrbano;
        this.indiceColonnaSettore = indiceColonnaSettore;
        this.indiceColonnaLotto = indiceColonnaLotto;
        this.indiceColonnaLottoDestinazione = indiceColonnaLottoDestinazione;
        this.indiceColonnaSettoreDestinazione = indiceColonnaSettoreDestinazione;
        this.indiceRigaSettori = indiceRigaSettori;
        this.indiceRigaLotti = indiceRigaLotti;
        lottiPerRiga = centroUrbano.getNumeroDiLottiPerRiga();

        this.indiceColonnaLottoDestinazione++;
        if ((this.indiceColonnaLottoDestinazione %= lottiPerRiga) == 0) {
            this.indiceColonnaSettoreDestinazione++;
        }
    }

    /**
     * {@inheritDoc}
     */
    public boolean hasNext() {
        return indiceColonnaLotto != indiceColonnaLottoDestinazione || indiceColonnaSettore != indiceColonnaSettoreDestinazione;
    }

    /**
     * {@inheritDoc}
     */
    public Lotto next() {
        Lotto risultato = centroUrbano.ottieni(indiceRigaSettori, indiceColonnaSettore).ottieni(indiceRigaLotti,indiceColonnaLotto++);

        if ((indiceColonnaLotto %= lottiPerRiga) == 0) {
            indiceColonnaSettore++;
        }

        return risultato;
    }
}
