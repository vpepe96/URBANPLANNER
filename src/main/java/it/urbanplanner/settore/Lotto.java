package it.urbanplanner.settore;

import it.urbanplanner.eccezioni.LottoLiberoException;
import it.urbanplanner.eccezioni.LottoOccupatoException;
import it.urbanplanner.struttura.EdificioPrivato;
import it.urbanplanner.struttura.ElementoLotto;
import it.urbanplanner.struttura.Strada;

import java.io.Serializable;
import java.util.Arrays;

/**
 * Astrazione di una zona edificabile del Settore di un CentroUrbano.
 */
public class Lotto implements Serializable, Cloneable {
    /**
     * Vettore di 4 elementi contente i lotti confinanti.
     * <p>
     * 0 indice lotto superiore
     * 1 indice lotto destro
     * 2 indice lotto inferiore
     * 3 indice lotto sinistro
     */
    private Lotto[] lottiConfinanti;

    /**
     * Riferimento all'elemento contenuto nel lotto.
     */
    private ElementoLotto elementoLotto;

    /**
     * Costruttore Lotto.
     */
    public Lotto() {
        lottiConfinanti = new Lotto[4];
    }

    /*
    |--------------------------------------------------------------------------
    | Metodi modificatori
    |--------------------------------------------------------------------------
    */

    /**
     * Invecchia Lotto.
     */
    public void invecchia() {
        if (occupato()) {
            elementoLotto.invecchia();
        }
    }

    /**
     * Applica una percentuale di disastro al lotto.
     *
     * @param percentualeDiDisastro
     */
    public void disastro(double percentualeDiDisastro) {
        if (occupato()) {
            int nuovoCoefficienteDiEfficienza;
            ElementoLotto elementoLottoDisastro = elementoLotto;

            nuovoCoefficienteDiEfficienza = (int) (elementoLottoDisastro.getCoefficienteDiEfficienza() *
                    (1 - percentualeDiDisastro));

            elementoLottoDisastro.setCoefficienteDiEfficienza(nuovoCoefficienteDiEfficienza);
        }
    }

    /**
     * Costruisce un nuovo elemento all'interno del lotto.
     * Pre-condizione: occuppato() == false
     *
     * @param elementoLotto
     */
    public void costruisci(ElementoLotto elementoLotto) {
        if (occupato()) {
            throw new LottoOccupatoException();
        }
        this.elementoLotto = elementoLotto;
    }

    /**
     * Demolisce l'elementoLotto presente nel lotto.
     * Pre-condizione: occupato()=true
     */
    public void liberaLotto() {
        if (!occupato()) {
            throw new LottoLiberoException();
        }
        this.elementoLotto = null;
    }

    /**
     * Modifica il riferimento al lotto superiore
     *
     * @param sopra
     */
    public void setSopra(Lotto sopra) {
        lottiConfinanti[0] = sopra;
    }

    /**
     * Modifica il riferimento al lotto inferiore
     *
     * @param sotto
     */
    public void setSotto(Lotto sotto) {
        lottiConfinanti[2] = sotto;
    }

    /**
     * Modifica il riferimento al lotto destro
     *
     * @param destra
     */
    public void setDestra(Lotto destra) {
        lottiConfinanti[1] = destra;
    }

    /**
     * Modifica il riferimento al lotto sinistro
     *
     * @param sinistra
     */
    public void setSinistra(Lotto sinistra) {
        lottiConfinanti[3] = sinistra;
    }

    /*
    |--------------------------------------------------------------------------
    | Metodi di accesso
    |--------------------------------------------------------------------------
    */

    /**
     * @return lottiConfinanti
     */
    public Lotto[] getLottiConfinanti() {
        return lottiConfinanti;
    }

    /**
     * @return true se è presente un elemento nel lotto, false altrimenti.
     */
    public boolean occupato() {
        return elementoLotto != null;
    }

    /**
     * @return elementoLotto
     */
    public ElementoLotto getElementoLotto() {
        return elementoLotto;
    }

    /**
     * Precondizione: elementoLotto istanceof EdificioPrivato.
     *
     * @return valore dell'edificio privato con beneficio delle strade adiacenti
     */
    public double getValore() {
        if (!(elementoLotto instanceof EdificioPrivato)) {
            throw new IllegalArgumentException("elementoLotto del lotto non è un'istanza di EdificioPrivato");
        }
        double valore = ((EdificioPrivato) elementoLotto).getValore();
        return valore + (valore * getCoefficientiAdiacenti() / 200);
    }

    /**
     * Restituisce la somma dei coefficienti di efficienza delle strade adiacenti.
     *
     * @return coefficienteAdiacenti
     */
    private int getCoefficientiAdiacenti() {
        int coefficienteAdiacenti = 0;
        for (int i = 0; i < 4; i++) {
            if (lottiConfinanti[i] != null && lottiConfinanti[i].getElementoLotto() instanceof Strada) {
                coefficienteAdiacenti += lottiConfinanti[i].getElementoLotto().getCoefficienteDiEfficienza();
            }
        }
        return coefficienteAdiacenti;
    }

    /**
     * {@inheritDoc}
     */
    public Lotto clone() {
        try {
            Lotto lotto = (Lotto) super.clone();
            lotto.lottiConfinanti = Arrays.copyOf(lottiConfinanti, lottiConfinanti.length);
            return lotto;
        } catch (CloneNotSupportedException e) {
            // Impossibile
            return null;
        }
    }

    /**
     * {@inheritDoc}
     */
    public boolean equals(Object compara) {
        if (compara == null || getClass() != compara.getClass()) return false;
        Lotto lottoCompara = (Lotto) compara;
        boolean result;
        if (elementoLotto != null && lottoCompara.elementoLotto != null) {
            result = elementoLotto.equals(lottoCompara.elementoLotto);
        } else {
            result = elementoLotto == null && lottoCompara.elementoLotto == null;
        }
        return result
                && lottoCompara.lottiConfinanti[0] == lottiConfinanti[0]
                && lottoCompara.lottiConfinanti[1] == lottiConfinanti[1]
                && lottoCompara.lottiConfinanti[2] == lottiConfinanti[2]
                && lottoCompara.lottiConfinanti[3] == lottiConfinanti[3];
    }

    /**
     * {@inheritDoc}
     */
    public String toString() {
        return getClass().getName() + "[elementoLotto=" + (elementoLotto != null ? elementoLotto.toString() : "null") + "]";
    }
}
