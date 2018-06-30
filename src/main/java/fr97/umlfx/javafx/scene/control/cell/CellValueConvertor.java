package fr97.umlfx.javafx.scene.control.cell;

/**
 * FunctionInterface to allow creation of easy convertor
 * from string to some given type
 *
 * @param <T> given type that string should be converted to
 */
@FunctionalInterface
public interface CellValueConvertor<T> {

    /**
     * Converts string to instance of T
     * @param str string to convert
     * @return instance of T retrieved from given string
     */
    T convert(String str);
}
