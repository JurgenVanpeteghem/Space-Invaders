package be.uantwerpen.fti.ei.space;

abstract public class Input {
    abstract public boolean inputAvailable();

    abstract public Inputs getInput();

    abstract public void Clear();

    public enum Inputs {LEFT, RIGHT, UP, DOWN, FIRE};
}
