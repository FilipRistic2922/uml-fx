package fr97.umlfx.javafx;

import fr97.umlfx.common.AccessModifier;
import javafx.beans.binding.StringBinding;
import javafx.collections.FXCollections;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.input.KeyCode;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.Collection;

/**
 *
 * Class with some utility static methods that are used to builder some common JavaFX elements
 *
 * @author Filip
 */
public class UtilsFX {


    /**
     * Creates new stage with given owner
     * @param owner owner of stage, can be null
     * @return new Stage
     */
    public static Stage createNewStage(Stage owner, StringBinding titleBinding) {
        Stage newStage = new Stage();
        newStage.titleProperty().bind(titleBinding);
        if (owner != null) {
            newStage.initOwner(owner);
            newStage.initModality(Modality.WINDOW_MODAL);
            /*double calculateMiddleX = owner.getWidth() / 2 - width / 2;
            double calculateMiddleY = owner.getHeight() / 2 - height / 2;
            newStage.setX(owner.getX() + calculateMiddleX);
            newStage.setY(owner.getY() + calculateMiddleY);*/
        }
      /*  newStage.setWidth(width);
        newStage.setHeight(height);*/
        return newStage;
    }

    /**
     * Creates {@link ComboBox} with true and false choice
     * @return combobox with true and false choice
     */
    public static ComboBox<Boolean> createBooleanComboBox() {
        ComboBox<Boolean> comboBox = new ComboBox<>(FXCollections.observableArrayList(true, false));
        comboBox.setPrefWidth(40);
        return comboBox;
    }

    /**
     * Creates {@link ComboBox} containing {@link AccessModifier}
     * @return combobox with AccessModifier values
     */
    public static ComboBox<AccessModifier> createAccessModifierComboBox() {
        ComboBox<AccessModifier> comboBox = new ComboBox<>(FXCollections.observableArrayList(AccessModifier.values()));
        comboBox.setPrefWidth(40);
        return comboBox;
    }

    /**
     * Performs necessary operations on table to make its cells editable
     * @param table table to make editable
     * @param <S> type of value that table holds
     */
    public static  <S> void makeEditableTable(TableView<S> table) {

        table.setEditable(true);

        table.getSelectionModel().cellSelectionEnabledProperty().set(true);
        table.setOnKeyPressed(event -> {
            if (event.getCode().isLetterKey() || event.getCode().isDigitKey()) {
                editFocusedCell(table);
            } else if (event.getCode() == KeyCode.RIGHT
                    || event.getCode() == KeyCode.TAB) {
                table.getSelectionModel().selectNext();
                event.consume();
            } else if (event.getCode() == KeyCode.LEFT) {
                selectPrevious(table);
                event.consume();
            }
        });
    }

    @SuppressWarnings("unchecked")
    private static  <S> void editFocusedCell(TableView<S> table) {
        final TablePosition<S, ?> focusedCell = table.focusModelProperty().get().focusedCellProperty().get();
        table.edit(focusedCell.getRow(), focusedCell.getTableColumn());
    }

    @SuppressWarnings("unchecked")
    private static  <S> void selectPrevious(TableView<S> table) {
        if (table.getSelectionModel().isCellSelectionEnabled()) {
            TablePosition<S, ?> pos = table.getFocusModel().getFocusedCell();
            if (pos.getColumn() - 1 >= 0) {
                table.getSelectionModel().select(pos.getRow(), getTableColumn(table, pos.getTableColumn()));
            } else if (pos.getRow() < table.getItems().size()) {
                table.getSelectionModel().select(pos.getRow() - 1,
                        table.getVisibleLeafColumn(table.getVisibleLeafColumns().size() - 1));
            }
        } else {
            int focusIndex = table.getFocusModel().getFocusedIndex();
            if (focusIndex == -1)
                table.getSelectionModel().select(table.getItems().size() - 1);
            else if (focusIndex > 0)
                table.getSelectionModel().select(focusIndex - 1);
        }
    }

    private static  <S> TableColumn<S, ?> getTableColumn(TableView<S> table, final TableColumn<S, ?> column) {
        int columnIndex = table.getVisibleLeafIndex(column);
        int newColumnIndex = columnIndex - 1;
        return table.getVisibleLeafColumn(newColumnIndex);
    }

}
