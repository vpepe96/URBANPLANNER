package it.urbanplanner.gui;

import it.urbanplanner.CentroUrbano;
import it.urbanplanner.iteratori.IteratoreLottiCentroUrbano;
import it.urbanplanner.selezione.FiltroDiRicerca;
import it.urbanplanner.selezione.OrdinatoreFiltratore;
import it.urbanplanner.selezione.criteri.*;
import it.urbanplanner.selezione.filtri.*;
import it.urbanplanner.settore.Lotto;
import it.urbanplanner.settore.Settore;
import it.urbanplanner.struttura.EdificioPrivato;
import it.urbanplanner.struttura.EdificioPubblico;
import it.urbanplanner.struttura.Strada;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Set;

/**
 * Finestra contente criteri di selezione e ordinamento applicabili ad un istanta di CentroUrbano.
 */
public class FrameSelezione extends JFrame {
    /**
     * Larghezza del frame.
     */
    private static final int LARGHEZZA = 900;

    /**
     * Altezza del frame.
     */
    private static final int ALTEZZA = 250;

    /**
     * CentroUrbano di riferimento.
     */
    private CentroUrbano centroUrbano;

    /**
     * Pannello contenente tutti i componenenti per l'input.
     */
    private JPanel intestazionePanel;

    /**
     * Pannello contenente tutti i componenenti per la ricerca.
     */
    private JPanel ricercaPanel;

    /**
     * Pannello contenente i componenti per i criteri di ricerca.
     */
    private JPanel criteriDiRicercaPanel;

    /**
     * ComboBox contenente i criteri di ricerca.
     */
    private JComboBox<String> criteriDiRicercaComboBox;

    /**
     * Pannello contenente l'input da inserire dato un
     * filtro.
     */
    private JPanel inputPanel;

    /**
     * Pannello contenente tutti i componenenti per l'ordinamento.
     */
    private JPanel ordinamentoPanel;

    /**
     * ComboBox contenente i criteri di ordinamento.
     */
    private JComboBox<String> criteriDiOrdinamentoComboBox;

    /**
     * Gruppo di bottoni per definire la crescenza/decrescenza
     * del criterio di ordinamento.
     */
    private ButtonGroup crescenzaDecrescenzaOrdinamento;

    /**
     * Bottone per indicare la crescenza dell'ordinamento.
     */
    private JRadioButton crescenteRadioButton;

    /**
     * Bottone per indicare la decrescenza dell'ordinamento.
     */
    private JRadioButton decrescenteRadioButton;

    /**
     * Area contenente le informazioni sui lotti.
     */
    private JTextArea outputArea;

    /**
     * Collezione dei dati su cui sono state o verranno
     * effettuate ricerche e ordinamenti.
     */
    private ArrayList<Lotto> dati;

    /**
     * Campo che permette l'inserimento di un dato valore
     * specifico per un filtro di ricerca.
     */
    private JTextField valoreField;

    /**
     * Campo che permette la scelta di un dato valore
     * specifico per un filtro di ricerca.
     */
    private JComboBox<String> valoreComboBox;

    /**
     * Instanza di Comparator instanziata in base
     * all'input dell'utente.
     */
    private Comparator comparatore;

    /**
     * Instanza di FiltroDiRicerca instanziato in base
     * all'input dell'utente.
     */
    private FiltroDiRicerca filtroDiRicerca;

    /**
     * Instanza di OrdinatoreFiltratore utilizzata.
     */
    private OrdinatoreFiltratore<Lotto> ordinatoreFiltratore;

    /**
     * Pannello contenente i bottoni per effettuare operazioni
     * di ricerca e ordinamento.
     */
    private PannelloBottoni bottoniPanel;

    /**
     * Costruttore FrameSelezione.
     *
     * @param centroUrbano
     */
    public FrameSelezione(CentroUrbano centroUrbano) {
        super("Frame Selezione");

        this.centroUrbano = centroUrbano;

        setSize(LARGHEZZA, ALTEZZA);

        creaInstestazionePanel();
        creaOutputArea();
        creaBottoniPanel();
        resetta();
    }

