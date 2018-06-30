package fr97.umlfx.app;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.geometry.Insets;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import java.util.Arrays;
import java.util.List;

public class Theme {

    public static Theme defautTheme = availableThemes().get(0);

    public SimpleObjectProperty<Paint> borderColorProperty = new SimpleObjectProperty<>(Color.BLACK);
    public SimpleObjectProperty<Border> borderProperty = new SimpleObjectProperty<>(createBorder(Color.BLACK, 1));
    public SimpleObjectProperty<Border> selectedBorderProperty = new SimpleObjectProperty<>(createBorder(Color.RED, 2));

    public SimpleObjectProperty<Font> fontProperty = new SimpleObjectProperty<>(Font.font("Arial", 12));
    public SimpleObjectProperty<Font> boldFontProperty = new SimpleObjectProperty<>(Font.font("Arial", FontWeight.BOLD, 16));
    public SimpleObjectProperty<Paint> selectionColorProperty = new SimpleObjectProperty<>(Color.RED);

    public SimpleObjectProperty<Paint> strokeColorProperty = new SimpleObjectProperty<>(Color.BLACK);
    public DoubleProperty strokeWidthProperty = new SimpleDoubleProperty(1);

    public SimpleObjectProperty<Paint> nodeBackgroundProperty = new SimpleObjectProperty<>(Color.LIGHTSKYBLUE);
    public SimpleObjectProperty<Paint> backgroundColorProperty = new SimpleObjectProperty<>(Color.WHITE);
    public SimpleObjectProperty<Background> backgroundProperty =
            new SimpleObjectProperty<>(new Background(new BackgroundFill(Color.GRAY, CornerRadii.EMPTY, Insets.EMPTY)));

    public static List<Theme> availableThemes() {

        Theme light = new Theme();
        Theme dark = new Theme();

        return Arrays.asList(light, dark);
    }

    private static Border createBorder(Paint color, double width) {
        return new Border(new BorderStroke(color, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(width)));
    }


    private Theme() {


    }

}
