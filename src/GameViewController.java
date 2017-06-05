import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import javafx.collections.ObservableList;
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
    @FXML private JFXButton moneyButton;
    @FXML private JFXButton settingsButton;

    private ObservableList<Resource> resourcesList;
    private ObservableList<Factory> factoryList;
    //private private ObservableList<Research> researchList;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
