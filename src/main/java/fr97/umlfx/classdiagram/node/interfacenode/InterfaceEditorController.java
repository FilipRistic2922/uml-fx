package fr97.umlfx.classdiagram.node.interfacenode;

import fr97.umlfx.common.AccessModifier;
import fr97.umlfx.common.Function;
import fr97.umlfx.javafx.UtilsFX;
import fr97.umlfx.javafx.scene.control.cell.EditableComboCell;
import fr97.umlfx.javafx.scene.control.cell.EditableTextCell;
import fr97.umlfx.views.FXMLController;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableMap;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.util.Callback;

import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;

public class InterfaceEditorController implements FXMLController<InterfaceNode> {

    private InterfaceNode node;

    @FXML
    private TableView<Function> tableFunctions;

    @FXML
    private TableColumn<Function, String> functionName, returnType;

    @FXML
    private TableColumn<Function, ObservableMap<String, String>> parameters;

    @FXML
    private TableColumn<Function, Boolean> functionIsStatic;

    @FXML
    private TableColumn<Function, AccessModifier> functionAccessModifier;


    @FXML
    private Button btnRemoveFunction;

    @FXML
    private TextField txtName;

    @FXML
    private Slider sliderWidth, sliderHeight;

    @Override
    public void setModel(InterfaceNode model) {
        node = model;
        onModelSet();
    }

    private void onModelSet() {
        txtName.textProperty().bindBidirectional(node.nameProperty());
        tableFunctions.setItems(node.getFunctions());
        sliderWidth.valueProperty().bindBidirectional(node.widthProperty());
        sliderHeight.valueProperty().bindBidirectional(node.heightProperty());
    }

    @Override
    public InterfaceNode getModel() {
        return node;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        Callback<TableColumn<Function, String>, TableCell<Function, String>> functionStringCellFactory
                = (TableColumn<Function, String> param) -> new EditableTextCell<>((str -> str));
        Callback<TableColumn<Function, AccessModifier>, TableCell<Function, AccessModifier>> functionAccessModifierCellFactory
                = (TableColumn<Function, AccessModifier> param) -> new EditableComboCell<>(this::createAccessModifierComboBox);

        Callback<TableColumn<Function, Boolean>, TableCell<Function, Boolean>> functionBooleanCellFactory
                = (TableColumn<Function, Boolean> param) -> new EditableComboCell<>(UtilsFX::createBooleanComboBox);

        Callback<TableColumn<Function, ObservableMap<String, String>>, TableCell<Function, ObservableMap<String, String>>> functionParameterCellFactory
                = (TableColumn<Function, ObservableMap<String, String>> param) -> new EditableTextCell<>((str -> {
            ObservableMap<String, String> map = FXCollections.observableHashMap();
            str = str.replace("{", "").replace("}", "");
            String[] parameters = str.split(", ");
            for (String p : parameters) {
                String[] keyToValue = p.split("=");
                if (keyToValue.length > 1)
                    map.put(keyToValue[0], keyToValue[1]);
            }
            return map;
        }));
        functionName.setCellValueFactory(cell -> cell.getValue().nameProperty());
        functionName.setCellFactory(functionStringCellFactory);
        returnType.setCellValueFactory(cell -> cell.getValue().returnTypeProperty());
        returnType.setCellFactory(functionStringCellFactory);
        functionIsStatic.setCellValueFactory(cell -> cell.getValue().staticProperty());
        functionIsStatic.setCellFactory(functionBooleanCellFactory);
        parameters.setCellValueFactory(cell -> {
            ObservableMap<String, String> params = cell.getValue().getParameters();
            ObjectProperty<ObservableMap<String, String>> prop = new SimpleObjectProperty<>(cell.getValue().getParameters());
            prop.addListener(c -> {
                params.clear();
                prop.get().forEach(params::put);
            });
            return prop;
        });
        parameters.setCellFactory(functionParameterCellFactory);
        functionAccessModifier.setCellFactory(functionAccessModifierCellFactory);
        btnRemoveFunction.disableProperty().bind(tableFunctions.getSelectionModel().selectedItemProperty().isNull());

        UtilsFX.makeEditableTable(tableFunctions);
    }

    private ComboBox<AccessModifier> createAccessModifierComboBox() {
        ComboBox<AccessModifier> comboBox = new ComboBox<>(FXCollections.observableArrayList(AccessModifier.values()));
        comboBox.setPrefWidth(40);
        return comboBox;
    }


    @FXML
    private void addNewFunction() {
        HashMap<String, String> map = new HashMap<>();
        map.put("str", "String");
        map.put("offset", "double");
        Function newFunction = new Function("void", AccessModifier.PRIVATE, "functionName", map);
        node.getFunctions().add(newFunction);
    }

    @FXML
    private void removeFunction() {
        Function fieldToRemove = tableFunctions.getSelectionModel().getSelectedItem();
        node.getFunctions().remove(fieldToRemove);
    }
}
