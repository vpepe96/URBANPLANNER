package it.urbanplanner.gui;

import it.urbanplanner.CentroUrbano;
import it.urbanplanner.eccezioni.LottiIntermediOccupatiException;
import it.urbanplanner.iteratori.IteratoreColonnaDestinazione;
import it.urbanplanner.iteratori.IteratoreRigaDestinazione;
import it.urbanplanner.selezione.Ordinatore;
import it.urbanplanner.settore.Lotto;
import it.urbanplanner.struttura.Strada;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

/**
 * Finestra che permette la costruzione di una strada all'interno
 * del CentroUrbano.
 */
public class FrameCostruzioneStrada extends FrameGriglia {
    /**
     * Lista dei pannelli dei lotti selezionati.
     */
    private ArrayList<LottoPanel> lottoPanelSelezionati;

    /**
     * Pannello contenente i dati e bottoni per la creazione
     * di una strada.
     */
    private JPanel inputPanel;

    /**
     * Pannello contenente i dati da inserire in input per
     * la creazione di una strada.
     */
    private JPanel datiPanel;

    /**
     * Campo contenente il nome della strada.
     */
    private JTextField nomeField;

    private JSlider coefficienteDiInvecchiamentoSlider;

    private JSlider coefficienteDiEfficienzaSlider;

    /**
     * Costruttore FrameCostruzioneStrada.
     *
     * @param centroUrbano
     */
    public FrameCostruzioneStrada(CentroUrbano centroUrbano) {
        super(centroUrbano, "Costruzione Strada");

        lottoPanelSelezionati = new ArrayList<LottoPanel>();

        setSize(getWidth(), getHeight() + 120);

        creaInputPanel();
    }

    /**
     * Crea il pannello contenente i componenti necessari
     * per l'input.
     */
    private void creaInputPanel() {
        inputPanel = new JPanel();
        inputPanel.setLayout(new BorderLayout());

        creaDatiPanel();
        creaCostruisciButton();

        add(inputPanel, BorderLayout.SOUTH);
    }

    /**
     * Crea i dati da aggiungere al pannello
     *
     */
    private void creaDatiPanel() {
        datiPanel = new JPanel();
        datiPanel.setLayout(new GridLayout(6, 2));

        creaNomeField();
        creaCoefficienteDiInvecchiamentoSlider();
        creaCoefficienteDiEfficienzaSlider();

        inputPanel.add(datiPanel, BorderLayout.CENTER);
    }

    /**
     * Crea uno slider per il coefficiente di invecchiamento
     *
     */
    private void creaCoefficienteDiInvecchiamentoSlider() {
        coefficienteDiInvecchiamentoSlider = new JSlider(1, 10, 1);

        aggiungiSliderConLabel("Coefficiente di invecchiamento: ", coefficienteDiInvecchiamentoSlider);
    }

    /**
     * Crea uno slider per il coefficiente di efficienza
     *
     */
    private void creaCoefficienteDiEfficienzaSlider() {
        coefficienteDiEfficienzaSlider = new JSlider(1, 100, 100);

        aggiungiSliderConLabel("Coefficiente di efficienza: ", coefficienteDiEfficienzaSlider);
    }

    /**
     * Crea il campo che conterrà il nome della strada.
     */
    private void creaNomeField() {
        nomeField = new JTextField();

        aggiungiConEtichetta("Nome Strada: ", nomeField);
    }

    /**
     * Permette di aggiungere uno Slider con Etichetta indicante il valore corrente
     * al costruisciPanel
     *
     * @param etichetta nome etichetta
     * @param slider    da aggiungere
     */
    private void aggiungiSliderConLabel(String etichetta, JSlider slider) {
        JPanel sliderPanel = new JPanel();
        sliderPanel.setLayout(new BorderLayout());

        JLabel valoreAttuale = new JLabel("" + slider.getValue());
        valoreAttuale.setPreferredSize(new Dimension(22, 0));

        slider.addChangeListener(
                (e) -> valoreAttuale.setText("" + slider.getValue())
        );

        sliderPanel.add(valoreAttuale, BorderLayout.WEST);
        sliderPanel.add(slider, BorderLayout.CENTER);

        aggiungiConEtichetta(etichetta, sliderPanel);
    }

    /**
     * Aggiungi una JLable con al suo fianco un JComponent
     *
     * @param etichetta
     * @param component
     */
    private void aggiungiConEtichetta(String etichetta, JComponent component) {
        datiPanel.add(new JLabel(etichetta));
        datiPanel.add(component);
    }

