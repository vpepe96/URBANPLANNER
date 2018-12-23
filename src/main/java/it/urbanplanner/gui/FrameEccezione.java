package it.urbanplanner.gui;

import javax.swing.*;
import java.awt.*;

/**
 * Finestra grafica che consente di visualizzare le eccezioni
 */
public class FrameEccezione extends JFrame {
    /**
     * Larghezza del frame
     */
    private static final int LARGHEZZA = 500;

    /**
     * Altezza del frame
     */
    private static final int ALTEZZA = 300;

    /**
     * Pannello contenente are testuale
     */
    protected JPanel eccezioniPanel;

    /**
     * Are testuale contente i messaggi dell'eccezzione
     */
    private JTextArea eccezioniArea;

    /**
     * Scroll per l'area testuale
     */
    private JScrollPane areaScroll;

    /**
     * Eccezzione da visualizzare
     */
    protected Exception eccezione;

    /**
     * Costruttore di FrameEccezzioni
     *
     * @param eccezione
     */
    public FrameEccezione(Exception eccezione){

        setTitle("Frame visualizza eccezioni");

        this.eccezione = eccezione;

        setSize(LARGHEZZA, ALTEZZA);
        setResizable(false);

        eccezioniPanel = new JPanel(new BorderLayout());
        creaAreaTesto();
    }

    /**
     * Crea un area testuale e la aggiunge al frame
     */
    private void creaAreaTesto(){
        eccezioniArea = new JTextArea();
        eccezioniArea.setLineWrap(true);
        eccezioniArea.setWrapStyleWord(true);
        areaScroll = new JScrollPane(eccezioniArea);

        eccezioniArea.append(eccezione.toString());

        eccezioniPanel.add(areaScroll,BorderLayout.CENTER);
        add(eccezioniPanel);
    }

    /**
     * Scrive sull'area testuale del frame un messaggio di errore
     *
     * @param message
     */
    public void write(String message){
        eccezioniArea.append(message);
    }

}
