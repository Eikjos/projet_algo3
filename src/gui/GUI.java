package gui;

import graph.Vertex;
import graph.exceptions.DuplicateArc;
import graph.exceptions.DuplicateVertex;
import graph.exceptions.VertexNotFound;
import social.SocialNetwork;
import social.accounts.Page;
import social.accounts.User;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Observable;
import java.util.Observer;
import java.util.Set;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * Classe principale de l'interface utilisateur mise à disposition dans le
 * cadre du réseau social miniature implémenté à l'aide d'un graphe.
 */
public class GUI {

    private JFrame mainFrame;
    private SocialNetwork model;

    private JTextField firstNameUser;
    private JTextField lastNameUser;
    private JTextField age;
    private JButton addUser;

    private JTextField pageName;
    private JButton addPage;

    private JLabel users;
    private JLabel pages;
    private JLabel usersStat;
    private JLabel pagesStat;

    private JLabel pagerank;

    private JTextField searchName;
    private JButton search;

    private JButton likeFollow;

    private JButton save;
    private JButton loadSave;


    private static String names;

    public GUI(String name) {
        names = name;
        createModel();
        createView();
        placeComponents();
        createController();
    }

    public GUI(String name, SocialNetwork models) {
        names = name;
        model = models;
        createView();
        placeComponents();
        createController();
    }

    public void display() {
        refresh();
        mainFrame.pack();
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setVisible(true);
    }

    private void createModel() {
        model = new SocialNetwork(names);
    }

    private void createView() {
        final int frameWidth = 1280;
        final int frameHeight = 720;

        mainFrame = new JFrame(names);
        mainFrame.setPreferredSize(new Dimension(frameWidth, frameHeight));

        firstNameUser = new JTextField("First name");
        lastNameUser = new JTextField("Last name");
        age = new JTextField("Age");
        addUser = new JButton("Add User");

        pageName = new JTextField("Page name");
        addPage = new JButton("Add Page");

        users = new JLabel(usersAsString());
        pages = new JLabel(pagesAsString());
        usersStat = new JLabel(userStats());
        pagesStat = new JLabel(pageStats());

        pagerank = new JLabel(pageRankAsString());

        searchName = new JTextField("Name");
        searchName.setColumns(10);
        search = new JButton("Search");

        likeFollow = new JButton("Like pages or follow other users");

        save = new JButton("Save");
        loadSave = new JButton("Load from file");

    }

    private void placeComponents() {
        JPanel q = new JPanel(new GridLayout(3, 5)); {
            JPanel p = new JPanel(new BorderLayout()); {
                p.add(users, BorderLayout.CENTER);
            }
            q.add(p);
            p = new JPanel(new BorderLayout()); {
                p.add(pages, BorderLayout.CENTER);
            }
            q.add(p);
            p = new JPanel(new BorderLayout()); {
                p.add(usersStat, BorderLayout.CENTER);
            }
            q.add(p);
            p = new JPanel(new BorderLayout()); {
                p.add(pagesStat, BorderLayout.CENTER);
            }
            q.add(p);
            p = new JPanel(new BorderLayout()); {
                p.add(pagerank, BorderLayout.CENTER);
            }
            q.add(p);
            q.add(new JPanel());
            p = new JPanel(); {
                p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
                p.add(new JPanel());
                JPanel r = new JPanel(); {
                    r.add(firstNameUser);
                    r.add(lastNameUser);
                    r.add(age);
                    r.add(addUser);
                }
                p.add(r);
                p.add(new JPanel());
            }
            q.add(p);
            p = new JPanel(); {
                p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
                p.add(new JPanel());
                JPanel r = new JPanel(); {
                    r.add(pageName);
                    r.add(addPage);
                }
                p.add(r);
                p.add(new JPanel());
            }
            q.add(p);
            p = new JPanel(); {
                p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
                p.add(new JPanel());
                JPanel r = new JPanel(); {
                    r.add(searchName);
                    r.add(search);
                }
                p.add(r);
                p.add(new JPanel());
            }
            q.add(p);
            p = new JPanel(); {
                p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
                p.add(new JPanel());
                JPanel r = new JPanel(); {
                    r.add(likeFollow);
                }
                p.add(r);
                p.add(new JPanel());
            }
            q.add(p);
            q.add(new JPanel());
            q.add(new JPanel());
            p = new JPanel(); {
                p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
                p.add(new JPanel());
                JPanel r = new JPanel(); {
                    r.add(save);
                    r.add(loadSave);
                }
                p.add(r);
                p.add(new JPanel());
            }
            q.add(p);
            q.add(new JPanel());
            q.add(new JPanel());
        }
        mainFrame.add(q);
    }

