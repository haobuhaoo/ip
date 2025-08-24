public abstract class Command {
    private Boolean exit = false;

    public Boolean isExit() { return exit; }

    public void setExit(boolean e) { exit = e; }

    abstract void execute(TaskList t, Ui u, Storage s) throws InvalidCommandException;
}
