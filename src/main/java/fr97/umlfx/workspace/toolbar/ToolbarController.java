package fr97.umlfx.workspace.toolbar;

import fr97.umlfx.api.tool.UmlTool;
import fr97.umlfx.app.Localization;
import fr97.umlfx.app.Theme;
import fr97.umlfx.views.FXMLController;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ToolBar;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

/**
 * Toolbar controller
 *
 * @see fr97.umlfx.views.FXMLController
 */
public class ToolbarController implements FXMLController<Toolbar> {

    private Toolbar toolbarModel;

    @FXML
    private ToolBar toolBar;

    @FXML
    private StackPane root;

    private Button currentBtn;

    @Override
    public void setModel(Toolbar model) {
        toolbarModel = model;
        onModelSet();
    }


    private void onModelSet() {
        toolBar.getItems().addAll(toolbarModel.getTools().stream()
                .sequential()
                .map(this::createToolButton)
                .collect(Collectors.toList()));

    }

    private Button createToolButton(UmlTool tool) {
        //System.out.println("Tool: " + tool.nameProperty().get());
        Button btn = new Button();
        btn.textProperty().bind(tool.nameProperty());
        btn.prefWidthProperty().bind(toolBar.widthProperty().multiply(0.8));
        Tooltip tooltip = new Tooltip();
        tooltip.textProperty().bind(tool.tooltipProperty());
        Tooltip.install(btn, tooltip);
        //btn.setUserData(tool);
        btn.setOnAction(event -> {
            if(currentBtn != null)
                currentBtn.getStyleClass().remove("active-tool");

            currentBtn = btn;
            currentBtn.getStyleClass().add("active-tool");
            toolbarModel.setActiveTool(tool);
        });

        return btn;
    }

    private void onToolSelected(UmlTool tool){

    }

    @Override
    public Toolbar getModel() {
        return toolbarModel;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        toolBar.prefHeightProperty().bind(root.heightProperty());
        toolBar.maxHeightProperty().bind(root.heightProperty());
    }
}
