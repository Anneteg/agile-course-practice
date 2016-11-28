package ru.unn.agile.Statistics.view;

import javax.swing.JFrame;
import javax.swing.UIManager;

import ru.unn.agile.Statistics.viewmodel.ViewModel;


public final class Main {
    public static void main(final String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) { }

        JFrame frame = new JFrame("Calculator");
        frame.setLocationByPlatform(true);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        Calculator calculator = new Calculator(new ViewModel());
        calculator.applyTo(frame);

        frame.pack();
        frame.setMinimumSize(frame.getSize());
        frame.setVisible(true);
    }

    private Main() { }
}
