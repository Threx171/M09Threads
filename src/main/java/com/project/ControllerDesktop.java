package com.project;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.text.Text;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ControllerDesktop {
    @FXML
    private ProgressBar progressbar1, progressbar2, progressbar3;

    @FXML
    private Text txtTask1, txtTask2, txtTask3;

    @FXML
    private Button btnTask1, btnTask2, btnTask3;

    private ExecutorService executor = Executors.newSingleThreadExecutor();

    public Future<Integer> countByOne (Integer input) {
        return executor.submit(() -> {
            Thread.sleep(1000);
            return input + 1;
        });
    }
}