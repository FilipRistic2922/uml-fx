package fr97.umlfx.javafx.scene.control.cell;

import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TableCell;

import java.util.function.Supplier;

/**
 * Extends {@link TableCell}, uses ComboBox for editing cell value
 * @param <S>
 * @param <T>
 *
 * @author Filip
 *
 * @see javafx.scene.control.ComboBox
 *   TODO treba da se proveri, radjeno na brzinu
 */
public class EditableComboCell<S, T> extends TableCell<S, T> {
    private ComboBox<T> comboBox;
    private final Supplier<ComboBox<T>> comboBoxSupplier;

    public EditableComboCell(Supplier<ComboBox<T>> comboBoxSupplier) {
        this.comboBoxSupplier = comboBoxSupplier;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void startEdit() {
        if (!isEmpty()) {
            super.startEdit();
            comboBox = comboBoxSupplier.get();
            comboBox.setOnAction((e) -> {
                //System.out.println("Committed: " + comboBox.getSelectionModel().getSelectedItem());
                commitEdit(comboBox.getSelectionModel().getSelectedItem());
            });
            comboBox.setCellFactory(this::createCell);
            setText(null);
            setGraphic(comboBox);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void cancelEdit() {
        super.cancelEdit();

        setText(getString());
        setGraphic(null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updateItem(T item, boolean empty) {
        super.updateItem(item, empty);

        if (empty) {
            setText(null);
            setGraphic(null);
        } else {
            if (isEditing()) {
                if (comboBox != null) {
                    comboBox.setValue(item);
                }
                setText(getString());
                setGraphic(comboBox);
            } else {
                setText(getString());
                setGraphic(null);
            }
        }
    }

    private String getString() {
        return getItem() == null ? "" : getItem().toString();
    }

    private ListCell<T> createCell(ListView<T> c) {
        return new ListCell<T>() {
            @Override
            protected void updateItem(T item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null || empty)
                    setText(null);
                else
                    setText(item.toString());
            }
        };
    }
}
