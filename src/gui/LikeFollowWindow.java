package gui;

import graph.exceptions.DuplicateArc;
import graph.exceptions.VertexNotFound;
import social.SocialNetwork;
import social.accounts.Page;
import social.accounts.User;

import javax.swing.*;
import java.awt.*;

public class LikeFollowWindow {
    private final JFrame frame;
    private final SocialNetwork model;

    public LikeFollowWindow(SocialNetwork models) {
        frame = new JFrame("Like / Follow Window");
        model = models;
    }

    public void display() {
        final int frameWidth = 700;
        final int frameHeight = 200;
        frame.setPreferredSize(new Dimension(frameWidth, frameHeight));
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(frame.DISPOSE_ON_CLOSE);
        JLabel title = new JLabel("Enter User that will like or follow pages or other users :");
        JTextField username = new JTextField();
        User u = (User) model.getVertexByName(username.getText());
        JLabel title2 = new JLabel("Enter User or Page that will be like by User entered above :");
        JTextField vertexToFollow = new JTextField();
        String[] names = vertexToFollow.getText().split(";");
        for (String s : names) {
            if (model.getVertexByName(s) instanceof User) {
                User v = (User) model.getVertexByName(s);
                try {
                    model.follow(u, v);
                } catch (VertexNotFound e) {
                    e.printStackTrace();
                } catch (DuplicateArc e) {
                    e.printStackTrace();
                }
            } else if (model.getVertexByName(s) instanceof Page) {
                Page v = (Page) model.getVertexByName(s);
                try {
                    model.like(u, v);
                } catch (DuplicateArc e) {
                    e.printStackTrace();
                } catch (VertexNotFound e) {
                    e.printStackTrace();
                }
            }
        }
        JPanel p = new JPanel(); {
            p.add(new JLabel("Like or follow !"));
        }
        frame.add(p, BorderLayout.NORTH);
        p = new JPanel(new BorderLayout()); {
            JPanel r = new JPanel(new FlowLayout(FlowLayout.CENTER)); {
                r.add(title);
                username.setPreferredSize(new Dimension(160,30));
                r.add(username);
            }
            p.add(r, BorderLayout.NORTH);
            r = new JPanel(new FlowLayout(FlowLayout.CENTER)); {
                r.add(title2);
                vertexToFollow.setPreferredSize(new Dimension(160, 30));
                r.add(vertexToFollow);
            }
            p.add(r, BorderLayout.CENTER);
        }
        frame.add(p, BorderLayout.CENTER);
    }
}
