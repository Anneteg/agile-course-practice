package ru.unn.agile.Integration.infrastructure;

import ru.unn.agile.Integrator.viewmodel.ViewModel;
import ru.unn.agile.Integrator.viewmodel.ViewModelTests;

public class ViewModelWithTxtLoggerTests extends ViewModelTests {
    @Override
    public void setUp() {
        TxtLogger realLogger =
            new TxtLogger("./ViewModelWithTxtLoggerTests.log");
        super.setViewModel(new ViewModel(realLogger));
    }
}
