package main.java.org.example.gatosApp;

import javax.swing.*;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        int opcionMenu = -1;
        String[] botones = {"1. Ver Gatos", "2. Ver favoritos","3. Salir"};

        do {

            //menú principal
            String opcion = (String) JOptionPane.showInputDialog(null,
                    "Gatitos Java",
                    "Menu principal",
                    JOptionPane.INFORMATION_MESSAGE,
                    null,
                    botones,
                    botones[0]);

            //Validamos que opcion selecciona el usuario
            for (int i = 0; i < botones.length; i++) {
                if(opcion.equals(botones[i])){
                    opcionMenu = i;
                }
            }

            if (opcionMenu == 0) {
                GatoService.verGatos();
            }
            if (opcionMenu == 1) {
                Gato gato = new Gato();
                GatoService.listarFavs(gato.getApiKey());
            }
            if (opcionMenu == 2) {
                System.exit(0);
            }

        }while (opcionMenu != 1);
    }
}