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
import javafx.scene.AccessibleRole;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.ListCell;
import javafx.scene.layout.*;
import javafx.scene.shape.StrokeType;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;

public class GameViewController implements Initializable {

    private static User player;

    // use @FXML injection to avoid overwriting the FXML View's objects and causing problems
    @FXML
    private JFXListView<Resource> resourcesListView;
    @FXML
    private JFXListView<Factory> productionListView;
    //@FXML private JFXListView<Research> researchListView;
    @FXML
    private JFXButton menuButton;
    @FXML
    private JFXButton settingsButton;
    @FXML
    private JFXButton balanceButton = new JFXButton();
    @FXML
    private JFXButton buyProductionIncrementButton = new JFXButton();
    @FXML
    private JFXButton buyStorageIncrementButton = new JFXButton();
    @FXML
    private JFXButton sellIncrementButton = new JFXButton();

    private ObservableList<Resource> resourcesList;
    private ObservableList<Factory> factoryList;
    //private private ObservableList<Research> researchList;

    public GameViewController() {
        player = new User(100000000);
    }

    public void start() {
        final double tickTime = 0.1;
        Timeline timer = new Timeline((new KeyFrame(Duration.millis(tickTime), event -> tick((tickTime / 1000)))));
        timer.setCycleCount(Animation.INDEFINITE);
        timer.play();
    }

