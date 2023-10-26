package com.project;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javax.imageio.ImageIO;
import javafx.embed.swing.SwingFXUtils;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

public class ControllerDesktop2 implements Initializable {

    @FXML
    public Label vista0Label, task1;

    @FXML
    private Button carregar, aturar;

    @FXML
    private ProgressBar bar1;

    @FXML
    private GridPane grid;

    LoadImage imageLoader;
    private AtomicInteger loadedImageCount = new AtomicInteger(0);

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        imageLoader = new LoadImage();
        carregar.setOnAction(e -> startTask());

        vista0Label.setOnMouseClicked(new EventHandler<Event>() {
            @Override
            public void handle(Event event) {
                UtilsViews.setViewAnimating("Desktop");
            }
        });
    }
    public void startTask() {
        Consumer<BufferedImage> imageConsumer = (BufferedImage image) -> {
            int Width = 75;
            int Height = 75;

            Image fxImage = SwingFXUtils.toFXImage(image, null);

            ImageView imageView = new ImageView(fxImage);
            imageView.setFitWidth(Width);
            imageView.setFitHeight(Height);

            grid.add(imageView, grid.getChildren().size() % 8, grid.getChildren().size() / 8);
            updateProgressBar();
        };

        // Load a total of 24 images
        imageLoader.load(24, imageConsumer);
    }
    public void updateProgressBar() {
        int currentCount = loadedImageCount.incrementAndGet();
        double progress = (double) currentCount / 24;
        task1.setText("Progrés: "+ currentCount +" de 24");
        bar1.setProgress(progress);

        if (currentCount >= 24) {
            aturar.setDisable(true);
        }
    }
}
