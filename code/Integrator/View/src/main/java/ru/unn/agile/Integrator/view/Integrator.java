package ru.unn.agile.Integrator.view;

import ru.unn.agile.Integration.infrastructure.TxtLogger;
import ru.unn.agile.Integrator.viewmodel.ViewModel;

import javax.swing.*;
import java.awt.event.*;
import java.util.List;

public class Integrator {

    private JPanel mainPanel;
    private JComboBox cbMethod;
    private JComboBox cbFunction;
    private JTextField textFieldA;
    private JTextField textFieldB;
    private JTextField textFieldN;
    private JButton calcButton;
    private JTextField textFieldResult;
    private JTextField textFieldError;
    private JLabel lbStatus;
    private JList lstLog;

    private ViewModel viewModel;
    private Integrator() {}

    private Integrator(final ViewModel viewModel) {
        this.viewModel = viewModel;
        backBind();

        loadListOfMethods();
        loadListOfFunctions();

        calcButton.addActionListener(actionEvent -> {
            bind();
            Integrator.this.viewModel.calculate();
            backBind();
        });

        cbMethod.addActionListener(actionEvent -> {
            bind();
            backBind();
        });

        cbFunction.addActionListener(actionEvent -> {
            bind();
            backBind();
        });

        KeyAdapter keyListener = new KeyAdapter() {
            public void keyReleased(final KeyEvent e) {
                bind();
                Integrator.this.viewModel.processKeyInTextField(e.getKeyCode());
                backBind();
            }
        };
        textFieldA.addKeyListener(keyListener);
        textFieldB.addKeyListener(keyListener);
        textFieldN.addKeyListener(keyListener);

        FocusAdapter focusLostListener = new FocusAdapter() {
            public void focusLost(final FocusEvent e) {
                bind();
                Integrator.this.viewModel.focusLost();
                backBind();
            }
        };
        textFieldA.addFocusListener(focusLostListener);
        textFieldB.addFocusListener(focusLostListener);
        textFieldN.addFocusListener(focusLostListener);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Integrator");

        TxtLogger logger = new TxtLogger("./Integrator.log");
        frame.setContentPane(new Integrator(new ViewModel(logger)).mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    private void loadListOfMethods() {
        ViewModel.Method[] methods = ViewModel.Method.values();
        cbMethod.setModel(new JComboBox<>(methods).getModel());
    }

    private void loadListOfFunctions() {
        ViewModel.Function[] functions = ViewModel.Function.values();
        cbFunction.setModel(new JComboBox<>(functions).getModel());
    }

    private void bind() {
        viewModel.setA(textFieldA.getText());
        viewModel.setB(textFieldB.getText());
        viewModel.setN(textFieldN.getText());

        viewModel.setMethod((ViewModel.Method) cbMethod.getSelectedItem());
        viewModel.setFunction((ViewModel.Function) cbFunction.getSelectedItem());
    }

    private void backBind() {
        calcButton.setEnabled(viewModel.isCalculateButtonEnabled());

        textFieldResult.setText(viewModel.getResult());
        textFieldError.setText(viewModel.getError());
        lbStatus.setText(viewModel.getStatus());


        List<String> log = viewModel.getLog();
        String[] items = log.toArray(new String[log.size()]);
        lstLog.setListData(items);
    }
}