    /**
     * Crea il bottone che permette di innescare la costruzione
     * di una strada.
     */
    private void creaCostruisciButton() {
        JButton costruisciButton = new JButton("Costruisci strada");
        costruisciButton.setPreferredSize(new Dimension(300, 30));
        costruisciButton.addActionListener((evento) -> {
            // Verifico che sono stati selezionati due lotti panel
            if (lottoPanelSelezionati.size() == 2) {
                try {
                    costruisci();
                    System.out.println("Costruzione strada effettuata.");
                } catch (LottiIntermediOccupatiException e) {
                    new FrameEccezioneGestibile(e).setVisible(true);
                    System.err.println("Costruzione strada interrotta.");
                } finally {
                    resetta();
                }
            }
        });

        inputPanel.add(costruisciButton, BorderLayout.EAST);
    }

    /**
     * Resetta i lotti selezionati e l'input inserito.
     */
    private void resetta() {
        lottoPanelSelezionati.get(1).setBackground(Color.decode("#EEEEEE"));
        lottoPanelSelezionati.get(0).setBackground(Color.decode("#EEEEEE"));
        lottoPanelSelezionati.clear();

        nomeField.setText("");
    }

    /**
     * Costruisce una strada in base ai lotti selezionati.
     *
     * @throws LottiIntermediOccupatiException
     */
    private void costruisci() throws LottiIntermediOccupatiException {
        ordinaSelezionati();

        /*
         * Verifica se i lotti selezionati si trovano sulla stessa
         * riga o sulla stessa colonna.
         */
        if (centroUrbano.stessaRiga(
                lottoPanelSelezionati.get(1).getIndiceRigaSettore(),
                lottoPanelSelezionati.get(1).getIndiceRigaLotto(),
                lottoPanelSelezionati.get(0).getIndiceRigaSettore(),
                lottoPanelSelezionati.get(0).getIndiceRigaLotto()
        )) {
            costruisciRiga();
        } else {
            costruisciColonna();
        }

        // Ridisegno il frame dopo la costruzione della strada
        repaint();
    }

    /**
     * Controlla che la riga dove si vuole costruire sia libera e nel
     * caso costruisce la strada.
     *
     * @throws LottiIntermediOccupatiException
     */
    private void costruisciRiga() throws LottiIntermediOccupatiException {

        if (controllaRiga()) {
            System.out.println("I lotti intermedi della riga selezionata sono liberi, procedo alla costruzione.");
            IteratoreRigaDestinazione iteratoreRiga = costruisciIteratoreRiga();
            while (iteratoreRiga.hasNext()) {
                costruisciStrada(iteratoreRiga.next());
            }
        } else {
            throw new LottiIntermediOccupatiException(this, centroUrbano);
        }
    }

    /**
     * @return true se la riga è libera (quindi tutti i lotti sono edificabili),
     * false altrimenti.
     */
    private boolean controllaRiga() {
        System.out.println("Sto controllando che i lotti intermedi siano liberi.");
        IteratoreRigaDestinazione iteratoreRiga = costruisciIteratoreRiga();
        while (iteratoreRiga.hasNext()) {
            if (iteratoreRiga.next().occupato()) {
                return false;
            }
        }
        return true;
    }

    /**
     * Controlla che la colonna dove si vuole costruire sia libera e nel
     * caso costruisce la strada.
     *
     * @throws LottiIntermediOccupatiException
     */
    private void costruisciColonna() throws LottiIntermediOccupatiException {
        if (controllaColonna()) {
            System.out.println("I lotti intermedi della colonna selezionata sono liberi, procedo alla costruzione.");
            IteratoreColonnaDestinazione iteratoreColonna = costruisciIteratoreColonna();
            while (iteratoreColonna.hasNext()) {
                costruisciStrada(iteratoreColonna.next());
            }
        } else {
            throw new LottiIntermediOccupatiException(this, centroUrbano);
        }
    }

    /**
     * @return true se la colonna è libera (quindi tutti i lotti sono edificabili),
     * false altrimenti.
     */
    private boolean controllaColonna() {
        System.out.println("Sto controllando che i lotti intermedi siano liberi.");
        IteratoreColonnaDestinazione iteratoreColonna = costruisciIteratoreColonna();
        while (iteratoreColonna.hasNext()) {
            if (iteratoreColonna.next().occupato()) {
                return false;
            }
        }
        return true;
    }

    /**
     * @return istanza di un IteratoreColonnaDestinazione con gli indici
     * dei lotti selezionati
     */
    private IteratoreColonnaDestinazione costruisciIteratoreColonna() {
        LottoPanel partenza = lottoPanelSelezionati.get(0);
        LottoPanel destinazione = lottoPanelSelezionati.get(1);

        return centroUrbano.iteratoreColonnaDestinazione(
                partenza.getIndiceRigaSettore(),
                partenza.getIndiceRigaLotto(),
                destinazione.getIndiceRigaSettore(),
                destinazione.getIndiceRigaLotto(),
                partenza.getIndiceColonnaSettore(),
                partenza.getIndiceColonnaLotto()
        );
    }

