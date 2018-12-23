package it.urbanplanner.iteratori;

import it.urbanplanner.CentroUrbano;
import it.urbanplanner.settore.Lotto;

import java.util.Iterator;

/**
 * Iteratore che permette di iterare i lotti di una data colonna specificando
 * inizio e destinazione.
 */
public class IteratoreColonnaDestinazione implements Iterator<Lotto> {
    /**
     * CentroUrbano di riferimento.
     */
    private CentroUrbano centroUrbano;

    /**
     * Indice riga del settore corrente.
     */
    private int indiceSettoreRiga;

    /**
     * Indice riga del lotto corrente.
     */
    private int indiceLottoRiga;

    /**
     * Indice colonna del settore corrente.
     */
    private int indiceColonnaLotti;

    /**
     * Indice colonna del lotto corrente.
     */
    private int indiceColonnaSettori;

    /**
     * Indice riga del settore destinazione.
     */
    private int indiceSettoreRigaDestinazione;

    /**
     * Indice riga del lotto destinazione.
     */
    private int indiceLottoRigaDestinazione;

    /**
     * Numero di lotti per colonna in un settore.
     */
    private int lottiPerColonna;

    /**
     * Costruttore IteratoreColonnaDestinazione.
     *
     * @param centroUrbano
     * @param indiceSettoreRiga
     * @param indiceLottoRiga
     * @param indiceSettoreRigaDestinazione
     * @param indiceLottoRigaDestinazione
     * @param indiceColonnaSettori
     * @param indiceColonnaLotti
     */
    public IteratoreColonnaDestinazione(CentroUrbano centroUrbano,
                            int indiceSettoreRiga, int indiceLottoRiga,
                            int indiceSettoreRigaDestinazione, int indiceLottoRigaDestinazione,
                            int indiceColonnaSettori, int indiceColonnaLotti) {

        this.centroUrbano = centroUrbano;

        this.indiceSettoreRiga=indiceSettoreRiga;
        this.indiceLottoRiga=indiceLottoRiga;
        this.indiceSettoreRigaDestinazione=indiceSettoreRigaDestinazione;
        this.indiceLottoRigaDestinazione=indiceLottoRigaDestinazione;
        this.indiceColonnaLotti=indiceColonnaLotti;
        this.indiceColonnaSettori= indiceColonnaSettori;
        lottiPerColonna=centroUrbano.getNumeroDiLottiPerColonna();

        this.indiceLottoRigaDestinazione++;
        if ((this.indiceLottoRigaDestinazione %= lottiPerColonna) == 0) {
            this.indiceSettoreRigaDestinazione++;
        }
    }

    /**
     * {@inheritDoc}
     */
    public boolean hasNext() {
        return indiceLottoRiga!=indiceLottoRigaDestinazione || indiceSettoreRiga!=indiceSettoreRigaDestinazione;
    }

    /**
     * {@inheritDoc}
     */
    public Lotto next() {
        Lotto result = centroUrbano.ottieni(indiceSettoreRiga,indiceColonnaSettori).ottieni(indiceLottoRiga++,indiceColonnaLotti);
        if((indiceLottoRiga%=lottiPerColonna)==0){
            indiceSettoreRiga++;
        }
        return result;
    }
}
