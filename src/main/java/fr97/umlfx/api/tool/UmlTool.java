package fr97.umlfx.api.tool;

import fr97.umlfx.api.UmlDiagram;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

/**
 * UmlTool describes common behaviour of tools that are used for interacting with diagram
 */
public interface UmlTool {

    /**
     * Tool name
     */
    StringProperty nameProperty();

    /**
     * Tooltip shown on hover
     */
    StringProperty tooltipProperty();

    /**
     * Every tool accepts all mouse events but its up to implementation on what event it will work,
     * usually there will be need to do something on more than one event
     * @param event {@link MouseEvent}
     * @param diagram target {@link UmlDiagram}
     */
    void onMouseEvent(MouseEvent event, UmlDiagram diagram);

    /**
     * TODO trenutno nista ne radi, treba da omoguci koriscenje precica
     * @param event KeyEvent
     * @param diagram target {@link UmlDiagram}
     */
    void onKeyEvent(KeyEvent event, UmlDiagram diagram);
}
