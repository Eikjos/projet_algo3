package social;

import graph.StdGraph;
import graph.Graph;
import graph.Vertex;
import graph.exceptions.VertexNotFound;
import graph.exceptions.DuplicateArc;
import graph.exceptions.DuplicateVertex;
import social.accounts.Page;
import social.accounts.User;
import util.Assert;

import java.io.*;
import java.util.Set;
import java.util.TreeSet;

/**
 * Représente le système de réseau social avec un graphe.
 */
public class SocialNetwork {

    // ATTRIBUT

    /**
     * Représente le graphe associé à ce réseau social.
     */
    private final Graph graphe;

    /**
     * Le nom de ce réseau social.
     */
    private final String name;

    // CONSTRUCTEUR

    /**
     * Créer un réseau social avec un graphe et de nom name.
     * @pre
     *      name != null
     * @post
     *      getGraph() != null
     */
    public SocialNetwork(String name) {
        Assert.check(name != null, "name is null");
        graphe = new StdGraph();
        this.name = name;
    }

    // REQUETES

    /**
     * Le nom de ce réseau social.
     */
    public String getName() {
        return name;
    }

    /**
     * L'ensemble des utilisateurs du réseau social.
     */
    public Set<User> getUser() {
        Set<User> result = new TreeSet<User>();
        for (Vertex s : graphe.vertexSet()) {
            if (s instanceof User) {
                result.add((User) s);
            }
        }
        return result;
    }

    /**
     * Le nombre d'utilisateur du réseau social.
     */
    public int getUserNb() {
        return getUser().size();
    }

    /**
     * L'ensemble des pages sur le réseau social.
     */
    public Set<Page> getPage() {
        Set<Page> result = new TreeSet<Page>();
        for (Vertex s : graphe.vertexSet()) {
            if (s instanceof Page) {
                result.add((Page) s);
            }
        }
        return result;
    }

    /**
     * Le nombre de Page dans le réseau social.
     */
    public int getPageNb() {
        return getPage().size();
    }

    /**
     * L'âge moyen des utilisateurs du réseau social.
     */
    public int getAverageAge() {
        int sum = 0;
        for (User u : getUser()) {
            sum = sum + u.getAge();
        }
        return sum / getUserNb();
    }

    /**
     * L'ensemble des utilisateurs qui sont administrateurs de page.
     */
    public Set<User> getAdmins() {
        Set<User> result = new TreeSet<User>();
        for (User u : getUser()) {
            for (Vertex v : graphe.vertexTo(u)) {
                if (v instanceof Page) {
                    result.add(u);
                }
            }
        }
        return result;
    }

    /**
     * L'ensemble des utilisateurs qui sont administrateurs de la page p.
     * @pre
     *      p != null
     */
    public Set<User> getAdminsOf(Page p) {
        Assert.check(p != null, "p is null");
        Set<User> result = new TreeSet<User>();
        for (Vertex v : graphe.vertexFrom(p)) {
            if (v instanceof User) {
                result.add((User) v);
            }
        }
        return result;
    }

    /**
     * L'ensemble des utilisateurs qui aiment la page p.
     * @pre
     *      p != null
     */
    public Set<User> getLiker(Page p) {
        Assert.check(p != null, "p is null");
        Set<User> result = new TreeSet<User>();
        for (Vertex v : graphe.vertexTo(p)) {
            if (v instanceof User) {
                result.add((User) v);
            }
        }
        return result;
    }

    /**
     * L'ensemble des followers de l'utilisateurs de u.
     * @pre
     *      u != null
     */
    public Set<User> getFollower(User u) {
        Assert.check(u != null, "u is null");
        Set<User> result = new TreeSet<User>();
        for (Vertex v : graphe.vertexTo(u)) {
            if (v instanceof User) {
                result.add((User) v);
            }
        }
        return result;
    }

    /**
     * L'ensemble des utilisateurs suivis par l'utilisateurs u.
     * @pre
     *      u != null
     */
    public Set<User> getFollow(User u) {
        Assert.check(u != null, "u is null");
        Set<User> result = new TreeSet<User>();
        for (Vertex v : graphe.vertexFrom(u)) {
            if (v instanceof User) {
                result.add((User) v);
            }
        }
        return result;
    }

    /**
     * L'ensemble des pages liker par l'utilisateur u.
     * @pre
     *      u != null
     */
    public Set<Page> getLike(User u) {
        Assert.check(u != null, "u is null");
        Set<Page> result = new TreeSet<Page>();
        for (Vertex v : graphe.vertexFrom(u)) {
            if (v instanceof Page) {
                result.add((Page) v);
            }
        }
        return result;
    }

