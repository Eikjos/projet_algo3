package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import social.SocialNetwork;
import social.accounts.Page;
import social.accounts.User;

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
            JLabel adminof = new JLabel(adminOf(u));
            JLabel getlike = new JLabel(getLikes(u));
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
            JLabel admin = new JLabel(getAdmins(p));
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

    //- OUTILS

    /**
     * @param u L'utilisateur ciblé par la demande.
     * @return La liste des utilisateurs suivant l'utilisateur dénoté par u.
     */
    private String getFollowers(User u) {
        StringBuilder sb = new StringBuilder("<html>Followers :");
        for (User v : model.getFollowers(u)) {
            sb.append("<br>").append(v);
        }
        return sb.append("</html>").toString();
    }

    /**
     * @param u L'utilisateur ciblé par la demande.
     * @return La liste des utilisateurs suivis par l'utilisateur dénoté par u.
     */
    private String getFollowing(User u) {
        StringBuilder sb = new StringBuilder("<html>Following :");
        for (User v : model.getFollow(u)) {
            sb.append("<br>").append(v);
        }
        return sb.append("</html>").toString();
    }

    /**
     * @param u L'utilisateur ciblé par la demande.
     * @return La liste des pages dans lesquelles l'utilisateur dénoté par u est
     * administrateur.
     */
    private String adminOf(User u) {
        StringBuilder sb = new StringBuilder("<html>Administrator of :");
        for (Page p : model.getPages()) {
            if (!model.getAdminsOf(p).contains(u)) {
                continue;
            }
            sb.append("<br>").append(p);
        }
        return sb.append("</html>").toString();
    }

    /**
     * @param u L'utilisateur ciblé par la demande.
     * @return La liste des pages qui ont reçues la mention J'aime de
     * l'utilisateur dénoté par u.
     */
    private String getLikes(User u) {
        StringBuilder sb = new StringBuilder("<html>Likes :");
        for (Page p : model.getLikes(u)) {
            sb.append("<br>").append(p);
        }
        return sb.append("</html>").toString();
    }

    /**
     * @param p La page ciblée par la demande.
     * @return La liste des utilisateurs qui aiment la page dénotée par p.
     */
    private String getLikers(Page p) {
        StringBuilder sb = new StringBuilder("<html>Likers :");
        for (User u : model.getLikers(p)) {
            sb.append("<br>").append(u);
        }
        return sb.append("</html>").toString();
    }

    /**
     * @param p La page ciblée par la demande.
     * @return La liste des administrateurs de la page dénotée par p.
     */
    private String getAdmins(Page p) {
        StringBuilder sb = new StringBuilder("<html>Administrators :");
        for (User u : model.getAdminsOf(p)) {
            sb.append("<br>").append(u);
        }
        return sb.append("</html>").toString();
    }
}
