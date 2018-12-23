package it.urbanplanner.gui;

import it.urbanplanner.CentroUrbano;
import it.urbanplanner.eccezioni.LottoLiberoException;
import it.urbanplanner.eccezioni.LottoOccupatoException;
import it.urbanplanner.settore.Lotto;
import it.urbanplanner.struttura.EdificioPrivato;
import it.urbanplanner.struttura.EdificioPubblico;
import it.urbanplanner.struttura.Strada;

import javax.swing.*;
import java.awt.*;

/**
 * Finestra che permette di ottenere informazioni su un Lotto,
 * effettuare operazioni di demolizione e costruzione su di esso.
 */
public class FrameModificaLotto extends JFrame {
    /**
     * Larghezza del frame.
     */
    private static final int LARGHEZZA = 650;

    /**
     * Altezza del frame.
     */
    private static final int ALTEZZA = 240;

    /**
     * CentroUrbano del lotto di riferimento.
     */
    private CentroUrbano centroUrbano;

    /**
     * LottoPanel rappresentante il Lotto.
     */
    private LottoPanel lottoPanel;

    /**
     * Lotto di riferimento.
     */
    private Lotto lotto;

    /**
     * Indice riga settore del Lotto di riferimento.
     */
    private int indiceRigaSettore;

    /**
     * Indice colonna del Lotto di riferimento.
     */
    private int indiceColonnaSettore;

    /**
     * Indice riga lotto di riferimento all'interno del Settore.
     */
    private int indiceRigaLotto;

    /**
     * Indice colonna lotto di riferimento all'interno del Settore.
     */
    private int indiceColonnaLotto;

    /**
     * Contiene gli elementi per la gestione del Lotto.
     */
    private JPanel gestisciPanel;

    /**
     * Contiene i bottoni per la gestione del Lotto.
     */
    private PannelloBottoni bottoniGestionePane;

    /**
     * Contiene le informazioni sul Lotto.
     */
    private JTextArea infoArea;

    /**
     * Contiene l'infoArea e ne permette lo scroll nel caso
     * si superi il limite della finestra.
     */
    private JScrollPane infoAreaScroll;

    /**
     * Contiene tutti gli elementi per la ristrutturazione dell'
     * ElementoLotto.
     */
    private JPanel ristrutturazionePanel;

    /**
     * Contiene tutti gli elementi per la ristrutturazione dell'
     * ElementoLotto.
     */
    private PannelloBottoni bottoniRistrutturazionePanel;

    /**
     * Indica il coefficienteDiMiglioramento per la ristrutturazione
     * dell'ElementoLotto.
     */
    private JSlider coefficienteDiMiglioramentoSlider;

    /**
     * Contiene tutti gli elementi per la costruzione di un nuovo
     * elemento nel Lotto.
     */
    private JPanel costruzionePanel;

    /**
     * Contiene gli elementi base per la costruzione di un elemento
     * nel Lotto.
     */
    private JPanel costruisciPanel;

    /**
     * Contiene gli elementi specifici per la costruzione di un tipo
     * di Edificio.
     */
    private JPanel costruisciEdificioPanel;

    /**
     * Contiene i bottoni per permettere un operazione di
     * costruzione.
     */
    private PannelloBottoni bottoniCostruzionePanel;

    /**
     * Contiene il nome dell'ElementoLotto che verrà costruito.
     */
    private JTextField nomeField;

    /**
     * Indica il tipo di ElementoLotto da instanziare e costruire
     * all'interno del Lotto.
     */
    private JComboBox<String> tipoComboBox;

    /**
     * Indica il coefficienteDiEfficienza dell'ElementoLotto che
     * verrà costruito
     */
    private JSlider coefficienteDiEfficienzaSlider;

    /**
     * Indica il coefficienteDiInvecchiamento dell'ElementoLotto che
     * verrà costruito
     */
    private JSlider coefficienteDiInvecchiamentoSlider;

    /**
     * Indica il tipo di EdificioPubblico dell'EdificioPubblico che
     * verrà costruito.
     */
    private JComboBox<EdificioPubblico.Tipo> tipoEdificioPubblicoComboBox;

    /**
     * Indica il tipo di EdificioPrivato dell'EdificioPrivato che
     * verrà costruito.
     */
    private JComboBox<EdificioPrivato.Tipo> tipoEdificioPrivatoComboBox;

    /**
     * Indica il valore dell'EdificioPrivato che verrà costruito.
     */
    private JTextField valoreField;

