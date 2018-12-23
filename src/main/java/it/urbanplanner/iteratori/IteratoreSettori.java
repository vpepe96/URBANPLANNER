package it.urbanplanner.iteratori;

import it.urbanplanner.CentroUrbano;
import it.urbanplanner.settore.Settore;

import java.util.Iterator;

/**
 * Iteratore che permette di iterare tutti i settori di un
 * CentroUrbano.
 */
public class IteratoreSettori implements Iterator<Settore> {
    /**
     * CentroUrbano di riferimento.
     */
    private CentroUrbano centroUrbano;

    /**
     * Indice riga del settore corrente.
     */
    private int indiceRiga;

    /**
     * Indice colonna del settore corrente.
     */
    private int indiceColonna;

    /**
     * Costruttore IteratoreSettori.
     *
     * @param centroUrbano
     */
    public IteratoreSettori(CentroUrbano centroUrbano) {
        this.centroUrbano = centroUrbano;
        indiceRiga = 0;
        indiceColonna = 0;
    }

    /**
     * {@inheritDoc}
     */
    public boolean hasNext() {
        return indiceRiga < centroUrbano.getNumeroDiSettoriPerColonna();
    }

    /**
     * {@inheritDoc}
     */
    public Settore next() {
        Settore risultato = centroUrbano.ottieni(indiceRiga, indiceColonna++);

        if((indiceColonna%=centroUrbano.getNumeroDiSettoriPerRiga()) == 0) {
            indiceRiga++;
        }

        return risultato;
    }
}
