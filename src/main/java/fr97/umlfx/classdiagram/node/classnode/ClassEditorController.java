package fr97.umlfx.classdiagram.node.classnode;

import fr97.umlfx.common.AccessModifier;
import fr97.umlfx.common.Field;
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
import java.util.*;

public class ClassEditorController implements FXMLController<ClassNode> {

    private ClassNode node;

    @FXML
    private TableView<Field> tableFields;

    @FXML
    private TableView<Function> tableFunctions;

    @FXML
    private TableColumn<Field, String> fieldName, type, defaultValue;

    @FXML
    private TableColumn<Function, String> functionName, returnType;

    @FXML
    private TableColumn<Function, ObservableMap<String, String >> parameters;

    @FXML
    private TableColumn<Field, AccessModifier> fieldAccessModifier;

    @FXML
    private TableColumn<Function, AccessModifier> functionAccessModifier;

    @FXML
    private TableColumn<Field, Boolean> fieldIsStatic, isConstant;

    @FXML
    private TableColumn<Function, Boolean> functionIsStatic;

    @FXML
    private Button btnRemoveField, btnRemoveFunction;

    @FXML
    private TextField txtName;

    @FXML
    private Slider sliderWidth, sliderHeight;

    @Override
    public void setModel(ClassNode model) {
        node = model;
        onModelSet();
    }

    private void onModelSet() {
        txtName.textProperty().bindBidirectional(node.nameProperty());
        tableFields.setItems(node.getFields());
        tableFunctions.setItems(node.getFunctions());
        sliderWidth.valueProperty().bindBidirectional(node.widthProperty());
        sliderHeight.valueProperty().bindBidirectional(node.heightProperty());
    }

    @Override
    public ClassNode getModel() {
        return node;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {


        Callback<TableColumn<Field, String>, TableCell<Field, String>> fieldStringCellFactory
                = (TableColumn<Field, String> param) -> new EditableTextCell<>((str -> str));

        Callback<TableColumn<Field, Boolean>, TableCell<Field, Boolean>> fieldBooleanCellFactory
                = (TableColumn<Field, Boolean> param) -> new EditableComboCell<>(UtilsFX::createBooleanComboBox);

        Callback<TableColumn<Field, AccessModifier>, TableCell<Field, AccessModifier>> fieldAccessModifierCellFactory
                = (TableColumn<Field, AccessModifier> param) -> new EditableComboCell<>(UtilsFX::createAccessModifierComboBox);

        Callback<TableColumn<Function, String>, TableCell<Function, String>> functionStringCellFactory
                = (TableColumn<Function, String> param) -> new EditableTextCell<>((str -> str));

        Callback<TableColumn<Function, AccessModifier>, TableCell<Function, AccessModifier>> functionAccessModifierCellFactory
                = (TableColumn<Function, AccessModifier> param) -> new EditableComboCell<>(UtilsFX::createAccessModifierComboBox);

        Callback<TableColumn<Function, Boolean>, TableCell<Function, Boolean>> functionBooleanCellFactory
                = (TableColumn<Function, Boolean> param) -> new EditableComboCell<>(UtilsFX::createBooleanComboBox);

        Callback<TableColumn<Function, ObservableMap<String, String>>, TableCell<Function, ObservableMap<String, String>>> functionParameterCellFactory
                = (TableColumn<Function, ObservableMap<String, String>> param) -> new EditableTextCell<>((str -> {
                    ObservableMap<String, String> map = FXCollections.observableHashMap();
                    str = str.replace("{","").replace("}", "");
                    String[] parameters = str.split(", ");
                    for(String p : parameters){
                        System.out.println("String: " + p + " into " + Arrays.toString(p.split("=")));
                        String[] keyToValue = p.split("=");
                        if(keyToValue.length > 1)
                            map.put(keyToValue[0], keyToValue[1]);
                    }
                    return map;
        }));

        fieldName.setCellValueFactory(cell -> cell.getValue().fieldNameProperty());
        fieldName.setCellFactory(fieldStringCellFactory);
        type.setCellValueFactory(cell -> cell.getValue().typeProperty());
        type.setCellFactory(fieldStringCellFactory);
        defaultValue.setCellValueFactory(cell -> cell.getValue().defaultValueProperty());
        defaultValue.setCellFactory(fieldStringCellFactory);
        fieldAccessModifier.setCellFactory(fieldAccessModifierCellFactory);
        isConstant.setCellValueFactory(cell -> cell.getValue().constantProperty());
        isConstant.setCellFactory(fieldBooleanCellFactory);
        fieldIsStatic.setCellValueFactory(cell -> cell.getValue().staticProperty());
        fieldIsStatic.setCellFactory(fieldBooleanCellFactory);

        functionName.setCellValueFactory(cell -> cell.getValue().nameProperty());
        functionName.setCellFactory(functionStringCellFactory);
        returnType.setCellValueFactory(cell -> cell.getValue().returnTypeProperty());
        returnType.setCellFactory(functionStringCellFactory);
        functionIsStatic.setCellValueFactory(cell -> cell.getValue().staticProperty());
        functionIsStatic.setCellFactory(functionBooleanCellFactory);
        parameters.setCellValueFactory(cell -> {
            ObservableMap<String, String> params = cell.getValue().getParameters();
            ObjectProperty<ObservableMap<String, String>> prop = new SimpleObjectProperty<>( params);
            prop.addListener(c->{
                params.clear();
                prop.get().forEach(params::put);
            });
           return prop;
        });
        parameters.setCellFactory(functionParameterCellFactory);
        functionAccessModifier.setCellFactory(functionAccessModifierCellFactory);
        btnRemoveField.disableProperty().bind(tableFields.getSelectionModel().selectedItemProperty().isNull());
        btnRemoveFunction.disableProperty().bind(tableFunctions.getSelectionModel().selectedItemProperty().isNull());

        UtilsFX.makeEditableTable(tableFields);
        UtilsFX.makeEditableTable(tableFunctions);
    }


    @FXML
    private void addNewField() {
        Field newField = new Field("int", AccessModifier.PRIVATE, "fieldName");
        node.getFields().add(newField);
    }

    @FXML
    private void removeField() {
        Field fieldToRemove = tableFields.getSelectionModel().getSelectedItem();
        node.getFields().remove(fieldToRemove);
    }

    @FXML
    private void addNewFunction() {
        HashMap<String, String> map = new HashMap<>();
        map.put("param1", "String");
        map.put("param2", "double");
        Function newFunction = new Function("void", AccessModifier.PRIVATE, "functionName", map);
        node.getFunctions().add(newFunction);
    }

    @FXML
    private void removeFunction() {
        Function fieldToRemove = tableFunctions.getSelectionModel().getSelectedItem();
        node.getFunctions().remove(fieldToRemove);
    }

}
