package it.urbanplanner.gui;

import it.urbanplanner.CentroUrbano;
import it.urbanplanner.iteratori.IteratoreLottiCentroUrbano;
import it.urbanplanner.selezione.Filtratore;
import it.urbanplanner.selezione.filtri.FiltroPerTipo;
import it.urbanplanner.settore.Lotto;
import it.urbanplanner.struttura.EdificioPrivato;
import it.urbanplanner.struttura.EdificioPubblico;
import it.urbanplanner.struttura.Strada;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

/**
 * Finestra visualizzato all'avvio dell'applicazione, che permette di
 * utilizzare le varie modalità del sistema.
 */
public class FramePrincipale extends JFrame {
    /**
     * Larghezza del frame.
     */
    private static final int LARGHEZZA = 500;

    /**
     * Altezza del frame.
     */
    private static final int ALTEZZA = 300;

    /**
     * Instanza di CentroUrbano da utilizzare durante l'esecuzione del
     * programma.
     */
    private CentroUrbano centroUrbano;

    /**
     * File contenente l'instanza serializzata di centroUrbano.
     */
    private File file;

    /**
     * Instanza di JTextArea contenente le informazioni del CentroUrbano.
     */
    private JTextArea descrizioneArea;

    /**
     * Pannello contenente i bottoni per cambiare modalità.
     */
    private PannelloBottoni bottoniPane;

    /**
     * Costruttore Principale.
     *
     * @param file
     * @param centroUrbano
     */
    public FramePrincipale(File file, CentroUrbano centroUrbano) {
        super("Frame Principale");

        this.file = file;

        this.centroUrbano = centroUrbano;

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(LARGHEZZA, ALTEZZA);

        creabottoniPane();
        creaDescrizioneArea();
    }

    /**
     * Instanzia la JTextArea e aggiunge le informazioni del CentroUrbano.
     */
    private void creaDescrizioneArea() {
        descrizioneArea = new JTextArea();
        descrizioneArea.setEditable(false);

        stampaDescrizione();

        add(descrizioneArea);
    }

    /**
     * Aggiunge le informazioni del CentroUrbano alla descrizioneArea.
     */
    private void stampaDescrizione() {
        descrizioneArea.setText("Informazioni centro urbano ");
        descrizioneArea.append("\n\nNumero settori=" + centroUrbano.getNumeroDiSettori());
        descrizioneArea.append("\nNumero di lotti=" + centroUrbano.getNumeroDiLotti());
        descrizioneArea.append("\nNumero di lotti liberi=" + getNumeroDiLottiLiberi());
        descrizioneArea.append("\nNumero di lotti occupati=" + getNumeroDiLottiOccupati());
        descrizioneArea.append("\nNumero di strade=" + getNumeroDi(Strada.class));
        descrizioneArea.append("\nNumero di edifici privati=" + getNumeroDi(EdificioPrivato.class));
        descrizioneArea.append("\nNumero di edifici pubblici=" + getNumeroDi(EdificioPubblico.class));
    }

    /**
     * @return numero di lotti liberi all'interno del CentroUrbano.
     */
    private int getNumeroDiLottiLiberi() {
        ArrayList<Lotto> dati = getLottiCentroUrbano();
        Filtratore<Lotto> filtratore = new Filtratore<>(dati, (e) -> !e.occupato());
        filtratore.filtra();
        return dati.size();
    }

    /**
     * @return numero di lotti occupati all'interno del CentroUrbano.
     */
    private int getNumeroDiLottiOccupati() {
        ArrayList<Lotto> dati = getLottiCentroUrbano();
        Filtratore<Lotto> filtratore = new Filtratore<>(dati, (e) -> e.occupato());
        filtratore.filtra();
        return dati.size();
    }

    /**
     * @param classe
     * @return numero di ElementoLotto instanza di classe
     */
    private int getNumeroDi(Class classe) {
        ArrayList<Lotto> dati = getLottiCentroUrbano();
        Filtratore<Lotto> filtratore = new Filtratore<>(dati, new FiltroPerTipo(classe));
        filtratore.filtra();
        return dati.size();
    }

    /**
     * @return un instanza di ArrayList contenente tutti i lotti del CentroUrbano.
     */
    private ArrayList<Lotto> getLottiCentroUrbano() {
        ArrayList<Lotto> dati = new ArrayList<>();
        IteratoreLottiCentroUrbano iteratoreLottiCentroUrbano = centroUrbano.iteratoreLotti();
        while(iteratoreLottiCentroUrbano.hasNext()) {
            dati.add(iteratoreLottiCentroUrbano.next());
        }
        return dati;
    }

    /**
     * Instanzia il JPanel contenente i bottoni per le funzionalità
     * previste dall'applicazione.
     */
    private void creabottoniPane() {
        bottoniPane = new PannelloBottoni();

        creaBottoni();

        add(bottoniPane, BorderLayout.NORTH);
    }

    /**
     * Crea i bottoni per le funzionalità previste dall'applicazione.
     */
    private void creaBottoni() {
        bottoniPane.creaBottone("Salvataggio", (e)->{
            try {
                ObjectOutputStream stream = new ObjectOutputStream(new FileOutputStream(file));
                stream.writeObject(centroUrbano);
                stream.close();
                stampaDescrizione();
                System.out.println("Salvataggio effettuato.");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });

        bottoniPane.creaBottone("Visualizzazione", (e)->{
            FrameVisualizza frameVisualizza = new FrameVisualizza(centroUrbano);
            frameVisualizza.setVisible(true);
            System.out.println("Hai selezionato di visualizzare il centro urbano.");
        });

        bottoniPane.creaBottone("Gestione", (e) -> {
            FrameGestione frameGestione = new FrameGestione(centroUrbano);
            frameGestione.setVisible(true);
        });

        bottoniPane.creaBottone("Selezione", (e)->{
            FrameSelezione frameSelezione = new FrameSelezione(centroUrbano);
            frameSelezione.setVisible(true);
        });
    }
}
