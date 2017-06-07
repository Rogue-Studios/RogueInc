import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXProgressBar;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;

public class GameViewController implements Initializable{
    // use @FXML injection to avoid overwriting the FXML View's objects and causing problems

    @FXML private JFXListView<Resource> resourcesListView;
    @FXML private JFXListView<Factory> productionListView;
    //@FXML private JFXListView<Research> researchListView;
    @FXML private JFXButton leaderboardButton;
    @FXML private JFXButton balanceButton = new JFXButton();
    @FXML private JFXButton settingsButton;

    private ObservableList<Resource> resourcesList;
    private ObservableList<Factory> factoryList;
    //private private ObservableList<Research> researchList;

    private static User player;

    public GameViewController() {
        player = new User(100);

    }

    public void start() {

        final double tickTime = 0.1;
        Timeline timer = new Timeline((new KeyFrame(Duration.millis(tickTime), event -> tick((tickTime / 1000)))));
        timer.setCycleCount(Animation.INDEFINITE);
        timer.play();

    }

    public void tick(double passedTime) {

        double moneyChange;
        moneyChange = resourcesList.get(0).updateResourceData(passedTime, 1);
        if(moneyChange > 0) {
            player.addMoney(moneyChange);
        }
        moneyChange = resourcesList.get(1).updateResourceData(passedTime, 1);
        if(moneyChange > 0) {
            player.addMoney(moneyChange);
        }
        moneyChange = resourcesList.get(2).updateResourceData(passedTime, 1);
        if(moneyChange > 0) {
            player.addMoney(moneyChange);
        }
        moneyChange = resourcesList.get(3).updateResourceData(passedTime, 1);
        if(moneyChange > 0) {
            player.addMoney(moneyChange);
        }
        moneyChange = resourcesList.get(4).updateResourceData(passedTime, 1);
        if(moneyChange > 0) {
            player.addMoney(moneyChange);
        }

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        // Set up arraylists and link them with listviews

        resourcesList = FXCollections.observableArrayList();
        factoryList = FXCollections.observableArrayList();
        //researchList = FXCollections.observableArrayList();

        resourcesListView.setItems(resourcesList);
        productionListView.setItems(factoryList);
        //researchListView.setItems(researchList);

        resourcesListView.setOnScrollTo(Event::consume);
        resourcesListView.setOnKeyPressed(Event::consume);
        productionListView.setOnScrollTo(Event::consume);
        productionListView.setOnKeyPressed(Event::consume);

       balanceButton.textProperty().bind(player.getBalance().asString("$%.0f"));

        importData();
        setCellFactories();
    }

    private void setCellFactories() {
        resourcesListView.setCellFactory(v -> new ResourceCell());
        productionListView.setCellFactory(null);
        //researchListView.setCellFactory();
    }

    private void importData() {

        ////
        // Import resources:
        Resource lemons = new Resource(
                "Lemons",
                50.0,
                10.0,
                2.0,
                5.0,
                10
        );

        Resource sugar = new Resource(
                "Sugar",
                100.0,
                20.0,
                5.0,
                8.0,
                5
        );

        Resource apples = new Resource(
                "Apples",
                200.0,
                40.0,
                1.0,
                0.5,
                50
        );

        Resource bananas = new Resource(
                "Bananas",
                400.0,
                1000.0,
                1.0,
                0.2,
                100
        );

        Resource moisture = new Resource(
                "Moisture",
                1000.0,
                2000.0,
                100.0,
                2.0,
                100
        );

        resourcesList.addAll(lemons, sugar, apples, bananas, moisture);

        ////
        // Import factories:

        Factory lemonade = new Factory(
                "Lemonade",
                lemons, sugar, null, null,
                3,1,0,0,
                12.0,5.0
        );

        factoryList.add(lemonade);

        factoryList.add(new Factory(
                "Sherbet lemons",
                lemons, sugar, null, null,
                1,2,0,0,
                15.0,10.0
        ));

        ////
        // Import research:


    }

    //////////////////////////////
    //       CUSTOM CELLS       //
    //////////////////////////////


    public ObservableList<Resource> getResourcesList() {
        return resourcesList;
    }

    public void setResourcesList(ObservableList<Resource> resourcesList) {
        this.resourcesList = resourcesList;
    }

    public ObservableList<Factory> getFactoryList() {
        return factoryList;
    }

    public void setFactoryList(ObservableList<Factory> factoryList) {
        this.factoryList = factoryList;
    }

    static class ResourceCell extends ListCell<Resource> {

        final VBox vbox = new VBox();
        final HBox hbox1 = new HBox();
        final HBox hbox2 = new HBox();