    public void tick(double passedTime) {
        double moneyChange;
        for (int i = 0; i < resourcesList.size(); i++) {
            moneyChange = resourcesList.get(i).updateResourceData(passedTime, 1);
            if (moneyChange > 0) {
                player.addMoney(moneyChange);
            }
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
        player.buyProductionIncrementProperty().addListener(v -> {
            buyProductionIncrementButton.setText(String.format("buy prod"));
        });

        player.buyProductionIncrementProperty().addListener(v -> {
            buyStorageIncrementButton.setText(String.format("buy stor"));
        });

        player.buyProductionIncrementProperty().addListener(v -> {
            sellIncrementButton.setText(String.format("sell"));
        });

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

        Resource moistureFarm = new Resource(
                "Moisture Farm",
                1000.0,
                2000.0,
                100.0,
                2.0,
                100
        );

        resourcesList.addAll(lemons, sugar, apples, bananas, moistureFarm);

        ////
        // Import factories:

        Factory lemonade = new Factory(
                "Lemonade",
                lemons, sugar, null, null,
                3, 1, 0, 0,
                12.0, 5.0
        );

        factoryList.add(lemonade);

        factoryList.add(new Factory(
                "Sherbet lemons",
                lemons, sugar, null, null,
                1, 2, 0, 0,
                15.0, 10.0
        ));

        ////
        // Import research:


    }

    @FXML
    private void clickBuyProductionIncrementButton() {
        // cycles between buy production modes
        if (player.buyProductionIncrementProperty().get() == 1) {
            player.buyProductionIncrementProperty().set(10);
        } else if (player.buyProductionIncrementProperty().get() == 10) {
            player.buyProductionIncrementProperty().set(100);
        } else if (player.buyProductionIncrementProperty().get() == 100) {
            player.buyProductionIncrementProperty().set(-1);
        } else {
            player.buyProductionIncrementProperty().set(1);
        }
    }

    @FXML
    private void clickBuyStorageIncrementButton() {
        // cycles between buy storage modes
        if (player.buyStorageIncrementProperty().get() == 1) {
            player.buyStorageIncrementProperty().set(10);
        } else if (player.buyStorageIncrementProperty().get() == 10) {
            player.buyStorageIncrementProperty().set(100);
        } else if (player.buyStorageIncrementProperty().get() == 100) {
            player.buyStorageIncrementProperty().set(-1);
        } else {
            player.buyStorageIncrementProperty().set(1);
        }
    }

    @FXML
    private void clickSellIncrementButton() {
        // cycles between sell modes
        if (player.sellResourceIncrementProperty().get() == 1) {
            player.sellResourceIncrementProperty().set(10);
        } else if (player.sellResourceIncrementProperty().get() == 10) {
            player.sellResourceIncrementProperty().set(100);
        } else if (player.sellResourceIncrementProperty().get() == 100) {
            player.sellResourceIncrementProperty().set(-1);
        } else {
            player.sellResourceIncrementProperty().set(1);
        }
    }

    @FXML
    private void clickMenuButton() {

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

        // CREATES COMPONENTS

        final HBox hbox1 = new HBox();
        final StackPane  stackPane1 = new StackPane();
        final VBox vbox1 = new VBox();

        final JFXButton iconButton = new JFXButton("ICON");
        final JFXButton iconNumberButton = new JFXButton("100");

        final HBox hbox2 = new HBox();
        final HBox hbox3 = new HBox();

        final StackPane stackPane2 = new StackPane();
        final StackPane stackPane3 = new StackPane();
        final StackPane stackPane4 = new StackPane();
        final StackPane stackPane5 = new StackPane();

        final JFXProgressBar progressBar = new JFXProgressBar();
        final Text progressBarText = new Text();
        final JFXButton storageTextButton = new JFXButton();

        final JFXButton timerButton = new JFXButton();
        final JFXButton buyProducerButton = new JFXButton();
        final JFXButton buyProducerBuyButton = new JFXButton();
        final JFXButton buyProducerWordButton = new JFXButton();

        final JFXButton buyStorerButton = new JFXButton();
        final JFXButton buyStorerBuyButton = new JFXButton();
        final JFXButton buyStorerWordButton = new JFXButton();

        final JFXButton sellButton = new JFXButton();
        final JFXButton sellSellButton = new JFXButton();
        final JFXButton sellWordButton = new JFXButton();

        /*
        final VBox vbox = new VBox();
        final HBox hbox1 = new HBox();
        final HBox hbox2 = new HBox();

        final JFXButton nameButton = new JFXButton("NAME");
        final JFXButton numProducersButton = new JFXButton("5");
        final Pane paneTop = new Pane();
        final StackPane timerProgressStackpane = new StackPane();
        final Text timerProgressText = new Text("0");
        final JFXProgressBar timerProgressBar = new JFXProgressBar();

        final JFXButton sellButton = new JFXButton("SELL ONE");
        final JFXButton buyProducerButton = new JFXButton("BUY PRODUCER");
        final JFXButton buyStorerButton = new JFXButton("BUY STORAGE");
        final Pane paneBottom = new Pane();
        final StackPane storageProgressStackpane = new StackPane();
        final Text storageText = new Text("0 / 0");
        final JFXProgressBar storageProgressBar = new JFXProgressBar();
        */

        public ResourceCell() {
            super();
            setProperties();
        }

        @Override
        public void updateItem(Resource resource, boolean empty) {
            super.updateItem(resource, empty);
            setGraphic(hbox1);
            /*
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

                    if (resource.getProductionTime() != 0) { // Ensure doesn't divide by 0
                        timerProgressBar.setProgress(timeLeft / resource.getProductionTime());
                    } else {
                        Main.outputError("Production time = 0. Cannot divide by 0.");
                    }

                });

                // UPDATE STORAGE PROGRESSBAR AND TEXT WHEN VALUES CHANGE
                resource.currentStorageProperty().addListener(v -> updateStorageGUI(resource));
                resource.maxStorageProperty().addListener(v -> updateStorageGUI(resource));

                // Update values so listeners are called immediately
                resource.currentStorageProperty().setValue(resource.getCurrentStorage() + 1);
                resource.currentStorageProperty().setValue(resource.getCurrentStorage() - 1);

                resource.timeSinceProductionProperty().setValue(resource.getTimeSinceProduction() + 1);
                resource.timeSinceProductionProperty().setValue(resource.getTimeSinceProduction() - 1);

                setButtonActions(resource);

                // if(!nameButton.textProperty().isBound()) {}
                // NOTE: adding the conditional above could improve efficiency
            }
            */
        }

        private void updateStorageGUI(Resource resource) {
            //System.out.println("storageText: " + storageText.getText());
            //System.out.println(resource.currentStorageProperty().getValue());
            //System.out.println(resource.maxStorageProperty().getValue());

            /*
            storageText.setText(Integer.toString(resource.getCurrentStorage()) + " / " + resource.getMaxStorage());

            if (resource.getMaxStorage() != 0) { // Ensure doesn't divide by 0
                storageProgressBar.setProgress((double) resource.getCurrentStorage() / (double) resource.getMaxStorage());
            } else {
                Main.outputError("Max storage = 0. Cannot divide by 0.");
            }
        }

        public void setButtonActions(Resource resource) {
            sellButton.setOnAction(v -> {

                // sell single item
                if (resource.currentStorageProperty().get() > player.sellResourceIncrementProperty().get()) {
                    resource.currentStorageProperty().set(resource.currentStorageProperty().get() - player.sellResourceIncrementProperty().get());
                    player.addMoney(resource.getMarketValue() * player.sellResourceIncrementProperty().get());
                }
            });


            buyProducerButton.setOnAction(v -> {

                // gives more production to the user
                if (player.ableToSpend(resource.getProducerCost() * player.buyProductionIncrementProperty().get()) == true) {
                    resource.producerCountProperty().set(resource.producerCountProperty().get() + player.buyProductionIncrementProperty().get());
                    player.subtractMoney(resource.getProducerCost() * player.buyProductionIncrementProperty().get());
                }
            });

            buyStorerButton.setOnAction(v -> {

                // gives the user more storage for the resource
                if (player.ableToSpend(resource.getStorerCost() * player.buyStorageIncrementProperty().get()) == true) {
                    resource.maxStorageProperty().set(resource.maxStorageProperty().get() + (resource.getStorageIncrement() * player.buyStorageIncrementProperty().get()));
                    resource.setStorerCount(resource.getStorerCount() + player.buyStorageIncrementProperty().get());
                    player.subtractMoney(resource.getStorerCost() * player.buyStorageIncrementProperty().get());
                }
            });
            */
        }

        private void setProperties() {

            /// TOP ROW

            hbox1.setMaxHeight(Double.NEGATIVE_INFINITY);
            hbox1.setMaxWidth(Double.NEGATIVE_INFINITY);
            hbox1.setPrefHeight(100.0);
            hbox1.setPrefWidth(460.0);

            stackPane1.setMaxHeight(Double.NEGATIVE_INFINITY);
            stackPane1.setMaxWidth(Double.NEGATIVE_INFINITY);
            stackPane1.setMinHeight(Double.NEGATIVE_INFINITY);
            stackPane1.setMinWidth(Double.NEGATIVE_INFINITY);
            stackPane1.setPrefHeight(100.0);
            stackPane1.setPrefWidth(100.0);

            vbox1.setMaxHeight(Double.NEGATIVE_INFINITY);
            vbox1.setMaxWidth(Double.NEGATIVE_INFINITY);
            vbox1.setMinHeight(Double.NEGATIVE_INFINITY);
            vbox1.setMinWidth(Double.NEGATIVE_INFINITY);
            vbox1.prefHeight(100.0);
            vbox1.prefWidth(360.0);

            iconButton.setAccessibleRole(AccessibleRole.TEXT);
            iconButton.setAlignment(Pos.CENTER);
            iconButton.setContentDisplay(ContentDisplay.CENTER);
            iconButton.setEllipsisString("..");
            iconButton.setFocusTraversable(false);
            iconButton.setMaxHeight(1.7976931348623157E308);
            iconButton.setMaxWidth(1.7976931348623157E308);
            iconButton.setText("ICON");

            iconNumberButton.setFocusTraversable(false);
            iconNumberButton.setMaxWidth(1.7976931348623157E308);
            iconNumberButton.setMouseTransparent(true);
            iconNumberButton.setText("100");

            hbox2.setMaxHeight(Double.NEGATIVE_INFINITY);
            hbox2.setMaxWidth(Double.NEGATIVE_INFINITY);
            hbox2.setMinHeight(Double.NEGATIVE_INFINITY);
            hbox2.setMinWidth(Double.NEGATIVE_INFINITY);
            hbox2.prefHeight(50.0);
            hbox2.prefWidth(360.0);

            hbox3.setMaxHeight(Double.NEGATIVE_INFINITY);
            hbox3.setMaxWidth(Double.NEGATIVE_INFINITY);
            hbox3.setMinHeight(Double.NEGATIVE_INFINITY);
            hbox3.setMinWidth(Double.NEGATIVE_INFINITY);
            hbox3.prefHeight(50.0);
            hbox3.prefWidth(360.0);

            stackPane2.setLayoutX(122.0);
            stackPane2.setLayoutY(10.0);
            stackPane2.setMaxHeight(Double.NEGATIVE_INFINITY);
            stackPane2.setMaxWidth(1.7976931348623157E308);
            stackPane2.setMinHeight(Double.NEGATIVE_INFINITY);
            stackPane2.setMinWidth(Double.NEGATIVE_INFINITY);
            stackPane2.setPrefHeight(50.0);
            stackPane2.setPrefWidth(240.0);

            progressBar.setMaxHeight(1.7976931348623157E308);
            progressBar.setMaxWidth(1.7976931348623157E308);
            progressBar.setMinHeight(Double.NEGATIVE_INFINITY);

            progressBarText.setStrokeType(StrokeType.OUTSIDE);
            progressBarText.setStrokeWidth(0.0);
            progressBarText.setTextAlignment(TextAlignment.CENTER);
            progressBarText.setText("$10");

            storageTextButton.setAccessibleRole(AccessibleRole.TEXT);
            storageTextButton.setAlignment(Pos.BOTTOM_RIGHT);
            storageTextButton.setContentDisplay(ContentDisplay.CENTER);
            storageTextButton.setEllipsisString("..");
            storageTextButton.setFocusTraversable(false);
            storageTextButton.setMaxHeight(1.7976931348623157E308);
            storageTextButton.setMaxWidth(1.7976931348623157E308);
            storageTextButton.setMinHeight(Double.NEGATIVE_INFINITY);
            storageTextButton.setMinWidth(Double.NEGATIVE_INFINITY);
            storageTextButton.setMouseTransparent(true);
            storageTextButton.setPrefWidth(120.0);
            storageTextButton.setTextAlignment(TextAlignment.CENTER);
            storageTextButton.setText("10 / 100");

            timerButton.setMaxHeight(1.7976931348623157E308);
            timerButton.setMaxWidth(Double.NEGATIVE_INFINITY);
            timerButton.setPrefWidth(120.0);

            stackPane3.setMaxHeight(1.7976931348623157E308);
            stackPane3.setPrefWidth(120.0);

            stackPane4.setLayoutX(10.0);
            stackPane4.setLayoutY(10.0);
            stackPane4.setMaxHeight(1.7976931348623157E308);
            stackPane4.setPrefWidth(120.0);

            stackPane5.setLayoutX(130.0);
            stackPane5.setLayoutY(10.0);
            stackPane5.setMaxHeight(1.7976931348623157E308);
            stackPane5.setPrefWidth(120.0);

            buyProducerButton.setAlignment(Pos.CENTER);
            buyProducerButton.setFocusTraversable(false);
            buyProducerButton.setMaxHeight(1.7976931348623157E308);
            buyProducerButton.setMaxWidth(1.7976931348623157E308);
            buyProducerButton.setMouseTransparent(true);

            buyProducerBuyButton.setAlignment(Pos.TOP_LEFT);
            buyProducerBuyButton.setText("Buy");

            buyProducerWordButton.setText("Million");

            buyStorerButton.setAlignment(Pos.CENTER);
            buyStorerButton.setFocusTraversable(false);
            buyStorerButton.setMaxHeight(1.7976931348623157E308);
            buyStorerButton.setMaxWidth(1.7976931348623157E308);
            buyStorerButton.setMouseTransparent(true);

            buyStorerBuyButton.setAlignment(Pos.TOP_LEFT);
            buyStorerBuyButton.setText("Buy");

            buyStorerWordButton.setText("Million");

            sellButton.setAlignment(Pos.CENTER);
            sellButton.setFocusTraversable(false);
            sellButton.setMaxHeight(1.7976931348623157E308);
            sellButton.setMaxWidth(1.7976931348623157E308);
            sellButton.setMouseTransparent(true);

            sellSellButton.setAlignment(Pos.TOP_LEFT);
            sellSellButton.setText("Buy");

            sellWordButton.setText("Million");

            //ADDING CHILDREN

            hbox1.getChildren().addAll(stackPane1, vbox1);
            stackPane1.getChildren().addAll(iconButton, iconNumberButton);
            vbox1.getChildren().addAll(hbox2, hbox3);
            hbox2.getChildren().addAll(stackPane2, timerButton);
            hbox3.getChildren().addAll(stackPane3, stackPane4, stackPane5);
            stackPane3.getChildren().addAll(buyProducerButton, buyProducerBuyButton, buyProducerWordButton);
            stackPane4.getChildren().addAll(buyStorerButton, buyStorerBuyButton, buyStorerWordButton);
            stackPane5.getChildren().addAll(sellButton, sellSellButton, sellWordButton);
        }




        /*
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

        setupButton(sellButton);
        setupButton(buyProducerButton);
        setupButton(buyStorerButton);

        sellButton.setPrefWidth(60);
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
        hbox2.getChildren().addAll(sellButton, buyProducerButton, buyStorerButton, paneBottom,  storageProgressStackpane);


        vbox.setPrefWidth(460.0);
        vbox.setPrefHeight(50.0);
        vbox.getChildren().addAll(hbox1, hbox2);
    }
*/
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

