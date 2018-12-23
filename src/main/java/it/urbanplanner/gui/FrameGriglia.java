package it.urbanplanner.gui;

import it.urbanplanner.CentroUrbano;

import javax.swing.*;
import java.awt.*;

/**
 * Finestra contenente una vista del centro urbano in
 * griglia.
 */
public abstract class FrameGriglia extends JFrame {
    /**
     * Pannello contente il centro urbano.
     */
    protected JPanel centroUrbanoPane;

    /**
     * CentroUrbano da visualizzare.
     */
    protected CentroUrbano centroUrbano;

    /**
     * Costruttore FrameGriglia.
     *
     * @param centroUrbano
     * @param title
     */
    public FrameGriglia(CentroUrbano centroUrbano, String title) {
        super(title);

        this.centroUrbano = centroUrbano;

        setSize(new Dimension(
                centroUrbano.getNumeroDiLottiPerRiga() * 101 * centroUrbano.getNumeroDiSettoriPerRiga(),
                centroUrbano.getNumeroDiLottiPerColonna() * 101 * centroUrbano.getNumeroDiSettoriPerColonna()
        ));

        creaCentroUrbanoPane();
    }

    /**
     * Crea e inizializza i pannelli necessari a visualizzare il CentroUrbano.
     */
    protected void creaCentroUrbanoPane() {
        centroUrbanoPane = new JPanel();
        centroUrbanoPane.setLayout(new GridLayout(
                centroUrbano.getNumeroDiSettoriPerColonna(),
                centroUrbano.getNumeroDiSettoriPerRiga()
        ));

        creaSettoriPanel(
                centroUrbano.getNumeroDiLottiPerRiga(),
                centroUrbano.getNumeroDiLottiPerColonna()
        );

        add(centroUrbanoPane);
    }

    /**
     * Aggiungi i pannelli per i settori del CentroUrbano.
     *
     * @param colonneLotti
     * @param righeLotti
     */
    protected void creaSettoriPanel(int colonneLotti, int righeLotti) {
        for (int indiceRigaSettore = 0; indiceRigaSettore < centroUrbano.getNumeroDiSettoriPerColonna(); indiceRigaSettore++) {
            for (int indiceColonnaSettore = 0; indiceColonnaSettore < centroUrbano.getNumeroDiSettoriPerRiga(); indiceColonnaSettore++) {
                centroUrbanoPane.add(getSettorePanel(
                        indiceRigaSettore,
                        indiceColonnaSettore
                ));
            }
        }

    }

    /**
     * Aggiungo i pannelli per i lotti di un dato Settore del CentroUrbano.
     *
     * @param settorePane
     * @param indiceRigaSettore
     * @param indiceColonnaSettore
     */
    protected void creaLottiPanel(JPanel settorePane,
                                  int indiceRigaSettore,
                                  int indiceColonnaSettore) {
        for (int indiceRigaLotto = 0; indiceRigaLotto < centroUrbano.getNumeroDiLottiPerColonna(); indiceRigaLotto++) {
            for (int indiceColonnaLotto = 0; indiceColonnaLotto < centroUrbano.getNumeroDiLottiPerRiga(); indiceColonnaLotto++) {
                settorePane.add(getLottoPanel(
                        indiceRigaSettore,
                        indiceColonnaSettore,
                        indiceRigaLotto,
                        indiceColonnaLotto
                ));
            }
        }
    }

    /**
     * Instanzia e restituisce il pannello relativo ad uno specifico Settore.
     *
     * @param indiceRigaSettore
     * @param indiceColonnaSettore
     * @return una nuova instanza di un JPanel per il Settore indicato
     */
    protected JPanel getSettorePanel(int indiceRigaSettore,
                                     int indiceColonnaSettore) {
        JPanel settorePane = new JPanel();
        settorePane.setLayout(new GridLayout(
                centroUrbano.getNumeroDiLottiPerColonna(),
                centroUrbano.getNumeroDiLottiPerRiga()
        ));
        settorePane.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));

        creaLottiPanel(settorePane, indiceRigaSettore, indiceColonnaSettore);

        return settorePane;
    }

    /**
     * Instanzia e restituisce il pannello relativo ad uno specifico lotto.
     *
     * @param indiceRigaSettore
     * @param indiceColonnaSettore
     * @param indiceRigaLotto
     * @param indiceColonnaLotto
     * @return una nuova instanza di LottoPanel specifica per il lotto indicato
     */
    protected JPanel getLottoPanel(int indiceRigaSettore,
                                   int indiceColonnaSettore,
                                   int indiceRigaLotto,
                                   int indiceColonnaLotto) {
        return new LottoPanel(
                centroUrbano.ottieni(indiceRigaSettore, indiceColonnaSettore)
                        .ottieni(indiceRigaLotto, indiceColonnaLotto),
                indiceRigaSettore,
                indiceColonnaSettore,
                indiceRigaLotto,
                indiceColonnaLotto
        );

    }
}
