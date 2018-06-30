package fr97.umlfx.common.node;

import fr97.umlfx.api.node.UmlNode;
import fr97.umlfx.app.Localization;
import fr97.umlfx.command.CommandManager;
import fr97.umlfx.common.AbstractDiagramController;
import fr97.umlfx.javafx.UtilsFX;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class NodeController {

    private AbstractDiagramController diagramController;
    private CommandManager commandManager;

    public NodeController(AbstractDiagramController diagramController, CommandManager commandManager){
        this.diagramController = diagramController;
        this.commandManager = commandManager;
    }



    public void showNodeEditDialog(UmlNode node){

        Stage editDialogStage = UtilsFX.createNewStage(diagramController.getStage(), Localization.createStringBinding("stage.title.editor"));

        BorderPane root = new BorderPane();

        Scene scene = new Scene(root, 400, 300);
        editDialogStage.setScene(scene);
        editDialogStage.show();

    }
}
