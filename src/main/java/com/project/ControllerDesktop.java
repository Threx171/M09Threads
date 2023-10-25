package com.project;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.text.Text;

import java.util.Random;
import java.util.concurrent.CompletableFuture;
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


    public void initialize() {
        btnTask1.setOnAction(e -> {
            toggleTask(btnTask1, run1);
            CompletableFuture<Void> pbar1 = CompletableFuture.runAsync(() -> {
                try {
                    while(value1.get() < 100 && run1.get()) {
                        Thread.sleep(1000);
                        value1.incrementAndGet();
                        AtomicInteger finalValue = value1;
                        Platform.runLater(() -> {
                            updatePbar(progressbar1, finalValue, txtTask1, btnTask1, run1, value1, 1);
                        });
                    }
                } catch (InterruptedException ex) {
                    throw new RuntimeException(ex);
                }
            });
        });
        btnTask2.setOnAction(e -> {
            toggleTask(btnTask2, run2);
            CompletableFuture<Void> pbar2 = CompletableFuture.runAsync(() -> {
                try {
                    while(value2.get() < 100 && run2.get()) {
                        Thread.sleep(random.nextInt(3000,5000));
                        value2.set(value2.get()+random.nextInt(2,4));
                        if (value2.get() > 100) { value2.set(100);  }
                        AtomicInteger finalValue = value2;
                        Platform.runLater(() -> {
                            updatePbar(progressbar2, finalValue, txtTask2, btnTask2, run2, value2, 2);
                        });

                    }
                } catch (InterruptedException ex) {
                    throw new RuntimeException(ex);
                }
            });
        });
        btnTask3.setOnAction(e -> {
            toggleTask(btnTask3, run3);
            CompletableFuture<Void> pbar3 = CompletableFuture.runAsync(() -> {
                try {
                    while (value3.get() < 100 && run3.get()) {
                        Thread.sleep(random.nextInt(3000, 8000));
                        value3.set(value3.get() + random.nextInt(4, 6));
                        if (value3.get() > 100) { value3.set(100); }
                        AtomicInteger finalValue = value3;
                        Platform.runLater(() -> {
                            updatePbar(progressbar3, finalValue, txtTask3, btnTask3, run3, value3, 3);
                        });
                    }
                } catch (InterruptedException ex) {
                    throw new RuntimeException(ex);
                }
            });
        });
    }
    public void updatePbar(ProgressBar pbar, AtomicInteger finalValue, Text txt, Button btn, AtomicBoolean isRunning,
                           AtomicInteger currentValue, int taskNumber) {
        pbar.setProgress((double) finalValue.get() / 100);
        txt.setText(String.format("Tasca %d: %d%%", taskNumber, finalValue.get()));
        if (finalValue.get() == 100) { btn.setText("Iniciar"); isRunning.set(false); currentValue.set(0); }
    }
    public void toggleTask(Button btn, AtomicBoolean isRunning) {
        if (isRunning.get()) {
            isRunning.set(false);
            btn.setText("Iniciar");
        }else{
            isRunning.set(true);
            btn.setText("Aturar");
        }
    }
}
