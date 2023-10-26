package com.project;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

import javax.imageio.ImageIO;

import javafx.application.Platform;

public class LoadImage {
    private static final String IMAGE_FOLDER = "/assets/images/";
    private Random random = new Random();

    public void load(int numberOfImages, Consumer<BufferedImage> callBack) {
        for (int i = 0; i < numberOfImages; i++) {
            CompletableFuture.supplyAsync(() -> loadImageWithRandomDelay())
                    .exceptionally(ex -> {
                        ex.printStackTrace();
                        return null;
                    })
                    .thenAcceptAsync(image -> {
                        callBack.accept(image);
                    }, Platform::runLater);
        }
    }

    private BufferedImage loadImageWithRandomDelay() {
        File file = null;
        try {
            String imageName = generateRandomImageName();
            file = new File(System.getProperty("user.dir") + "/src/main/resources/assets/images/" + imageName);
            System.out.println(file);
            BufferedImage img = ImageIO.read(file);
            simulateRandomDelay();
            return img;
        } catch (IOException e) {
            System.out.println(file);
            e.printStackTrace();
            return null;
        }
    }

    public void simulateRandomDelay() {
        int delay = random.nextInt(5000, 50000);
        try {
            Thread.sleep(delay);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public String generateRandomImageName() {
        String[] imageNames = {
                "character_bowser.png", "character_dk.png", "character_fox.png",
                "character_inkling.png", "character_kirby.png", "character_link.png",
                "character_luigi.png", "character_mario.png", "character_olimar.png",
                "character_peach.png", "character_pikachu.png", "character_samus.png",
                "character_toad.png", "character_wario.png", "game_dk.png",
                "game_metroid.png", "game_pikmin.png",
                "game_smk.png", "nintendo_64.png", "nintendo_gamecube.png",
                "nintendo_switch.png", "nintendo_nes.png"
        };
        return imageNames[random.nextInt(imageNames.length)];
    }
}
