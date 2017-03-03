package ru.unn.agile.Integrator.viewmodel;

import java.util.List;

public interface ILogger {
    void log(String s);

    List<String> getLog();
}