    /**
     * Crea il pannello contenente diversi componenti che permettono
     * di effettuare ricerca e ordinamento.
     */
    private void creaInstestazionePanel() {
        intestazionePanel = new JPanel();
        intestazionePanel.setPreferredSize(new Dimension(0, 80));
        intestazionePanel.setLayout(new BorderLayout());

        creaRicercaPanel();
        creaOrdinamentoPanel();

        add(intestazionePanel, BorderLayout.NORTH);
    }

    /**
     * Crea il pannello contenente i componenti per effettuare una ricerca.
     */
    private void creaRicercaPanel() {
        ricercaPanel = new JPanel();
        ricercaPanel.setLayout(new GridLayout(2, 1));

        creaCriteriDiRicercaPanel();
        creaInputPanel();

        intestazionePanel.add(ricercaPanel, BorderLayout.WEST);
    }

    /**
     * Crea il pannello contenente i criteri di ricerca.
     */
    private void creaCriteriDiRicercaPanel() {
        criteriDiRicercaPanel = new JPanel();

        creaCriteriDiRicercaComboBox();

        ricercaPanel.add(criteriDiRicercaPanel);
    }

    /**
     * Crea una ComboBox contenente i criteri di ricerca.
     */
    private void creaCriteriDiRicercaComboBox() {
        criteriDiRicercaComboBox = new JComboBox<>();

        criteriDiRicercaComboBox.addItem(null);
        criteriDiRicercaComboBox.addItem("Valore > di");
        criteriDiRicercaComboBox.addItem("Valore < di");
        criteriDiRicercaComboBox.addItem("Coefficiente di efficienza > di");
        criteriDiRicercaComboBox.addItem("Coefficiente di efficienza < di");
        criteriDiRicercaComboBox.addItem("Settore");
        criteriDiRicercaComboBox.addItem("Tipo di ElementoLotto");

        criteriDiRicercaComboBox.addActionListener((e) -> {
            inputPanel.removeAll();
            valoreComboBox.removeAllItems();
            valoreField.setText("");
            if (criteriDiRicercaComboBox.getSelectedItem() != null) {
                caricaInput((String) criteriDiRicercaComboBox.getSelectedItem());
            }
            inputPanel.revalidate();
            inputPanel.repaint();
        });

        aggiungiConEtichetta(
                "Criteri di ricerca: ",
                criteriDiRicercaComboBox,
                criteriDiRicercaPanel
        );
    }

    /**
     * Dato un criterio di ricerca visualizza i componenti
     * per specificare i parametri.
     *
     * @param criterio
     */
    private void caricaInput(String criterio) {
        switch (criterio) {
            case "Valore > di":
                aggiungiConEtichetta(
                        "Valore da cercare",
                        valoreField,
                        inputPanel
                );
                break;
            case "Valore < di":
                aggiungiConEtichetta(
                        "Valore da cercare",
                        valoreField,
                        inputPanel
                );
                break;
            case "Coefficiente di efficienza > di":
                aggiungiConEtichetta(
                        "Coefficiente di efficienza",
                        valoreField,
                        inputPanel
                );
                break;
            case "Coefficiente di efficienza < di":
                aggiungiConEtichetta(
                        "Coefficiente di efficienza",
                        valoreField,
                        inputPanel
                );
                break;
            case "Settore":
                for (int i = 0; i < centroUrbano.getNumeroDiSettori(); i++) {
                    valoreComboBox.addItem("" + (i + 1));
                }
                aggiungiConEtichetta(
                        "Settori:",
                        valoreComboBox,
                        inputPanel
                );
                break;
            case "Tipo di ElementoLotto":
                valoreComboBox.addItem("Edificio Privato");
                valoreComboBox.addItem("Edificio Pubblico");
                valoreComboBox.addItem("Strada");
                aggiungiConEtichetta(
                        "Tipo di edificio:",
                        valoreComboBox,
                        inputPanel
                );
                break;
        }
    }

