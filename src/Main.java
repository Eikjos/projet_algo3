import graph.exceptions.DuplicateVertex;
import graph.exceptions.VertexNotFound;
import social.SocialNetwork;
import social.accounts.Page;
import social.accounts.User;
import java.io.File;
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
        SocialNetwork social = new SocialNetwork("test");
        try {
            social.createUser("Biojout", "Kévin", 20);
            social.createUser("Hamelin", "Thomas", 20);
            social.createUser("Ficker", "Lucas", 20);
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
            social.addAdmin(p, u);
            System.out.println(social.getAdmins());
            System.out.println(social.getAdminsOf(p));
            System.out.println(social.getFollow(x));
            System.out.println(social.getFollower(v));
            System.out.println(social.getPageOfAdmin(u));
            social.save();
            System.out.println("Init");
            SocialNetwork social2 = SocialNetwork.init(new File(social.getName() + ".txt"));
            System.out.println(social.getAdmins());
            System.out.println(social.getAdminsOf(p));
            System.out.println(social.getFollow(x));
            System.out.println(social.getFollower(v));
            System.out.println(social.getPageOfAdmin(u));
        } catch (DuplicateVertex e) {
            throw new AssertionError("duplication de sommet");
        } catch (VertexNotFound e) {
            throw new AssertionError("sommet introuvables");
        } catch (IOException e) {
            throw new AssertionError("problème d'entrée sortie");
        } catch (Exception e) {
            throw new AssertionError("problème des testes");
        }

    }
}
