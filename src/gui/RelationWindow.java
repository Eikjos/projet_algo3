package gui;

import graph.exceptions.ArcNotFound;
import graph.exceptions.DuplicateArc;
import graph.exceptions.VertexNotFound;
import social.SocialNetwork;
import social.accounts.Page;
import social.accounts.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

public class RelationWindow {
    private final JFrame frame;
    private final SocialNetwork model;
    private JTextField vertexToFollow;
    private JTextField username;
    private JButton likeFollow;
    private JButton unlikeFollow;
    private JButton addAdmin;
    private JButton removeAdmin;


    public RelationWindow(SocialNetwork models) {
        frame = new JFrame("Like / Follow Window");
        model = models;
        createView();
        createController();
    }

    public void display() {
        final int frameWidth = 700;
        final int frameHeight = 200;
        frame.setPreferredSize(new Dimension(frameWidth, frameHeight));
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private void refresh() {
        Container contentPane = frame.getContentPane();
    }

    private void createView() {
        JLabel title = new JLabel("Enter User that will like / follow / or become admin :");
        username = new JTextField();
        JLabel title2 = new JLabel("Enter Users or Pages that User entered above will like / follow or become admin :");
        vertexToFollow = new JTextField();
        likeFollow = new JButton("Like / Follow");
        unlikeFollow = new JButton("Unlike / Unfollow");
        addAdmin = new JButton("Add as admin");
        removeAdmin = new JButton("Remove from admin");
        JPanel p = new JPanel();
        {
            p.add(new JLabel("Like / Follow / Become admin"));
        }
        frame.add(p, BorderLayout.NORTH);
        p = new JPanel(new BorderLayout());
        {
            JPanel r = new JPanel(new FlowLayout(FlowLayout.CENTER));
            {
                r.add(title);
                username.setColumns(15);
                r.add(username);
            }
            p.add(r, BorderLayout.NORTH);
            r = new JPanel(new FlowLayout(FlowLayout.CENTER));
            {
                r.add(title2);
                vertexToFollow.setColumns(15);
                r.add(vertexToFollow);
            }
            p.add(r, BorderLayout.CENTER);
            r = new JPanel(new FlowLayout(FlowLayout.CENTER));
            {
                r.add(likeFollow);
                r.add(unlikeFollow);
                r.add(addAdmin);
                r.add(removeAdmin);
            }
            p.add(r, BorderLayout.SOUTH);
        }
        frame.add(p, BorderLayout.CENTER);
    }

    private void createController() {
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        ((Observable) model).addObserver(new Observer() {
            @Override
            public void update(Observable o, Object arg) {
                refresh();
            }
        });

        likeFollow.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                if (model.getVertexByName(username.getText()) instanceof User) {
                    User u = (User) model.getVertexByName(username.getText());
                    String[] names = vertexToFollow.getText().split("; ");
                    for (String s : names) {
                        if (model.getVertexByName(s) instanceof User) {
                            User v = (User) model.getVertexByName(s);
                            try {
                                model.follow(u, v);
                            } catch (VertexNotFound e) {
                                JOptionPane.showMessageDialog(frame, "This user doesn't exist");
                            } catch (DuplicateArc e) {
                                JOptionPane.showMessageDialog(frame, "This relation already exist");
                            }
                        } else if (model.getVertexByName(s) instanceof Page) {
                            Page v = (Page) model.getVertexByName(s);
                            try {
                                model.like(u, v);
                            } catch (DuplicateArc e) {
                                JOptionPane.showMessageDialog(frame, "This relation already exist");
                            } catch (VertexNotFound e) {
                                JOptionPane.showMessageDialog(frame, "This Page doesn't exist");
                            }
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(frame, "Page can't like, follow or behing admin");
                }
                frame.dispose();
            }
        });

        unlikeFollow.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                if (model.getVertexByName(username.getText()) instanceof User) {
                    User u = (User) model.getVertexByName(username.getText());
                    String[] names = vertexToFollow.getText().split("; ");
                    for (String s : names) {
                        if (model.getVertexByName(s) instanceof User) {
                            User v = (User) model.getVertexByName(s);
                            try {
                                model.removeFollow(u, v);
                            } catch (VertexNotFound e) {
                                JOptionPane.showMessageDialog(frame, "This Page doesn't exist");
                            } catch (ArcNotFound arcNotFound) {
                                JOptionPane.showMessageDialog(frame, "This relation doesn't exist");
                            }
                        } else if (model.getVertexByName(s) instanceof Page) {
                            Page v = (Page) model.getVertexByName(s);
                            try {
                                try {
                                    model.removeLike(u, v);
                                } catch (ArcNotFound arcNotFound) {
                                    JOptionPane.showMessageDialog(frame, "This relation doesn't exist");
                                }
                            } catch (VertexNotFound e) {
                                JOptionPane.showMessageDialog(frame, "This Page doesn't exist");
                            }
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(frame, "Page can't like, follow or behing admin");
                }
                frame.dispose();
            }
        });

        addAdmin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (model.getVertexByName(username.getText()) instanceof User) {
                    User u = (User) model.getVertexByName(username.getText());
                    String[] names = vertexToFollow.getText().split("; ");
                    for (String s : names) {
                        if (model.getVertexByName(s) instanceof Page) {
                            Page p = (Page) model.getVertexByName(s);
                            try {
                                model.addAdmin(p, u);
                            } catch (VertexNotFound ex) {
                                JOptionPane.showMessageDialog(frame, "This Page doesn't exist");
                            } catch (DuplicateArc ex) {
                                JOptionPane.showMessageDialog(frame, "This relation already exist");
                            }
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(frame, "Page can't like, follow or behing admin");
                }
                frame.dispose();
            }
        });

        removeAdmin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (model.getVertexByName(username.getText()) instanceof User) {
                    User u = (User) model.getVertexByName(username.getText());
                    String[] names = vertexToFollow.getText().split("; ");
                    for (String s : names) {
                        if (model.getVertexByName(s) instanceof Page) {
                            Page p = (Page) model.getVertexByName(s);
                            try {
                                model.removeAdmin(p, u);
                            } catch (VertexNotFound ex) {
                                JOptionPane.showMessageDialog(frame, "This Page doesn't exist");
                            } catch (ArcNotFound arcNotFound) {
                                JOptionPane.showMessageDialog(frame, "This relation doesn't exist");
                            }
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(frame, "Page can't like, follow or behing admin");
                }
                frame.dispose();
            }
        });
    }
}

