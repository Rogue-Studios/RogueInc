import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.ListCell;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
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

        //setCellFactories();
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
        final Text productionAmountText = new Text("AMOUNT");

        final Text timerProgress = new Text("00:05");
        final Text storageProgress = new Text("10 / 100");

        final JFXButton sellAllButton= new JFXButton("SELL ALL");
        final JFXButton buyProducer= new JFXButton("BUY PRODUCER");
        final JFXButton buyStorer= new JFXButton("BUY STORAGE");
        final JFXButton sellOneButton = new JFXButton("SELL ONE");

        //final Separator separator = new Separator();
        //final Pane spacer = new Pane();


        public ResourceCell() {
            super();
            setProperties();
        }

        private void setProperties() {

            setupText(nameText, 14.0, 3.0, 0.0, 3.0, 0.0, TextAlignment.LEFT);
            setupText(productionAmountText, 14.0, 3.0, 0.0, 3.0, 0.0, TextAlignment.LEFT);
            setupText(timerProgress, 14.0, 3.0, 0.0, 3.0, 0.0, TextAlignment.LEFT);
            setupText(storageProgress, 14.0, 3.0, 0.0, 3.0, 0.0, TextAlignment.LEFT);

            hbox1.getChildren().addAll(nameText, productionAmountText, timerProgress);
            hbox2.getChildren().addAll(sellOneButton, sellAllButton, buyProducer, buyStorer, storageProgress);
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
            button.setContentDisplay(ContentDisplay.CENTER);
            button.setPrefSize(25.0, 25.0);
            button.setTextAlignment(TextAlignment.CENTER);
            button.setFocusTraversable(false);
            HBox.setMargin(button, new Insets(5.0));
        }

    }
}
