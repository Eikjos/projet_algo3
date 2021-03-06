package social;

import graph.StdGraph;
import graph.Graph;
import graph.Vertex;
import graph.exceptions.ArcNotFound;
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
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Set;
import java.util.TreeSet;

/**
 * Représente le système de réseau social avec un graphe.
 */
public class SocialNetwork extends Observable {

    // ATTRIBUT

    /**
     * Représente le graphe associé à ce réseau social.
     */
    private final Graph graph;

    /**
     * Le nom de ce réseau social.
     */
    private final String name;

    // CONSTRUCTEUR

    /**
     * Crée un nouveau réseau social portant le nom dénoté par name.
     * @pre
     *      name != null
     * @post
     *      getGraph() != null
     *      getName() != null
     *      for 0 <= i < name.length():
     *          name.charAt(i) == this.name.charAt(i)
     */
    public SocialNetwork(String name) {
        Assert.check(name != null, "name is null");
        this.graph = new StdGraph();
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
        for (Vertex s : graph.vertexSet()) {
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
        for (Vertex s : graph.vertexSet()) {
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
     * Calcule l'âge moyen des utilisateurs de ce réseau social. Il est supposé
     * que le réseau compte au moins un utilisateur.
     * @return L'âge moyen des utilisateurs de ce réseau social arrondi à
     * l'unité la plus proche.
     */
    public int getAverageAge() {
        Set<User> users = getUsers();
        Assert.check(users.size() > 0, "No users are registered.");
        int sum = 0;
        for (User u : users) {
            sum += u.getAge();
        }
        return (int) Math.round(sum / (double) users.size());
    }

    /**
     * @return Un ensemble ordonnée des administrateurs de pages sur ce réseau
     * social.
     */
    public Set<User> getAdmins() {
        Set<User> result = new TreeSet<User>();
        for (User u : getUsers()) {
            for (Vertex v : graph.vertexTo(u)) {
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
        for (Vertex v : graph.vertexFrom(p)) {
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
        for (Vertex v : graph.vertexTo(p)) {
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
        for (Vertex v : graph.vertexTo(u)) {
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
        for (Vertex v : graph.vertexFrom(u)) {
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
        for (Vertex v : graph.vertexFrom(u)) {
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
        for (Vertex v : graph.vertexTo(u)) {
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
        return graph.findVertexByName(n);
    }


    // COMMANDES

    /**
     * Créer un utilisateur dans ce réseau social, s'il n'y ait pas inscrit.
     */
    public void createUser(String lastname, String firstname, int age)
            throws DuplicateVertex {
        User u = new User(lastname, firstname, age);
        graph.addVertex(u);
    }

    /**
     * Créer une page pour ce réseau socail, si elle n'existe pas.
     */
    public void createPage(String name) throws DuplicateVertex {
        Page p = new Page(name);
        graph.addVertex(p);
    }

    /**
     * Supprime l'utilisateur reconnu par son nom (name).
     * @pre
     *      name != null
     * @post
     *      !getUsers().contains(getVertexByName(name))
     */
    public void removeUser(String name) throws VertexNotFound {
        Assert.check(name != null,
                "name is null");
        graph.removeVertex(getVertexByName(name));
    }

    /**
     * Supprime la page de nom name du réseau social.
     * @pre
     *      name != null
     * @post
     *      !getPages().contains(getVertexByName(name))
     */
    public void removePage(String name) throws VertexNotFound {
        Assert.check(name != null,
                "name is null");
        graph.removeVertex(getVertexByName(name));
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
        graph.createArc(u, p);
    }

    /**
     * Supprime le like de l'utilisateur u sur la page p.
     */
    public void removeLike(User u, Page p) throws ArcNotFound, VertexNotFound {
        Assert.check(u != null, "u is null");
        Assert.check(p != null, "p is null");
        graph.deleteArc(u, p);
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
        graph.createArc(p, u);
    }

    /**
     * Supprime l'utilisateur u comme admin de la page p.
     * @pre
     *      p != null
     *      u != null
     */
    public void removeAdmin(Page p, User u) throws ArcNotFound, VertexNotFound {
        Assert.check(u != null, "u is null");
        Assert.check(p != null, "p is null");
        graph.deleteArc(p, u);
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
        graph.createArc(u, v);
    }

    /**
     * Retire le follow de l'utilisateur u vers l'utilisateur p.
     * @pre
     *      u != null
     *      p != null
     */
    public void removeFollow(User u, User v)
            throws ArcNotFound, VertexNotFound {
        Assert.check(u != null, "u is null");
        Assert.check(v != null, "v is null");
        graph.deleteArc(u, v);
    }

    /**
     * Renvoie les degrés de connaissances pour chaque compte du réseau social
     * à partir du compte dénoté par x.
     * @param x Le compte à partir duquel calculer les degrés.
     * @return Une map liant le degré de connaissance pour chaque sommet, un
     * degré égal à Integer.MAX_VALUE est considéré comme "infini" et donc sans
     * aucun lien.
     */
    public Map<Vertex, Integer> degreeKnowledge(Vertex x) {
        return graph.shortestPathsFrom(x);
    }

    /**
     * Permet de sauvegarder l'état du réseau social dans un fichier.
     * dans un fichier getName().txt
     */
    public void save() throws IOException {
        String filename = getName() + ".txt";
        BufferedWriter output = new BufferedWriter(new FileWriter(filename));
        for (Vertex v : graph.vertexSet()) {
            output.write(v.serialize());
            output.newLine();
        }
        for (Vertex v : graph.vertexSet()) {
            Set<Vertex> out = graph.vertexFrom(v);
            if (out.size() > 0) {
                StringBuilder line = new StringBuilder("A:" + v.getName());
                for (Vertex u : graph.vertexFrom(v)) {
                    line.append(":").append(u.getName());
                }
                output.write(line.toString());
                output.newLine();
            }
        }
        output.close();
    }

    /**
     * Permet d'initialiser le social à partir d'un fichier texte.
     * Le nom du réseau social correspond au nom du fichier chargé sans son
     * extension.
     */
    public static SocialNetwork init(File f)
            throws IOException, VertexNotFound, DuplicateArc, DuplicateVertex {
        BufferedReader input = new BufferedReader(new FileReader(f));
        String name = f.getName().lastIndexOf('.') > -1
                ? f.getName().substring(0, f.getName().lastIndexOf('.'))
                : f.getName();
        SocialNetwork social = new SocialNetwork(name);
        String line;
        while ((line = input.readLine()) != null) {
            String[] comp = line.split(":");
            switch (comp[0].charAt(0)) {
                case 'U':
                    social.createUser(comp[1], comp[2],
                            Integer.parseInt(comp[3]));
                    break;
                case 'P':
                    social.createPage(comp[1]);
                    break;
                case 'A':
                    Vertex source = social.getVertexByName(comp[1]);
                    for (int i = 2; i < comp.length; ++i) {
                        Vertex dest = social.getVertexByName(comp[i]);
                        if (source instanceof User) {
                            if (dest instanceof User) {
                                social.follow((User) source, (User) dest);
                            } else {
                                social.like((User) source, (Page) dest);
                            }
                        } else {
                            social.addAdmin((Page) source, (User) dest);
                        }
                    }
                    break;
                default:
                    throw new AssertionError("Unrecognized: \"" + line + "\"");
            }
        }
        input.close();
        return social;
    }

    /**
     * Exécute l'algorithme de PageRank sur les comptes du réseau social.
     * @pre
     *     graphe.vertexCount() > 0
     * @return L'ensemble des sommets triés par leur influence décroissante.
     */
    public Set<Vertex> pageRank() {
        if (graph.vertexCount() == 0) {
            return new HashSet<Vertex>(0);
        }
        HashMap<Vertex, Double> pr = new HashMap<Vertex, Double>();
        // INITIALISATION DU PAGERANK POUR TOUT LES SOMMETS
        for (Vertex v : graph.vertexSet()) {
            pr.put(v, 1.0);
        }
        // CALCUL DU PAGERANK
        int i = 0;
        final int valeur = 100;
        final double f1 = 0.15;
        final double f2 = 0.85;
        while (i <= valeur) {
            for (Vertex v : graph.vertexSet()) {
                double value;
                // SOMME DES VOISINS ENTRANTS DE V.
                double sum = 0.0;
                for (Vertex e : graph.vertexTo(v)) {
                    sum = sum + (pr.get(e) / graph.vertexFrom(e).size());
                }
                value = (f1 / graph.vertexCount()) + f2 * sum;
                pr.put(v, value);
                ++i;
            }
        }
        // Tri de la map selon le valeur du pagerank
        return sortedByValue(pr).keySet();
    }

    // OUTILS
    private HashMap<Vertex, Double> sortedByValue(HashMap<Vertex, Double> map) {
        List<Map.Entry<Vertex, Double>> list =
                new LinkedList<Map.Entry<Vertex, Double>>(map.entrySet());
        Collections.sort(list, new Comparator<Map.Entry<Vertex, Double>>() {
            @Override
            public int compare(Map.Entry<Vertex, Double> o1,
                               Map.Entry<Vertex, Double> o2) {
                return (o2.getValue().compareTo(o1.getValue()));
            }
        });
        HashMap<Vertex, Double> sortedMap = new LinkedHashMap<Vertex, Double>();
        for (Map.Entry<Vertex, Double> entry : list) {
            sortedMap.put(entry.getKey(), entry.getValue());
        }
        return sortedMap;
    }
}
