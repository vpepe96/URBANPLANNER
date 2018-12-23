package it.urbanplanner.struttura;

/**
 * Astrazione di un Edificio.
 */
public abstract class Edificio extends ElementoLotto {
    /**
     * {@inheritDoc}
     */
    public Edificio(int coefficienteDiEfficienza, int coefficienteDiInvecchiamento, String nome) {
        super(coefficienteDiEfficienza, coefficienteDiInvecchiamento, nome);
    }
}