        final JFXButton nameButton = new JFXButton("NAME");
        final JFXButton numProducersButton = new JFXButton("5");
        final Pane paneTop = new Pane();
        final StackPane timerProgressStackpane = new StackPane();
        final Text timerProgressText = new Text("Nah");
        final JFXProgressBar timerProgressBar = new JFXProgressBar();

        final JFXButton sellOneButton = new JFXButton("SELL ONE");
        final JFXButton sellAllButton= new JFXButton("SELL ALL");
        final JFXButton buyProducerButton = new JFXButton("BUY PRODUCER");
        final JFXButton buyStorerButton = new JFXButton("BUY STORAGE");
        final Pane paneBottom = new Pane();
        final StackPane storageProgressStackpane = new StackPane();
        final Text storageText = new Text("0 / 0");
        final JFXProgressBar storageProgressBar = new JFXProgressBar();

        Resource resource;


        public ResourceCell() {
            super();
            setProperties();
            setButtonActions();
        }

        @Override
        public void updateItem(Resource resource, boolean empty) {
            super.updateItem(resource, empty);

            if (resource != null) {
                // Use vbox for cell display
                setGraphic(vbox);

                //// UPDATE RESOURCE NAME & PRODUCTION VALUES

                // nameText and productionAmountText will automatically update when the resource's name and producerCount change
                // UPDATE RESOURCE NAME & PRODUCER TEXT WHEN VALUES CHANGE
                nameButton.textProperty().bind(resource.nameProperty());

                resource.producerCountProperty().addListener(v -> {
                            numProducersButton.setText(String.format(resource.producerCountProperty().get() + "x"));
                });

                numProducersButton.setText("a");
                numProducersButton.setText(String.format(resource.producerCountProperty().get() + "x"));
                // productionAmountText.textProperty().bind(resource.producerCountProperty().asString("x" + resource.getMarketValue()));
                //timerProgressText.textProperty().bind(resource.timeSinceProductionProperty().asString());

                // UPDATE TIMER PROGRESSBAR AND TEXT WHEN TIME VALUE CHANGES
                resource.timeSinceProductionProperty().addListener(v -> {
                    Double timeLeft = resource.getProductionTime() - resource.getTimeSinceProduction();
                    timerProgressText.setText(String.format("%.2f", timeLeft));

                    if(resource.getProductionTime() != 0) { // Ensure doesn't divide by 0
                        timerProgressBar.setProgress(timeLeft / resource.getProductionTime());
                    } else {
                        Main.outputError("Production time = 0. Cannot divide by 0.");
                    }

                });

                // UPDATE STORAGE PROGRESSBAR AND TEXT WHEN VALUES CHANGE
                resource.currentStorageProperty().addListener(v -> {
                    storageText.setText(Integer.toString(resource.getCurrentStorage()) + " / " + resource.getMaxStorage());

                    if(resource.getMaxStorage() != 0) { // Ensure doesn't divide by 0
                        storageProgressBar.setProgress((double) resource.getCurrentStorage() / (double) resource.getMaxStorage());
                    } else {
                        Main.outputError("Max storage = 0. Cannot divide by 0.");
                    }

                });

                // Update values so listeners are called immediately
                resource.currentStorageProperty().setValue(resource.getCurrentStorage() + 1);
                resource.currentStorageProperty().setValue(resource.getCurrentStorage() - 1);

                resource.timeSinceProductionProperty().setValue(resource.getTimeSinceProduction() + 1);
                resource.timeSinceProductionProperty().setValue(resource.getTimeSinceProduction() - 1);

                // if(!nameButton.textProperty().isBound()) {}
                // NOTE: adding the conditional above could improve efficiency
            }

        }

        public void setButtonActions() {
            sellOneButton.setOnAction(v -> {
                updateCellResource();
                sellOne();
            });

            sellAllButton.setOnAction(v -> {
                updateCellResource();
                sellAll();
            });

            buyProducerButton.setOnAction(v -> {
                updateCellResource();
                addProducer();
            });

            buyStorerButton.setOnAction(v -> {
                updateCellResource();
                addStorer();
            });
        }

        private void updateCellResource() {
            resource = getItem();
        }

        private void sellOne() {
            // sell single item
            if(resource.currentStorageProperty().get() > 0) {
                resource.currentStorageProperty().set(resource.currentStorageProperty().get() - 1);
                player.addMoney(resource.getMarketValue());
            }
        }

        private void sellAll() {
            // sell all items
            if(resource.currentStorageProperty().get() > 0) {
                double totalValue;
                totalValue = resource.getMarketValue() * resource.currentStorageProperty().get();
                resource.currentStorageProperty().set(0);
                player.addMoney(totalValue);
            }
        }

