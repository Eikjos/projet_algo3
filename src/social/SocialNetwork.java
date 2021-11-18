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

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

/**
 * Représente le système de réseau social avec un graphe.
 */
public class SocialNetwork extends Observable {

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
     * @return Le nom de ce réseau social.
     */
    public String getName() {
        return name;
    }

    /**
     * @return Un ensemble ordonné des utilisateurs de ce réseau social.
     */
    public Set<User> getUsers() {
        Set<User> result = new TreeSet<User>();
        for (Vertex s : graphe.vertexSet()) {
            if (s instanceof User) {
                result.add((User) s);
            }
        }
        return result;
    }

    /**
     * @return Le nombre d'utilisateurs de ce réseau social.
     */
    public int getUserCount() {
        return getUsers().size();
    }

    /**
     * @return Un ensemble ordonné des pages de ce réseau social.
     */
    public Set<Page> getPages() {
        Set<Page> result = new TreeSet<Page>();
        for (Vertex s : graphe.vertexSet()) {
            if (s instanceof Page) {
                result.add((Page) s);
            }
        }
        return result;
    }

    /**
     * @return Le nombre de pages de ce réseau social.
     */
    public int getPageCount() {
        return getPages().size();
    }

    /**
     * @pre
     *      getUserCount() > 0
     * @return L'âge moyen (entier) des utilisateurs de ce réseau social.
     */
    public int getAverageAge() {
        Assert.check(getUserCount() > 0,
                "impossible pas d'utilisateur");
        int sum = 0;
        for (User u : getUsers()) {
            sum = sum + u.getAge();
        }
        return sum / getUserCount();
    }

    /**
     * @return Un ensemble ordonnées des administrateurs de pages sur ce réseau
     * social.
     */
    public Set<User> getAdmins() {
        Set<User> result = new TreeSet<User>();
        for (User u : getUsers()) {
            for (Vertex v : graphe.vertexTo(u)) {
                if (v instanceof Page) {
                    result.add(u);
                }
            }
        }
        return result;
    }

    /**
     * @return Un ensemble ordonné des administrateurs de la page dénotée par
     * p.
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
     * @return Un ensemble ordonné des utilisateurs aimant la page dénotée par
     * p.
     * @pre
     *      p != null
     */
    public Set<User> getLikers(Page p) {
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
     * @return Un ensemble ordonné des utilisateurs suivant l'utilisateur
     * dénoté par u.
     * @pre
     *      u != null
     */
    public Set<User> getFollowers(User u) {
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
     * @return Un ensemble ordonné des utilisateurs suivis par l'utilisateur
     * dénoté par u.
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
     * @return Un ensemble ordonné des pages aimées par l'utilisateur dénoté
     * par u.
     * @pre
     *      u != null
     */
    public Set<Page> getLikes(User u) {
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
     * @return Un ensemble ordonné des pages où l'utilisateur dénoté par u est
     * administrateur.
     * @pre
     *      u != null
     */
    public Set<Page> getPagesOfAdmin(User u) {
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
     * Recherche dans le graphe un sommet ayant pour clé (nom pour une page,
     * "nom prénom" pour un utilisateur) la chaine dénotée par n.
     * @return Le sommet ayant pour clé la chaine dénoté par n, null sinon.
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

    /**
     * Donne un ensemble trier selon leurs pagerank.
     * @pre
     *     graphe.vertexSet().size() > 0
     * @return le sommet le plus influent.
     */
    public Set<Vertex> Pagerank() {
        Assert.check(graphe.vertexCount() > 0,
                "impossible le graphe est vide");
        HashMap<Vertex, Double> PR = new HashMap<Vertex, Double>();
        // INITIALISATION DU PAGERANK POUR TOUT LES SOMMETS
        for (Vertex v : graphe.vertexSet()) {
            PR.put(v, 1.0);
        }
        // CALCUL DU PAGERANK
        int i = 0;
        final int valeur = 100;
        final double f1 = 0.15;
        final double f2 = 0.85;
        while (i <= valeur) {
            for (Vertex v : graphe.vertexSet()) {
                Double value = PR.get(v);
                // SOMME DES VOISINS ENTRANTS DE V.
                double sum = 0.0;
                for (Vertex e : graphe.vertexTo(v)) {
                    sum = sum + (PR.get(e) / graphe.vertexFrom(e).size());
                }
                value = (f1 / graphe.vertexCount()) + f2 * sum;
                PR.put(v, value);
                ++i;
            }
        }
        // Tri de la map selon le valeur du pagerank
        return sortedByValue(PR).keySet();
    }

    // OUTILS
    private HashMap<Vertex, Double> sortedByValue(HashMap<Vertex, Double> map) {
        List<Map.Entry<Vertex, Double>> list =
                new LinkedList<Map.Entry<Vertex, Double>>(map.entrySet());
        Collections.sort(list, new Comparator<Map.Entry<Vertex, Double>>() {
            @Override
            public int compare(Map.Entry<Vertex, Double> o1, Map.Entry<Vertex, Double> o2) {
                return (o2.getValue().compareTo(o1.getValue()));
            }
        });
        HashMap<Vertex, Double> sorted_map = new LinkedHashMap<Vertex, Double>();
        for (Map.Entry<Vertex, Double> entry : list) {
            sorted_map.put(entry.getKey(), entry.getValue());
        }
        return sorted_map;
    }
}