    /**
     * @return istanza di un IteratoreRigaDestinazione con gli indici
     * dei lotti selezionati
     */
    private IteratoreRigaDestinazione costruisciIteratoreRiga() {
        LottoPanel partenza = lottoPanelSelezionati.get(0);
        LottoPanel destinazione = lottoPanelSelezionati.get(1);
        return centroUrbano.iteratoreRigaDestinazione(
                partenza.getIndiceColonnaSettore(),
                partenza.getIndiceColonnaLotto(),
                destinazione.getIndiceColonnaSettore(),
                destinazione.getIndiceColonnaLotto(),
                partenza.getIndiceRigaSettore(),
                partenza.getIndiceRigaLotto()
        );
    }

    /**
     * Costruisce la strada nel lotto inviato come parametro
     * esplicito.
     *
     * @param lotto
     */
    private void costruisciStrada(Lotto lotto) {
        lotto.costruisci(
                new Strada(
                        coefficienteDiEfficienzaSlider.getValue(),
                        coefficienteDiInvecchiamentoSlider.getValue(),
                        nomeField.getText().length() <= 0 ? null : nomeField.getText()
                )
        );
    }

    /**
     * Ordina i lotti selezionati in base agli indici di settore
     * e di lotto.
     */
    private void ordinaSelezionati() {
        new Ordinatore<>(lottoPanelSelezionati, (primo, secondo) -> {

            int indiceSettorePrimo = primo.getIndiceRigaSettore() * centroUrbano.getNumeroDiSettoriPerRiga() + primo.getIndiceColonnaSettore();
            int indiceLottoPrimo = primo.getIndiceRigaLotto() * centroUrbano.getNumeroDiLottiPerRiga() + primo.getIndiceColonnaLotto();
            int indiceSettoreSecondo = secondo.getIndiceRigaSettore() * centroUrbano.getNumeroDiSettoriPerRiga() + secondo.getIndiceColonnaSettore();
            int indiceLottoSecondo = secondo.getIndiceRigaLotto() * centroUrbano.getNumeroDiLottiPerRiga() + secondo.getIndiceColonnaLotto();

            if (indiceSettorePrimo < indiceSettoreSecondo)
                return -1;
            else if (indiceSettorePrimo > indiceSettoreSecondo)
                return 1;
            else if (indiceLottoPrimo < indiceLottoSecondo)
                return -1;
            else if (indiceLottoPrimo > indiceLottoSecondo)
                return 1;
            else
                return 0;
        }).ordina();
    }

    /**
     * {@inheritDoc}
     */
    protected LottoPanel getLottoPanel(int indiceRigaSettore, int indiceColonnaSettore, int indiceRigaLotto, int indiceColonnaLotto) {

        LottoPanel lottoPanel = (LottoPanel) super.getLottoPanel(indiceRigaSettore, indiceColonnaSettore, indiceRigaLotto, indiceColonnaLotto);

        lottoPanel.addMouseListener(new MouseAdapter() {
            private boolean selezionato = false;

            public void mousePressed(MouseEvent e) {
                if (selezionato) {
                    deseleziona();
                } else if (selezionabile()) {
                    seleziona();
                }
            }

            private void seleziona() {
                selezionato = true;
                lottoPanel.setBackground(Color.green);
                lottoPanelSelezionati.add(lottoPanel);
            }

            private void deseleziona() {
                lottoPanel.setBackground(Color.decode("#EEEEEE"));
                selezionato = false;
                lottoPanelSelezionati.remove(lottoPanel);
            }

            private boolean selezionabile() {
                boolean result = lottoPanelSelezionati.size() < 2;
                if (lottoPanelSelezionati.size() > 0) {
                    result = result && (
                            centroUrbano.stessaRiga(
                                    indiceRigaSettore, indiceRigaLotto,
                                    lottoPanelSelezionati.get(0).getIndiceRigaSettore(),
                                    lottoPanelSelezionati.get(0).getIndiceRigaLotto())
                                    ||
                                    centroUrbano.stessaColonna(
                                            indiceColonnaSettore, indiceColonnaLotto,
                                            lottoPanelSelezionati.get(0).getIndiceColonnaSettore(),
                                            lottoPanelSelezionati.get(0).getIndiceColonnaLotto()
                                    )
                    );
                }
                return result;
            }
        });
        return lottoPanel;
    }
}
