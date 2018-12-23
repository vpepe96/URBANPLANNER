package it.urbanplanner.iteratori;

import it.urbanplanner.settore.Lotto;
import it.urbanplanner.settore.Settore;

import java.util.Iterator;

/**
 * Iteratore che permette di iterare i lotti di un dato
 * Settore.
 */
public class IteratoreLotti implements Iterator<Lotto> {
    /**
     * Settore di riferimento.
     */
    private Settore settore;

    /**
     * Indice riga del lotto corrente.
     */
    private int indiceRiga;

    /**
     * Indice colonna del lotto corrente.
     */
    private int indiceColonna;

    /**
     * Costruttore IteratoreLotti.
     *
     * @param settore
     */
    public IteratoreLotti(Settore settore) {
        this.settore = settore;
        indiceRiga = 0;
        indiceColonna = 0;
    }

    /**
     * {@inheritDoc}
     */
    public boolean hasNext() {
        return indiceRiga < settore.getNumeroDiLottiPerColonna();

    }

    /**
     * {@inheritDoc}
     */
    public Lotto next() {
        Lotto risultato = settore.ottieni(indiceRiga, indiceColonna++);

        if((indiceColonna%=settore.getNumeroDiLottiPerRiga())==0){
            indiceRiga++;
        }

        return risultato;
    }
}
