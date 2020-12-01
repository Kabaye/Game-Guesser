package edu.bsu.games.guesser.view;

import edu.bsu.games.guesser.data.storage.DataStorage;
import edu.bsu.games.guesser.data.storage.Game;
import edu.bsu.games.guesser.data.storage.Genre;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTree;
import javax.swing.border.EmptyBorder;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import org.springframework.stereotype.Component;

/**
 * created by @Kabaye
 * date 29.11.2020
 */

@Component
public class ViewController {
    private final JFrame jFrame;
    private final DataStorage dataStorage;

    public ViewController(DataStorage dataStorage) {
        this.dataStorage = dataStorage;
        jFrame = new JFrame();
        jFrame.setVisible(false);
        jFrame.setAlwaysOnTop(true);
    }

    public int showMenu() {
        String[] buttons = {"Show feature values", "Show training set", "Start game", "Exit game"};
        return JOptionPane.showOptionDialog(jFrame, "Game Guesser © Kabaye Inc.", "Main Menu",
                JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE, null, buttons, buttons[0]);
    }

    public void showErrorMessage(String message) {
        JOptionPane.showMessageDialog(jFrame, message, "Error: ", JOptionPane.ERROR_MESSAGE);
    }

    public void showFeatureValues() {
        Object[][] values = new Object[dataStorage.getAllFeatures().size()][dataStorage.getGenres().size() + 1];
        for (int i = 0; i < dataStorage.getFeaturesValues().length; i++) {
            Object[] objects = Stream.concat(Stream.of(dataStorage.getAllFeatures().get(i)), Stream.of(dataStorage.getFeaturesValues()[i])).toArray();
            values[i] = objects;
        }
        Object[] columnNames = Stream.concat(Stream.of("Features"), dataStorage.getGenres().stream().map(Genre::getGenre)).toArray();
        JTable jTable = new JTable(values, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        JScrollPane jScrollPane = new JScrollPane(jTable);
        jTable.getColumnModel().getColumn(0).setPreferredWidth(255);
        jScrollPane.setPreferredSize(new Dimension(700, 263));
        JOptionPane.showMessageDialog(jFrame, jScrollPane, "Values: ", JOptionPane.INFORMATION_MESSAGE);
    }

    public void showTrainingSet() {
        DefaultMutableTreeNode root = new DefaultMutableTreeNode("Genres", true);
        DefaultTreeModel model = new DefaultTreeModel(root);
        JTree jTree = new JTree(model);
        for (Genre genre : dataStorage.getGenres()) {
            DefaultMutableTreeNode genreNode = new DefaultMutableTreeNode(genre.getGenre());
            for (Game game : genre.getExamples()) {
                DefaultMutableTreeNode gameNode = new DefaultMutableTreeNode(game.getName());
                for (String s : game.getFeatures()) {
                    gameNode.add(new DefaultMutableTreeNode(s));
                }
                genreNode.add(gameNode);
            }
            root.add(genreNode);
        }
        JScrollPane jScrollPane = new JScrollPane(jTree);
        jScrollPane.setPreferredSize(new Dimension(350, 350));
        JOptionPane.showMessageDialog(jFrame, jScrollPane, "All genres", JOptionPane.INFORMATION_MESSAGE);
    }

    public String showFeaturesMenu() {
        JPanel ui = new JPanel(new BorderLayout(4, 4));
        ui.setBorder(new EmptyBorder(40, 4, 40, 4));
        JPanel checkPanel = new JPanel(new GridLayout(0, 2));
        ui.add(checkPanel, BorderLayout.CENTER);
        List<JCheckBox> jCheckBoxes = new ArrayList<>();
        for (String s : dataStorage.getAllFeatures()) {
            JCheckBox comp = new JCheckBox(s);
            jCheckBoxes.add(comp);
            checkPanel.add(comp);
        }
        ui.setPreferredSize(new Dimension(500, 500));
        JOptionPane.showConfirmDialog(jFrame, ui, "Check some features:", JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE);
        return jCheckBoxes.stream().reduce("", (s, jCheckBox) -> s + (jCheckBox.isSelected() ? "1" : "0"), String::concat);
    }

    public void showResult(String genreName, Double[] featuresCheckArr) {
        JPanel jPanel = new JPanel(new BorderLayout());
        String[] columnNames = {"Genre", "Genre value"};
        Object[][] values = new Object[dataStorage.getGenres().size()][2];
        for (int i = 0; i < dataStorage.getGenres().size(); i++) {
            values[i][0] = dataStorage.getGenres().get(i).getGenre();
            values[i][1] = featuresCheckArr[i];
        }

        JTable jTable = new JTable(values, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        JScrollPane jScrollPane = new JScrollPane(jTable);
        jScrollPane.setPreferredSize(new Dimension(450, 150));
        jPanel.add(jScrollPane, BorderLayout.CENTER);
        JLabel label = new JLabel("The best matched genre is: " + genreName.toUpperCase());
        label.setPreferredSize(new Dimension(450, 100));
        jPanel.add(label, BorderLayout.NORTH);
        jPanel.setPreferredSize(new Dimension(450, 300));
        JOptionPane.showMessageDialog(jFrame, jPanel, "Result: ", JOptionPane.INFORMATION_MESSAGE);
    }
}
