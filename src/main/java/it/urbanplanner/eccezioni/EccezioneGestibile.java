package it.urbanplanner.eccezioni;

/**
 * Astrazione di un'eccezione gestibile
 */
public abstract class EccezioneGestibile extends Exception {
    /**
     * Costruttore di EccezioneGestibile
     *
     * @param message
     */
    public EccezioneGestibile(String message) {
        super(message);
    }

    /**
     * Metodo per tornare al frame precedente
     */
    public abstract void tornaIndietro();

    /**
     * Metodo per ripetere l'operazione che ha generato l'eccezione
     */
    public abstract void ripeti();
}