package fr97.umlfx.menubar;

import fr97.umlfx.app.Localization;
import fr97.umlfx.classdiagram.ClassDiagram;
import fr97.umlfx.command.CommandManager;
import fr97.umlfx.javafx.dialog.Dialogs;
import fr97.umlfx.views.FXMLController;
import javafx.application.Platform;
import javafx.beans.Observable;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.net.URL;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

public class MenubarController implements FXMLController<Menubar> {

    private Menubar menubarModel;

    @FXML
    Menu menuFile, menuSettings, menuLang, menuTheme, menuHelp;


    @FXML
    MenuItem itemNewDiagram, itemSaveDiagram, itemLoadDiagram, itemExit, itemUndo, itemRedo;

    @FXML
    MenuItem itemEn, itemSr, itemLight, itemDark;


    @FXML
    ToggleGroup toggleLanguage, toggleTheme;

    /**
     * {@inheritDoc}
     */
    @Override
    public void setModel(Menubar model) {
        this.menubarModel = model;
        onModelSet();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Menubar getModel() {
        return menubarModel;
    }

    private void onModelSet(){
        // if there are no commands to undo/redo it will disable item in menu
        CommandManager cmdManager = menubarModel.getCommandManager();

        itemUndo.disableProperty().bind(Bindings.createBooleanBinding(
                () -> cmdManager.getUndoSize() == 0,
                cmdManager.undoSizeProperty()));

        itemRedo.disableProperty().bind(Bindings.createBooleanBinding(
                () -> cmdManager.getRedoSize() == 0,
                cmdManager.redoSizeProperty()));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("Initializing Menubar Controller");

        initLocalizationBindings();

        toggleLanguage.selectedToggleProperty().addListener(this::toggleLangListener);
        toggleTheme.selectedToggleProperty().addListener(this::toggleThemeListener);
    }

    /**
     * Binds text properties to localized string binding which change value based on current locale
     */
    private final void initLocalizationBindings(){
        menuFile.textProperty().bind(Localization.createStringBinding("menu.file"));
        itemNewDiagram.textProperty().bind(Localization.createStringBinding("menu.file.new"));
        itemLoadDiagram.textProperty().bind(Localization.createStringBinding("menu.file.load"));
        itemSaveDiagram.textProperty().bind(Localization.createStringBinding("menu.file.save"));
        itemExit.textProperty().bind(Localization.createStringBinding("menu.file.exit"));

        menuSettings.textProperty().bind(Localization.createStringBinding("menu.settings"));
        menuLang.textProperty().bind(Localization.createStringBinding("menu.settings.lang"));
        itemEn.textProperty().bind(Localization.createStringBinding("menu.settings.lang.en"));
        itemSr.textProperty().bind(Localization.createStringBinding("menu.settings.lang.sr"));

        menuHelp.textProperty().bind(Localization.createStringBinding("menu.help"));
    }

    /**
     * Creates new diagram (currently only ClassDiagram is supported)
     */
    @FXML
    void createNewDiagram() {
        menubarModel.getDiagrams().add(new ClassDiagram());
        //System.out.println("New diagram added, all diagrams: " + menubarModel.getDiagrams());
    }

    /**
     * Undo last command
     *
     */
    @FXML
    void undo() {
        //TODO povezati sve sa command managerom kako bi radio undo/redo
        System.out.println("UNDO COMMAND");
        menubarModel.getCommandManager().undo();
    }

    /**
     * Redo last undone command
     */
    @FXML
    void redo() {
        System.out.println("REDO COMMAND");
        menubarModel.getCommandManager().redo();
    }

    /**
     * Closes application after user confirms it in dialog
     */
    @FXML
    void close() {
        Dialogs.builder()
                .setType(Alert.AlertType.CONFIRMATION)
                .setTitle("UmlFX Dialog")
                .setMessage("Are you sure you want to exit?")
                .resultHandler(btnType -> {
                    if (btnType.getButtonData() == ButtonBar.ButtonData.OK_DONE) {
                        System.out.println("Closing application");
                        Platform.exit();
                    }
                })
                .show();
    }


    private void toggleThemeListener(Observable obs, Toggle o, Toggle n) {
        //TODO implement theme change
        /*if (n != null) {
            if(n.getUserData().toString().equals("light"))
                Theme.setCurrentTheme(Theme.LIGHT_THEME);
            else
                Theme.setCurrentTheme(Theme.DARK_THEME);
        }*/
    }

    /**
     * Changes {@link Locale} to one of the supported locales
     * @param obs
     * @param o
     * @param n
     */
    private void toggleLangListener(Observable obs, Toggle o, Toggle n) {
        if (n != null) {
            List<Locale> supportedLocale = Localization.getSupportedLocales();
            try {
                int index = Integer.parseInt(n.getUserData().toString());
                Localization.setLocale(supportedLocale.get(index));
            } catch (Exception ex) {
                Dialogs.builder()
                        .setType(Alert.AlertType.ERROR)
                        .setTitle("Error changing language")
                        .setMessage("Couldn't change language, it will be set to default")
                        .show();
                Localization.setLocale(Localization.getDefaultLocale());
            }
        }
    }

}
