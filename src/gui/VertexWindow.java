package gui;

import graph.Vertex;
import social.SocialNetwork;
import social.accounts.Page;
import social.accounts.User;

import javax.swing.*;
import java.awt.*;
import java.util.Map;
import java.util.Set;

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
        final int frameWidth = 1000;
        final int frameHeight = 500;
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
            JLabel degree = new JLabel(getdegree(u));
            JPanel p = new JPanel(new GridLayout(2, 3)); {
                p.add(info);
                p.add(followers);
                p.add(following);
                p.add(adminof);
                p.add(getlike);
                p.add(degree);
            }
            frame.add(p, BorderLayout.CENTER);
        } else {
            Page p = (Page) model.getVertexByName(string);
            JLabel info = new JLabel(p.toString());
            JLabel likers = new JLabel(getLikers(p));
            JLabel admin = new JLabel(getAdmin(p));
            JLabel degree = new JLabel(getdegree(p));
            JPanel l = new JPanel(new GridLayout(2, 2)); {
                l.add(info);
                l.add(likers);
                l.add(admin);
                l.add(degree);
            }
            frame.add(l, BorderLayout.CENTER);
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

    private String getdegree(Vertex x) {
        StringBuilder sb = new StringBuilder();
        sb.append("<html>Other Vertex Distances : <br>");
        Map<Vertex, Integer> s = model.degreeKnowledge(x);
        for (Vertex t : s.keySet()) {
            sb.append(t.toString()).append(": ");
            if (s.get(t) != Integer.MAX_VALUE) {
                sb.append(s.get(t)).append("<br>");
            } else {
                sb.append("Inatteignable <br>");
            }
        }
        sb.append("<html>");
        return sb.toString();
    }
}
