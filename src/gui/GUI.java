package gui;

import graph.Vertex;
import graph.exceptions.DuplicateArc;
import graph.exceptions.DuplicateVertex;
import graph.exceptions.VertexNotFound;
import social.*;
import social.accounts.Page;
import social.accounts.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

/**
 * À décrire.
 */
public class GUI {

    private JFrame mainFrame;
    private SocialNetwork model;

    private JTextField firstNameUser;
    private JTextField lastNameUser;
    private JTextField age;
    private JButton addUser;

    private JTextField delName;
    private JButton delUser;

    private JTextField pageName;
    private JButton addPage;

    private JTextField delpageName;
    private JButton delPage;

    private JLabel users;
    private JLabel pages;
    private JLabel usersStat;
    private JLabel pagesStat;

    private JLabel pagerank;

    private JTextField searchName;
    private JButton search;

    private JButton relation;

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
        final int frameWidth = 1750;
        final int frameHeight = 1000;

        mainFrame = new JFrame(names);
        mainFrame.setPreferredSize(new Dimension(frameWidth, frameHeight));

        firstNameUser = new JTextField("First Name");
        lastNameUser = new JTextField("Last Name");
        age = new JTextField("Age");
        addUser = new JButton("Add User");

        delName = new JTextField("Name");
        delName.setColumns(10);
        delUser = new JButton("Delete User");

        pageName = new JTextField("Page Name");
        pageName.setColumns(10);
        addPage = new JButton("Add Page");

        delpageName = new JTextField("Page Name");
        delpageName.setColumns(10);
        delPage = new JButton("Delete Page");

        users = new JLabel(usersAsString());
        pages = new JLabel(pagesAsString());
        usersStat = new JLabel(usersStat());
        pagesStat = new JLabel(pagesStat());

        pagerank = new JLabel(pageRankAsString());

        searchName = new JTextField("Name");
        searchName.setColumns(10);
        search = new JButton("Search");

        relation = new JButton("Add / Remove Relation");

        save = new JButton("Save");
        loadSave = new JButton("Load from file");

    }

    private void placeComponents() {
        JPanel q = new JPanel(new GridLayout(4, 5)); {
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
                    r.add(delName);
                    r.add(delUser);
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
                    r.add(delpageName);
                    r.add(delPage);
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
            q.add(new JPanel());
            q.add(new JPanel());
            p = new JPanel(); {
                p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
                p.add(new JPanel());
                JPanel r = new JPanel(); {
                    r.add(relation);
                }
                p.add(r);
                p.add(new JPanel());
            }
            q.add(p);
            q.add(new JPanel());
            q.add(new JPanel());
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
                    model.createUser(firstNameUser.getText(), lastNameUser.getText(), Integer.parseInt(age.getText()));
                } catch (DuplicateVertex ex) {
                    JOptionPane.showMessageDialog(mainFrame, "This user already exist");
                }
                refresh();
            }
        });

        delUser.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    model.removeUser(delName.getText());
                } catch (VertexNotFound vertexNotFound) {
                    vertexNotFound.printStackTrace();
                }
                refresh();
            }
        });

        addPage.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    model.createPage(pageName.getText());
                } catch (DuplicateVertex ex) {
                    JOptionPane.showMessageDialog(mainFrame, "This user already exist");
                }
                refresh();
            }
        });

        delPage.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    model.removePage(delpageName.getText());
                } catch (VertexNotFound vertexNotFound) {
                    vertexNotFound.printStackTrace();
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

        relation.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                RelationWindow w = new RelationWindow(model);
                w.display();
            }
        });
    }

    private void refresh() {
        Container contentPane = mainFrame.getContentPane();
        pages.setText(pagesAsString());
        pagesStat.setText(pagesStat());
        users.setText(usersAsString());
        usersStat.setText(usersStat());
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new GUI(names).display();
            }
        });
    }

    private String usersAsString() {
        StringBuilder sb = new StringBuilder();
        sb.append("<html> Users : ").append(" <br>");
        for (User u : model.getUsers()) {
            sb.append(u.toString()).append(" <br>");
        }
        sb.append("</html>");
        return sb.toString();
    }

    private String usersStat() {
        String sb;
        if (model.getUserCount() == 0) {
            sb = "<html> <center><b>UsersStat :</b> </center> <br> Users number = " + model.getUserCount()
                    + "<br> Average Age = <html>";
        } else {
            sb = "<html>Users Stat : <br> Users number = " + model.getUserCount()
                    + "<br> Average Age = " + model.getAverageAge() + "<html>";
        }
        return sb;
    }

    private int likers() {
        int a = 0;
        for (Page u : model.getPages()) {
            a += model.getLikers(u).size();
        }
        return a;
    }

    private String pagesStat() {
        String sb = "<html>Pages Stat : <br> Pages number = " + model.getPageCount()
                + "<br> Admins Numbers = " + model.getAdmins().size()
                + "<br> Users who like a page = " + likers() + "<html>";
        return sb;
    }

    private String pagesAsString() {
        StringBuilder sb = new StringBuilder();
        sb.append("<html> Pages :").append(" <br>");
        for (Page u : model.getPages()) {
            sb.append(u.toString()).append(" <br>");
        }
        sb.append("</html>");
        return sb.toString();
    }

    private String pageRankAsString() {
        StringBuilder sb = new StringBuilder();
        sb.append("<html> Pagerank : ").append(" <br>");
        int i = 1;
        for (Vertex u : model.pageRank()) {
            sb.append(i).append(":");
            if (u instanceof User) {
                sb.append(((User) u).toString()).append(" <br>");
            } else {
                sb.append(((Page) u).toString()).append(" <br>");
            }
            i++;
        }
        sb.append("</html>");
        return sb.toString();
    }
}