    /**
     * Crea un pannello contenente i componenti
     * per specificare i parametri di un criterio di ricerca.
     */
    private void creaInputPanel() {
        inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(1, 2));
        valoreField = new JTextField();
        valoreComboBox = new JComboBox<>();

        ricercaPanel.add(inputPanel);
    }

    /**
     * Crea un pannello contenente i componeneti per effettuare
     * l'ordinamento.
     */
    private void creaOrdinamentoPanel() {
        ordinamentoPanel = new JPanel();
        ordinamentoPanel.setLayout(new GridLayout(2, 2));

        creaCriteriDiOrdinamentoComboBox();
        creaCrescenzaDecrescenzaOrdinamento();

        intestazionePanel.add(ordinamentoPanel, BorderLayout.EAST);
    }

    /**
     * Crea una ComboBox contenente i criteri di ordinamento.
     */
    private void creaCriteriDiOrdinamentoComboBox() {
        criteriDiOrdinamentoComboBox = new JComboBox<>();

        criteriDiOrdinamentoComboBox.addItem(null);
        criteriDiOrdinamentoComboBox.addItem("Valore");
        criteriDiOrdinamentoComboBox.addItem("Coefficiente di efficienza");
        criteriDiOrdinamentoComboBox.addItem("Coefficiente di invecchiamento");
        criteriDiOrdinamentoComboBox.addItem("Nome (ordine lessico-grafico)");
        criteriDiOrdinamentoComboBox.addItem("Nome (lunghezza)");

        aggiungiConEtichetta(
                "Criteri di ordinamento: ",
                criteriDiOrdinamentoComboBox,
                ordinamentoPanel
        );
    }

    /**
     * Crea un gruppo di bottoni per definire se l'ordinamento
     * è crescente o decrescente.
     */
    private void creaCrescenzaDecrescenzaOrdinamento() {
        crescenzaDecrescenzaOrdinamento = new ButtonGroup();

        creaCrescenteRadioButton();
        creaDecrescenteRadioButton();
    }

    /**
     * Crea un radio button per indicare la crescenza.
     */
    private void creaCrescenteRadioButton() {
        crescenteRadioButton = new JRadioButton("Crescente");
        crescenteRadioButton.setSelected(true);

        crescenzaDecrescenzaOrdinamento.add(crescenteRadioButton);
        ordinamentoPanel.add(crescenteRadioButton);
    }

    /**
     * Crea un radio button per indicare la decrescenza.
     */
    private void creaDecrescenteRadioButton() {
        decrescenteRadioButton = new JRadioButton("Decrescente");

        crescenzaDecrescenzaOrdinamento.add(decrescenteRadioButton);
        ordinamentoPanel.add(decrescenteRadioButton);
    }

    /**
     * Permette l'aggiunta di un componenete ad un contenitore accompagnandolo
     * ad un etichetta avente la stringa indicata come valore.
     *
     * @param etichetta
     * @param componente
     * @param contenitore
     */
    private void aggiungiConEtichetta(String etichetta, Component componente, JPanel contenitore) {
        contenitore.add(new JLabel(etichetta));
        contenitore.add(componente);
    }

    /**
     * Crea una TextArea contenente le informazioni sui lotti.
     */
    private void creaOutputArea() {
        outputArea = new JTextArea();
        outputArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(outputArea);

        add(scrollPane, BorderLayout.CENTER);
    }

    /**
     * Stampa le informazioni sui lotti nella TextArea.
     */
    private void stampaLotti() {
        outputArea.setText("");
        for (Lotto lotto : dati) {
            outputArea.append("Informazioni Lotto: ");
            if(lotto.occupato()) {
                stampaInformazioniElementoLotto(lotto);
            } else {
                outputArea.append(" Libero");
            }
            outputArea.append("\n");
        }
    }

    /**
     * Stampa nella TextArea le informazioni dell'ElementoLotto.
     */
    private void stampaInformazioniElementoLotto(Lotto lotto) {
        outputArea.append("ElementoLotto: ");
        outputArea.append("Nome = " + lotto.getElementoLotto().getNome() + " , ");
        outputArea.append("Coefficiente di efficienza = " + lotto.getElementoLotto().getCoefficienteDiEfficienza() + " , ");
        outputArea.append("Coefficiente di invecchiamento = " + lotto.getElementoLotto().getCoefficienteDiInvecchiamento() + " , tipo = ");
        if (lotto.getElementoLotto() instanceof EdificioPubblico) {
            stampaInformazioniEdificioPubblico(lotto);
        } else if (lotto.getElementoLotto() instanceof EdificioPrivato) {
            stampaInformazioniEdificoPrivato(lotto);
        } else if(lotto.getElementoLotto() instanceof Strada) {
            outputArea.append("Strada");
        }
    }

    /**
     * Stampa nella TextArea le informazioni dell'EdificioPubblico.
     */
    private void stampaInformazioniEdificioPubblico(Lotto lotto) {
        outputArea.append("Edificio Pubblico: ");
        outputArea.append("Tipo edificio = " + ((EdificioPubblico) lotto.getElementoLotto()).getTipo().toString());
    }

    /**
     * Stampa nella TextArea le informazioni dell'EdificioPrivato.
     */
    private void stampaInformazioniEdificoPrivato(Lotto lotto) {
        outputArea.append("Edificio Privato: ");
        outputArea.append("Tipo edificio = " + ((EdificioPrivato) lotto.getElementoLotto()).getTipo().toString() + " , ");
        Settore settore = centroUrbano.ottieniSettore(lotto);
        if(settore != null) {
            outputArea.append("Valore = " + settore.getValore(lotto));
        } else {
            throw new IllegalArgumentException("Il lotto non è presente nel CentroUrbano");
        }
    }

    /**
     * Crea il pannello contenente i bottoni per effettuare
     * la ricerca.
     */
    private void creaBottoniPanel() {
        bottoniPanel = new PannelloBottoni();

        creaBottoni();

        add(bottoniPanel, BorderLayout.SOUTH);
    }

    /**
     * Crea i bottoni per l'applicazione e il reset dei filtri
     * e dei criteri applicati.
     */
    private void creaBottoni() {
        bottoniPanel.creaBottone("Applica", (e) -> {
            applicaFiltri();
            stampaLotti();
            System.out.println("Hai effettuato una ricerca.");
        });

        bottoniPanel.creaBottone("Resetta", (e) -> {
            resetta();
            System.out.println("Hai resettato la ricerca.");
        });
    }

    /**
     * Applica i filtri e i criteri selezionati.
     */
    private void applicaFiltri() {
        creaComparatore();
        creaFiltroDiRicerca();

        if (filtroDiRicerca != null) {
            ordinatoreFiltratore.setFiltroDiRicerca(filtroDiRicerca);
            ordinatoreFiltratore.filtra();
        }

        if (comparatore != null) {
            ordinatoreFiltratore.setCriterioDiOrdinamento(comparatore);
            ordinatoreFiltratore.ordina();
        }
    }

    /**
     * Crea il comparatore per il criterio di ricerca selezionato.
     */
    private void creaComparatore() {
        if (criteriDiOrdinamentoComboBox.getSelectedItem() != null) {
            switch ((String) criteriDiOrdinamentoComboBox.getSelectedItem()) {
                case "Valore":
                    if (crescenteRadioButton.isSelected()) {
                        comparatore = new ValoreCrescente(centroUrbano);
                    } else {
                        comparatore = new ValoreDecrescente(centroUrbano);
                    }
                    break;
                case "Coefficiente di efficienza":
                    if (crescenteRadioButton.isSelected()) {
                        comparatore = new CoefficienteEfficienzaCrescente();
                    } else {
                        comparatore = new CoefficienteEfficienzaDecrescente();
                    }
                    break;
                case "Coefficiente di invecchiamento":
                    if (crescenteRadioButton.isSelected()) {
                        comparatore = new CoefficienteInvecchiamentoCrescente();
                    } else {
                        comparatore = new CoefficienteInvecchiamentoDecrescente();
                    }
                    break;
                case "Nome (ordine lessico-grafico)":
                    if (crescenteRadioButton.isSelected()) {
                        comparatore = new NomeLessicoGraficoCrescente();
                    } else {
                        comparatore = new NomeLessicoGraficoDecrescente();
                    }
                    break;
                case "Nome (lunghezza)":
                    if (crescenteRadioButton.isSelected()) {
                        comparatore = new LunghezzaNomeCrescente();
                    } else {
                        comparatore = new LunghezzaNomeDecrescente();
                    }
                    break;
            }
        }
    }

    /**
     * Crea il filtro di ricerca in base a quello selezionato.
     */
    private void creaFiltroDiRicerca() {
        if (criteriDiRicercaComboBox.getSelectedItem() != null) {
            try {
                switch ((String) criteriDiRicercaComboBox.getSelectedItem()) {
                    case "Valore > di":
                        filtroDiRicerca = new FiltroValoreMaggioreDi(
                                Double.parseDouble(valoreField.getText()),
                                centroUrbano
                        );
                        break;
                    case "Valore < di":
                        filtroDiRicerca = new FiltroValoreMinoreDi(
                                Double.parseDouble(valoreField.getText()),
                                centroUrbano
                        );
                        break;
                    case "Coefficiente di efficienza > di":
                        filtroDiRicerca = new FiltroCoefficienteDiEfficienzaMaggioreDi(
                                Integer.parseInt(valoreField.getText())
                        );
                        break;
                    case "Coefficiente di efficienza < di":
                        filtroDiRicerca = new FiltroCoefficienteDiEfficienzaMinoreDi(
                                Integer.parseInt(valoreField.getText())
                        );
                        break;
                    case "Settore":
                        if (valoreComboBox.getSelectedItem() != null) {
                            int indiceSettore = Integer.parseInt((String) valoreComboBox.getSelectedItem()) - 1;
                            filtroDiRicerca = new FiltroPerSettore(centroUrbano.ottieni(
                                    indiceSettore / centroUrbano.getNumeroDiSettoriPerRiga(),
                                    indiceSettore % centroUrbano.getNumeroDiSettoriPerRiga()
                            ));
                        }
                        break;
                    case "Tipo di ElementoLotto":
                        creaFiltroDiRicercaTipo();
                        break;
                }
            } catch (NumberFormatException exception) {
                FrameEccezione frameEccezioni = new FrameEccezione(exception);
                frameEccezioni.write("\n\n Inserire correttamente il valore");
                frameEccezioni.setVisible(true);
            }
        }
    }

    /**
     * Crea un filtro di ricerca di tipo in base a quello selezionato.
     */
    private void creaFiltroDiRicercaTipo() {
        if (valoreComboBox.getSelectedItem() != null) {
            Class tipo = null;
            switch ((String) valoreComboBox.getSelectedItem()) {
                case "Edificio Privato":
                    tipo = EdificioPrivato.class;
                    break;
                case "Edificio Pubblico":
                    tipo = EdificioPubblico.class;
                    break;
                case "Strada":
                    tipo = Strada.class;
                    break;
            }
            if (tipo != null) {
                filtroDiRicerca = new FiltroPerTipo(tipo);
            }
        }
    }

    private void resetta() {
        dati = new ArrayList<>();
        IteratoreLottiCentroUrbano iteratoreLottiCentroUrbano = centroUrbano.iteratoreLotti();
        while (iteratoreLottiCentroUrbano.hasNext()) {
            dati.add(iteratoreLottiCentroUrbano.next());
        }
        outputArea.setText("");
        ordinatoreFiltratore = new OrdinatoreFiltratore<>(dati);
        criteriDiRicercaComboBox.setSelectedItem(null);
        criteriDiOrdinamentoComboBox.setSelectedItem(null);
        inputPanel.removeAll();
        inputPanel.revalidate();
        inputPanel.repaint();
        filtroDiRicerca = null;
        comparatore = null;
        crescenteRadioButton.setSelected(true);
    }
}