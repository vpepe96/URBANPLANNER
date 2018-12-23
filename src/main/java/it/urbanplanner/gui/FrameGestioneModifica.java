package it.urbanplanner.gui;

import it.urbanplanner.CentroUrbano;

import javax.swing.*;

/**
 * Frame che permettere di scegliere una tra le operazioni:
 * Costruzione strada, Modifica lotto
 */
public class FrameGestioneModifica extends JFrame {
    /**
     * Larghezza del frame
     */
    private static final int LARGHEZZA = 250;

    /**
     * Altezza del frame
     */
    private static final int ALTEZZA = 105;

    /**
     * Pannello contenente i bottoni per cambiare modalità.
     */
    private PannelloBottoni bottoniPanel;

    /**
     * Instanza di CentroUrbano da utilizzare durante l'esecuzione del
     * programma.
     */
    private CentroUrbano centroUrbano;

    /**
     * Costruttore
     *
     * @param centroUrbano
     */
    public FrameGestioneModifica(CentroUrbano centroUrbano) {
        super("Gestione modifica CentroUrbano");

        this.centroUrbano = centroUrbano;

        creaBottoniPane();

        setSize(LARGHEZZA, ALTEZZA);
        setResizable(false);
    }

    /**
     * Instanzia il JPanel contenente i bottoni per le funzionalità
     * previste dall'applicazione.
     */
    private void creaBottoniPane() {
        bottoniPanel = new PannelloBottoni();

        creaBottoni();

        add(bottoniPanel);
    }

    /**
     * Crea i bottoni per le funzionalità previste dall'applicazione.
     */
    private void creaBottoni() {
        bottoniPanel.creaBottone("Costruisci strada", (e)-> {
                FrameCostruzioneStrada frameCostruzioneStrada = new FrameCostruzioneStrada(centroUrbano);
            frameCostruzioneStrada.setVisible(true);
        }, 200, 30);

        bottoniPanel.creaBottone("Modifica lotto", (e)->{
            FrameModifica frameModifica =new FrameModifica(centroUrbano);
            frameModifica.setVisible(true);
        }, 200, 30);
    }
}