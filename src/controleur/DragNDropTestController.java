package controleur;

import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Circle;
import javafx.scene.paint.Color;
import javafx.scene.shape.StrokeType;
import java.util.ArrayList;
import java.util.List;

public class DragNDropTestController {
    
    @FXML
    private Button crayonButton;
    
    @FXML
    private Pane drawingArea;
    
    private boolean drawingMode = false;
    private List<Double> currentPath = new ArrayList<>();
    private List<Circle> pathPoints = new ArrayList<>();
    
    @FXML
    private void initialize() {
        // Configuration de la zone de dessin
        drawingArea.setStyle("-fx-background-color: #F4E3C1;");
    }
    
    @FXML
    private void handleCrayonClick() {
        // Toggle du mode dessin
        drawingMode = !drawingMode;
        
        if (drawingMode) {
            crayonButton.setStyle("-fx-background-color: #ffaa00; -fx-border-color: #ff8800; -fx-border-width: 3px; -fx-border-radius: 5px; -fx-background-radius: 5px;");
            drawingArea.setCursor(javafx.scene.Cursor.CROSSHAIR);
        } else {
            crayonButton.setStyle("-fx-background-color: #fad489; -fx-border-color: #ffaa00; -fx-border-width: 2px; -fx-border-radius: 5px; -fx-background-radius: 5px;");
            drawingArea.setCursor(javafx.scene.Cursor.DEFAULT);
            finishDrawing();
        }
    }
    
    @FXML
    private void handleMousePressed(MouseEvent event) {
        if (drawingMode) {
            // Commencer un nouveau tracé
            currentPath.clear();
            clearPathPoints();
            
            // Ajouter le premier point
            addPoint(event.getX(), event.getY());
        }
    }
    
    @FXML
    private void handleMouseDragged(MouseEvent event) {
        if (drawingMode && !currentPath.isEmpty()) {
            // Ajouter des points pendant le glissement
            addPoint(event.getX(), event.getY());
        }
    }
    
    @FXML
    private void handleMouseReleased(MouseEvent event) {
        if (drawingMode && currentPath.size() >= 6) { // Au moins 3 points (x,y pour chaque)
            // Vérifier si le tracé forme une boucle fermée
            double startX = currentPath.get(0);
            double startY = currentPath.get(1);
            double endX = event.getX();
            double endY = event.getY();
            
            // Si on est proche du point de départ (dans un rayon de 20 pixels)
            double distance = Math.sqrt(Math.pow(endX - startX, 2) + Math.pow(endY - startY, 2));
            
            if (distance <= 25) {
                // Fermer la forme et la remplir
                createPizzaDough();
            }
        }
    }
    
    private void addPoint(double x, double y) {
        currentPath.add(x);
        currentPath.add(y);
        
        // Créer un petit cercle pour visualiser le tracé
        Circle point = new Circle(x, y, 2);
        point.setFill(Color.web("#8B4513"));
        point.setStroke(Color.web("#654321"));
        point.setStrokeWidth(1);
        
        pathPoints.add(point);
        drawingArea.getChildren().add(point);
    }
    
    private void createPizzaDough() {
        // Supprimer les points de tracé
        clearPathPoints();
        
        // Créer un polygone avec les points du tracé
        Polygon dough = new Polygon();
        dough.getPoints().addAll(currentPath);
        
        // Style de la pâte à pizza
        dough.setFill(Color.web("#FEF9EA"));
        dough.setStroke(Color.web("#E6D3A3"));
        dough.setStrokeWidth(3);
        dough.setStrokeType(StrokeType.INSIDE);
        
        // Ajouter un effet d'ombre légère
        dough.setEffect(new javafx.scene.effect.DropShadow(5, Color.web("#00000020")));
        
        drawingArea.getChildren().add(dough);
        
        // Réinitialiser
        currentPath.clear();
        drawingMode = false;
        crayonButton.setStyle("-fx-background-color: #fad489; -fx-border-color: #ffaa00; -fx-border-width: 2px; -fx-border-radius: 5px; -fx-background-radius: 5px;");
        drawingArea.setCursor(javafx.scene.Cursor.DEFAULT);
    }
    
    private void clearPathPoints() {
        for (Circle point : pathPoints) {
            drawingArea.getChildren().remove(point);
        }
        pathPoints.clear();
    }
    
    private void finishDrawing() {
        // Nettoyer si on annule le dessin
        clearPathPoints();
        currentPath.clear();
    }
    
    // Méthodes pour les autres boutons d'ingrédients
    @FXML
    private void handleTomateClick() {
        // Logique pour la sauce tomate
    }
    
    @FXML
    private void handleCremeClick() {
        // Logique pour la crème
    }
    
    @FXML
    private void handleFromageRapeClick() {
        // Logique pour le fromage râpé
    }
    
    @FXML
    private void handleMozzarellaClick() {
        // Logique pour la mozzarella
    }
    
    @FXML
    private void handleBasilicClick() {
        // Logique pour le basilic
    }
    
    @FXML
    private void handleViandeClick() {
        // Logique pour la viande hachée
    }
    
    @FXML
    private void handleChorizoClick() {
        // Logique pour le chorizo
    }
}