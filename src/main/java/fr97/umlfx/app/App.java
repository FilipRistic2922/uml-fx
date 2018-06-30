package fr97.umlfx.app;


import fr97.umlfx.api.UmlDiagram;
import fr97.umlfx.command.CommandManager;
import fr97.umlfx.javafx.dialog.Dialogs;
import fr97.umlfx.menubar.Menubar;
import fr97.umlfx.menubar.MenubarView;
import fr97.umlfx.workspace.Workspace;
import fr97.umlfx.workspace.WorkspaceView;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class App extends Application {

    public static void main(String[] args) {
        launch(args);
    }


    // private WorkspaceView workspaceView;


    @Override
    public void start(Stage primaryStage) {

        final BorderPane root = new BorderPane();

        final CommandManager commandManager = new CommandManager();
        final ObservableList<UmlDiagram> diagrams = FXCollections.observableArrayList();

        final Menubar menubar = new Menubar(commandManager, diagrams);
        final MenubarView menubarView = new MenubarView(menubar);


        final Workspace workspace = new Workspace(diagrams, commandManager, primaryStage);
        final WorkspaceView workspaceView = new WorkspaceView(workspace);

        root.setTop(menubarView.getRoot());
        root.setCenter(workspaceView.getRoot());

        final Scene scene = new Scene(root, 1200, 800);

        menubarView.getRoot().prefWidthProperty().bind(scene.widthProperty());
        workspaceView.getRoot().prefHeightProperty().bind(scene.heightProperty().multiply(0.96));
        workspaceView.getRoot().prefWidthProperty().bind(scene.widthProperty());

        scene.getStylesheets().add("fr97/umlfx/style.css");

        primaryStage.titleProperty().bind(Localization.createStringBinding("stage.title"));
        primaryStage.setMaximized(true);
        primaryStage.setMinWidth(800);
        primaryStage.setMinHeight(640);
        primaryStage.setScene(scene);
        primaryStage.setOnCloseRequest(App::onCloseRequest);
        primaryStage.show();


    }

    private static void onCloseRequest(WindowEvent event) {
        Dialogs.builder()
                .setType(Alert.AlertType.CONFIRMATION)
                .setTitle("UmlFX Dialog")
                .setMessage("Are you sure you want to exit?")
                .resultHandler(btnType -> {
                    if (btnType.getButtonData() == ButtonBar.ButtonData.CANCEL_CLOSE)
                        event.consume();
                    else
                        System.out.println("Closing application");
                })
                .show();
    }


}
