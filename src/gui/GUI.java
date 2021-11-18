package gui;

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

    private JTextField pageName;
    private JButton addPage;

    private JLabel users;
    private JLabel pages;
    private JLabel usersStat;
    private JLabel pagesStat;

    private JTextField searchName;
    private JButton search;

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
        final int frameWidth = 1500;
        final int frameHeight = 1000;

        mainFrame = new JFrame(names);
        mainFrame.setPreferredSize(new Dimension(frameWidth, frameHeight));

        firstNameUser = new JTextField("First Name");
        lastNameUser = new JTextField("Last Name");
        age = new JTextField("Age");
        addUser = new JButton("Add User");

        pageName = new JTextField("Page Name");
        addPage = new JButton("Add Page");

        users = new JLabel(usersAsString());
        pages = new JLabel(pagesAsString());
        usersStat = new JLabel(usersStat());
        pagesStat = new JLabel(pagesStat());

        searchName = new JTextField("Name");
        searchName.setColumns(10);
        search = new JButton("Search");

        save = new JButton("Save");
        loadSave = new JButton("Load from file");

    }

    private void placeComponents() {
        JPanel p = new JPanel(); {
            p.add(firstNameUser);
            p.add(lastNameUser);
            p.add(age);
            p.add(addUser);
            p.add(pageName);
            p.add(addPage);
        }
        JPanel j = new JPanel(); {
            j.add(users);
            j.add(pages);
            j.add(usersStat);
            j.add(pagesStat);
            j.add(searchName);
            j.add(search);
        }
        JPanel i = new JPanel(); {
            i.add(save);
            i.add(loadSave);
        }
        mainFrame.add(p, BorderLayout.NORTH);
        mainFrame.add(j, BorderLayout.CENTER);
        mainFrame.add(i, BorderLayout.SOUTH);
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
                    usersStat.setText(usersStat());
                } catch (DuplicateVertex ex) {
                    ex.printStackTrace();
                }
                users.setText(usersAsString());
            }
        });

        addPage.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    model.createPage(pageName.getText());
                    pagesStat.setText(pagesStat());
                } catch (DuplicateVertex ex) {
                    ex.printStackTrace();
                }
                pages.setText(pagesAsString());
            }
        });

        search.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                NewWindow w = new NewWindow(searchName.getText(), model);
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
    }

    private void refresh() {
        Container contentPane = mainFrame.getContentPane();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new GUI("facebook").display();
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
            sb = "<html>Users Stat : <br> Users number = " + model.getUserCount()
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
}
