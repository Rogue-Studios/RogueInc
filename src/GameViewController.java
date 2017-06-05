import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

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
}
