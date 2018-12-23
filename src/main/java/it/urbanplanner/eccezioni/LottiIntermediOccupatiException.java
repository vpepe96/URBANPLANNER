package it.urbanplanner.eccezioni;

import it.urbanplanner.CentroUrbano;
import it.urbanplanner.gui.FrameCostruzioneStrada;

/**
 * Eccezione sollevata nel caso in cui c'è almeno un lotto occupato tra due lotti
 * sui quali si vuole eseguire un'operazione.
 */
public class LottiIntermediOccupatiException extends EccezioneGestibile {
    /**
     * Frame sorgente dell'errore
     */
    private FrameCostruzioneStrada frameSorgenteErrore;

    /**
     * Centro urbano
     */
    private CentroUrbano centroUrbano;

    /**
     * {@inheritDoc}
     */
    public LottiIntermediOccupatiException(FrameCostruzioneStrada frameSorgenteErrore, CentroUrbano centroUrbano) {
        super("Durante un tentativo di costruzione di una strada, "
                + "non è stato possibile completare la costruzione poichè almeno"
                + " uno dei lotti intermedi risulta occupato");
        this.frameSorgenteErrore = frameSorgenteErrore;
        this.centroUrbano = centroUrbano;
    }

    /**
     * Permette di tornare al frame precedente
     */
    public void tornaIndietro() {
        frameSorgenteErrore.dispose();
    }

    /**
     * Permette di ripetere l'operazione che ha generato l'eccezione
     */
    public void ripeti() {
        frameSorgenteErrore.dispose();
        FrameCostruzioneStrada frameCostruzioneStrada = new FrameCostruzioneStrada(centroUrbano);
        frameCostruzioneStrada.setVisible(true);
    }
}