    /**
     * Costruzione FrameModificaLotto.
     *
     * @param centroUrbano
     * @param lotto
     * @param indiceRigaSettore
     * @param indiceColonnaSettore
     * @param indiceRigaLotto
     * @param indiceColonnaLotto
     */
    public FrameModificaLotto(CentroUrbano centroUrbano,
                              Lotto lotto,
                              int indiceRigaSettore,
                              int indiceColonnaSettore,
                              int indiceRigaLotto,
                              int indiceColonnaLotto
    ) {
        super();

        this.centroUrbano = centroUrbano;
        this.lottoPanel = new LottoPanel(
                lotto,
                indiceRigaSettore,
                indiceColonnaSettore,
                indiceRigaLotto,
                indiceColonnaLotto
        );
        this.lotto = lottoPanel.getLotto();
        this.indiceRigaSettore = indiceRigaSettore;
        this.indiceColonnaSettore = indiceColonnaSettore;
        this.indiceRigaLotto = indiceRigaLotto;
        this.indiceColonnaLotto = indiceColonnaLotto;

        setTitle("Modifica Lotto "
                + ((indiceRigaLotto * centroUrbano.getNumeroDiLottiPerRiga()) + indiceColonnaLotto+1)
                + " Settore "
                + ((indiceRigaSettore * centroUrbano.getNumeroDiSettoriPerRiga()) + indiceColonnaSettore+1)
        );

        setSize(LARGHEZZA, ALTEZZA);
        add(lottoPanel, BorderLayout.CENTER);
        creaGesticiPanel();
    }

    /**
     * Torna indietro alla schermata iniziale.
     */
    private void tornaIndietro() {
        gestisciPanel.removeAll();

        creaInfoArea();
        creaBottoniGestionePanel();

        revalidate();
    }

    /**
     * Crea il pannello contenente gli elementi per gestire
     * il Lotto.
     */
    private void creaGesticiPanel() {
        gestisciPanel = new JPanel();
        gestisciPanel.setPreferredSize(
                new Dimension(LARGHEZZA - ALTEZZA, ALTEZZA)
        );
        gestisciPanel.setLayout(new BorderLayout());

        creaInfoArea();
        creaBottoniGestionePanel();

        add(gestisciPanel, BorderLayout.EAST);
    }

    /**
     * Crea il pannello contente i bottoni per effettuare
     * una operazione di gestione.
     */
    private void creaBottoniGestionePanel() {
        bottoniGestionePane = new PannelloBottoni();

        creaBottoniGestione();

        gestisciPanel.add(bottoniGestionePane, BorderLayout.SOUTH);
    }

    /**
     * Crea i bottoni per le funzionalità previste dalla
     * gestione del Lotto.
     */
    private void creaBottoniGestione() {
        if(lotto.getElementoLotto() != null && lotto.getElementoLotto().getCoefficienteDiEfficienza() < 100) {
            bottoniGestionePane.creaBottone("Ristruttura", (e) -> {
                if (lotto.getElementoLotto() != null) {
                    gestisciPanel.removeAll();

                    creaRistrutturazionePanel();
                    creaBottoniRistrutturazionePanel();

                    revalidate();
                }
            });
        }

        bottoniGestionePane.creaBottone("Costruisci", (e) -> {
            gestisciPanel.removeAll();

            creaCostruzionePanel();
            creaBottoniCostruzionePanel();

            revalidate();
        });

        bottoniGestionePane.creaBottone("Demolisci", (e) -> {
            try {
                centroUrbano.demolisci(lotto);
                lottoPanel.repaint();
                stampaInformazioniLotto();
                System.out.println("Hai scelto di demolire un elemento lotto.");
            } catch (LottoLiberoException exception) {
                System.err.println("Hai tentato di demolire un lotto libero.");
                new FrameEccezione(exception).setVisible(true);
            }
        });
    }

    /**
     * Crea il pannello contenente tutti i dati da inserire per poter
     * restaurare un ElementoLotto.
     */
    private void creaRistrutturazionePanel() {
        ristrutturazionePanel = new JPanel();
        ristrutturazionePanel.setLayout(new GridLayout(2, 1));

        creaCoefficienteDiMiglioramentoSlider();

        gestisciPanel.add(ristrutturazionePanel, BorderLayout.CENTER);
    }

    /**
     * Crea il pannello contente i bottoni per effettuare
     * una operazione di restaurazione.
     */
    private void creaBottoniRistrutturazionePanel() {
        bottoniRistrutturazionePanel = new PannelloBottoni();

        creaBottoniRistrutturazione();

        gestisciPanel.add(bottoniRistrutturazionePanel, BorderLayout.SOUTH);
    }

