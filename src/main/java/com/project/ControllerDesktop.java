package com.project;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.text.Text;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class ControllerDesktop {
    @FXML
    private ProgressBar progressbar1, progressbar2, progressbar3;

    @FXML
    private Text txtTask1, txtTask2, txtTask3;

    @FXML
    private Button btnTask1, btnTask2, btnTask3;

    private Random random = new Random();
    private AtomicInteger value1 = new AtomicInteger(0);
    private AtomicInteger value2 = new AtomicInteger(0);
    private AtomicInteger value3 = new AtomicInteger(0);
    private AtomicBoolean run1 = new AtomicBoolean(false);
    private AtomicBoolean run2 = new AtomicBoolean(false);
    private AtomicBoolean run3 = new AtomicBoolean(false);

    private ExecutorService executor = Executors.newFixedThreadPool(3);

    public void initialize() {
        btnTask1.setOnAction(e -> runTask(1, value1, run1, progressbar1, txtTask1, btnTask1));
        btnTask2.setOnAction(e -> runTask(2, value2, run2, progressbar2, txtTask2, btnTask2));
        btnTask3.setOnAction(e -> runTask(3, value3, run3, progressbar3, txtTask3, btnTask3));
    }

    public void runTask(int taskNumber, AtomicInteger currentValue, AtomicBoolean isRunning,
                        ProgressBar progressBar, Text txt, Button btn) {
        if (isRunning.get()) {
            isRunning.set(false);
            btn.setText("Iniciar");
        } else {
            isRunning.set(true);
            btn.setText("Aturar");
            executor.submit(() -> {
                try {
                    while (currentValue.get() < 100 && isRunning.get()) {
                        Thread.sleep(getSleepDuration(taskNumber));
                        int increment = getIncrement(taskNumber);
                        int newValue = currentValue.addAndGet(increment);
                        if (newValue > 100) {
                            newValue = 100;
                        }
                        int finalValue = newValue;
                        Platform.runLater(() -> updateUI(progressBar, txt, btn, isRunning, currentValue, finalValue, taskNumber));
                    }
                } catch (InterruptedException ex) {
                    throw new RuntimeException(ex);
                }
            });
        }
    }

    public long getSleepDuration(int taskNumber) {
        if (taskNumber == 1) {
            return 1000;
        } else if (taskNumber == 2) {
            return random.nextInt(3000, 5000);
        } else {
            return random.nextInt(3000, 8000);
        }
    }

    public int getIncrement(int taskNumber) {
        if (taskNumber == 1) {
            return 1;
        } else if (taskNumber == 2) {
            return random.nextInt(2, 4);
        } else {
            return random.nextInt(4, 6);
        }
    }

    public void updateUI(ProgressBar progressBar, Text txt, Button btn, AtomicBoolean isRunning, AtomicInteger currentValue,
                         int finalValue, int taskNumber) {
        progressBar.setProgress((double) finalValue / 100);
        txt.setText(String.format("Tasca %d: %d%%", taskNumber, finalValue));
        if (finalValue == 100) {
            btn.setText("Iniciar");
            isRunning.set(false);
            currentValue.set(0);
        }
    }
}
