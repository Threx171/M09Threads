package com.project;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.text.Text;

import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicBoolean;

public class ControllerDesktop {
    @FXML
    private ProgressBar progressbar1, progressbar2, progressbar3;

    @FXML
    private Text txtTask1, txtTask2, txtTask3;

    @FXML
    private Button btnTask1, btnTask2, btnTask3;

    private Random random = new Random();
    private int value1 = 0, value2 = 0, value3 = 0;
    private AtomicBoolean run1 = new AtomicBoolean(false);
    private AtomicBoolean run2 = new AtomicBoolean(false);
    private AtomicBoolean run3 = new AtomicBoolean(false);


    public void initialize() {
        btnTask1.setOnAction(e -> {
            toggleTask(btnTask1, run1);
            CompletableFuture<Void> pbar1 = CompletableFuture.runAsync(() -> {
                try {
                    while(value1 < 100 && run1.get()) {
                        Thread.sleep(1000);
                        value1++;
                        int finalvalue = value1;
                        Platform.runLater(() -> {
                            updatePbar(progressbar1, finalvalue);
                            txtTask1.setText("Tasca 1" + ": " + finalvalue + "%");
                            if (finalvalue == 100) { btnTask1.setText("Iniciar"); run1.set(false); value1 = 0; }
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
                    while(value2 < 100 && run2.get()) {
                        Thread.sleep(random.nextInt(3000,5000));
                        value2 += random.nextInt(2,4);
                        if (value2 > 100) { value2 = 100; }
                        int finalValue = value2;
                        Platform.runLater(() -> {
                           updatePbar(progressbar2, finalValue);
                           txtTask2.setText("Tasca 2" + ": " + finalValue + "%");
                           if (finalValue == 100) { btnTask2.setText("Iniciar"); run2.set(false); value2 = 0; }
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
                    while (value3 < 100 && run3.get()) {
                        Thread.sleep(random.nextInt(3000, 8000));
                        value3 += random.nextInt(4, 6);
                        if (value3 > 100) { value3 = 100; }
                        int finalValue = value3;
                        Platform.runLater(() -> {
                            updatePbar(progressbar3, finalValue);
                            txtTask3.setText("Tasca 3" + ": " + finalValue + "%");
                            if (finalValue == 100) { btnTask3.setText("Iniciar"); run3.set(false); value3 = 0; }
                        });
                    }
                } catch (InterruptedException ex) {
                    throw new RuntimeException(ex);
                }
            });
        });
    }
    public void updatePbar(ProgressBar pbar, int value) {
        pbar.setProgress((double) value / 100);
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
