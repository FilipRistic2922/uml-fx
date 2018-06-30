package fr97.umlfx.common;

import fr97.umlfx.api.UmlDiagram;
import fr97.umlfx.api.edge.UmlEdge;
import fr97.umlfx.api.edge.UmlEdgeView;
import fr97.umlfx.api.node.UmlNode;
import fr97.umlfx.api.node.UmlNodeView;
import fr97.umlfx.command.CommandManager;
import fr97.umlfx.command.NewNodeCommand;
import fr97.umlfx.common.edge.AbstractEdgeView;
import fr97.umlfx.common.node.AbstractNodeView;
import fr97.umlfx.common.node.NodeController;
import fr97.umlfx.common.node.NodeEditorView;
import fr97.umlfx.math.geometry.Point;
import fr97.umlfx.workspace.toolbar.Toolbar;
import javafx.collections.ListChangeListener;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseButton;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public abstract class AbstractDiagramController {


    protected UmlDiagram diagram;

    /**
     * Gui data
     */
    protected Stage stage;
    protected AbstractDiagramView diagramView;
    protected ContextMenu contextMenu;

    protected List<UmlNodeView> selectedNodes = new ArrayList<>();
    protected List<UmlEdgeView> selectedEdges = new ArrayList<>();
    protected List<UmlNodeView> nodeViews = new ArrayList<>();
    protected List<UmlEdgeView> edgeViews = new ArrayList<>();

    protected boolean nodeSelected = false;

    protected CommandManager commandManager;

    private NodeController nodeController;

    protected Toolbar toolbar;

    protected Action currentAction = Action.NO_ACTION;

    enum Action {
        NO_ACTION, SELECTING, DRAGGING_NODE, DRAGGING_EDGE, RESIZING, MOVING, DRAWING, CREATING, CONTEXT_MENU
    }

    protected AbstractDiagramController(final UmlDiagram diagram,
                                        final AbstractDiagramView diagramView,
                                        final CommandManager commandManager,
                                        final Toolbar toolbar) {
        this.diagram = diagram;
        this.commandManager = commandManager;
        this.diagramView = diagramView;
        this.toolbar = toolbar;
        this.nodeController = new NodeController(this, commandManager);

        initialize();
    }

    /**
     * Initializes this controller
     */
    public final void initialize() {
        //overrideCursor();
        registerMouseEventListeners();
        createContextMenu();
        addDiagramListeners();
        onInitialize();
    }

    private void registerMouseEventListeners() {
        diagramView.setOnMouseClicked(event -> {
            if (event.getButton() == MouseButton.PRIMARY) {
                if (event.getClickCount() == 2) {
                    System.out.println("Double Click");

                } else {
                    diagram.getEdges().forEach(e -> e.selectedProperty().set(false));
                    toolbar.getActiveTool().ifPresent(t -> t.onMouseEvent(event, diagram));
                }
            } else if (event.getButton() == MouseButton.SECONDARY) {
                diagram.getNodes().stream()
                        .filter(n -> n.getBounds().contains(Point.of(event.getX(), event.getY())))
                        .findFirst()
                        .ifPresent(n -> n.selectedProperty().set(true));
            }
            contextMenu.hide();
        });
        diagramView.setOnMousePressed(event -> toolbar.getActiveTool().ifPresent(t -> t.onMouseEvent(event, diagram)));
        diagramView.setOnMouseReleased(event -> toolbar.getActiveTool().ifPresent(t -> t.onMouseEvent(event, diagram)));
        diagramView.setOnMouseDragged(event -> toolbar.getActiveTool().ifPresent(t -> t.onMouseEvent(event, diagram)));
        diagramView.setOnMouseMoved(event -> toolbar.getActiveTool().ifPresent(t -> t.onMouseEvent(event, diagram)));
    }

    private void addDiagramListeners() {
        diagram.getNodes().addListener(this::diagramNodesChangeListener);
        diagram.getEdges().addListener(this::diagramEdgesChangeListener);
    }

    private void diagramNodesChangeListener(ListChangeListener.Change<? extends UmlNode> c) {
        c.next();
        for (UmlNode node : c.getRemoved()) {
            List<UmlNodeView> viewsToRemove = nodeViews.stream()
                    .filter(n -> n.getNode() == node)
                    .collect(Collectors.toList());
            List<UmlEdge> edgesToRemove = diagram.getEdges().stream()
                    .filter(e -> e.getHead() == node || e.getTail() == node)
                    .collect(Collectors.toList());
            diagram.getEdges().removeAll(edgesToRemove);
            nodeViews.removeAll(viewsToRemove);
            diagramView.getChildren().removeAll(viewsToRemove);
        }

        for (UmlNode node : c.getAddedSubList()) {
            AbstractNodeView nodeView = AbstractNodeView.of(node);
            nodeViews.add(nodeView);
            diagramView.getChildren().add(nodeView);
        }
    }

    private void diagramEdgesChangeListener(ListChangeListener.Change<? extends UmlEdge> c) {
        c.next();
        for (UmlEdge edge : c.getRemoved()) {
            List<UmlEdgeView> viewsToRemove = edgeViews.stream()
                    .filter(ev -> ev.getEdge() == edge)
                    .collect(Collectors.toList());
            edgeViews.removeAll(viewsToRemove);
            diagramView.getChildren().removeAll(viewsToRemove);
        }

        for (UmlEdge edge : c.getAddedSubList()) {
            AbstractEdgeView edgeView = AbstractEdgeView.of(edge);
            edgeViews.add(edgeView);
            diagramView.getChildren().add(edgeView);
        }
    }

    /**
     * Creates context menu to show on right click
     */
    private void createContextMenu() {
        contextMenu = new ContextMenu();

        contextMenu.setMinWidth(100);
        contextMenu.setWidth(100);
        MenuItem itemEdit = new MenuItem("Edit");
        MenuItem itemCopy = new MenuItem("Copy");
        MenuItem itemPaste = new MenuItem("Paste");
        MenuItem itemDelete = new MenuItem("Delete");
        MenuItem itemTakeSnapshot = new MenuItem("Snapshot");

        itemEdit.setOnAction(this::editSelected);
        itemDelete.setOnAction(this::deleteSelected);

        itemTakeSnapshot.setOnAction(event -> {
            try{
                WritableImage image =  diagramView.snapshot(
                        new SnapshotParameters(),
                        new WritableImage((int) diagramView.getWidth(), (int) diagramView.getHeight()));
                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle("Save Image");
                fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PNG Image", "*.png"));
                File output = fileChooser.showSaveDialog(getStage());
                if(output != null) {
                    ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", output);
                }
            } catch (IOException ex) {
                System.out.println("Couldn't save image " + ex.getMessage());
            }

        });

        contextMenu.getItems().addAll(itemEdit, itemCopy, itemPaste, itemDelete, itemTakeSnapshot);

        diagramView.setOnContextMenuRequested(event -> {
            diagram.getNodes().forEach(n -> n.selectedProperty().set(false));
            diagram.getEdges().forEach(e -> e.selectedProperty().set(false));
            Optional<UmlNode> targetNode = diagram.getNodes().stream()
                    .filter(n -> n.getBounds().contains(Point.of(event.getX(), event.getY())))
                    .findFirst();
            if (targetNode.isPresent()) {
                targetNode.get().selectedProperty().set(true);
                //contextMenu.show(diagramView, event.getScreenX(), event.getScreenY());
            } else {
                Optional<UmlEdge> targetEdge = edgeViews.stream()
                        .filter(ev ->
                                ev.getTailLine().contains(event.getX(), event.getY())
                                        || ev.getHeadLine().contains(event.getX(), event.getY())
                                        || ev.getMiddleLine().contains(event.getX(), event.getY())
                        ).map(UmlEdgeView::getEdge)
                        .findFirst();
                targetEdge.ifPresent(edge -> {
                    edge.selectedProperty().set(true);
                    //contextMenu.show(diagramView, event.getScreenX(), event.getScreenY());
                });
            }
            contextMenu.show(diagramView, event.getScreenX(), event.getScreenY());
        });
    }

    /**
     * Marks all selected nodes and edges
     */
    private void markSelected() {
        nodeViews.forEach(nodeView -> nodeView.setSelected(selectedNodes.contains(nodeView)));
        edgeViews.forEach(edgeView -> edgeView.setSelected(selectedEdges.contains(edgeView)));
    }

    private void editSelected(ActionEvent event) {
        Optional<UmlNode> selectedNode = diagram.getNodes().stream()
                .filter(n -> n.selectedProperty().get())
                .findFirst();
        if (selectedNode.isPresent()) {
            NodeEditorView.builder()
                    .setEditedNode(selectedNode.get())
                    .setOnApply(ev -> System.out.println("Apply"))
                    .setOnCancel(ev -> System.out.println("Cancel"))
                    .setOwner(stage)
                    .build();
            edgeViews.forEach(ev -> ((AbstractEdgeView) ev).calculatePositions(null, null, null));
        } else {
            Optional<UmlEdge> selectedEdge = diagram.getEdges().stream()
                    .filter(e -> e.selectedProperty().get())
                    .findFirst();
            selectedEdge.ifPresent(edge -> {
                System.out.println("Edge Boy");
            });
        }

    }

    private void deleteSelected(ActionEvent event) {
        List<UmlNode> nodesToDelete = diagram.getNodes().stream()
                .filter(n -> n.selectedProperty().get())
                .collect(Collectors.toList());
        List<UmlEdge> edgesToDelete = diagram.getEdges().stream()
                .filter(e -> e.selectedProperty().get())
                .collect(Collectors.toList());
        diagram.getNodes().removeAll(nodesToDelete);
        diagram.getEdges().removeAll(edgesToDelete);
    }

    /**
     * Called once super is initialized
     */
    protected abstract void onInitialize();

    protected void addNode(UmlNode node) {
        if (!diagram.containsNode(node))
            commandManager.execute(new NewNodeCommand(node, diagram));

    }


    public UmlDiagram getDiagram() {
        return diagram;
    }

    public AbstractDiagramView getDiagramView() {
        return diagramView;
    }

    public Toolbar getToolbar() {
        return toolbar;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public Stage getStage() {
        return stage;
    }
}
