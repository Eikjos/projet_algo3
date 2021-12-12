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

public class LikeFollowWindow {
    private final JFrame frame;
    private final SocialNetwork model;
    private JTextField vertexToFollow;
    private JTextField username;
    private JButton likefollow;
    private JButton unlikefollow;



    public LikeFollowWindow(SocialNetwork models) {
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
        JLabel title = new JLabel("Enter User that will like or follow pages or other users :");
        username = new JTextField();
        JLabel title2 = new JLabel("Enter Users or Pages that will be like by User entered above :");
        vertexToFollow = new JTextField();
        likefollow = new JButton("Like / Follow");
        unlikefollow = new JButton("Unlike / Unfollow");
        JPanel p = new JPanel(); {
            p.add(new JLabel("Like or follow !"));
        }
        frame.add(p, BorderLayout.NORTH);
        p = new JPanel(new BorderLayout()); {
            JPanel r = new JPanel(new FlowLayout(FlowLayout.CENTER)); {
                r.add(title);
                username.setColumns(15);
                r.add(username);
            }
            p.add(r, BorderLayout.NORTH);
            r = new JPanel(new FlowLayout(FlowLayout.CENTER)); {
                r.add(title2);
                vertexToFollow.setColumns(15);
                r.add(vertexToFollow);
            }
            p.add(r, BorderLayout.CENTER);
            r = new JPanel(new FlowLayout(FlowLayout.CENTER)); {
                r.add(likefollow);
                r.add(unlikefollow);
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

        likefollow.addActionListener(new ActionListener() {
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

        unlikefollow.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
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
                frame.dispose();
            }
        });
    }
}
