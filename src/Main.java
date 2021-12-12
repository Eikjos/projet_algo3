import graph.exceptions.DuplicateVertex;
import graph.exceptions.VertexNotFound;
import social.SocialNetwork;
import social.accounts.Page;
import social.accounts.User;
import gui.GUI;

import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public final class Main {
    private Main() {
    }

    /**
     * Démarre l'application de visualisation d'un réseau social représenté à
     * l'aide de graphes.
     * @param args Arguments de la ligne de commande.
     */
    public static void main(String[] args) {
        // TEST
        final SocialNetwork social = new SocialNetwork("test");
        try {
            social.createUser("Biojout", "Kévin", 20);
            social.createUser("Hamelin", "Thomas", 20);
            social.createUser("Ficker", "Lucas", 20);
            social.createUser("Fouquer", "Mattéo", 20);
            social.createUser("Lozach", "Lucas", 21);
            social.createUser("Merieau", "Lucas", 20);
            social.createUser("Le Bas", "Nathan", 19);
            social.createUser("Briet", "Romain", 19);
            social.createUser("La meuf de", "Romain", 18);
            social.createPage("La page de CH");
            social.createPage("La page du salami");
            User u = (User) social.getVertexByName("Biojout Kévin");
            User v = (User) social.getVertexByName("Hamelin Thomas");
            User x = (User) social.getVertexByName("Ficker Lucas");
            Page p = (Page) social.getVertexByName("La page de CH");
            Page q = (Page) social.getVertexByName("La page du salami");
            social.like(u, p);
            social.like(v, q);
            social.follow(x, u);
            social.follow(x, v);
            System.out.println(social.getAdmins());
            System.out.println(social.getAdminsOf(p));
            System.out.println(social.getFollow(x));
            System.out.println(social.getFollowers(v));
            System.out.println(social.getPagesOfAdmin(u));
            social.save();
            System.out.println("Init");
        } catch (DuplicateVertex e) {
            throw new AssertionError("duplication de sommet");
        } catch (VertexNotFound e) {
            throw new AssertionError("sommet introuvables");
        } catch (IOException e) {
            throw new AssertionError("problème d'entrée sortie");
        } catch (Exception e) {
            System.out.println(e);
            throw new AssertionError("problème des testes");
        }
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new GUI(social.getName(), social).display();
            }
        });
    }
}
