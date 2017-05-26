
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class JavaFxTest extends Application {

	Button button;
	Label label;
	String emailAddress = " ";

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStage.setTitle("Email+");

		BorderPane border = new BorderPane();
		Scene scene = new Scene(border, 550, 325);

		border.setTop(addMenuBar());
		border.setLeft(addLeftToolBar());
		border.setCenter(addCenter());

		// Display
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	public VBox addLeftToolBar() {
		VBox vbox = new VBox();
		vbox.setPadding(new Insets(12, 10, 12, 10));
		vbox.setSpacing(5);
		vbox.setStyle("-fx-background-color: #E6DEA5");

		Button newEmailBtn = new Button("New Email");
		newEmailBtn.setPrefSize(75, 20);

		Button contactsBtn = new Button("Contacts");
		contactsBtn.setPrefSize(75, 20);

		vbox.getChildren().addAll(newEmailBtn, contactsBtn);

		return vbox;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public GridPane addCenter() {
		GridPane grid = new GridPane();
		grid.setPadding(new Insets(12, 10, 12, 10));
		grid.setVgap(4);
		grid.setHgap(10);
		grid.setPrefWidth(10);
		grid.setStyle("-fx-background-color: #FFF6B5");

		final TextField subject = new TextField(" ");
		final TextArea text = new TextArea(" ");
		final Label notification = new Label();
		final Button button = new Button("Send");

		String[] emailComboBoxList = { 
				"johndoe@gmail.com", 
				"mcg@google.com", 
				"dustin@example.com",
				"fakename@gmail.com", 
				"test@yahoo.com", 
				"business@biz.com" };

		final ComboBox emailComboBox = new ComboBox();
		emailComboBox.getItems().addAll(emailComboBoxList);

		emailComboBox.setPromptText("Email address");
		emailComboBox.setEditable(true);
		emailComboBox.valueProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue ov, String t, String t1) {
				emailAddress = t1;
			}
		});

		String[] priorityComboBoxList = { 
				"Highest", 
				"High", 
				"Normal", 
				"Low", 
				"Lowest" };

		final ComboBox priorityComboBox = new ComboBox();
		priorityComboBox.getItems().addAll(priorityComboBoxList);

		button.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				if (emailComboBox.getValue() != null && !emailComboBox.getValue().toString().isEmpty()) {
					notification.setText("Your message was successfully sent" + " to " + emailAddress);
					emailComboBox.setValue(null);
					if (priorityComboBox.getValue() != null && !priorityComboBox.getValue().toString().isEmpty()) {
						priorityComboBox.setValue(null);
					}
					subject.clear();
					text.clear();
				} else {
					notification.setText("You have not selected a recipient!");
				}
			}
		});
		priorityComboBox.setValue("Normal");

		grid.add(new Label("To: "), 0, 0);
		grid.add(new Label("Priority: "), 2, 0);
		grid.add(new Label("Subject: "), 0, 1);

		grid.add(notification, 1, 3, 3, 1);
		grid.add(text, 0, 2, 4, 1);
		grid.add(priorityComboBox, 3, 0);
		grid.add(emailComboBox, 1, 0);
		grid.add(subject, 1, 1, 3, 1);
		grid.add(button, 0, 3);
		return grid;
	}

	public MenuBar addMenuBar() {
		final MenuBar menuBar = new MenuBar();
		menuBar.getMenus().addAll(addFileMenu(), addEditMenu(), addViewMenu());
		return menuBar;
	}

	public Menu addFileMenu() {
		final Menu fileMenu = new Menu("File");
		MenuItem newFile = new MenuItem("New");
		MenuItem open = new MenuItem("Open");
		MenuItem save = new MenuItem("Save");
		MenuItem saveAs = new MenuItem("SaveAs");
		MenuItem exit = new MenuItem("Exit");
		
		fileMenu.getItems().add(newFile);
		fileMenu.getItems().add(open);
		fileMenu.getItems().add(save);
		fileMenu.getItems().add(saveAs);
		fileMenu.getItems().add(new SeparatorMenuItem());
		fileMenu.getItems().add(exit);
		
		exit.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
                final Stage dialogStage = new Stage();
                dialogStage.initModality(Modality.WINDOW_MODAL);

                Label exitLabel = new Label("Are you sure you want to exit?");
                exitLabel.setAlignment(Pos.BASELINE_CENTER);

                Button yesBtn = new Button("Yes");
                yesBtn.setOnAction(new EventHandler<ActionEvent>() {

                    @Override
                    public void handle(ActionEvent arg0) {
                        dialogStage.close();
                        System.exit(0);
                    }
                });
                Button noBtn = new Button("No");

                noBtn.setOnAction(new EventHandler<ActionEvent>() {

                    @Override
                    public void handle(ActionEvent arg0) {
                        dialogStage.close();
                        
                    }
                });

                HBox hBox = new HBox();
                hBox.setAlignment(Pos.BASELINE_CENTER);
                hBox.setSpacing(40.0);
                hBox.getChildren().addAll(yesBtn, noBtn);

                VBox vBox = new VBox();
                vBox.setSpacing(10.0);
                vBox.getChildren().addAll(exitLabel, hBox);

                dialogStage.setScene(new Scene(vBox));
                dialogStage.show();
            }
		});
		
		return fileMenu;
	}
	
	public Menu addEditMenu() {
		final Menu editMenu = new Menu("Edit");
		MenuItem undo = new MenuItem("Undo Typing");
		MenuItem redo = new MenuItem("Redo");
		MenuItem cut = new MenuItem("Cut");
		MenuItem copy = new MenuItem("Copy");
		MenuItem paste = new MenuItem("Paste");
		MenuItem delete = new MenuItem("Delete");
		
		
		
		editMenu.getItems().add(undo);
		editMenu.getItems().add(redo);
		editMenu.getItems().add(new SeparatorMenuItem());
		editMenu.getItems().add(cut);
		editMenu.getItems().add(copy);
		editMenu.getItems().add(paste);
		editMenu.getItems().add(new SeparatorMenuItem());
		editMenu.getItems().add(delete);
		return editMenu;
	}
	
	public Menu addViewMenu() {
		final Menu viewMenu = new Menu("View");
		MenuItem view100 = new MenuItem("100%");
		MenuItem view150= new MenuItem("150%");
		MenuItem view200 = new MenuItem("200%");
		MenuItem fullScreen = new MenuItem("Full Screen");
		
		viewMenu.getItems().add(view100);
		viewMenu.getItems().add(view150);
		viewMenu.getItems().add(view200);
		viewMenu.getItems().add(new SeparatorMenuItem());
		viewMenu.getItems().add(fullScreen);
		return viewMenu;
	}

}