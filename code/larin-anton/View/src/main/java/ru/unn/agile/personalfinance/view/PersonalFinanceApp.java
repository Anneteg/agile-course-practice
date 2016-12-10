package ru.unn.agile.personalfinance.view;

import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;

public class PersonalFinanceApp extends Application {

    public static void main(final String[] args) {
        launch(args);
    }

    @Override
    public void start(final Stage primaryStage) throws IOException {
        WindowsManager.getInstance().showHomeScreenView(primaryStage);
    }
}
