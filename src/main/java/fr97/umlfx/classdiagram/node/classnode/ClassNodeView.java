package fr97.umlfx.classdiagram.node.classnode;

import fr97.umlfx.common.AccessModifier;
import fr97.umlfx.common.Field;
import fr97.umlfx.common.Function;
import fr97.umlfx.common.node.AbstractNodeView;
import fr97.umlfx.javafx.scene.layout.SectionPane;
import fr97.umlfx.javafx.scene.layout.section.Section;
import javafx.collections.ListChangeListener;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Line;

import java.util.HashMap;


/**
 * View for {@link ClassNode}
 */
public class ClassNodeView extends AbstractNodeView {

    private final SectionPane sectionPane = new SectionPane();

    public ClassNodeView(final ClassNode node){
        super(node);

        bindWithNode(node);
        //container.getChildren().addAll(/*rectangle,*/ sectionPane);
        getChildren().add(sectionPane);
        createResizeLines(sectionPane);
    }

    private void bindWithNode(final ClassNode node){

        sectionPane.getHeaderSection().textProperty().bind(node.nameProperty());
        sectionPane.getHeaderSection().stereotypeProperty().bind(node.stereotypeProperty());
        sectionPane.backgroundColorProperty().bind(fillProperty());
        Section section1 = new Section("fields");
        node.getFields().addListener((ListChangeListener<? super Field>) c -> {
            c.next();
            c.getRemoved().forEach(f ->
                section1.getChildren().removeIf(n -> ((Label)n).getText().equals(f.bindSignature().get())));
            c.getAddedSubList().forEach(f->{
                Label lbl = new Label();
                lbl.textProperty().bind(f.bindSignature());
                lbl.underlineProperty().bind(f.staticProperty());
                section1.addItem(lbl);
            });
        });

        Section section2 = new Section("functions");
        node.getFunctions().addListener((ListChangeListener<? super Function>) c -> {
            c.next();
            c.getRemoved().forEach(f ->
                    section2.getChildren().removeIf(n -> ((Label)n).getText().equals(f.bindSignature().get())));
            for(Function f : c.getAddedSubList()){
                Label lbl = new Label();
                lbl.textProperty().bind(f.bindSignature());
                lbl.underlineProperty().bind(f.staticProperty());
                section2.addItem(lbl);
            }
        });
        sectionPane.getSubsections().addAll(section1, section2);
        sectionPane.prefWidthProperty().bind(node.widthProperty());
        sectionPane.maxWidthProperty().bind(node.widthProperty());
        sectionPane.prefHeightProperty().bind(node.heightProperty());
        sectionPane.maxHeightProperty().bind(node.heightProperty());
    }

    /**
     *
     * {@inheritDoc}
     */
    @Override
    public void setSelected(boolean selected) {
        sectionPane.setSelected(selected);
    }

    /**
     *
     * {@inheritDoc}
     */
    @Override
    public Bounds getBounds() {
        return getBoundsInParent();
    }
}
