package fr97.umlfx.utils;

/**
 * Small class used to check if arguments are null or not
 *
 * @author Filip
 */
public class ArgumentChecker {

    /**
     *
     * Checks if argument is null, if it is throws error
     *
     * @param arg argument to check
     * @param msg message for exception
     * @param <T> type of argument
     * @throws IllegalArgumentException if arg is null
     */
    public static <T> void notNull(T arg, String msg) throws IllegalArgumentException {
        if(arg == null)
            throw new IllegalArgumentException(msg);
    }

}
