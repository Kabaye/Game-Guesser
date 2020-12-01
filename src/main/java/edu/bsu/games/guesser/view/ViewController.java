package edu.bsu.games.guesser.view;

import edu.bsu.games.guesser.data.storage.DataStorage;
import java.awt.Dimension;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
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
        String[] buttons = {"Show feature values", "Show training data", "Start game", "Exit game"};
        return JOptionPane.showOptionDialog(jFrame, "Game Guesser © Kabaye Inc.", "Main Menu",
                JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE, null, buttons, buttons[0]);
    }

    public void showErrorMessage(String message) {
        JOptionPane.showMessageDialog(jFrame, message, "Error: ", JOptionPane.ERROR_MESSAGE);
    }

    public void showFeatureValues() {
        Object[][] values = new Object[dataStorage.getAllFeatures().size()][dataStorage.getGenres().size() + 1];
        for (int i = 0; i < dataStorage.getFeaturesValues().length; i++) {
            Object[] objects = Stream.concat(Stream.of(i + 1), Stream.of(dataStorage.getFeaturesValues()[i])).toArray();
            values[i] = objects;
        }
        Object[] columnNames = IntStream.range(-1, dataStorage.getGenres().size()).boxed().toArray();
        columnNames[0] = "Row names";
        JTable jTable = new JTable(values, columnNames) {
            @Override
            public boolean editCellAt(int row, int column) {
                return false;
            }

            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        JScrollPane jScrollPane = new JScrollPane(jTable);
        jScrollPane.setPreferredSize(new Dimension(600,263));
        JOptionPane.showMessageDialog(jFrame, jScrollPane, "Values: ", JOptionPane.INFORMATION_MESSAGE);
    }
}
