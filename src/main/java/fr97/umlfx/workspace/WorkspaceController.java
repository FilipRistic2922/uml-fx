package fr97.umlfx.workspace;


import fr97.umlfx.api.UmlDiagram;
import fr97.umlfx.classdiagram.ClassDiagram;
import fr97.umlfx.classdiagram.ClassDiagramController;
import fr97.umlfx.classdiagram.ClassDiagramView;
import fr97.umlfx.common.AbstractDiagramController;
import fr97.umlfx.views.FXMLController;
import fr97.umlfx.workspace.toolbar.Toolbar;
import fr97.umlfx.workspace.toolbar.ToolbarView;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controller for workspace
 */
public class WorkspaceController implements FXMLController<Workspace> {

    private Workspace workspace;

    @FXML
    TabPane tabPaneDiagrams;

    @FXML
    ToolBar toolBar;

    @FXML
    TitledPane activeDiagramPane;

    @FXML
    BorderPane root;

    private Toolbar toolbar;

    /**
     * {@inheritDoc}
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("Workspace initialized");

        tabPaneDiagrams.prefHeightProperty().bind(root.heightProperty());
        tabPaneDiagrams.maxHeightProperty().bind(root.heightProperty());

        activeDiagramPane.prefHeightProperty().bind(root.heightProperty());
        activeDiagramPane.maxHeightProperty().bind(root.heightProperty());

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final void setModel(Workspace model) {
        workspace = model;

        onModelSet();
    }

    private void onModelSet() {
        workspace.getDiagrams().addListener(this::diagramsChangeListener);

        tabPaneDiagrams.getSelectionModel().selectedItemProperty().addListener(this::selectedTabChanged);

        workspace.activeDiagramProperty().addListener(this::activeDiagramChanged);
    }


    private void activeDiagramChanged(ObservableValue<? extends UmlDiagram> obs,
                                      UmlDiagram oldDiagram, UmlDiagram newDiagram) {
        if (oldDiagram != newDiagram) {
            //System.out.println("Creating new toolbar");
            ToolbarView toolbarView = new ToolbarView(toolbar);
            toolbarView.getRoot().prefHeightProperty().bind(root.heightProperty());
            toolbarView.getRoot().maxHeightProperty().bind(root.heightProperty());
            root.setRight(null);
            root.setRight(toolbarView.getRoot());
        }
    }

    private void selectedTabChanged(ObservableValue<? extends Tab> obs, Tab oldTab, Tab newTab) {
        if (newTab != null) {
            AbstractDiagramController diagramController = (AbstractDiagramController) newTab.getUserData();

           // System.out.println("New active diagram:\n   " +
                   // diagramController.getDiagram() + "\n   " +
                   // diagramController.getToolbar());
            toolbar = diagramController.getToolbar();
            workspace.setActiveDiagram(diagramController.getDiagram());
        } else {
            root.setRight(null);
            //System.out.println("All tabs closed");
        }
    }

    private void diagramsChangeListener(ListChangeListener.Change<? extends UmlDiagram> change) {
        change.next();

        change.getAddedSubList().forEach(diagram -> {
            Tab tab = createDiagramTab(diagram);
            ScrollPane scroller = createScrollPane();

            ClassDiagramView diagramView = new ClassDiagramView((ClassDiagram) diagram);


            diagramView.setPrefWidth(2000);
            diagramView.setPrefHeight(2000);



            Toolbar toolbar = new Toolbar(diagram.getSupportedTools());

            ClassDiagramController diagramController = new ClassDiagramController(
                    diagram, diagramView, workspace.getCommandManager(), toolbar);
            diagramController.setStage(workspace.getStage());
            tab.setUserData(diagramController);
            StackPane stackPane = new StackPane();
            stackPane.getChildren().add(scroller);
            scroller.setContent(diagramView);
            tab.setContent(stackPane);
            tabPaneDiagrams.getTabs().add(tab);
        });

    }

    private ScrollPane createScrollPane() {
        ScrollPane scroller = new ScrollPane();
        scroller.setPrefWidth(300);
        scroller.setPrefHeight(720);
        scroller.setHbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        scroller.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        return scroller;
    }

    private Tab createDiagramTab(UmlDiagram diagram) {
        Tab tab = new Tab();
        tab.setClosable(true);
        tab.setText(diagram.getName());
        tab.setId(diagram.getId());
        tab.setOnClosed(event -> workspace.getDiagrams().remove(diagram));
        return tab;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Workspace getModel() {
        return workspace;
    }

}
