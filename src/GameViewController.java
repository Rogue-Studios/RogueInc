import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXProgressBar;
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

import java.net.URL;
import java.util.ResourceBundle;

public class GameViewController implements Initializable{
    // use @FXML injection to avoid overwriting the FXML View's objects and causing problems

    @FXML private JFXListView<Resource> resourcesListView;
    @FXML private JFXListView<Factory> productionListView;
    //@FXML private JFXListView<Research> researchListView;
    @FXML private JFXButton leaderboardButton;
    @FXML private JFXButton balanceButton;
    @FXML private JFXButton settingsButton;

    private ObservableList<Resource> resourcesList;
    private ObservableList<Factory> factoryList;
    //private private ObservableList<Research> researchList;

    private double balance;

    public GameViewController() {
        this.balance = 100.0;

    }
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
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

        importData();

        setCellFactories();

        resourcesList.get(0).setCurrentStorage(50);
        resourcesList.get(0).setMaxStorage(100);
        resourcesList.get(0).setTimeSinceProduction(5.00);
        resourcesList.get(0).nameProperty().setValue("Lemon test?");

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
                1.0,
                5.0
        );

        Resource sugar = new Resource(
                "Sugar",
                100.0,
                20.0,
                5.0,
                8.0
        );

        resourcesList.addAll(lemons, sugar);

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

    private void loadSaveData() {

    }

    private void writeSaveData() {

    }

    //////////////////////////////
    //       CUSTOM CELLS       //
    //////////////////////////////

    static class ResourceCell extends ListCell<Resource> {

        final VBox vbox = new VBox();
        final HBox hbox1 = new HBox();
        final HBox hbox2 = new HBox();

        final Text nameText = new Text("NAME");
        final Text productionAmountText = new Text("0");
        final Pane paneTop = new Pane();
        final StackPane timerProgressStackpane = new StackPane();
        final Text timerProgressText = new Text("Nah");
        final JFXProgressBar timerProgressBar = new JFXProgressBar();

        final JFXButton sellOneButton = new JFXButton("SELL ONE");
        final JFXButton sellAllButton= new JFXButton("SELL ALL");
        final JFXButton buyProducer= new JFXButton("BUY PRODUCER");
        final JFXButton buyStorer= new JFXButton("BUY STORAGE");
        final Pane paneBottom = new Pane();
        final StackPane storageProgressStackpane = new StackPane();
        final Text currentStorageText = new Text("1");
        //final Text storageSeparatorText = new Text("/");
        final Text maxStorageText = new Text("100");
        final JFXProgressBar storageProgressBar = new JFXProgressBar();


        public ResourceCell() {
            super();
            setProperties();
        }

        @Override
        public void updateItem(Resource resource, boolean empty) {
            super.updateItem(resource, empty);

            if (resource != null) {
                setGraphic(vbox);

                nameText.textProperty().bind(resource.nameProperty());

                productionAmountText.textProperty().bind(resource.producerCountProperty().asString());

                timerProgressText.textProperty().bind(resource.timeSinceProductionProperty().asString());

                currentStorageText.textProperty().bind(resource.currentStorageProperty().asString());
                maxStorageText.textProperty().bind(resource.maxStorageProperty().asString());

                if(resource.getMaxStorage() == 0) {
                    storageProgressBar.setProgress(0);
                } else {
                    storageProgressBar.setProgress(resource.getCurrentStorage() / resource.getMaxStorage());
                    storageProgressBar.setProgress(0.5);
                }

                resource.currentStorageProperty().addListener(v -> {
                    System.out.println("Current storage: " + resource.getCurrentStorage());
                    storageProgressBar.setProgress(resource.getCurrentStorage() / resource.getMaxStorage());
                });


                // if(!nameText.textProperty().isBound()) {}
                // NOTE: adding the conditional above could improve efficiency
            }

        }

        private void setProperties() {

            /// TOP ROW

            setupText(nameText, 17.0, 0.0, 30.0, 0.0, 30.0, TextAlignment.CENTER);
            setupText(productionAmountText, 14.0, 3.0, 30.0, 0.0, 30.0, TextAlignment.CENTER);
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
            hbox1.getChildren().addAll(nameText, productionAmountText, paneTop, timerProgressStackpane);

            /// BOTTOM ROW

            setupButton(sellOneButton);
            sellOneButton.setPrefWidth(60);
            setupButton(sellAllButton);
            sellAllButton.setPrefWidth(60);
            setupButton(buyProducer);
            buyProducer.setPrefWidth(87);
            setupButton(buyStorer);
            buyStorer.setPrefWidth(80);
            HBox.setHgrow(paneBottom, Priority.ALWAYS);

            storageProgressStackpane.setPrefHeight(20.0);
            storageProgressStackpane.setPrefWidth(120.0);
            HBox.setMargin(storageProgressStackpane, new Insets(0,5,0,0));
            storageProgressBar.setPrefHeight(20.0);
            //StackPane.setMargin(storageProgressBar, new Insets(0));
            setupText(currentStorageText, 15.0, 0.0, 0.0, 0.0, 0.0, TextAlignment.CENTER);
            StackPane.setAlignment(currentStorageText, Pos.CENTER_LEFT);
            //setupText(storageSeparatorText, 15.0, 0.0, 0.0, 0.0, 0.0, TextAlignment.CENTER);
            setupText(maxStorageText, 15.0, 0.0, 0.0, 0.0, 0.0, TextAlignment.CENTER);
            StackPane.setAlignment(maxStorageText, Pos.CENTER_RIGHT);


            hbox2.setPrefHeight(25.0);
            hbox2.setPrefWidth(460.0);
            hbox2.setPadding(new Insets(2.0,0,3.0,0));

            storageProgressStackpane.getChildren().addAll(storageProgressBar, currentStorageText, maxStorageText);
            hbox2.getChildren().addAll(sellOneButton, sellAllButton, buyProducer, buyStorer, paneBottom,  storageProgressStackpane);


            vbox.setPrefWidth(460.0);
            vbox.setPrefHeight(50.0);
            vbox.getChildren().addAll(hbox1, hbox2);
        }

        private void setupText(Text text, double fontSize, double top, double right, double bottom, double left, TextAlignment alignment) {
            text.setTextAlignment(alignment);
            text.setTextOrigin(VPos.CENTER);
            text.setFont(new Font(fontSize));
            HBox.setMargin(text, new Insets(top, right, bottom, left));
        }

        private void setupButton(Button button) {
            button.setAlignment(Pos.CENTER);
            //button.setContentDisplay(ContentDisplay.CENTER);
            //button.setPrefSize(25.0, 25.0);
            button.setTextAlignment(TextAlignment.CENTER);
            button.setFocusTraversable(false);
            button.setFont(new Font(10.0));
            HBox.setMargin(button, new Insets(0,2,0,2));
        }

    }
}
