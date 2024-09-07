package org.example;

import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.html.parser.ParserDelegator;
import javax.swing.text.MutableAttributeSet;
import javax.swing.text.html.HTML;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.BadLocationException;
import java.io.*;
import java.util.ArrayList;
import java.util.List;


public class BuscarPalabraHTML {
    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("Tienes que usar: java BuscarPalabraHTML [nombre-archivo-html] [palabra]");
            return;
        }

        String nombreArchivo = args[0];
        String palabra = args[1];
        List<Integer> posiciones = new ArrayList<>();

        try {
            // Crear un reader para el archivo HTML
            BufferedReader reader = new BufferedReader(new FileReader(nombreArchivo));

            // Crear un documento HTML con HTMLEditorKit
            HTMLEditorKit htmlKit = new HTMLEditorKit();
            HTMLDocument htmlDoc = (HTMLDocument) htmlKit.createDefaultDocument();

            // Leer el contenido del archivo HTML en el documento
            htmlKit.read(reader, htmlDoc, 0);

            // Obtener el texto plano del documento HTML
            String textoPlano = htmlDoc.getText(0, htmlDoc.getLength());

            // Buscar la palabra en el texto plano
            int posicion = textoPlano.indexOf(palabra);
            while (posicion != -1) {
                posiciones.add(posicion);
                posicion = textoPlano.indexOf(palabra, posicion + 1);
            }

            if (posiciones.isEmpty()) {
                System.out.println("La palabra '" + palabra + "' no se encontró en el archivo.");
            } else {
                System.out.println("La palabra '" + palabra + "' se encontró en las siguientes posiciones:");
                for (int pos : posiciones) {
                    System.out.println(pos);
                }

                String nombreLog = "file-" + palabra + ".log";
                PrintWriter logWriter = new PrintWriter(new FileWriter(nombreLog));
                logWriter.println("Nombre del archivo: " + nombreArchivo);
                logWriter.println("Ocurrencias de la palabra '" + palabra + "':");
                for (int pos : posiciones) {
                    logWriter.println("Posicion: " + pos);
                }
                logWriter.close();

                System.out.println("Archivo de bitacora '" + nombreLog + "' creado.");
            }

        } catch (IOException | BadLocationException e) {
            System.out.println("Error al buscar el archivo: " + e.getMessage());
        }
    }
}

