package fr97.umlfx.javafx.scene.layout;

import fr97.umlfx.app.Theme;
import fr97.umlfx.javafx.scene.layout.section.HeaderSection;
import fr97.umlfx.javafx.scene.layout.section.Section;
import javafx.beans.property.ObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

import java.util.Optional;

/**
 * Extends {@link StackPane} with some separates sections to make
 * it easier to represent nodes
 *
 * TODO treba da se kleanupuje
 */
public class SectionPane extends StackPane {

    private HeaderSection headerSection;
    private final Rectangle background = new Rectangle();
    private final VBox container = new VBox();

    private ObservableList<Section> subsections;

    public SectionPane() {
        this("");
    }

    public SectionPane(String text) {

        background.xProperty().bind(this.layoutXProperty());
        background.yProperty().bind(this.layoutYProperty());
        background.widthProperty().bind(this.prefWidthProperty());
        background.heightProperty().bind(this.prefHeightProperty());

        background.setStrokeWidth(1);
        //background.setFill(Theme.defautTheme.nodeBackgroundProperty.get());
        background.setStroke(Color.BLACK);
        headerSection = new HeaderSection(text);

        subsections = FXCollections.observableArrayList();
        container.getChildren().add(headerSection);

        subsections.addListener((ListChangeListener<? super Section>) c -> {
            c.next();
            for (Section s : c.getRemoved()) {
                subsections.remove(s);
            }
            for (Section s : c.getAddedSubList()) {
                container.getChildren().add(s.getSectionSeparator());
                container.getChildren().add(s);
            }
        });
        super.getChildren().add(background);
        super.getChildren().add(container);
    }

    public ObjectProperty<Paint> backgroundColorProperty(){
        return background.fillProperty();
    }

    public void setHeaderSection(HeaderSection headerSection) {
        container.getChildren().remove(this.headerSection);
        this.headerSection = headerSection;
        container.getChildren().add(headerSection);
    }

    public HeaderSection getHeaderSection() {
        return headerSection;
    }

    public ObservableList<Section> getSubsections() {
        return subsections;
    }

    public Optional<Section> getSection(String sectionName) {
        return subsections.stream()
                .filter(section -> section.getName().equals(sectionName))
                .findFirst();
    }

    public void setSelected(boolean selected) {
        if (selected) {
            //System.out.println("Selected");
            background.setStroke(Color.RED);
        } else {
            //System.out.println("Deselected");
            background.setStroke(Color.BLACK);
        }
    }
}