    /**
     * Crea i bottoni per permettere la restaurazione di un ElementoLotto.
     */
    private void creaBottoniRistrutturazione() {
        bottoniRistrutturazionePanel.creaBottone("Conferma", (e) -> {
            System.out.println("Hai ristrutturato un elemento lotto.");
            lotto.getElementoLotto().restaura(coefficienteDiMiglioramentoSlider.getValue());
            tornaIndietro();
            lottoPanel.repaint();
        });

        bottoniRistrutturazionePanel.creaBottone("Annulla", (e) -> {
            tornaIndietro();
        });
    }

    /**
     * Crea lo slider che permette la scelta del coefficiente di
     * efficienza per l'ElementoLotto da costruire.
     */
    private void creaCoefficienteDiMiglioramentoSlider() {
        coefficienteDiMiglioramentoSlider = new JSlider(
                0,
                100 - lotto.getElementoLotto().getCoefficienteDiEfficienza(),
                0
        );

        aggiungiSliderConLabel(
                "Coefficiente di miglioramento:",
                coefficienteDiMiglioramentoSlider,
                ristrutturazionePanel
        );
    }

    /**
     * Crea il pannello contenente tutti i dati da inserire per poter
     * costruire un nuovo ElementoLotto.
     */
    private void creaCostruzionePanel() {
        costruzionePanel = new JPanel();
        costruzionePanel.setLayout(new BoxLayout(costruzionePanel, BoxLayout.Y_AXIS));

        creaCostruisciPanel();
        creaCostruisciEdificioPanel();

        gestisciPanel.add(costruzionePanel, BorderLayout.CENTER);
    }

    /**
     * Crea il pannello contenente i dati di base da inserire per
     * poter creare un nuovo ElementoLotto.
     */
    private void creaCostruisciPanel() {
        costruisciPanel = new JPanel();
        costruisciPanel.setLayout(new GridLayout(4, 2));

        creaNomeField();
        creaTipoComboBox();
        creaCoefficienteDiEfficienzaSlider();
        creaCoefficienteDiInvecchiamentoSlider();

        costruzionePanel.add(costruisciPanel);
    }

    /**
     * Crea il pannello contenente i dati specifici da inserire per
     * poter creare un nuovo Edificio.
     */
    private void creaCostruisciEdificioPanel() {
        costruisciEdificioPanel = new JPanel();

        costruzionePanel.add(costruisciEdificioPanel);
    }

    /**
     * Crea un instanza di ComboBox contenente i tipi di edifici
     * creabili.
     */
    private void creaTipoComboBox() {
        tipoComboBox = new JComboBox<>(new String[]{
                null,
                "Strada",
                "Edificio Pubblico",
                "Edificio Privato",
        });
        tipoComboBox.addActionListener((e) -> {
            costruisciEdificioPanel.removeAll();
            if (tipoComboBox.getSelectedItem() != null) {
                cambiaTipo((String) tipoComboBox.getSelectedItem());
            }
            costruisciPanel.revalidate();
        });

        aggiungiConEtichetta("Tipo costruzione:", tipoComboBox, costruisciPanel);
    }

    /**
     * Permette di cambiare i dati da inserire in base
     * al tipo di costruzione scelta.
     *
     * @param tipo di costruzione scelta
     */
    private void cambiaTipo(String tipo) {
        switch (tipo) {
            case "Edificio Pubblico":
                costruisciEdificioPanel.setLayout(new GridLayout(1, 2));
                creaTipoEdificioPubblicoComboBox();
                break;
            case "Edificio Privato":
                costruisciEdificioPanel.setLayout(new GridLayout(2, 2));
                creaTipoEdificioPrivatoComboBox();
                creaValoreField();
                break;
        }
    }

    /**
     * Crea il campo contenente il nome dell'ElementoLotto da costruire.
     */
    private void creaNomeField() {
        nomeField = new JTextField();

        aggiungiConEtichetta("Nome:", nomeField, costruisciPanel);
    }

    /**
     * Crea lo slider che permette la scelta del coefficiente di
     * efficienza per l'ElementoLotto da costruire.
     */
    private void creaCoefficienteDiEfficienzaSlider() {
        coefficienteDiEfficienzaSlider = new JSlider(1, 100, 100);

        aggiungiSliderConLabel(
                "Coefficiente di efficienza:",
                coefficienteDiEfficienzaSlider,
                costruisciPanel
        );
    }

