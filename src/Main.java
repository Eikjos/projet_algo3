import java.io.File;

import gui.GUI;
import social.SocialNetwork;

import javax.swing.SwingUtilities;

/**
 * Programme simulant un réseau social miniature à l'aide de graphes.
 * Initialise un nouveau réseau social, ou si un chemin de fichier est passé sur
 * la ligne de commande alors le programme tente de lire ce dernier.
 */
public final class Main {

    //- CONSTANTES DE TEXTE

    private static final String NETWORK_UNAMED = "Sans nom";

    //- CONSTRUCTEURS

    private Main() {
    }

    //- POINT D'ENTRÉE

    /**
     * Démarre l'application de visualisation d'un réseau social représenté à
     * l'aide de graphes.
     * @param args Arguments de la ligne de commande.
     */
    public static void main(String[] args) {
        SocialNetwork social = null;

        try {
            social = args.length > 1
                    ? SocialNetwork.init(new File(args[1]))
                    : new SocialNetwork(NETWORK_UNAMED);
        } catch (Exception ex) {
            System.err.println("An error occurred while initiating network!");
            ex.printStackTrace();
            System.exit(1);
        }

        final SocialNetwork finalSocial = social;
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new GUI(finalSocial).display();
            }
        });
    }
}
