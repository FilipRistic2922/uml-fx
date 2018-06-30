package fr97.umlfx.classdiagram.node.interfacenode;

import fr97.umlfx.common.Function;
import fr97.umlfx.common.node.AbstractNodeView;
import fr97.umlfx.javafx.scene.layout.SectionPane;
import fr97.umlfx.javafx.scene.layout.section.Section;
import javafx.collections.ListChangeListener;
import javafx.geometry.Bounds;
import javafx.scene.control.Label;

/**
 * View for {@link InterfaceNode}
 */
public class InterfaceNodeView extends AbstractNodeView {

    private final SectionPane sectionPane = new SectionPane();

    public InterfaceNodeView(final InterfaceNode node) {
        super(node);

        bindWithNode(node);
        //container.getChildren().addAll(/*rectangle,*/ sectionPane);
        getChildren().add(sectionPane);
        createResizeLines(sectionPane);
    }

    private void bindWithNode(final InterfaceNode node) {

        sectionPane.getHeaderSection().textProperty().bind(node.nameProperty());
        sectionPane.getHeaderSection().stereotypeProperty().bind(node.stereotypeProperty());
        sectionPane.backgroundColorProperty().bind(fillProperty());
        Section section2 = new Section("functions");
        node.getFunctions().addListener((ListChangeListener<? super Function>) c -> {
            c.next();
            c.getAddedSubList().forEach(f -> {
                Label lbl = new Label();
                lbl.underlineProperty().bind(f.staticProperty());
                lbl.textProperty().bind(f.bindSignature());
                section2.addItem(lbl);
            });
            c.getRemoved().forEach(f ->
                    section2.getChildren().removeIf(n -> ((Label) n).getText().equals(f.bindSignature().get())));
        });


        sectionPane.getSubsections().addAll(section2);
        sectionPane.prefWidthProperty().bind(node.widthProperty());
        sectionPane.maxWidthProperty().bind(node.widthProperty());
        sectionPane.prefHeightProperty().bind(node.heightProperty());
        sectionPane.maxHeightProperty().bind(node.heightProperty());
    }

    @Override
    public void setSelected(boolean selected) {
        sectionPane.setSelected(selected);
    }


    @Override
    public Bounds getBounds() {
        return getBoundsInParent();
    }
}
