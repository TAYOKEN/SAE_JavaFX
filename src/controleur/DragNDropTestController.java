package controleur;

import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Ellipse;
import javafx.scene.paint.Color;
import javafx.scene.shape.StrokeType;
import javafx.scene.Node;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.Parent;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class DragNDropTestController {
    
    @FXML
    private Button crayonButton;
    
    @FXML
    private Button tomateButton;
    
    @FXML
    private Button cremeButton;
    
    @FXML
    private Button fromageRapeButton;
    
    @FXML
    private Button mozzarellaButton;
    
    @FXML
    private Button basilicButton;
    
    @FXML
    private Button viandeButton;
    
    @FXML
    private Button chorizoButton;
    
    // Nouveaux boutons d'action
    @FXML
    private Button retourButton;
    
    @FXML
    private Button toutSupprimerButton;
    
    @FXML
    private Button quitterButton;
    
    @FXML
    private Pane drawingArea;
    
    private boolean drawingMode = false;
    private String selectedIngredient = null;
    private List<Double> currentPath = new ArrayList<>();
    private List<Circle> pathPoints = new ArrayList<>();
    private List<Polygon> pizzaDoughs = new ArrayList<>();
    
    // Variables pour le drag & drop
    private Node draggedNode = null;
    private double dragStartX, dragStartY;
    
    // Variables pour l'application des sauces en glissant
    private boolean isDraggingSauce = false;
    private double lastSauceX = -1, lastSauceY = -1;
    
    // Système d'historique pour l'annulation (version simplifiée)
    private Stack<List<Node>> historyStack = new Stack<>();
    private static final int MAX_HISTORY_SIZE = 20;
    
    @FXML
    private void initialize() {
        drawingArea.setStyle("-fx-background-color: #F4E3C1;");
        // Sauvegarder l'état initial
        saveCurrentState();
    }
    
    @FXML
    private void handleCrayonClick() {
        resetButtonStyles();
        selectedIngredient = null;
        
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
            currentPath.clear();
            clearPathPoints();
            addPoint(event.getX(), event.getY());
        } else if (selectedIngredient != null) {
            // Vérifier si on clique sur une pâte existante
            if (isOnPizzaDough(event.getX(), event.getY())) {
                saveCurrentState(); // Sauvegarder avant modification
                if (isSauce(selectedIngredient)) {
                    // Pour les sauces, commencer le mode glisser
                    isDraggingSauce = true;
                    lastSauceX = event.getX();
                    lastSauceY = event.getY();
                    applySauceAt(event.getX(), event.getY());
                } else {
                    // Pour les autres ingrédients, placement normal
                    placeIngredient(event.getX(), event.getY());
                }
            }
        } else {
            // Mode drag & drop - vérifier si on clique sur un ingrédient
            Node clickedNode = getNodeAt(event.getX(), event.getY());
            if (clickedNode != null && !isPizzaDough(clickedNode)) {
                draggedNode = clickedNode;
                dragStartX = event.getX();
                dragStartY = event.getY();
                drawingArea.setCursor(javafx.scene.Cursor.MOVE);
            }
        }
    }
    
    @FXML
    private void handleMouseDragged(MouseEvent event) {
        if (drawingMode && !currentPath.isEmpty()) {
            addPoint(event.getX(), event.getY());
        } else if (isDraggingSauce && selectedIngredient != null && isSauce(selectedIngredient)) {
            // Appliquer la sauce pendant le glissement
            if (isOnPizzaDough(event.getX(), event.getY())) {
                // Calculer la distance depuis le dernier point
                double distance = Math.sqrt(Math.pow(event.getX() - lastSauceX, 2) + Math.pow(event.getY() - lastSauceY, 2));
                
                // Appliquer la sauce à intervalles réguliers pour créer un effet continu
                if (distance > 8) { // Ajuster cette valeur pour contrôler la densité
                    applySauceAt(event.getX(), event.getY());
                    lastSauceX = event.getX();
                    lastSauceY = event.getY();
                }
            }
        } else if (draggedNode != null) {
            // Déplacer l'ingrédient
            double deltaX = event.getX() - dragStartX;
            double deltaY = event.getY() - dragStartY;
            
            draggedNode.setTranslateX(draggedNode.getTranslateX() + deltaX);
            draggedNode.setTranslateY(draggedNode.getTranslateY() + deltaY);
            
            dragStartX = event.getX();
            dragStartY = event.getY();
        }
    }
    
    @FXML
    private void handleMouseReleased(MouseEvent event) {
        if (drawingMode && currentPath.size() >= 6) {
            double startX = currentPath.get(0);
            double startY = currentPath.get(1);
            double endX = event.getX();
            double endY = event.getY();
            
            double distance = Math.sqrt(Math.pow(endX - startX, 2) + Math.pow(endY - startY, 2));
            
            if (distance <= 25) {
                saveCurrentState(); // Sauvegarder avant de créer la pâte
                createPizzaDough();
            }
        } else if (isDraggingSauce) {
            // Arrêter l'application de sauce
            isDraggingSauce = false;
            lastSauceX = -1;
            lastSauceY = -1;
        } else if (draggedNode != null) {
            // Vérifier si l'ingrédient est toujours sur une pâte
            double centerX = draggedNode.getBoundsInParent().getCenterX();
            double centerY = draggedNode.getBoundsInParent().getCenterY();
            
            if (!isOnPizzaDough(centerX, centerY)) {
                // Remettre l'ingrédient à sa position d'origine ou le supprimer
                drawingArea.getChildren().remove(draggedNode);
            }
            
            draggedNode = null;
            drawingArea.setCursor(javafx.scene.Cursor.DEFAULT);
        }
    }
    
    // Nouvelles méthodes pour les boutons d'action
    @FXML
    private void handleRetourClick() {
        if (historyStack.size() > 1) {
            historyStack.pop(); // Enlever l'état actuel
            List<Node> previousState = historyStack.peek();
            restoreState(previousState);
        } else {
            showAlert("Information", "Aucune action à annuler.", Alert.AlertType.INFORMATION);
        }
    }
    
    @FXML
    private void handleToutSupprimerClick() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Tout supprimer");
        alert.setContentText("Êtes-vous sûr de vouloir supprimer toute la pizza ?");
        
        if (alert.showAndWait().orElse(ButtonType.CANCEL) == ButtonType.OK) {
            saveCurrentState();
            clearDrawingArea();
        }
    }
    
    @FXML
    private void handleQuitterClick() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Quitter");
        alert.setContentText("Voulez-vous vraiment quitter ?");
        
        if (alert.showAndWait().orElse(ButtonType.CANCEL) == ButtonType.OK) {
            returnToMenu();
        }
    }
    
    // Méthodes utilitaires pour la gestion de l'historique (simplifiée)
    private void saveCurrentState() {
        List<Node> currentState = new ArrayList<>(drawingArea.getChildren());
        historyStack.push(currentState);
        
        // Limiter la taille de l'historique
        if (historyStack.size() > MAX_HISTORY_SIZE) {
            Stack<List<Node>> newStack = new Stack<>();
            for (int i = 1; i < historyStack.size(); i++) {
                newStack.push(historyStack.get(i));
            }
            historyStack = newStack;
        }
    }
    
    private void restoreState(List<Node> state) {
        // Vider la zone de dessin
        drawingArea.getChildren().clear();
        pizzaDoughs.clear();
        
        // Restaurer les éléments (version simplifiée)
        for (Node node : state) {
            drawingArea.getChildren().add(node);
            if (node instanceof Polygon) {
                pizzaDoughs.add((Polygon) node);
            }
        }
    }
    
    private void clearDrawingArea() {
        drawingArea.getChildren().clear();
        pizzaDoughs.clear();
        pathPoints.clear();
        currentPath.clear();
        drawingMode = false;
        selectedIngredient = null;
        resetButtonStyles();
        drawingArea.setCursor(javafx.scene.Cursor.DEFAULT);
    }
    
    private void returnToMenu() {
        try {
            // Ici vous devrez adapter le chemin vers votre FXML de menu principal
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/vue/Menu.fxml"));
            Parent root = loader.load();
            
            Stage stage = (Stage) quitterButton.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            showAlert("Erreur", "Impossible de retourner au menu : " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }
    
    private void showAlert(String title, String message, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    
   private boolean isSauce(String ingredient) {
        return "TOMATE".equals(ingredient) || "CREME".equals(ingredient);
    }
    
    private void applySauceAt(double x, double y) {
        Node sauce = null;
        
        switch (selectedIngredient) {
            case "TOMATE":
                // Sauce tomate - cercle rouge semi-transparent plus petit pour l'effet étalé
                Circle tomato = new Circle(x, y, 8 + Math.random() * 4); // Taille variable
                tomato.setFill(Color.web("#FF4500"));
                sauce = tomato;
                break;
                
            case "CREME":
                // Crème - cercle blanc/beige semi-transparent plus petit pour l'effet étalé
                Circle creme = new Circle(x, y, 7 + Math.random() * 3); // Taille variable
                creme.setFill(Color.web("#FFFFFF"));
                sauce = creme;
                break;
        }
        
        if (sauce != null) {
            drawingArea.getChildren().add(sauce);
        }
    }
    
    private void addPoint(double x, double y) {
        currentPath.add(x);
        currentPath.add(y);
        
        Circle point = new Circle(x, y, 2);
        point.setFill(Color.web("#8B4513"));
        point.setStroke(Color.web("#654321"));
        point.setStrokeWidth(1);
        
        pathPoints.add(point);
        drawingArea.getChildren().add(point);
    }
    
    private void createPizzaDough() {
        clearPathPoints();
        
        Polygon dough = new Polygon();
        dough.getPoints().addAll(currentPath);
        
        dough.setFill(Color.web("#FEF9EA"));
        dough.setStroke(Color.web("#E6D3A3"));
        dough.setStrokeWidth(3);
        dough.setStrokeType(StrokeType.INSIDE);
        dough.setEffect(new javafx.scene.effect.DropShadow(5, Color.web("#00000020")));
        
        pizzaDoughs.add(dough);
        drawingArea.getChildren().add(dough);
        
        currentPath.clear();
        drawingMode = false;
        crayonButton.setStyle("-fx-background-color: #fad489; -fx-border-color: #ffaa00; -fx-border-width: 2px; -fx-border-radius: 5px; -fx-background-radius: 5px;");
        drawingArea.setCursor(javafx.scene.Cursor.DEFAULT);
    }
    
    private boolean isOnPizzaDough(double x, double y) {
        for (Polygon dough : pizzaDoughs) {
            if (dough.contains(x, y)) {
                return true;
            }
        }
        return false;
    }
    
    private boolean isPizzaDough(Node node) {
        return pizzaDoughs.contains(node);
    }
    
    private Node getNodeAt(double x, double y) {
        for (Node node : drawingArea.getChildren()) {
            if (node.getBoundsInParent().contains(x, y) && !isPizzaDough(node)) {
                return node;
            }
        }
        return null;
    }
    
    private void placeIngredient(double x, double y) {
        Node ingredient = null;
        
        switch (selectedIngredient) {
            case "FROMAGE_RAPE":
                // Fromage râpé - petits rectangles jaunes
                Rectangle fromage = new Rectangle(x-4, y-2, 8, 4);
                fromage.setFill(Color.web("#FFD700"));
                fromage.setStroke(Color.web("#FFA500"));
                fromage.setStrokeWidth(0.5);
                fromage.setRotate(Math.random() * 360);
                ingredient = fromage;
                break;
                
            case "MOZZARELLA":
                // Mozzarella - cercle blanc
                Circle mozzarella = new Circle(x, y, 10);
                mozzarella.setFill(Color.web("#FFFACD"));
                mozzarella.setStroke(Color.web("#F0E68C"));
                mozzarella.setStrokeWidth(1);
                ingredient = mozzarella;
                break;
                
            case "BASILIC":
                // Basilic - petite ellipse verte
                Ellipse basilic = new Ellipse(x, y, 6, 3);
                basilic.setFill(Color.web("#228B22"));
                basilic.setStroke(Color.web("#006400"));
                basilic.setStrokeWidth(0.5);
                basilic.setRotate(Math.random() * 360);
                ingredient = basilic;
                break;
                
            case "VIANDE":
                // Viande hachée - petit cercle brun
                Circle viande = new Circle(x, y, 5);
                viande.setFill(Color.web("#8B4513"));
                viande.setStroke(Color.web("#654321"));
                viande.setStrokeWidth(0.5);
                ingredient = viande;
                break;
                
            case "CHORIZO":
                // Chorizo - petit cercle rouge foncé
                Circle chorizo = new Circle(x, y, 6);
                chorizo.setFill(Color.web("#DC143C"));
                chorizo.setStroke(Color.web("#B22222"));
                chorizo.setStrokeWidth(0.5);
                ingredient = chorizo;
                break;
        }
        
        if (ingredient != null) {
            drawingArea.getChildren().add(ingredient);
        }
    }
    
    private void resetButtonStyles() {
        String defaultStyle = "-fx-background-color: #fad489; -fx-border-color: #ffaa00; -fx-border-width: 2px; -fx-border-radius: 5px; -fx-background-radius: 5px;";
        crayonButton.setStyle(defaultStyle);
        tomateButton.setStyle(defaultStyle);
        cremeButton.setStyle(defaultStyle);
        fromageRapeButton.setStyle(defaultStyle);
        mozzarellaButton.setStyle(defaultStyle);
        basilicButton.setStyle(defaultStyle);
        viandeButton.setStyle(defaultStyle);
        chorizoButton.setStyle(defaultStyle);
    }
    
    private void setSelectedIngredient(String ingredient, Button button) {
        resetButtonStyles();
        selectedIngredient = ingredient;
        drawingMode = false;
        
        // Style du bouton sélectionné
        button.setStyle("-fx-background-color: #ffaa00; -fx-border-color: #ff8800; -fx-border-width: 3px; -fx-border-radius: 5px; -fx-background-radius: 5px;");
        
        // Changer le curseur selon le type d'ingrédient
        if (isSauce(ingredient)) {
            drawingArea.setCursor(javafx.scene.Cursor.CROSSHAIR);
        } else {
            drawingArea.setCursor(javafx.scene.Cursor.CROSSHAIR);
        }
    }
    
    private void clearPathPoints() {
        for (Circle point : pathPoints) {
            drawingArea.getChildren().remove(point);
        }
        pathPoints.clear();
    }
    
    private void finishDrawing() {
        clearPathPoints();
        currentPath.clear();
    }
    
    // Méthodes pour les boutons d'ingrédients
    @FXML
    private void handleTomateClick() {
        setSelectedIngredient("TOMATE", tomateButton);
    }
    
    @FXML
    private void handleCremeClick() {
        setSelectedIngredient("CREME", cremeButton);
    }
    
    @FXML
    private void handleFromageRapeClick() {
        setSelectedIngredient("FROMAGE_RAPE", fromageRapeButton);
    }
    
    @FXML
    private void handleMozzarellaClick() {
        setSelectedIngredient("MOZZARELLA", mozzarellaButton);
    }
    
    @FXML
    private void handleBasilicClick() {
        setSelectedIngredient("BASILIC", basilicButton);
    }
    
    @FXML
    private void handleViandeClick() {
        setSelectedIngredient("VIANDE", viandeButton);
    }
    
    @FXML
    private void handleChorizoClick() {
        setSelectedIngredient("CHORIZO", chorizoButton);
    }
}