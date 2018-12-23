package it.urbanplanner.eccezioni;

/**
 * Eccezione sollevata nel caso in cui si tenti di eseguire operazioni
 * su lotti esterni al Centro Urbano.
 */
public class LottoNonTrovatoException extends RuntimeException {
    /**
     * {@inheritDoc}
     */
    public LottoNonTrovatoException(String message) {
        super(message);
    }

    /**
     * {@inheritDoc}
     */
    public LottoNonTrovatoException() {
        this("Il lotto non è presente nel Centro Urbano, pertanto non è possibile eseguire l'operazione richiesta");
    }
}