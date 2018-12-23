package it.urbanplanner.eccezioni;

/**
 * Eccezione sollevata nel caso in cui si tenti di eseguire operazioni
 * non supportate da lotto occupati.
 */
public class LottoOccupatoException extends RuntimeException {
    /**
     * {@inheritDoc}
     */
    public LottoOccupatoException() {
        super("Il lotto è già occupato, pertanto non è possibile eseguire l'operazione richiesta");
    }
}
