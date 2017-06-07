import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;

public class MainController extends Stage {

    final StackPane pane = new StackPane();
    GameViewController gameViewController;
    double version;

    public MainController(double version) {
        this.version = version;

        loadFXMLLoaders();
        loadScene();
        gameViewController.start();

    }

    public void loadFXMLLoaders() {
        FXMLLoader gameViewFXMLLoader = new FXMLLoader((getClass().getResource("GameView.fxml")));

        try {
            pane.getChildren().add(gameViewFXMLLoader.load());
        } catch (IOException e) {
            Main.outputError(e);
        }

        gameViewController = gameViewFXMLLoader.getController();

    }

    private void loadSaveData() {

    }

    private void writeSaveData() {

    }

    public void loadScene() {
        this.setScene(new Scene(pane, 480, 854));
        this.setTitle("Rogue Inc. v" + Double.toString(getVersion()));
        this.setResizable(false);

        // load icon using platform-agnostic file handling
        // (however, setting the icon Dock icon on Mac requires making additional calls)
        try {

            this.getIcons().add(new Image(this.getClass().getResourceAsStream("icon.png")));

            ///
            // Icon from: http://www.pelfusion.com
            // License: Linkware
            // Source: http://www.iconarchive.com/show/christmas-shadow-2-icons-by-pelfusion/Crown-icon.html
            ///

        } catch (Exception e) {
            System.out.println("Error: application icon not found");
            Main.outputError(e);
        }

        this.setOnCloseRequest(v -> {
            // TODO: Save game data on exit
            //gameViewController.writeSaveData();
            Platform.exit();
            System.exit(0);
        });

        this.show();
    }

    private void switchScene(Node newScene) {
        PauseTransition pause = new PauseTransition((javafx.util.Duration.millis(300)));

        pause.setOnFinished(v -> {
            // TODO: Finish implementing scene switching
            // add new scene:
            pane.getChildren().add(newScene);
            // remove old scene??
        });
    }

    public double getVersion() {
        return version;
    }
}
