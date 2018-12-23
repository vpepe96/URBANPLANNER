package it.urbanplanner.gui;

import it.urbanplanner.settore.Lotto;
import it.urbanplanner.struttura.EdificioPrivato;
import it.urbanplanner.struttura.EdificioPubblico;
import it.urbanplanner.struttura.Strada;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

/**
 * Pannello utilizzabile per rappresentare uno specifico Lotto.
 */
public class LottoPanel extends JPanel {
    /**
     * Lotto da rappresentare.
     */
    private Lotto lotto;

    /**
     * Indice riga del settore del lotto da rappresentare.
     */
    private int indiceRigaSettore;

    /**
     * Indice colonna del settore del lotto da rappresentare.
     */
    private int indiceColonnaSettore;

    /**
     * Indice riga del settore del lotto da rappresentare.
     */
    private int indiceRigaLotto;

    /**
     * Indice colonna del settore del lotto da rappresentare.
     */
    private int indiceColonnaLotto;

    /**
     * Costruttore LottoPanel.
     *
     * @param lotto
     * @param indiceRigaSettore
     * @param indiceColonnaSettore
     * @param indiceRigaLotto
     * @param indiceColonnaSettore
     */
    public LottoPanel(Lotto lotto,
                      int indiceRigaSettore,
                      int indiceColonnaSettore,
                      int indiceRigaLotto,
                      int indiceColonnaLotto) {
        this.lotto = lotto;
        this.indiceRigaSettore = indiceRigaSettore;
        this.indiceColonnaSettore = indiceColonnaSettore;
        this.indiceRigaLotto = indiceRigaLotto;
        this.indiceColonnaLotto = indiceColonnaLotto;

        setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
    }


    /**
     * {@inheritDoc}
     */
    protected void paintComponent(Graphics grafica) {
        super.paintComponent(grafica);
        try {
            if (lotto.getElementoLotto() != null) {
                // Disegno l'icona dell'edificio all'interno del lotto
                grafica.drawImage(
                        ImageIO.read(new File("icons/" + getIcon())), // immagine da disegnare
                        10, //x
                        10, //y
                        getWidth() - 20, //width
                        getHeight() - 20, //height
                        this
                );

                // Disegno una stringa indicante il coefficiente di efficenza
                grafica.drawString(lotto.getElementoLotto().getCoefficienteDiEfficienza() + "", 10, 13);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*
    |--------------------------------------------------------------------------
    | Metodi di accesso
    |--------------------------------------------------------------------------
    */

    /**
     * @return lotto
     */
    public Lotto getLotto() {
        return lotto;
    }

    /**
     * @return indiceRigaSettore
     */
    public int getIndiceRigaSettore() {
        return indiceRigaSettore;
    }

    /**
     * @return indiceColonnaSettore
     */
    public int getIndiceColonnaSettore() {
        return indiceColonnaSettore;
    }

    /**
     * @return indiceRigaLotto
     */
    public int getIndiceRigaLotto() {
        return indiceRigaLotto;
    }

    /**
     * @return indiceColonnaLotto
     */
    public int getIndiceColonnaLotto() {
        return indiceColonnaLotto;
    }

    /**
     * @return nome dell'icona in base al tipo di ElementoLotto del Lotto da rappresentare.
     */
    private String getIcon() {
        if (lotto.getElementoLotto() instanceof Strada) {
            String str = "/Strada-";
            if (lotto.getLottiConfinanti()[0] != null && (lotto.getLottiConfinanti())[0].getElementoLotto() instanceof Strada)
                str = str + "u";
            if (lotto.getLottiConfinanti()[2] != null && (lotto.getLottiConfinanti())[2].getElementoLotto() instanceof Strada)
                str = str + "b";
            if (lotto.getLottiConfinanti()[3] != null && (lotto.getLottiConfinanti())[3].getElementoLotto() instanceof Strada)
                str = str + "s";
            if (lotto.getLottiConfinanti()[1] != null && (lotto.getLottiConfinanti())[1].getElementoLotto() instanceof Strada)
                str = str + "d";

            return "strade" + str + ".png";
        }

        if (lotto.getElementoLotto() instanceof EdificioPubblico) {
            EdificioPubblico edificioPubblico = (EdificioPubblico) lotto.getElementoLotto();

            if (edificioPubblico.getTipo() == EdificioPubblico.Tipo.SCUOLA) {
                return "school.png";
            }
            if (edificioPubblico.getTipo() == EdificioPubblico.Tipo.OSPEDALE) {
                return "hospital.png";
            }
            if (edificioPubblico.getTipo() == EdificioPubblico.Tipo.MUNICIPIO) {
                return "cityHall.png";
            }
            if (edificioPubblico.getTipo() == EdificioPubblico.Tipo.BANCA) {
                return "bank.png";
            }
        }

        if (lotto.getElementoLotto() instanceof EdificioPrivato) {
            EdificioPrivato edificioPrivato = (EdificioPrivato) lotto.getElementoLotto();

            if (edificioPrivato.getTipo() == EdificioPrivato.Tipo.CASA) {
                return "house.png";
            }
            if (edificioPrivato.getTipo() == EdificioPrivato.Tipo.NEGOZIO) {
                return "market.png";
            }
            if (edificioPrivato.getTipo() == EdificioPrivato.Tipo.HOTEL) {
                return "hotel.png";
            }
        }

        return null; //IMPOSSIBILE
    }
}
