package it.urbanplanner.eccezioni;

/**
 * Eccezione sollevata nel caso in cui si tenti di eseguire operazioni
 * non supportate da lotti liberi.
 */
public class LottoLiberoException extends RuntimeException {
    /**
     * {@inheritDoc}
     */
    public LottoLiberoException() {
        super("Il lotto è libero, pertanto non è possibile eseguire l'operazione richiesta");
    }
}
