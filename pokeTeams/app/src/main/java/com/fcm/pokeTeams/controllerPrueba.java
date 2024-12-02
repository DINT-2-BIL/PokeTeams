/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fcm.pokeTeams;

/**
 *
 * @author Francisco
 */
import java.awt.Rectangle;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicInteger;
import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.util.Duration;

public class controllerPrueba implements Initializable {
    private boolean isRefreshing = false;

    @FXML
    private AnchorPane anchor;

    @FXML
    private GridPane grid;

    @FXML
    private ScrollPane scroll;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        AtomicInteger fila = new AtomicInteger(0);
        AtomicInteger columna = new AtomicInteger(0);
        for (int i = 0; i < 14; i++) {
            Button b = new Button("Botón"+i);
            grid.add(b, columna.get(), fila.get());
            if (columna.get() == 1) {
                fila.set(fila.get()+1);
                columna.set(0);
            }
            columna.set(columna.get()+1);
        }
        
        scroll.vvalueProperty().addListener((obs, oldVal, newVal) -> {
            if (!isRefreshing) {
                isRefreshing = true;

                PauseTransition pause = new PauseTransition(Duration.millis(200));
                pause.setOnFinished(event -> {
                    grid.getChildren().forEach(node -> {
                        boolean isVisible = isNodeVisible(scroll, node);
                        System.out.println(((Button)node).getText()+": " + isVisible);
                        if (((Button)node).getText().equals("Botón13")) {
                            System.out.println("");
                        }
                    });
                    isRefreshing = false;
                });
                pause.play();
            }
        });
    }
    
    private boolean isNodeVisible(ScrollPane scrollPane, Node node) {
        Bounds nodeBounds = node.localToScene(node.getBoundsInLocal());
        Bounds viewportBounds = scrollPane.localToScene(scrollPane.getViewportBounds());

        return nodeBounds.intersects(viewportBounds);
    }

}
