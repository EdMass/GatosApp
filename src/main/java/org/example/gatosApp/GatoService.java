package main.java.org.example.gatosApp;

import com.google.gson.Gson;
import okhttp3.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.URL;

public class GatoService {
    public static void verGatos() throws IOException {
        //Vamos a traer los datos de la API
        OkHttpClient client = new OkHttpClient().newBuilder().build();

        MediaType mediaType = MediaType.parse("text/plain");
        RequestBody body = RequestBody.create(mediaType, "");
        Request request = new Request.Builder()
                .url("https://api.thecatapi.com/v1/images/search")
                .get()
                .build();
        Response response = client.newCall(request).execute();

        String elJson = response.body().string();

        //cortar corchetes
        //elJson = elJson.substring(1, elJson.length());
       //elJson = elJson.substring(0, elJson.length()-1);

        //Crear un objeto de la calse Gatos
        Gson gson = new Gson();
        Gato gato = gson.fromJson(elJson, Gato[].class)[0];

        //Redimensionar la imagen
        Image image = null;
        try{
            URL url = new URL(gato.getUrl());
            image = ImageIO.read(url);

            ImageIcon imagenGato = new ImageIcon(image);

            if (imagenGato.getIconWidth() > 800) {
                //redimencion
                Image fondo = imagenGato.getImage();
                Image modificada = fondo.getScaledInstance(800, 600, Image.SCALE_SMOOTH);
                imagenGato = new ImageIcon(modificada);
            }

            String menu = "Opciones: \n"
                    + "1. Ver otra imagen\n"
                    + "2. Favorito\n"
                    + "3. Volver \n";

            String [] botones = { "1. Ver otra imagen", "2. Favorito", "3. Volver"};
            String idGato = gato.getId();
            String opcion = (String) JOptionPane.showInputDialog(
                    null,
                    menu,
                    idGato,
                    JOptionPane.INFORMATION_MESSAGE,
                    imagenGato,
                    botones,
                    botones[0]);

            int seleccion = -1;
            //Validamos que opcion selecciona el usuario
            for (int i = 0; i < botones.length; i++) {
                if(opcion.equals(botones[i])){
                    seleccion = i;
                }
            }

            switch (seleccion) {
                case 0 -> verGatos();
                case 1 -> favoritoGato(gato);
                default -> new Main();

            }


        }catch (IOException e){
            System.out.println(e);
        }
    }

    private static void favoritoGato(Gato gato) {
        try{
            OkHttpClient client = new OkHttpClient().newBuilder()
                    .build();
            MediaType mediaType = MediaType.parse("application/json");
            RequestBody body = RequestBody.create(mediaType, "{\r\n    \"image_id\":\""+ gato.getId()+"\"\r\n}");
            Request request = new Request.Builder()
                    .url("https://api.thecatapi.com/v1/favourites")
                    .method("POST", body)
                    .addHeader("Content-Type", "application/json")
                    .addHeader("x-api-key", gato.apiKey)
                    .build();
            Response response = client.newCall(request).execute();
        }catch (IOException e){
            System.out.println(e);
        }
    }

    public static void listarFavs(String apiKey) throws IOException {
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        //MediaType mediaType = MediaType.parse("text/plain");
       // RequestBody body = RequestBody.create(mediaType, "");
        Request request = new Request.Builder()
                .url("https://api.thecatapi.com/v1/favourites")
                .get()
                .addHeader("x-api-key", apiKey)
                .build();

        Response response = client.newCall(request).execute();

        //Guardamos el string como respuesta
        String elJson = response.body().string();

        if(!response.isSuccessful()) {
            response.body().close();
        }
        //Creamos el objeto Gson
        Gson gson = new Gson();

        GatoFav[] gatosArray = gson.fromJson(elJson, GatoFav[].class);

        if(gatosArray.length > 0){
            int min = 1;
            int max = gatosArray.length;
            int aleatorio = (int) (Math.random() * ((max-min)+1)) + min;
            int indice = aleatorio - 1;

            GatoFav gatoFav = gatosArray[indice];

            //Redimensionar la imagen
            Image image = null;
            try{
                URL url = new URL(gatoFav.image.getUrl());
                image = ImageIO.read(url);

                ImageIcon imagenGato = new ImageIcon(image);

                if (imagenGato.getIconWidth() > 800) {
                    //redimencion
                    Image fondo = imagenGato.getImage();
                    Image modificada = fondo.getScaledInstance(800, 600, Image.SCALE_SMOOTH);
                    imagenGato = new ImageIcon(modificada);
                }

                String menu = "Opciones: \n"
                        + "1.Ver otro favorito\n"
                        + "2.Eliminar favorito\n"
                        + "3. Volver \n";

                String [] botones = { "1. Ver otra imagen", "2. Eliminar favorito", "3. Volver"};
                String idGato = gatoFav.getId();
                String opcion = (String) JOptionPane.showInputDialog(
                        null,
                        menu,
                        idGato,
                        JOptionPane.INFORMATION_MESSAGE,
                        imagenGato,
                        botones,
                        botones[0]);

                int seleccion = -1;
                //Validamos que opcion selecciona el usuario
                for (int i = 0; i < botones.length; i++) {
                    if(opcion.equals(botones[i])){
                        seleccion = i;
                    }
                }

                switch (seleccion) {
                    case 0 -> listarFavs(apiKey);
                    case 1 -> eliminarFavorito(gatoFav);
                    default -> verGatos();


                }


            }catch (IOException e){
                System.out.println(e);
            }

        }
    }

    private static void eliminarFavorito(GatoFav gatoFav) {

        try{
            OkHttpClient client = new OkHttpClient().newBuilder()
                    .build();
            MediaType mediaType = MediaType.parse("application/json");
            RequestBody body = RequestBody.create(mediaType, "");
            Request request = new Request.Builder()
                    .url("https://api.thecatapi.com/v1/favourites/"+gatoFav.getId()+"")
                    .method("DELETE", body)
                    .addHeader("Content-Type", "application/json")
                    .addHeader("x-api-key", gatoFav.apiKey)
                    .build();
            Response response = client.newCall(request).execute();
        }catch (IOException e){
            System.out.println(e);
        }
    }
}
