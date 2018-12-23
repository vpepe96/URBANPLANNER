package it.urbanplanner.gui;

import it.urbanplanner.CentroUrbano;
import it.urbanplanner.Disastro;

import javax.swing.*;

/**
 * Finestra che permette di scegliere un tra le operazioni:
 * Invecchia, disastro, modifica
 */
public class FrameGestione extends JFrame {
    /**
     * Larghezza del frame.
     */
    private static final int LARGHEZZA = 250;

    /**
     * Altezza del frame
     */
    private static final int ALTEZZA = 140;

    /**
     * Contatore dei cicli di invecchiamento effettuati
     */
    private int contatoreInvecchiamento = 0;

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
    public FrameGestione(CentroUrbano centroUrbano) {
        setTitle("Gestione CentroUrbano");

        this.centroUrbano = centroUrbano;

        creabottoniPane();

        setSize(LARGHEZZA, ALTEZZA);
        setResizable(false);
    }

    /**
     * Instanzia il JPanel contenente i bottoni per le funzionalità
     * previste dall'applicazione.
     */
    private void creabottoniPane() {
        bottoniPanel = new PannelloBottoni();

        creaBottoni();

        add(bottoniPanel);
    }

    /**
     * Crea i bottoni per le funzionalità previste dall'applicazione.
     */
    private void creaBottoni() {
        bottoniPanel.creaBottone("Invecchia", (e) -> {
            centroUrbano.invecchia();
            contatoreInvecchiamento++;
            System.out.println("\nInvecchiamento n°" + contatoreInvecchiamento + " effettuato.\n");
        }, 200, 30);

        bottoniPanel.creaBottone("Disastro", (e) -> {
            Disastro disastro = centroUrbano.disastro();
            System.out.println("Tipo Disastro: " + disastro.getNome() + ".");
        }, 200, 30);

        bottoniPanel.creaBottone("Modifica", (e) -> {
            //puoi costruire selezione demolire
            FrameGestioneModifica frameGestioneModifica = new FrameGestioneModifica(centroUrbano);
            frameGestioneModifica.setVisible(true);
        }, 200, 30);
    }
}
