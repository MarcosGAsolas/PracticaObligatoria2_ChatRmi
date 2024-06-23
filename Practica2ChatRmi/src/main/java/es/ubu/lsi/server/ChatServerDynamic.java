package es.ubu.lsi.server;

import java.rmi.server.RMIClassLoader;
import java.util.Properties;

/**
 * Dynamic server.
 * 
 * author Raúl Marticorena
 * author Joaquin P. Seco
 */
public class ChatServerDynamic {
    /**
     * Método raíz.
     * 
     * @param args argumentos
     */
    public static void main(String args[]) {

        try {
            Properties p = System.getProperties();
            // lee el codebase
            String url = p.getProperty("java.rmi.server.codebase");
            // cargador de clases
            Class<?> serverClass = RMIClassLoader.loadClass(url, "es.ubu.lsi.server.ChatServerStarter");
            // inicia el cliente
            serverClass.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            System.err.println("Excepción en arranque del servidor " + e.toString());
        }
    }
}