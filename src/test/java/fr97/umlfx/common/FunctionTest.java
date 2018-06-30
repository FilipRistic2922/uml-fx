package fr97.umlfx.common;

import javafx.beans.binding.StringBinding;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class FunctionTest {

    private Function function;

    @BeforeEach
    void setup(){
        HashMap<String, String> params = new HashMap<>();
        params.put("str1", "String");
        params.put("str2", "String");

        function = new Function("String", AccessModifier.PUBLIC, "concat", params);
    }

    @Test
    @DisplayName("Bind signature changes when property changes")
    void bindSignature() {

        StringBinding binding = function.bindSignature();

        BooleanProperty hasChanged = new SimpleBooleanProperty(false);
        binding.addListener(c->{
            assertEquals(AccessModifier.PROTECTED, function.getAccessModifier());
            hasChanged.set(true);
        });

        function.accessModifierProperty().set(AccessModifier.PROTECTED);

        assertTrue(hasChanged.get(), "string bidning didn't change");
    }
}