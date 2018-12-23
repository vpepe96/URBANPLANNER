package it.urbanplanner.gui;

import it.urbanplanner.CentroUrbano;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Frame contenente la vista di un CentroUrbano
 * con funzioni di ingrandimento/riduzione.
 */
public class FrameVisualizza extends FrameGriglia {
    /**
     * Massimo fattore di zoom raggiungibile.
     */
    private static final double MASSIMO_ZOOM = 1.8;

    /**
     * Minimo fattore di zoom raggiungibile.
     */
    private static final double MINIMO_ZOOM = 1;

    /**
     * Aumento/Decremento del fattore di zoom.
     */
    private static final double FATTORE_SCALE = 0.2;

    /**
     * Rappresenta il fattore di Zoom applicato.
     */
    private double fattoreZoom = MINIMO_ZOOM;

    /**
     * Rappresenta la barra dei menu.
     *
     */
    private JMenuBar menuBar;

    /**
     * Rappresenta il menu contenente i componenti per lo zoom.
     *
     */
    private JMenu menu;

    /**
     * Rappresenta il componente che permette l'ingrandimento.
     *
     */
    private JMenuItem ingrandisciItem;

    /**
     * Rappresenta il componente che permette il rimpicciolimento.
     *
     */
    private JMenuItem rimpicciolisciItem;

    /**
     * Instanza di ScrollPane contentente il CentroUrbano
     * che permetterà di muoversi dopo aver effettuato un
     * operazione di Zoom.
     */
    private JScrollPane scrollPane = new JScrollPane(centroUrbanoPane);

    /**
     * Costruttore FrameVisualizza.
     *
     * @param centroUrbano
     */
    public FrameVisualizza(CentroUrbano centroUrbano) {
        super(centroUrbano, "Visualizza Centro Urbano");

        remove(centroUrbanoPane);

        creaMenuBar();
        creaScrollPanel();
    }

    /**
     * Crea la barra del menu.
     */
    private void creaMenuBar() {
        menuBar = new JMenuBar();

        creaMenu();

        setJMenuBar(menuBar);
    }

    /**
     * Crea il menu contenente i componeneti per lo zoom.
     */
    private void creaMenu() {
        menu = new JMenu("Zoom");

        creaIngrandisciItem();
        creaRimpicciolisciItem();

        menuBar.add(menu);
    }

    /**
     * Crea il componenete del menu che permette l'ingrandimento.
     */
    private void creaIngrandisciItem() {
        ingrandisciItem = new JMenuItem("Ingrandisci");

        ingrandisciItem.addActionListener((e) -> {
            if (fattoreZoom < MASSIMO_ZOOM) {
                fattoreZoom += FATTORE_SCALE;
            }
            zoom();
        });

        menu.add(ingrandisciItem);
    }

    /**
     * Crea il componenete del menu che permette il rimpicciolimento.
     */
    private void creaRimpicciolisciItem() {
        rimpicciolisciItem = new JMenuItem("Rimpicciolisci");

        rimpicciolisciItem.addActionListener((e) -> {
            if (fattoreZoom > MINIMO_ZOOM) {
                fattoreZoom -= FATTORE_SCALE;
            }
            zoom();
        });

        menu.add(rimpicciolisciItem);
    }

    /**
     * Applica il fattore di zoom.
     */
    private void zoom() {
        /*
         * Prendo la larghezza corrente occupata dalla scrollbar e gli sottraggo quella
         * occupata dalla barra di scorrimento.
         */
        int larghezzaPartenza = (int) (scrollPane.getWidth() - scrollPane.getVerticalScrollBar().getPreferredSize().getWidth());

        /*
         * Prendo l'altezza corrente occupata dalla scrollbar e gli sottraggo quella
         * occupata dalla barra di scorrimento.
         */
        int altezzaPartenza = (int) (scrollPane.getHeight() - scrollPane.getHorizontalScrollBar().getPreferredSize().getHeight());

        centroUrbanoPane.setPreferredSize(new Dimension(
                (int) (larghezzaPartenza * fattoreZoom),
                (int) (altezzaPartenza * fattoreZoom)
        ));

        centroUrbanoPane.revalidate();
    }

    /**
     * Inzializza e costruisce lo ScrollPane contenente il
     * CentroUrbano.
     */
    private void creaScrollPanel() {
        scrollPane = new JScrollPane(centroUrbanoPane);

        add(scrollPane);
    }
}
