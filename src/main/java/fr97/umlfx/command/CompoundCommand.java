package fr97.umlfx.command;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Stack;

public class CompoundCommand implements Command {

    private Stack<Command> commands = new Stack<>();

    /**
     * Adds a command to the stack of commands
     * @param command The command to be added
     */
    public void add(Command command)
    {
        commands.push(command);
    }


    @Override
    public void execute() {
        Stack<Command> temp = new Stack<>();
        while(!commands.empty()) {
            Command c = commands.pop();
            c.execute();
            temp.push(c);
        }
        commands = temp;
    }

    @Override
    public void undo() {
        Stack<Command> temp = new Stack<>();
        while(!commands.empty()) {
            Command c = commands.pop();
            c.undo();
            temp.push(c);
        }
        commands = temp;
    }

    public Collection<Command> getCommands(){
        return new ArrayList<>(commands);
    }

    /**
     * Returns number of commands that this compount command consists of
     * @return number of commands
     */
    public int commandCount(){
        return commands.size();
    }
}
