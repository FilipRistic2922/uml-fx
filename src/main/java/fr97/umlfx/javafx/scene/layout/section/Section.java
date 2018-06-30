package fr97.umlfx.javafx.scene.layout.section;


import fr97.umlfx.utils.ArgumentChecker;
import javafx.beans.InvalidationListener;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Separator;
import javafx.scene.layout.VBox;

/**
 * Section extends {@link VBox}, it is used together with section pane
 *
 * TODO treba da se kleanupuje
 */
public class Section extends VBox {


    private final String name;

    private final Separator sectionSeparator = new Separator();

    public boolean isEmpty() {
        return empty.get();
    }

    public BooleanProperty emptyProperty() {
        return empty;
    }

    public void setEmpty(boolean empty) {
        this.empty.set(empty);
    }

    private final BooleanProperty empty = new SimpleBooleanProperty(true);

    public Section(String name) {
        ArgumentChecker.notNull(name, "name can't be null");

        this.name = name;
        //borderProperty().bind(Theme.defautTheme.borderProperty);
        setAlignment(Pos.TOP_LEFT);
        setPadding(new Insets(5, 5, 5, 5));

        getChildren().addListener((InvalidationListener) l -> {
            empty.set(getChildren().isEmpty());
        });

        sectionSeparator.visibleProperty().bind(empty.not());

    }

    public String getName() {
        return name;
    }

    public Separator getSectionSeparator() {
        return sectionSeparator;
    }

    public boolean addItem(Node node) {
        return getChildren().add(node);
    }


}
