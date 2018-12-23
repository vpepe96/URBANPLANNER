package it.urbanplanner.test;


import it.urbanplanner.CentroUrbano;
import it.urbanplanner.Disastro;
import it.urbanplanner.GeneratoreDisastro;
import it.urbanplanner.gui.FramePrincipale;

import java.io.*;

public class ApplicationTester {
    public static void main(String[] args) {
        File file = new File("urban_planner.dat");
        CentroUrbano centroUrbano = null;

        if (!file.exists()) {
            centroUrbano = new CentroUrbano(3, 2, 4, 3);
            centroUrbano.crea();
            GeneratoreDisastro generatoreDisastro = centroUrbano.getGeneratoreDisastro();
            generatoreDisastro.aggiungi(new Disastro("TERREMOTO", 0.6));
            generatoreDisastro.aggiungi(new Disastro("NUBIFRAGIO", 0.5));
            generatoreDisastro.aggiungi(new Disastro("INONDAZIONE", 0.4));
            generatoreDisastro.aggiungi(new Disastro("INCENDIO", 0.3));
            generatoreDisastro.aggiungi(new Disastro("TERRORISMO", 0.3));
            generatoreDisastro.aggiungi(new Disastro("ATTO_VANDALICO", 0.1));

            try {
                file.createNewFile();
                ObjectOutputStream stream = new ObjectOutputStream(new FileOutputStream(file));
                stream.writeObject(centroUrbano);
                stream.close();
            } catch (IOException selezione) {
                selezione.printStackTrace();
            }
        } else {
            try {
                ObjectInputStream stream = new ObjectInputStream(new FileInputStream(file));
                centroUrbano = (CentroUrbano) stream.readObject();
                stream.close();
            } catch (IOException selezione) {
                selezione.printStackTrace();
            } catch (ClassNotFoundException selezione) {
                selezione.printStackTrace();
            }
        }

        FramePrincipale framePrincipale = new FramePrincipale(file, centroUrbano);

        framePrincipale.setResizable(false);
        framePrincipale.setVisible(true);
    }
}