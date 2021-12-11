package gui;

import social.SocialNetwork;
import social.accounts.Page;
import social.accounts.User;

import javax.swing.*;
import java.awt.*;

public class VertexWindow {

    private final JFrame frame;
    private final SocialNetwork model;
    private final String string;

    public VertexWindow(String s, SocialNetwork models) {
        frame = new JFrame(s);
        model = models;
        string = s;
    }

    public void display() {
        final int frameWidth = 900;
        final int frameHeight = 400;
        frame.setPreferredSize(new Dimension(frameWidth, frameHeight));
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(frame.DISPOSE_ON_CLOSE);
        if ((model.getVertexByName(string)) instanceof User) {
            User u = (User) model.getVertexByName(string);
            JLabel info = new JLabel(u.toString());
            JLabel followers = new JLabel(getFollowers(u));
            JLabel following = new JLabel(getFollowing(u));
            JLabel adminof = new JLabel(adminof(u));
            JLabel getlike = new JLabel(getLike(u));
            JPanel p = new JPanel(); {
                p.add(info);
            }
            JPanel i = new JPanel(); {
                i.add(followers);
                i.add(following);
            }
            JPanel t = new JPanel(); {
                t.add(getlike);
                t.add(adminof);
            }
            frame.add(p, BorderLayout.NORTH);
            frame.add(i, BorderLayout.CENTER);
            frame.add(t, BorderLayout.SOUTH);
        } else {
            Page p = (Page) model.getVertexByName(string);
            JLabel info = new JLabel(p.toString());
            JLabel likers = new JLabel(getLikers(p));
            JLabel admin = new JLabel(getAdmin(p));
            JPanel l = new JPanel(); {
                l.add(info);
            }
            JPanel i = new JPanel(); {
                i.add(likers);
            }
            JPanel t = new JPanel(); {
                i.add(admin);
            }
            frame.add(l, BorderLayout.NORTH);
            frame.add(i, BorderLayout.CENTER);
            frame.add(t, BorderLayout.SOUTH);
        }
    }

    private String getFollowers(User u) {
        StringBuilder sb = new StringBuilder();
        sb.append("<html>Followers : <br>");
        for (User t : model.getFollowers(u)) {
            sb.append(t.toString()).append(" <br>");
        }
        sb.append("<html>");
        return sb.toString();
    }

    private String getFollowing(User u) {
        StringBuilder sb = new StringBuilder();
        sb.append("<html>Following : <br>");
        for (User t : model.getFollow(u)) {
            sb.append(t.toString()).append(" <br>");
        }
        sb.append("<html>");
        return sb.toString();
    }

    private String adminof(User u) {
        StringBuilder sb = new StringBuilder();
        sb.append("<html> Admin of : <br>");
        for (Page p : model.getPages()) {
            if (model.getAdminsOf(p).contains(u)) {
                sb.append(p.toString()).append("<br>");
            }
        }
        sb.append("<html>");
        return sb.toString();
    }

    private String getLike(User u) {
        StringBuilder sb = new StringBuilder();
        sb.append("<html>Liked Page : <br>");
        for (Page t : model.getLikes(u)) {
            sb.append(t.toString()).append(" <br>");
        }
        sb.append("<html>");
        return sb.toString();
    }

    private String getLikers(Page p) {
        StringBuilder sb = new StringBuilder();
        sb.append("<html>Likers : <br>");
        for (User t : model.getLikers(p)) {
            sb.append(t.toString()).append(" <br>");
        }
        sb.append("<html>");
        return sb.toString();
    }

    private String getAdmin(Page p) {
        StringBuilder sb = new StringBuilder();
        sb.append("<html>Admins : <br>");
        for (User t : model.getAdminsOf(p)) {
            sb.append(t.toString()).append(" <br>");
        }
        sb.append("<html>");
        return sb.toString();
    }
}