    /**
     * Crea lo slider che permette la scelta del coefficiente di
     * invecchiamento per l'ElementoLotto da costruire.
     */
    private void creaCoefficienteDiInvecchiamentoSlider() {
        coefficienteDiInvecchiamentoSlider = new JSlider(1, 10, 1);

        aggiungiSliderConLabel(
                "Coefficiente di invecchiamento:",
                coefficienteDiInvecchiamentoSlider,
                costruisciPanel
        );
    }

    /**
     * Crea una ComboBox che permette la scelta del Tipo di EdificioPubblico
     * per l'EdificioPubblico da costruire.
     */
    private void creaTipoEdificioPubblicoComboBox() {
        tipoEdificioPubblicoComboBox = new JComboBox<>(new EdificioPubblico.Tipo[]{
                EdificioPubblico.Tipo.BANCA,
                EdificioPubblico.Tipo.SCUOLA,
                EdificioPubblico.Tipo.MUNICIPIO,
                EdificioPubblico.Tipo.OSPEDALE
        });

        aggiungiConEtichetta("Tipo edificio pubblico:", tipoEdificioPubblicoComboBox, costruisciEdificioPanel);
    }

    /**
     * Crea una ComboBox che permette la scelta del Tipo di EdificioPrivato
     * per l'EdificioPrivato da costruire.
     */
    private void creaTipoEdificioPrivatoComboBox() {
        tipoEdificioPrivatoComboBox = new JComboBox<>(new EdificioPrivato.Tipo[]{
                EdificioPrivato.Tipo.CASA,
                EdificioPrivato.Tipo.HOTEL,
                EdificioPrivato.Tipo.NEGOZIO,
        });

        aggiungiConEtichetta("Tipo edificio privato:", tipoEdificioPrivatoComboBox, costruisciEdificioPanel);
    }

    /**
     * Crea un campo contenente il valore dell'EdificioPrivato da costruire.
     */
    private void creaValoreField() {
        valoreField = new JTextField();

        aggiungiConEtichetta("Valore:", valoreField, costruisciEdificioPanel);
    }

    /**
     * Permette di aggiungere uno Slider con Etichetta indicante il valore corrente
     * al costruisciPanel
     *
     * @param etichetta nome etichetta
     * @param slider    da aggiungere
     * @param panel     dove aggiungere etichetta e componenete
     */
    private void aggiungiSliderConLabel(String etichetta, JSlider slider, JPanel panel) {
        JPanel sliderPanel = new JPanel();
        sliderPanel.setLayout(new BorderLayout());

        JLabel valoreAttuale = new JLabel("" + slider.getValue());
        valoreAttuale.setPreferredSize(new Dimension(22, 0));

        slider.addChangeListener(
                (e) -> valoreAttuale.setText("" + slider.getValue())
        );

        sliderPanel.add(valoreAttuale, BorderLayout.WEST);
        sliderPanel.add(slider, BorderLayout.CENTER);

        aggiungiConEtichetta(etichetta, sliderPanel, panel);
    }

    /**
     * Aggiunge il componente indicato con un etichetta al pannello
     * inviato.
     *
     * @param etichetta  nome etichetta
     * @param componente da aggiungere
     * @param panel      dove aggiungere etichetta e componenete
     */
    private void aggiungiConEtichetta(String etichetta, JComponent componente, JPanel panel) {
        panel.add(new JLabel(etichetta));
        panel.add(componente);
    }

    /**
     * Crea il pannello contente i bottoni per effettuare
     * una operazione di costruzione.
     */
    private void creaBottoniCostruzionePanel() {
        bottoniCostruzionePanel = new PannelloBottoni();

        creaBottoniCostruzione();

        gestisciPanel.add(bottoniCostruzionePanel, BorderLayout.SOUTH);
    }

    /**
     * Crea i bottoni per le funzionalità previste dalla
     * gestione del Lotto.
     */
    private void creaBottoniCostruzione() {
        bottoniCostruzionePanel.creaBottone("Conferma", (e) -> {
            try {
                if (tipoComboBox.getSelectedItem() != null) {
                    confermaCreazione((String) tipoComboBox.getSelectedItem());
                }
                System.out.println("Hai costruito un elemento lotto.");
            } catch (LottoOccupatoException exception) {
                new FrameEccezione(exception).setVisible(true);
            }
        });

        bottoniCostruzionePanel.creaBottone("Annulla", (e) -> {
            tornaIndietro();
        });
    }

    /**
     * Sulla base dei dati inseriti crea un nuovo ElementoLotto.
     *
     * @param tipo
     */
    private void confermaCreazione(String tipo) {
        switch (tipo) {
            case "Strada":
                creaStrada();
                break;
            case "Edificio Pubblico":
                creaEdificioPubblico();
                break;
            case "Edificio Privato":
                creaEdificioPrivato();
                break;
        }
        lottoPanel.repaint();
        tornaIndietro();
    }

