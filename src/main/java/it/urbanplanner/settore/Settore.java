package it.urbanplanner.settore;

import it.urbanplanner.Disastro;
import it.urbanplanner.iteratori.IteratoreLotti;
import it.urbanplanner.struttura.EdificioPubblico;
import it.urbanplanner.struttura.ElementoLotto;

import java.io.Serializable;
import java.util.Arrays;

/**
 * Astrazione di una zona edificabile di un centro urbano.
 */
public class Settore implements Serializable, Cloneable {
    /**
     * Lista dei lotti presenti nel settore
     */
    private Lotto[][] lotti;

    /**
     * Numero di righe di lotti.
     */
    private int righeLotti;

    /**
     * Numero di colonne di lotti.
     */
    private int colonneLotti;

    /**
     * Costruttore Settore.
     *
     * @param righeLotti
     * @param colonneLotti
     */
    public Settore(int righeLotti, int colonneLotti) {
        lotti = new Lotto[righeLotti][colonneLotti];
        this.righeLotti = righeLotti;
        this.colonneLotti = colonneLotti;
    }

     /*
    |--------------------------------------------------------------------------
    | Metodi modificatori
    |--------------------------------------------------------------------------
    */

    /**
     * Inzializza un settore
     */
    public void crea() {
        for (int i = 0; i < righeLotti; i++) {
            for (int j = 0; j < colonneLotti; j++) {
                lotti[i][j] = new Lotto();
            }
        }
    }

    /**
     * Invecchia Settore.
     */
    public void invecchia() {
        IteratoreLotti iteratoreLotti = iteratoreLotti();
        while (iteratoreLotti.hasNext()) {
            iteratoreLotti.next().invecchia();
        }
    }

    /**
     * Applica un disastro ad un lotto ed ai lotto ad esso
     * confinanti.
     *
     * @param lotto    dove applicare il disastro
     * @param disastro da applicare
     */
    public void disastro(Lotto lotto, Disastro disastro) {
        lotto.disastro(disastro.getPercentualeDisastro());
        for (Lotto lottoConfintante : lotto.getLottiConfinanti()) {
            if (lottoConfintante != null) {
                lottoConfintante.disastro(disastro.getPercentualeDisastro() / 2);
            }
        }
    }

    /*
    |--------------------------------------------------------------------------
    | Metodi di accesso
    |--------------------------------------------------------------------------
    */

    /**
     * Pre-condizioni: riga < lottiPerRiga && colonna < lottiPerColonna.
     *
     * @param riga    indice riga
     * @param colonna indice colonna
     * @return il lotto di riga e colonna selezionati
     */
    public Lotto ottieni(int riga, int colonna) {
        return lotti[riga][colonna];
    }

    /**
     * @param lotto
     * @return true se il lotto è contenuto nel settore, false altrimenti
     */
    public boolean contiene(Lotto lotto) {
        IteratoreLotti iteratoreLotti = new IteratoreLotti(this);

        while (iteratoreLotti.hasNext()) {
            if (lotto == iteratoreLotti.next())
                return true;
        }

        return false;
    }

    /**
     * Precondizione: lotto.elementoLotto istanceof EdificioPrivato AND lotto contenuto in lotti.
     *
     * @return valore dell'edificio privato, con beneficio delle strade adiacenti
     * selezione beneficio degli edifici pubblici.
     */
    public double getValore(Lotto lotto) {
        if (!contiene(lotto)) {
            throw new IllegalArgumentException("il lotto non è presente nel settore");
        }
        double valore = lotto.getValore();
        return valore + (valore * getCoefficientiEdificiPubblici() / 100);
    }

    /**
     * @return il valore dell'edificio privato, con beneficio delle strade adiacenti
     */
    public int getCoefficientiEdificiPubblici() {
        int risultato = 0;
        IteratoreLotti iteratoreLotti = new IteratoreLotti(this);

        while (iteratoreLotti.hasNext()) {
            ElementoLotto elementoLotto = iteratoreLotti.next().getElementoLotto();
            if (elementoLotto instanceof EdificioPubblico)
                risultato += elementoLotto.getCoefficienteDiEfficienza();
        }

        return risultato;
    }

    /**
     * @return istanza di un IteratoreLotti
     */
    public IteratoreLotti iteratoreLotti() {
        return new IteratoreLotti(this);
    }

    /**
     * {@inheritDoc}
     */
    public Settore clone() {
        try {
            Settore settore = (Settore) super.clone();
            settore.lotti = Arrays.copyOf(lotti, lotti.length);
            return settore;
        } catch (CloneNotSupportedException e) {
            // Impossibile
            return null;
        }
    }

    /**
     * Restituisce il numero di colonne per colonna di un settore
     *
     * @return righeLotti
     */
    public int getNumeroDiLottiPerColonna() {
        return righeLotti;
    }


    /**
     * Restiuisce il numero di lotti per riga di un settore
     *
     * @return colonneLotti
     */
    public int getNumeroDiLottiPerRiga() {
        return colonneLotti;
    }

    /**
     * {@inheritDoc}
     */
    public boolean equals(Object compara) {
        if (compara == null || getClass() != compara.getClass()) return false;
        Settore settoreCompara = (Settore) compara;
        return lotti.equals(settoreCompara.lotti);
    }

    /**
     * {@inheritDoc}
     */
    public String toString() {
        return getClass().getName()
                + "["
                + "lotti="
                + lotti
                + "]";
    }
}
