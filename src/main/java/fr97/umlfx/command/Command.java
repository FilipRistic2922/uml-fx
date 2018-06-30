package fr97.umlfx.command;

/**
 * TODO treba da se implementira jos bar 7-8 klasa, CommandManager je uglavnom prosledjen svugde gde treba
 * <p>
 *  Interface describing an command in this project, apart from standard execute every command
 *  must be able to undo itself.
 *  </p>
 *
 * @author Filip
 */
public interface Command {

    /**
     * This method is called in other to execute command
     */
    void execute();

    /**
     * This method is called in other to undo change made by this command
     * It must be implemented in such way that calling it will restore application to exactly same state
     * it had before executing this command.
     */
    void undo();

}