    /**
     * Crea l'EdificioPrivato sulla base dei dati inseriti.
     */
    private void creaEdificioPrivato() {
        try {
            lotto.costruisci(new EdificioPrivato(
                    coefficienteDiEfficienzaSlider.getValue(),
                    coefficienteDiInvecchiamentoSlider.getValue(),
                    nomeField.getText().length() <= 0 ? null : nomeField.getText(),
                    (EdificioPrivato.Tipo) tipoEdificioPrivatoComboBox.getSelectedItem(),
                    Double.parseDouble(valoreField.getText())
            ));
        } catch (NumberFormatException exception) {
            FrameEccezione frameEccezioni = new FrameEccezione(exception);
            frameEccezioni.write("\n\n Inserire correttamente il valore che deve essere di tipo double");
            frameEccezioni.setVisible(true);
        }
    }

    /**
     * Crea l'EdificioPubblico sulla base dei dati inseriti.
     */
    private void creaEdificioPubblico() {
        lotto.costruisci(new EdificioPubblico(
                coefficienteDiEfficienzaSlider.getValue(),
                coefficienteDiInvecchiamentoSlider.getValue(),
                nomeField.getText().length() <= 0 ? null : nomeField.getText(),
                (EdificioPubblico.Tipo) tipoEdificioPubblicoComboBox.getSelectedItem()
        ));
    }

    /**
     * Crea l'Strada sulla base dei dati inseriti.
     */
    private void creaStrada() {
        lotto.costruisci(new Strada(
                coefficienteDiEfficienzaSlider.getValue(),
                coefficienteDiInvecchiamentoSlider.getValue(),
                nomeField.getText().length() <= 0 ? null : nomeField.getText()
        ));
    }

    /**
     * Inizializza un instanza di TextArea contenente le
     * informazioni sul Lotto.
     */
    private void creaInfoArea() {
        infoArea = new JTextArea();
        infoAreaScroll = new JScrollPane(infoArea);
        infoArea.setEditable(false);

        stampaInformazioniLotto();

        gestisciPanel.add(infoAreaScroll, BorderLayout.CENTER);
    }

    /**
     * Stampa nella TextArea le informazioni del Lotto.
     */
    private void stampaInformazioniLotto() {
        infoArea.setText("Informazioni Lotto\n");
        infoArea.append("Indice Settore=(riga=" + (indiceRigaSettore+1) + ", colonna=" + (indiceColonnaSettore+1) + ")\n");
        infoArea.append("Indice Lotto=(riga=" + (indiceRigaLotto+1) + ", colonna=" + (indiceColonnaLotto+1) + ")\n");
        if (lotto.getElementoLotto() != null) {
            stampaInformazioniElementoLotto();
        }
    }

    /**
     * Stampa nella TextArea le informazioni dell'ElementoLotto.
     */
    private void stampaInformazioniElementoLotto() {
        infoArea.append("ElementoLotto\n");
        infoArea.append("Nome=" + lotto.getElementoLotto().getNome() + "\n");
        infoArea.append("Coefficiente di efficienza=" + lotto.getElementoLotto().getCoefficienteDiEfficienza() + "\n");
        infoArea.append("Coefficiente di invecchiamento=" + lotto.getElementoLotto().getCoefficienteDiInvecchiamento() + "\n");
        if (lotto.getElementoLotto() instanceof EdificioPubblico) {
            stampaInformazioniEdificioPubblico();
        } else if (lotto.getElementoLotto() instanceof EdificioPrivato) {
            stampaInformazioniEdificoPrivato();
        }
    }

    /**
     * Stampa nella TextArea le informazioni dell'EdificioPubblico.
     */
    private void stampaInformazioniEdificioPubblico() {
        infoArea.append("Edificio Pubblico\n");
        infoArea.append("Tipo edificio=" + ((EdificioPubblico) lotto.getElementoLotto()).getTipo().toString() + "\n");
    }

    /**
     * Stampa nella TextArea le informazioni dell'EdificioPrivato.
     */
    private void stampaInformazioniEdificoPrivato() {
        infoArea.append("Edificio Privato\n");
        infoArea.append("Tipo edificio=" + ((EdificioPrivato) lotto.getElementoLotto()).getTipo().toString() + "\n");
        infoArea.append("Valore=" + centroUrbano.ottieni(indiceRigaSettore, indiceColonnaSettore).getValore(lotto));
    }
}
