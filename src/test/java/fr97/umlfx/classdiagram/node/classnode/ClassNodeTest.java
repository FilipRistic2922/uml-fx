package fr97.umlfx.classdiagram.node.classnode;

import fr97.umlfx.common.AccessModifier;
import fr97.umlfx.common.Field;
import fr97.umlfx.common.Function;
import javafx.scene.layout.Pane;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;

class ClassNodeTest {

    private ClassNode node = new ClassNode("TestClass");

    @Test
    @DisplayName("Default node name should be equal to id")
    void defaultNodeNameShouldBeEqualId() {
        ClassNode defaultNode = new ClassNode();

        String expected = defaultNode.getId();
        String actual = defaultNode.getName();

        assertEquals(expected, actual,
                "Default node name isn't equal to id, node state:\n" + defaultNode.toString());
    }

    @BeforeEach
    void setup() {
        node.getFields().addAll(
                new Field("double", AccessModifier.PRIVATE, "value"),
                new Field("Integer", AccessModifier.DEFAULT, "constantValue", true),
                new Field("int", AccessModifier.DEFAULT, "CONSTANT_STATIC_VALUE", true, true)
        );

        HashMap<String, String> params = new HashMap<>();
        params.put("a", "int");
        params.put("b", "int");

        node.getFunctions().addAll(
                new Function("String", AccessModifier.PUBLIC, "getString"),
                new Function("int", AccessModifier.PROTECTED, "sum", params)
        );
    }

    @Test
    @DisplayName("Copy not same as original test")
    void copyTest() {
        ClassNode copy = node.copy();
        assertNotSame(node, copy, "copy is same as original");
        node.getFields().forEach(f1 ->{
            copy.getFields().forEach(f2->{
                assertNotSame(f2, f1, "fields are same: " + f1);
            });
        });
        node.getFunctions().forEach(f1 ->{
            copy.getFunctions().forEach(f2->{
                assertNotSame(f2, f1, "functions are same: " + f1);
            });
        });
    }

}