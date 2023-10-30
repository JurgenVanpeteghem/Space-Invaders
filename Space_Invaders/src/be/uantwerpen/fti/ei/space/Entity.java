package be.uantwerpen.fti.ei.space;

import java.io.IOException;

public abstract class Entity {
    private MovementComponent movementComponent = new MovementComponent();

    public MovementComponent getMovementComponent() {
        return movementComponent;
    }

    protected abstract void Vis() throws IOException;

}
