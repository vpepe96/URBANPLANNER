package it.urbanplanner.struttura;

import java.io.Serializable;

/**
 * Astrazione del concetto di struttura contenuta nel lotto.
 */
public abstract class ElementoLotto implements Cloneable, Serializable {
    /**
     * Rappresenta l'efficienza dell'elemento, dominio: [0, 100].
     */
    protected int coefficienteDiEfficienza;

    /**
     * Rappresenta un indice di peggioramento dell'efficienza nel tempo, dominio: [1, 10].
     */
    protected int coefficienteDiInvecchiamento;

    /**
     * Nome dell'elemento.
     */
    protected String nome;

    /**
     * Costruttore ElementoLotto.
     *
     * @param coefficienteDiEfficienza
     * @param coefficienteDiInvecchiamento
     * @param nome
     */
    public ElementoLotto(int coefficienteDiEfficienza, int coefficienteDiInvecchiamento, String nome) {
        this.nome = nome;
        this.coefficienteDiEfficienza = coefficienteDiEfficienza;
        this.coefficienteDiInvecchiamento = coefficienteDiInvecchiamento;
    }

    /*
    |--------------------------------------------------------------------------
    | Metodi modificatori
    |--------------------------------------------------------------------------
    */

    /**
     * Modifica il coefficiente di efficienza per invecchiamento.
     * <p>
     * Postcondizione: coefficienteDiEfficienza >= 0
     */
    public void invecchia() {
        if (coefficienteDiEfficienza < coefficienteDiInvecchiamento) {
            coefficienteDiEfficienza = 0;
        } else {
            coefficienteDiEfficienza -= coefficienteDiInvecchiamento;
        }
    }

    /**
     * Migliora il coefficiente di efficienza con un operazione di restauro.
     * <p>
     * Pre-condizione: 0 < coefficienteDiMiglioramento < 100 - coefficienteDiEfficienza
     * Post-condizione: coefficienteDiEfficienza += coefficienteDiMiglioramento
     *
     * @param coefficienteDiMiglioramento
     */
    public void restaura(int coefficienteDiMiglioramento) {
        if (coefficienteDiMiglioramento < 0 || coefficienteDiMiglioramento > 100 - coefficienteDiEfficienza) {
            throw new IllegalArgumentException("coefficienteDiMiglioramento deve essere un valore compreso tra 0 e 100 - coefficienteDiEfficienza");
        }
        coefficienteDiEfficienza += coefficienteDiMiglioramento;
    }

    /*
    |--------------------------------------------------------------------------
    | Metodi di accesso
    |--------------------------------------------------------------------------
    */

    /**
     * @return coefficienteDiEfficienza
     */
    public int getCoefficienteDiEfficienza() {
        return coefficienteDiEfficienza;
    }

    /**
     * Aggiorna il coefficienteDiEfficienza.
     *
     * @param coefficienteDiEfficienza
     */
    public void setCoefficienteDiEfficienza(int coefficienteDiEfficienza) {
        this.coefficienteDiEfficienza = coefficienteDiEfficienza;
    }

    /**
     * @return coefficienteDiInvecchiamento
     */
    public int getCoefficienteDiInvecchiamento() {
        return coefficienteDiInvecchiamento;
    }

    /**
     * @return nome
     */
    public String getNome() {
        return nome;
    }

    /**
     * @return restituisce la durata in cicli di invecchiamento
     * dell'ElementoLotto.
     */
    public int getDurataDemolizione() {
        return 0;
    }

    /**
     * {@inheritDoc}
     */
    public ElementoLotto clone() {
        try {
            return (ElementoLotto) super.clone();
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
        ElementoLotto elementoCompara = (ElementoLotto) compara;
        boolean result = coefficienteDiEfficienza == elementoCompara.coefficienteDiEfficienza &&
                coefficienteDiInvecchiamento == elementoCompara.coefficienteDiInvecchiamento;
        if(nome == null) {
            return result && elementoCompara.nome == null;
        } else {
            return result && nome.equals(elementoCompara.nome);
        }
    }

    /**
     * {@inheritDoc}
     */
    public String toString() {
        return getClass().getName()
                + "["
                + "coefficienteDiEfficienza="
                + coefficienteDiEfficienza
                + ", coefficienteDiInvecchiamento="
                + coefficienteDiInvecchiamento
                + ", nome="
                + nome
                + "]";
    }
}