        private void addStorer() {
            // gives the user more storage for the resource
            resource.maxStorageProperty().set(resource.maxStorageProperty().get() + resource.getStorageIncrement());
            resource.setStorerCount(resource.getStorerCount() + 1);
        }

        private void addProducer() {
            // gives more production to the user
            resource.producerCountProperty().set(resource.producerCountProperty().get() + 1);
        }

        private void setProperties() {

            /// TOP ROW

            setupButton(nameButton, 14.0, 2,0,0,30, 136, false, true);
            setupButton(numProducersButton, 15.0, 2.0, 0.0, 0.0, 0.0,55, false, true);

            nameButton.paddingProperty().setValue(new Insets(0,0,0,2));
            nameButton.setFont(new Font("System Bold", 14.0));
            numProducersButton.paddingProperty().setValue(new Insets(0,0,1,0));
            HBox.setHgrow(paneTop, Priority.ALWAYS);

            timerProgressStackpane.setPrefHeight(20.0);
            timerProgressStackpane.setPrefWidth(200.0);
            HBox.setMargin(timerProgressStackpane, new Insets(0,5.0,0,0));
            timerProgressStackpane.setPadding(new Insets(2.0,0,3.0,0));
            timerProgressBar.setPrefHeight(20.0);
            //StackPane.setMargin(timerProgressBar, new Insets(0));
            setupText(timerProgressText, 16.0, 0.0, 0.0, 0.0, 0.0, TextAlignment.CENTER);


            hbox1.setPrefHeight(25.0);
            hbox1.setPrefWidth(460.0);
            hbox1.setPadding(new Insets(3.0,0,2.0,0));

            timerProgressStackpane.getChildren().addAll(timerProgressBar, timerProgressText);
            hbox1.getChildren().addAll(nameButton, numProducersButton, paneTop, timerProgressStackpane);

            /// BOTTOM ROW

            setupButton(sellOneButton);
            setupButton(sellAllButton);
            setupButton(buyProducerButton);
            setupButton(buyStorerButton);

            sellOneButton.setPrefWidth(60);
            sellAllButton.setPrefWidth(60);
            buyProducerButton.setPrefWidth(87);
            buyStorerButton.setPrefWidth(80);
            HBox.setHgrow(paneBottom, Priority.ALWAYS);

            storageProgressStackpane.setPrefHeight(20.0);
            storageProgressStackpane.setPrefWidth(120.0);
            HBox.setMargin(storageProgressStackpane, new Insets(0,5,0,0));
            storageProgressBar.setPrefHeight(20.0);
            //StackPane.setMargin(storageProgressBar, new Insets(0));

            setupText(storageText, 15.0, 0.0, 0.0, 0.0, 0.0, TextAlignment.CENTER);
            StackPane.setMargin(storageText, new Insets(0,0,0,0));
            StackPane.setAlignment(storageText, Pos.CENTER);


            hbox2.setPrefHeight(25.0);
            hbox2.setPrefWidth(460.0);
            hbox2.setPadding(new Insets(2.0,0,3.0,0));

            storageProgressStackpane.getChildren().addAll(storageProgressBar, storageText);
            hbox2.getChildren().addAll(sellOneButton, sellAllButton, buyProducerButton, buyStorerButton, paneBottom,  storageProgressStackpane);


            vbox.setPrefWidth(460.0);
            vbox.setPrefHeight(50.0);
            vbox.getChildren().addAll(hbox1, hbox2);
        }

        private static void setupText(Text text, double fontSize, double top, double right, double bottom, double left, TextAlignment alignment) {
            text.setTextAlignment(alignment);
            text.setTextOrigin(VPos.CENTER);
            text.setFont(new Font(fontSize));
            HBox.setMargin(text, new Insets(top, right, bottom, left));
        }

        private static void setupButton(Button button) {
            button.setAlignment(Pos.CENTER);
            //button.setContentDisplay(ContentDisplay.CENTER);
            //button.setPrefSize(25.0, 25.0);
            button.setTextAlignment(TextAlignment.CENTER);
            button.setFocusTraversable(false);
            button.setEllipsisString("..");
            button.setFont(new Font(10.0));
            HBox.setMargin(button, new Insets(0,2,0,2));
        }

        public static void setupButton(Button button, double fontSize, double top, double right, double bottom, double left, double prefWidth, boolean focusTraversable, boolean mouseTransparent) {
            button.setAlignment(Pos.CENTER);
            button.setTextAlignment(TextAlignment.CENTER);
            button.setFocusTraversable(false);
            button.setEllipsisString("..");
            button.setFont(new Font(fontSize));
            HBox.setMargin(button, new Insets(top,right,bottom,left));
            button.setPrefWidth(prefWidth);
            button.setFocusTraversable(focusTraversable);
            button.setMouseTransparent(mouseTransparent);
        }

    }
}
