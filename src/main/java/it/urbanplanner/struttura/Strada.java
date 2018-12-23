package it.urbanplanner.struttura;

/**
 * Astrazione di una Strada.
 */
public class Strada extends ElementoLotto {
    /**
     * Costruttore Strada.
     *
     * @param coefficienteDiEfficienza
     * @param coefficienteDiInvecchiamento
     * @param nome
     */
    public Strada(int coefficienteDiEfficienza, int coefficienteDiInvecchiamento, String nome) {
        super(coefficienteDiEfficienza, coefficienteDiInvecchiamento, nome);
    }

    /**
     * {@inheritDoc}
     */
    public Strada clone() {
        return (Strada) super.clone();
    }

}
