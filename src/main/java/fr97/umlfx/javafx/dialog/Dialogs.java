package fr97.umlfx.javafx.dialog;


import fr97.umlfx.app.Localization;
import fr97.umlfx.utils.ArgumentChecker;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

import static javafx.scene.control.ButtonBar.ButtonData;

/**
 *
 * This class has some utility functions for creating JavaFX dialogs
 *
 * @author Filip
 *
 * @see Dialog
 *
 * TODO moze da se prosiri funkcionalnost
 */
public final class Dialogs {


    private static ButtonType createLocalizedButton(ButtonData data, String key) {
        return new ButtonType(Localization.get(key), data);
    }


    public static DialogBuilder builder() {
        return new DialogBuilder();
    }

    public static class DialogBuilder {

        private Alert.AlertType type;
        private String title;
        private String header;
        private String message;
        private boolean resizible;
        private Consumer<ButtonType> consumer;
        private List<ButtonType> buttons;

        private DialogBuilder() {


        }

        public DialogBuilder setType(Alert.AlertType type) {
            this.type = type;
            return this;
        }

        public DialogBuilder setTitle(String title) {
            this.title = title;
            return this;
        }

        public DialogBuilder setHeader(String header) {
            this.header = title;
            return this;
        }

        public DialogBuilder setMessage(String message) {
            this.message = message;
            return this;
        }

        public DialogBuilder resizible(boolean resizible) {
            this.resizible = resizible;
            return this;
        }

        public DialogBuilder resultHandler(Consumer<ButtonType> consumer) {
            this.consumer = consumer;
            return this;
        }

        public void show() {

            Dialog<ButtonType> dialog = new Dialog<>();

            dialog.setResizable(resizible);
            dialog.getDialogPane().getButtonTypes().clear();


            switch (type) {
                case NONE:
                    break;
                case INFORMATION:
                    createInformationDialog(dialog);
                    break;
                case WARNING:
                    break;
                case CONFIRMATION:
                    createConfirmationDialog(dialog);
                    break;
                case ERROR:
                    break;
            }

            dialog.setTitle(title);
            dialog.setHeaderText(header);
            dialog.setContentText(message);


            Optional<ButtonType> result = dialog.showAndWait();
            System.out.println("Result: " + result);
            if (consumer != null)
                result.ifPresent(consumer);
        }

    }

    private static void createInformationDialog(Dialog dialog) {
        dialog.getDialogPane().getButtonTypes().addAll(createLocalizedButton(ButtonData.OK_DONE, "dialog.btn.ok"));
    }

    private static void createConfirmationDialog(Dialog dialog) {
        dialog.getDialogPane().getButtonTypes().addAll(
                createLocalizedButton(ButtonData.OK_DONE, "dialog.btn.ok"),
                createLocalizedButton(ButtonData.CANCEL_CLOSE, "dialog.btn.cancel")
        );

    }

}
