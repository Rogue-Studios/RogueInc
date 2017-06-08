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
import javafx.scene.control.ListCell;
import javafx.scene.layout.*;
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
		resourcesListView.setCellFactory(v -> new ResourceCellv2());
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

//		resourcesList.addAll(lemons, sugar, apples, bananas, moistureFarm);
        resourcesList.addAll(lemons, sugar);

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

	static class ResourceCellv2 extends ListCell<Resource> {

		// CREATES COMPONENTS

		final HBox container1Hbox = new HBox();
		final VBox container2Vbox = new VBox();

		final StackPane iconStackPane = new StackPane();
		final JFXButton iconImageButton = new JFXButton("ICON");
		final JFXButton iconNumberButton = new JFXButton("100");

		final HBox topHbox = new HBox();
		final JFXButton storageTextButton = new JFXButton();
		final Text progressBarText = new Text();
		final JFXProgressBar progressBar = new JFXProgressBar();
		StackPane progressBarStackpane = new StackPane();

		final JFXButton timerTextButton = new JFXButton();

		final HBox bottomHbox = new HBox();

		final StackPane producerStackpane = new StackPane();
		final JFXButton buyProducerButton = new JFXButton();
		final JFXButton buyProducerBuyButton = new JFXButton();
		final JFXButton buyProducerWordButton = new JFXButton();

		final StackPane storerStackpane = new StackPane();
				final JFXButton buyStorerButton = new JFXButton();
		final JFXButton buyStorerBuyButton = new JFXButton();
		final JFXButton buyStorerWordButton = new JFXButton();

		final StackPane sellStackpane = new StackPane();
		final JFXButton sellButton = new JFXButton();
		final JFXButton sellSellButton = new JFXButton();
		final JFXButton sellWordButton = new JFXButton();

		public ResourceCellv2() {
			super();
			setProperties();
		}

		@Override
		public void updateItem(Resource resource, boolean empty) {
			super.updateItem(resource, empty);

			if (resource != null) {
				// Use vbox for cell display
				setGraphic(container1Hbox);

                /*

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

                */

				// UPDATE TIMER PROGRESSBAR AND TEXT WHEN TIME VALUE CHANGES
				resource.timeSinceProductionProperty().addListener(v -> {
					Double timeLeft = resource.getProductionTime() - resource.getTimeSinceProduction();
					timerTextButton.setText(String.format("%.2f", timeLeft));

	                /*
                    if (resource.getProductionTime() != 0) { // Ensure doesn't divide by 0
                        timerProgressBar.setProgress(timeLeft / resource.getProductionTime());
                    } else {
                        Main.outputError("Production time = 0. Cannot divide by 0.");
                    }
                    */


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

		}

		private void updateStorageGUI(Resource resource) {
			//System.out.println("storageText: " + storageText.getText());
			//System.out.println(resource.currentStorageProperty().getValue());
			//System.out.println(resource.maxStorageProperty().getValue());


			storageTextButton.setText(Integer.toString(resource.getCurrentStorage()) + " / " + resource.getMaxStorage());

			/*
            if (resource.getMaxStorage() != 0) { // Ensure doesn't divide by 0
                storageProgressBar.setProgress((double) resource.getCurrentStorage() / (double) resource.getMaxStorage());
            } else {
                Main.outputError("Max storage = 0. Cannot divide by 0.");
            }
            */

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

		}

		private void setProperties() {

			/// TOP ROW

			container1Hbox.setPrefHeight(100.0);
			container1Hbox.setPrefWidth(460.0);

			iconStackPane.setPrefHeight(100.0);
			iconStackPane.setPrefWidth(100.0);

			setupButton(iconImageButton, 14.0, true);
			iconImageButton.setText("ICON");
			iconImageButton.paddingProperty().setValue(new Insets(0, 0, 1, 0));
			StackPane.setAlignment(iconImageButton, Pos.CENTER);

			setupButton(iconNumberButton, 14.0, true);
			iconNumberButton.setText("100");
			StackPane.setAlignment(iconNumberButton, Pos.BOTTOM_CENTER);

			container2Vbox.prefHeight(100.0);
			container2Vbox.prefWidth(360.0);

			topHbox.prefHeight(50.0);
			topHbox.prefWidth(360.0);

			progressBarStackpane.setPrefHeight(50.0);
			progressBarStackpane.setPrefWidth(240.0);

			progressBar.setProgress(0.7);
			StackPane.setMargin(progressBar, new Insets(2, 0, 2, 0));

			setupText(progressBarText, 16.0, TextAlignment.CENTER);
			progressBarText.setText("$10");

			setupButton(storageTextButton, 16.0, true);
			storageTextButton.setAlignment(Pos.BOTTOM_RIGHT);
			storageTextButton.setPrefWidth(120);
			storageTextButton.setText("10 / 100");
			storageTextButton.paddingProperty().setValue(new Insets(20, 0, 0, 0));

            setupButton(timerTextButton, 15);
            timerTextButton.setPrefWidth(120.0);
            timerTextButton.setText("10:11");
            timerTextButton.paddingProperty().setValue(new Insets(3, 0, 2, 0));

            bottomHbox.prefHeight(50.0);
            bottomHbox.prefWidth(360.0);

            producerStackpane.setPrefWidth(120.0);

            setupButton(buyProducerButton, 18, true);
            buyProducerButton.setText("$20");
            buyProducerButton.paddingProperty().setValue(new Insets(0, 0, 5, 0));

            setupButton(buyProducerBuyButton, 13);
            buyProducerBuyButton.setAlignment(Pos.TOP_LEFT);
            buyProducerBuyButton.setText("Buy");
            StackPane.setAlignment(buyProducerBuyButton, Pos.TOP_LEFT);

            setupButton(buyProducerWordButton, 14);
            buyProducerWordButton.setAlignment(Pos.BOTTOM_CENTER);
            buyProducerWordButton.setText("Million");

            storerStackpane.setPrefWidth(120.0);

            setupButton(buyStorerButton, 18, true);
            buyStorerButton.paddingProperty().setValue(new Insets(0, 0, 5, 0));

            setupButton(buyStorerBuyButton, 13);
            buyStorerBuyButton.setAlignment(Pos.TOP_LEFT);
            buyStorerBuyButton.setText("Buy");
            StackPane.setAlignment(buyStorerBuyButton, Pos.TOP_LEFT);

            setupButton(buyStorerWordButton, 14);
            StackPane.setAlignment(buyStorerWordButton, Pos.BOTTOM_CENTER);
            buyStorerWordButton.setText("Million");

            sellStackpane.setPrefWidth(120.0);

            setupButton(sellButton, 18, true);
            sellButton.setText("$20");

            setupButton(sellSellButton, 13);
            sellSellButton.setAlignment(Pos.TOP_LEFT);
            sellSellButton.setText("Sell");
            StackPane.setAlignment(sellSellButton, Pos.TOP_LEFT);

            setupButton(sellWordButton, 14);
            StackPane.setAlignment(sellWordButton, Pos.BOTTOM_CENTER);
            sellWordButton.setText("Million");

            //ADDING CHILDREN

            sellStackpane.getChildren().addAll(sellButton, sellSellButton, sellWordButton);
            storerStackpane.getChildren().addAll(buyStorerButton, buyStorerBuyButton, buyStorerWordButton);
            producerStackpane.getChildren().addAll(buyProducerButton, buyProducerBuyButton, buyProducerWordButton);

            bottomHbox.getChildren().addAll(producerStackpane, storerStackpane, sellStackpane);
            topHbox.getChildren().addAll(progressBarStackpane, timerTextButton);
            container2Vbox.getChildren().addAll(topHbox, bottomHbox);

            iconStackPane.getChildren().addAll(iconImageButton, iconNumberButton);
            container1Hbox.getChildren().addAll(iconStackPane, container2Vbox);

		}


		private static void setupText(Text text, double fontSize, TextAlignment alignment) {
			text.setTextAlignment(alignment);
			text.setTextOrigin(VPos.CENTER);
			text.setFont(new Font(fontSize));
			//HBox.setMargin(text, new Insets(top, right, bottom, left));
		}

		private static void setupButton(Button button, double fontSize) {
			button.setAlignment(Pos.CENTER);
			button.setTextAlignment(TextAlignment.CENTER);
			button.setEllipsisString("..");
			button.setFont(new Font(fontSize));

		}

		public static void setupButton(Button button, double fontSize, boolean isActuallyText) {
			setupButton(button, fontSize);
			button.setAccessibleRole(AccessibleRole.TEXT);
			button.setFocusTraversable(false);
			button.setMouseTransparent(true);
		}

        //			container1Hbox.setMaxHeight(Double.NEGATIVE_INFINITY);
//			container1Hbox.setMaxWidth(Double.NEGATIVE_INFINITY);


//			iconStackPane.setMaxHeight(Double.NEGATIVE_INFINITY);
//			iconStackPane.setMaxWidth(Double.NEGATIVE_INFINITY);
//			iconStackPane.setMinHeight(Double.NEGATIVE_INFINITY);
//			iconStackPane.setMinWidth(Double.NEGATIVE_INFINITY);

        //			iconImageButton.setAccessibleRole(AccessibleRole.TEXT);
//			iconImageButton.setAlignment(Pos.CENTER);
//			iconImageButton.setContentDisplay(ContentDisplay.CENTER);
//			iconImageButton.setEllipsisString("..");
//			iconImageButton.setFocusTraversable(false);
//			iconImageButton.setMaxHeight(1.7976931348623157E308);
//			iconImageButton.setMaxWidth(1.7976931348623157E308);

//			iconNumberButton.setFocusTraversable(false);
//			iconNumberButton.setMaxWidth(1.7976931348623157E308);
//			iconNumberButton.setMouseTransparent(true);

//			container2Vbox.setMaxHeight(Double.NEGATIVE_INFINITY);
//			container2Vbox.setMaxWidth(Double.NEGATIVE_INFINITY);
//			container2Vbox.setMinHeight(Double.NEGATIVE_INFINITY);
//			container2Vbox.setMinWidth(Double.NEGATIVE_INFINITY);

//			topHbox.setMaxHeight(Double.NEGATIVE_INFINITY);
//			topHbox.setMaxWidth(Double.NEGATIVE_INFINITY);
//			topHbox.setMinHeight(Double.NEGATIVE_INFINITY);
//			topHbox.setMinWidth(Double.NEGATIVE_INFINITY);

//			progressBarStackpane.setLayoutX(122.0);
//			progressBarStackpane.setLayoutY(10.0);
//			progressBarStackpane.setMaxHeight(Double.NEGATIVE_INFINITY);
//			progressBarStackpane.setMaxWidth(1.7976931348623157E308);
//			progressBarStackpane.setMinHeight(Double.NEGATIVE_INFINITY);
//			progressBarStackpane.setMinWidth(Double.NEGATIVE_INFINITY);
//			progressBar.setMaxHeight(1.7976931348623157E308);
//			progressBar.setMaxWidth(1.7976931348623157E308);

//			storageTextButton.setAccessibleRole(AccessibleRole.TEXT);
//			storageTextButton.setAlignment(Pos.BOTTOM_RIGHT);
//			storageTextButton.setContentDisplay(ContentDisplay.CENTER);
//			storageTextButton.setEllipsisString("..");
//			storageTextButton.setFocusTraversable(false);
//			storageTextButton.setMaxHeight(1.7976931348623157E308);
//			storageTextButton.setMaxWidth(1.7976931348623157E308);
//			storageTextButton.setMinHeight(Double.NEGATIVE_INFINITY);
//			storageTextButton.setMinWidth(Double.NEGATIVE_INFINITY);
//			storageTextButton.setMouseTransparent(true);
//			storageTextButton.setPrefWidth(120.0);
//			storageTextButton.setTextAlignment(TextAlignment.CENTER);

//			timerTextButton.setMaxHeight(1.7976931348623157E308);
//			timerTextButton.setMaxWidth(Double.NEGATIVE_INFINITY);



//			bottomHbox.setMaxHeight(Double.NEGATIVE_INFINITY);
//			bottomHbox.setMaxWidth(Double.NEGATIVE_INFINITY);
//			bottomHbox.setMinHeight(Double.NEGATIVE_INFINITY);
//			bottomHbox.setMinWidth(Double.NEGATIVE_INFINITY);
//			producerStackpane.setMaxHeight(1.7976931348623157E308);

//          storerStackpane.setLayoutX(10.0);
//			storerStackpane.setLayoutY(10.0);
//			storerStackpane.setMaxHeight(1.7976931348623157E308);



//			buyStorerButton.setAlignment(Pos.CENTER);
//			buyStorerButton.setFocusTraversable(false);
//			buyStorerButton.setMaxHeight(1.7976931348623157E308);
//			buyStorerButton.setMaxWidth(1.7976931348623157E308);
//			buyStorerButton.setMouseTransparent(true);


//			sellStackpane.setLayoutX(130.0);
//			sellStackpane.setLayoutY(10.0);
//			sellStackpane.setMaxHeight(1.7976931348623157E308);

//            sellButton.setAlignment(Pos.CENTER);
//			sellButton.setFocusTraversable(false);
//			sellButton.setMaxHeight(1.7976931348623157E308);
//			sellButton.setMaxWidth(1.7976931348623157E308);
//			sellButton.setMouseTransparent(true);

	}

	static class ResourceCell extends ListCell<Resource> {

		final VBox vbox = new VBox();
		final HBox hbox1 = new HBox();
		final HBox hbox2 = new HBox();

		final JFXButton nameButton = new JFXButton("NAME");
		final JFXButton numProducersButton = new JFXButton("5");
		final Pane paneTop = new Pane();
		final StackPane timerProgressStackpane = new StackPane();
		final Text timerProgressText = new Text("0");
		final JFXProgressBar timerProgressBar = new JFXProgressBar();

		final JFXButton sellOneButton = new JFXButton("SELL ONE");
		final JFXButton sellAllButton = new JFXButton("SELL ALL");
		final JFXButton buyProducerButton = new JFXButton("BUY PRODUCER");
		final JFXButton buyStorerButton = new JFXButton("BUY STORAGE");
		final Pane paneBottom = new Pane();
		final StackPane storageProgressStackpane = new StackPane();
		final Text storageText = new Text("0 / 0");
		final JFXProgressBar storageProgressBar = new JFXProgressBar();


		public ResourceCell() {
			super();
			setProperties();
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

		}

		private void updateStorageGUI(Resource resource) {
			//System.out.println("storageText: " + storageText.getText());
			//System.out.println(resource.currentStorageProperty().getValue());
			//System.out.println(resource.maxStorageProperty().getValue());
			storageText.setText(Integer.toString(resource.getCurrentStorage()) + " / " + resource.getMaxStorage());

			if (resource.getMaxStorage() != 0) { // Ensure doesn't divide by 0
				storageProgressBar.setProgress((double) resource.getCurrentStorage() / (double) resource.getMaxStorage());
			} else {
				Main.outputError("Max storage = 0. Cannot divide by 0.");
			}
		}

		public void setButtonActions(Resource resource) {
			sellOneButton.setOnAction(v -> {

				// sell single item
				if (resource.currentStorageProperty().get() > 0) {
					resource.currentStorageProperty().set(resource.currentStorageProperty().get() - 1);
					player.addMoney(resource.getMarketValue());
				}
			});

			sellAllButton.setOnAction(v -> {

				// sell all items
				if (resource.currentStorageProperty().get() > 0) {
					double totalValue;
					totalValue = resource.getMarketValue() * resource.currentStorageProperty().get();
					resource.currentStorageProperty().set(0);
					player.addMoney(totalValue);
				}
			});

			buyProducerButton.setOnAction(v -> {

				// gives more production to the user
				if (player.ableToSpend(resource.getProducerCost()) == true) {
					resource.producerCountProperty().set(resource.producerCountProperty().get() + 1);
					player.subtractMoney(resource.getProducerCost());
				}
			});

			buyStorerButton.setOnAction(v -> {

				// gives the user more storage for the resource
				if (player.ableToSpend(resource.getStorerCost()) == true) {
					resource.maxStorageProperty().set(resource.maxStorageProperty().get() + resource.getStorageIncrement());
					resource.setStorerCount(resource.getStorerCount() + 1);
					player.subtractMoney(resource.getStorerCost());
				}
			});
		}

		private void setProperties() {

			/// TOP ROW

			setupButton(nameButton, 14.0, 2, 0, 0, 30, 136, false, true);
			setupButton(numProducersButton, 15.0, 2.0, 0.0, 0.0, 0.0, 55, false, true);

			nameButton.paddingProperty().setValue(new Insets(0, 0, 0, 2));
			nameButton.setFont(new Font("System Bold", 14.0));
			numProducersButton.paddingProperty().setValue(new Insets(0, 0, 1, 0));
			HBox.setHgrow(paneTop, Priority.ALWAYS);

			timerProgressStackpane.setPrefHeight(20.0);
			timerProgressStackpane.setPrefWidth(200.0);
			HBox.setMargin(timerProgressStackpane, new Insets(0, 5.0, 0, 0));
			timerProgressStackpane.setPadding(new Insets(2.0, 0, 3.0, 0));
			timerProgressBar.setPrefHeight(20.0);
			//StackPane.setMargin(timerProgressBar, new Insets(0));
			setupText(timerProgressText, 16.0, 0.0, 0.0, 0.0, 0.0, TextAlignment.CENTER);


			hbox1.setPrefHeight(25.0);
			hbox1.setPrefWidth(460.0);
			hbox1.setPadding(new Insets(3.0, 0, 2.0, 0));

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
			HBox.setMargin(storageProgressStackpane, new Insets(0, 5, 0, 0));
			storageProgressBar.setPrefHeight(20.0);
			//StackPane.setMargin(storageProgressBar, new Insets(0));

			setupText(storageText, 15.0, 0.0, 0.0, 0.0, 0.0, TextAlignment.CENTER);
			StackPane.setMargin(storageText, new Insets(0, 0, 0, 0));
			StackPane.setAlignment(storageText, Pos.CENTER);


			hbox2.setPrefHeight(25.0);
			hbox2.setPrefWidth(460.0);
			hbox2.setPadding(new Insets(2.0, 0, 3.0, 0));

			storageProgressStackpane.getChildren().addAll(storageProgressBar, storageText);
			hbox2.getChildren().addAll(sellOneButton, sellAllButton, buyProducerButton, buyStorerButton, paneBottom, storageProgressStackpane);


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
			HBox.setMargin(button, new Insets(0, 2, 0, 2));
		}

		public static void setupButton(Button button, double fontSize, double top, double right, double bottom, double left, double prefWidth, boolean focusTraversable, boolean mouseTransparent) {
			button.setAlignment(Pos.CENTER);
			button.setTextAlignment(TextAlignment.CENTER);
			button.setFocusTraversable(false);
			button.setEllipsisString("..");
			button.setFont(new Font(fontSize));
			HBox.setMargin(button, new Insets(top, right, bottom, left));
			button.setPrefWidth(prefWidth);
			button.setFocusTraversable(focusTraversable);
			button.setMouseTransparent(mouseTransparent);
		}

	}
}