package ui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

import backend.UserManager;
import backend.User;

public class RankingUI extends JFrame {

    public RankingUI(UserManager userManager) {

        setTitle("Ranking Table");
        setSize(600, 450);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBackground(Color.WHITE);

        // Table headings
        String[] columns = {"Rank", "Username", "Wins", "Losses", "Draws", "Points"};

        ArrayList<User> ranking = userManager.getRanking();
        Object[][] data = new Object[ranking.size()][6];

        for (int i = 0; i < ranking.size(); i++) {
            User u = ranking.get(i);
            data[i][0] = (i + 1);
            data[i][1] = u.getUsername();
            data[i][2] = u.getWins();
            data[i][3] = u.getLosses();
            data[i][4] = u.getDraws();
            data[i][5] = u.getPoints();
        }

        DefaultTableModel model = new DefaultTableModel(data, columns) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // lock table
            }
        };

        JTable table = new JTable(model);
        table.setRowHeight(28);
        table.setFont(new Font("SansSerif", Font.PLAIN, 14));
        table.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 14));

        JScrollPane scrollPane = new JScrollPane(table);
        panel.add(scrollPane, BorderLayout.CENTER);

        add(panel);
        setVisible(true);
    }
}
