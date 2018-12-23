package it.urbanplanner.gui;

import it.urbanplanner.CentroUrbano;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Finestra che permette di selezionare un Lotto
 * all'interno di un Settore e aprire una finestra
 * per gestirlo.
 */
public class FrameModifica extends FrameGriglia {
    /**
     * Altezza del bottone torna indietro.
     */
    private static final int ALTEZZA_BUTTON = 30;

    /**
     * Indica se c'è un settore selezionato.
     */
    private boolean selezionato;

    /**
     * Permette di tornare alla vista dei settori
     * dopo averne selezionato uno.
     */
    private JButton tornaIndietroButton;

    /**
     * Costruttore FrameModifica.
     *
     * @param centroUrbano
     */
    public FrameModifica(CentroUrbano centroUrbano) {
        super(centroUrbano, "Modifica CentroUrbano");

        setSize(
                centroUrbano.getNumeroDiLottiPerRiga() * 200 + ALTEZZA_BUTTON,
                centroUrbano.getNumeroDiLottiPerColonna() * 200
        );

        this.centroUrbano = centroUrbano;
        selezionato = false;
    }

    /**
     * Mostra tutti i settori del CentroUrbano.
     */
    private void mostraSettori() {
        setTitle("Modifica CentroUrbano");
        selezionato = false;
        getContentPane().removeAll();
        creaCentroUrbanoPane();
        revalidate();
    }

    /**
     * Mostra i lotti di un Settore del CentroUrbano.
     *
     * @param settorePane
     * @param indiceRigaSettore
     * @param indiceColonnaSettore
     */
    private void mostraLotti(JPanel settorePane, int indiceRigaSettore, int indiceColonnaSettore) {
        setTitle("Modifica Settore " +
                ((indiceRigaSettore * centroUrbano.getNumeroDiSettoriPerRiga()) + indiceColonnaSettore+1)
        );
        selezionato = true;
        getContentPane().removeAll();
        settorePane.removeAll();
        settorePane.setLayout(new GridLayout(
                centroUrbano.getNumeroDiLottiPerColonna(),
                centroUrbano.getNumeroDiLottiPerRiga()
        ));
        creaTornaIndietroButton();
        creaLottiPanel(settorePane, indiceRigaSettore, indiceColonnaSettore);
        add(settorePane);
        revalidate();
    }

    /**
     * Inizializza e costruisce un bottone per tornare alla
     * schermata di selezione dei settori.
     */
    private void creaTornaIndietroButton() {
        tornaIndietroButton = new JButton("<-- Torna Indietro");
        tornaIndietroButton.setPreferredSize(new Dimension(0, ALTEZZA_BUTTON));
        tornaIndietroButton.addActionListener((e) -> mostraSettori());
        add(tornaIndietroButton, BorderLayout.SOUTH);
    }

    /**
     * Aggiunge una Label indicante l'indice del settore.
     *
     * @param settorePanel
     * @param indiceRigaSettore
     * @param indiceColonnaSettore
     */
    private void creaSettoreLabel(JPanel settorePanel, int indiceRigaSettore, int indiceColonnaSettore) {
        JLabel settoreLabel = new JLabel("Settore " +
                ((indiceRigaSettore * centroUrbano.getNumeroDiSettoriPerRiga()) + indiceColonnaSettore+1)
        );
        settoreLabel.setFont(new Font("", Font.PLAIN, 20));
        settoreLabel.setHorizontalAlignment(JLabel.CENTER);
        settorePanel.add(settoreLabel, BorderLayout.CENTER);
    }

    /**
     * Aggiunge un instanza di MouseListener settore per permetterne
     * l'apertura.
     *
     * @param settorePanel
     * @param indiceRigaSettore
     * @param indiceColonnaSettore
     */
    private void creaSettorePanelListener(JPanel settorePanel, int indiceRigaSettore, int indiceColonnaSettore) {
        settorePanel.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                if (!selezionato) {
                    settorePanel.setBackground(Color.WHITE);
                }
            }

            public void mouseExited(MouseEvent e) {
                if (!selezionato) {
                    settorePanel.setBackground(Color.decode("#EEEEEE"));
                }
            }

            public void mousePressed(MouseEvent e) {
                if (!selezionato) {
                    System.out.println("Hai selezionato il settore "+ (indiceRigaSettore * centroUrbano.getNumeroDiSettoriPerRiga() + indiceColonnaSettore + 1) +".");
                    mostraLotti(settorePanel, indiceRigaSettore, indiceColonnaSettore);
                }
            }
        });
    }

    /**
     * {@inheritDoc}
     */
    protected JPanel getLottoPanel(int indiceRigaSettore,
                                   int indiceColonnaSettore,
                                   int indiceRigaLotto,
                                   int indiceColonnaLotto) {
        LottoPanel lottoPanel = new LottoPanel(
                centroUrbano.ottieni(
                        indiceRigaSettore,
                        indiceColonnaSettore
                ).ottieni(
                        indiceRigaLotto,
                        indiceColonnaLotto
                ),
                indiceRigaSettore,
                indiceColonnaSettore,
                indiceRigaLotto,
                indiceColonnaLotto
        );
        lottoPanel.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                lottoPanel.setBackground(Color.WHITE);
            }

            public void mouseExited(MouseEvent e) {
                lottoPanel.setBackground(Color.decode("#EEEEEE"));
            }

            public void mousePressed(MouseEvent e) {
                (new FrameModificaLotto(
                        centroUrbano,
                        lottoPanel.getLotto(),
                        indiceRigaSettore,
                        indiceColonnaSettore,
                        indiceRigaLotto,
                        indiceColonnaLotto)
                ).setVisible(true);
                System.out.println("Hai selezionato il lotto "+ (indiceRigaLotto * centroUrbano.getNumeroDiLottiPerRiga() + indiceColonnaLotto + 1) +".");

            }
        });
        return lottoPanel;
    }

    /**
     * {@inheritDoc}
     */
    protected JPanel getSettorePanel(int indiceRigaSettore,
                                     int indiceColonnaSettore) {
        JPanel settorePanel = new JPanel();
        settorePanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        settorePanel.setLayout(new BorderLayout());

        creaSettoreLabel(settorePanel, indiceRigaSettore, indiceColonnaSettore);
        creaSettorePanelListener(settorePanel, indiceRigaSettore, indiceColonnaSettore);

        return settorePanel;
    }
}