    private void createController() {
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        ((Observable) model).addObserver(new Observer() {
            @Override
            public void update(Observable o, Object arg) {
                refresh();
            }
        });

        addUser.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    model.createUser(firstNameUser.getText(),
                            lastNameUser.getText(),
                            Integer.parseInt(age.getText()));
                    usersStat.setText(userStats());
                } catch (DuplicateVertex ex) {
                    JOptionPane.showMessageDialog(mainFrame,
                            "Someone is already registered under this name.");
                }
                refresh();
            }
        });

        addPage.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    model.createPage(pageName.getText());
                    pagesStat.setText(pageStats());
                } catch (DuplicateVertex ex) {
                    JOptionPane.showMessageDialog(mainFrame,
                            "There is already a page with this name.");
                }
                refresh();
            }
        });

        search.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                VertexWindow w = new VertexWindow(searchName.getText(), model);
                w.display();
            }
        });

        save.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    model.save();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });

        loadSave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                File file = null;
                SocialNetwork news;
                final JFileChooser fc = new JFileChooser();
                int returnVal = fc.showOpenDialog(fc.getParent());
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    file = fc.getSelectedFile();
                }
                try {
                    news = SocialNetwork.init(file);
                    mainFrame.dispose();
                    GUI newg = new GUI(model.getName(), news);
                    newg.display();
                } catch (IOException ex) {
                    ex.printStackTrace();
                } catch (VertexNotFound ex) {
                    ex.printStackTrace();
                } catch (DuplicateArc ex) {
                    ex.printStackTrace();
                } catch (DuplicateVertex ex) {
                    ex.printStackTrace();
                }

            }
        });

        likeFollow.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                LikeFollowWindow w = new LikeFollowWindow(model);
                w.display();
            }
        });
    }

    private void refresh() {
        pages.setText(pagesAsString());
        pagesStat.setText(pageStats());
        users.setText(usersAsString());
        usersStat.setText(userStats());
        pagerank.setText(pageRankAsString());
    }

    //- OUTILS

    /**
     * @return Renvoie la liste des utilisateurs de ce réseau social.
     */
    private String usersAsString() {
        StringBuilder sb = new StringBuilder();
        sb.append("<html>Users :");
        for (User u : model.getUsers()) {
            sb.append("<br>").append(u);
        }
        sb.append("</html>");
        return sb.toString();
    }

    /**
     * @return Renvoie des statistiques sur les utilisateurs du réseau social en
     * général.
     */
    private String userStats() {
        int userCount = model.getUserCount();
        StringBuilder sb = new StringBuilder("<html>Statistics on users :<br>")
                .append("> Number of users: ").append(userCount);
        if (userCount > 0) {
            sb.append("<br>> Average age of users: ")
                    .append(model.getAverageAge());
        }
        return sb.append("</html>").toString();
    }

    /**
     * @return Renvoie le nombre d'utilisateurs uniques ayant aimé au moins une
     * page sur le réseau social.
     */
    private int likers() {
        Set<User> likers = new HashSet<User>();
        for (Page p : model.getPages()) {
            likers.addAll(model.getLikers(p));
        }
        return likers.size();
    }

    /**
     * @return Renvoie des statistiques sur les pages du réseau social en
     * général.
     */
    private String pageStats() {
        return "<html>Statistics on pages :<br>"
                + "> Number of pages: " + model.getPageCount() + "<br>"
                + "> Number of admins: " + model.getAdmins().size() + "<br>"
                + "> Users who like a page: " + likers()
                + "</html>";
    }

    /**
     * @return Renvoie la liste des pages créées sur le réseau social.
     */
    private String pagesAsString() {
        StringBuilder sb = new StringBuilder("<html>Pages :");
        for (Page p : model.getPages()) {
            sb.append("<br>").append(p);
        }
        return sb.append("</html>").toString();
    }

    /**
     * @return Renvoie la liste ordonnée des comptes les plus influents sur le
     * réseau social.
     */
    private String pageRankAsString() {
        StringBuilder sb = new StringBuilder("<html>PageRank :<br>");
        int i = 1;
        for (Vertex x : model.pageRank()) {
            sb.append('#').append(i).append(": ").append(x).append("<br>");
            ++i;
        }
        return sb.append("</html>").toString();
    }
}
