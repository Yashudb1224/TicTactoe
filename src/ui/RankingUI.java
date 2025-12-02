package ui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.util.ArrayList;

import backend.UserManager;
import backend.User;

public class RankingUI extends JFrame {

    public RankingUI(UserManager userManager) {

        setTitle("Tic Tac Toe - Leaderboard");
        setSize(700, 550);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBackground(new Color(18, 18, 18));

        // Header Panel
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(18, 18, 18));
        headerPanel.setBorder(BorderFactory.createEmptyBorder(25, 30, 15, 30));

        JLabel titleLabel = new JLabel("🏆 Leaderboard");
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 32));
        titleLabel.setForeground(new Color(99, 102, 241));
        headerPanel.add(titleLabel);

        // Table
        String[] columns = {"Rank", "Username", "Wins", "Losses", "Draws", "Points"};

        ArrayList<User> ranking = userManager.getRanking();
        Object[][] data = new Object[ranking.size()][6];

        for (int i = 0; i < ranking.size(); i++) {
            User u = ranking.get(i);
            data[i][0] = getRankDisplay(i + 1);
            data[i][1] = u.getUsername();
            data[i][2] = u.getWins();
            data[i][3] = u.getLosses();
            data[i][4] = u.getDraws();
            data[i][5] = u.getPoints();
        }

        DefaultTableModel model = new DefaultTableModel(data, columns) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        JTable table = new JTable(model);
        table.setRowHeight(45);
        table.setFont(new Font("SansSerif", Font.PLAIN, 14));
        table.setBackground(new Color(31, 41, 55));
        table.setForeground(new Color(229, 231, 235));
        table.setSelectionBackground(new Color(55, 65, 81));
        table.setSelectionForeground(new Color(229, 231, 235));
        table.setGridColor(new Color(55, 65, 81));
        table.setShowGrid(true);
        table.setIntercellSpacing(new Dimension(1, 1));

        // Custom cell renderer for alternating row colors
        DefaultTableCellRenderer cellRenderer = new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                    boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                
                if (!isSelected) {
                    if (row % 2 == 0) {
                        c.setBackground(new Color(31, 41, 55));
                    } else {
                        c.setBackground(new Color(37, 47, 63));
                    }
                }
                
                // Highlight top 3
                if (row < 3 && column == 0) {
                    setFont(new Font("SansSerif", Font.BOLD, 16));
                } else {
                    setFont(new Font("SansSerif", Font.PLAIN, 14));
                }
                
                setForeground(new Color(229, 231, 235));
                setHorizontalAlignment(SwingConstants.CENTER);
                
                return c;
            }
        };

        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(cellRenderer);
        }

        // Table header styling
        JTableHeader header = table.getTableHeader();
        header.setFont(new Font("SansSerif", Font.BOLD, 14));
        header.setBackground(new Color(99, 102, 241));
        header.setForeground(Color.WHITE);
        header.setPreferredSize(new Dimension(header.getWidth(), 40));
        header.setReorderingAllowed(false);
        
        DefaultTableCellRenderer headerRenderer = (DefaultTableCellRenderer) header.getDefaultRenderer();
        headerRenderer.setHorizontalAlignment(SwingConstants.CENTER);

        // Column widths
        table.getColumnModel().getColumn(0).setPreferredWidth(80);
        table.getColumnModel().getColumn(1).setPreferredWidth(180);
        table.getColumnModel().getColumn(2).setPreferredWidth(80);
        table.getColumnModel().getColumn(3).setPreferredWidth(80);
        table.getColumnModel().getColumn(4).setPreferredWidth(80);
        table.getColumnModel().getColumn(5).setPreferredWidth(100);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(0, 30, 20, 30));
        scrollPane.getViewport().setBackground(new Color(18, 18, 18));
        
        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBackground(new Color(18, 18, 18));
        tablePanel.add(scrollPane, BorderLayout.CENTER);

        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(tablePanel, BorderLayout.CENTER);

        add(mainPanel);
        setVisible(true);
    }

    private String getRankDisplay(int rank) {
        switch (rank) {
            case 1: return "🥇 1";
            case 2: return "🥈 2";
            case 3: return "🥉 3";
            default: return String.valueOf(rank);
        }
    }
}