    /**
     * L'ensemble des pages où l'utilisateur u est admins.
     * @pre
     *      u != null
     */
    public Set<Page> getPageOfAdmin(User u) {
        Assert.check(u != null, "u is null");
        Set<Page> result = new TreeSet<Page>();
        for (Vertex v : graphe.vertexTo(u)) {
            if (v instanceof Page) {
                result.add((Page) v);
            }
        }
        return result;
    }

    /**
     * Permet de repérer un élément du réseau social par son nom.
     */
    public Vertex getVertexByName(String n) {
        return graphe.findVertexByName(n);
    }


    // COMMANDES

    /**
     * Créer un utilisateur dans ce réseau social, s'il n'y ait pas inscrit.
     */
    public void createUser(String lastname, String firstname, int age)
            throws DuplicateVertex {
        User u = new User(lastname, firstname, age);
        graphe.addVertex(u);
    }

    /**
     * Créer une page pour ce réseau socail, si elle n'existe pas.
     */
    public void createPage(String name) throws DuplicateVertex {
        Page p = new Page(name);
        graphe.addVertex(p);
    }

    /**
     *  L'utilisateur u aime la page p.
     * @pre
     *      u != null
     *      p != null
     * @post
     *      getLiker(p).contains(u)
     */
    public void like(User u, Page p) throws DuplicateArc, VertexNotFound {
        Assert.check(u != null, "u is null");
        Assert.check(p != null, "p is null");
        graphe.createArc(u, p);
    }

    /**
     * Définit l'utilisateur u comme un des administrateurs de la page p.
     * @pre
     *      u != null
     *      p != null
     * @post
     *      getAdminsOf(p).contains(u)
     *      getAdmins().contains(u)
     */
    public void addAdmin(Page p, User u) throws VertexNotFound, DuplicateArc {
        Assert.check(u != null, "u is null");
        Assert.check(p != null, "p is null");
        graphe.createArc(p, u);
    }

    /**
     * L'utilisateur u suit l'utilisateur v.
     * @pre
     *      u != null
     *      v != null
     * @post
     *      getFollower(v).contains(u)
     *      getFollow(u).contains(v)
     */
    public void follow(User u, User v) throws VertexNotFound, DuplicateArc {
        Assert.check(u != null, "u is null");
        Assert.check(v != null, "v is null");
        graphe.createArc(u, v);
    }

    /**
     * Permet de sauvegarder l'état du réseau social dans un fichier.
     * dans un fichier getName().txt
     */
    public void save() throws IOException {
        BufferedWriter output = new BufferedWriter(new FileWriter(getName() + ".txt"));
        // sauvegarde tout les sommets du graphe.
        for (Vertex v : graphe.vertexSet()) {
            String line = v.serialize();
            output.write(line);
            output.newLine();
        }
        // sauvegarde les liens sous forme de liste d'adjacence
        for (Vertex v : graphe.vertexSet()) {
            Set<Vertex> out = graphe.vertexFrom(v);
            if (out.size() > 0) {
                String line = "A:" + v.getName();
                for (Vertex u : graphe.vertexFrom(v)) {
                    line = line.concat(":" + u.getName());
                }
                output.write(line);
                output.newLine();
            }
        }
        output.close();
    }

    /**
     * Permet d'initialiser le social à partir d'une sauvegarde à partie d'un fichier.
     * Le nom du réseau social est le nom du fichier
     */
    public static SocialNetwork init(File f)
            throws IOException, VertexNotFound, DuplicateArc, DuplicateVertex {
        BufferedReader input = new BufferedReader(new FileReader(f));
        SocialNetwork social = new SocialNetwork(f.getName());
        String line;
        while ((line = input.readLine()) != null) {
            if (line.charAt(0) == 'U') {
                String[] splitted = line.split(":");
                if (splitted.length != 4) {
                    throw new AssertionError("ligne non reconnu");
                }
                social.createUser(splitted[1], splitted[2], Integer.parseInt(splitted[3]));
            } else if (line.charAt(0) == 'P') {
                String[] splitted = line.split(":");
                if (splitted.length != 2) {
                    throw new AssertionError("ligne non reconnu");
                }
                social.createPage(splitted[1]);
            } else if (line.charAt(0) == 'A') {
                String[] splitted = line.split(":");
                Vertex u = social.graphe.findVertexByName(splitted[1]);
                for (int i = 2; i < splitted.length; ++i) {
                    Vertex v = social.graphe.findVertexByName(splitted[i]);
                    if (u instanceof User) {
                        if (v instanceof User)  {
                            social.follow((User) u, (User) v);
                        } else if (v instanceof Page) {
                            social.like((User) u, (Page) v);
                        }
                    } else if (u instanceof Page) {
                        if (v instanceof User) {
                            social.addAdmin((Page) u, (User) v);
                        } else if (v instanceof Page) {
                            throw new AssertionError("Impossible lien entre 2 pages");
                        }
                    }
                }
            }
        }
        input.close();
        return social;
    }
}
