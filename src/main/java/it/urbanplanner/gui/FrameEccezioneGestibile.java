package it.urbanplanner.gui;

import it.urbanplanner.eccezioni.EccezioneGestibile;

import java.awt.*;

/**
 * Finestra che consente di visualizzare le eccezioni gesstibili e di gestirle
 */
public class FrameEccezioneGestibile extends FrameEccezione {
    /**
     * Pannello dei bottoni del frame
     */
    private PannelloBottoni buttonPanel;

    /**
     * Costruttore di FrameEccezioneGestibile
     *
     * @param eccezioneGestibile
     */
    public FrameEccezioneGestibile(EccezioneGestibile eccezioneGestibile){

        super(eccezioneGestibile);

        setTitle("Frame gestione eccezioni");

        creaPannelloBottone();
    }

    /**
     * Crea un pannello per i bottoni
     */
    private void creaPannelloBottone(){

        buttonPanel = new PannelloBottoni();

        buttonPanel.creaBottone("Indietro", (e)->{
            System.out.println("Sei tornato indietro.");
            ((EccezioneGestibile)eccezione).tornaIndietro();
            dispose();
        });

        buttonPanel.creaBottone("Ripeti", (e)->{
            System.out.println("Hai scelto di ripetere l'operazione.");
            ((EccezioneGestibile)eccezione).ripeti();
            dispose();
        });

        eccezioniPanel.add(buttonPanel,BorderLayout.SOUTH);
    }

}
