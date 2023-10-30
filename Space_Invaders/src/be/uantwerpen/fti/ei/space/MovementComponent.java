package be.uantwerpen.fti.ei.space;

public class MovementComponent {
    private float dx;
    private float dy;

    private float x;
    private float y;

    /**
     * Deze functie geeft de dx waarde terug.
     * @return dx verandering in x waarde
     */
    public float getDx() {
        return dx;
    }

    /**
     * Deze functie geeft Dx een waarde.
     * @param dx de verandering in x waarde.
     */
    public void setDx(float dx) {
        this.dx = dx;
    }

    /**
     * Deze functie geeft de dy waarde terug.
     * @return dy de verandering in y waarde
     */
    public float getDy() {
        return dy;
    }

    /**
     * Deze functie geeft dy een waarde.
     * @param dy de verandering in y waarde.
     */
    public void setDy(float dy) {
        this.dy = dy;
    }

    /**
     * Deze functie geeft de x waarde terug.
     * @return x de x waarde
     */
    public float getX() {
        return x;
    }

    /**
     * Deze functie geeft x een waarde
     * @param x de x waarde
     */
    public void setX(float x) {
        this.x = x;
    }

    /**
     * Deze functie geeft de y waarde terug
     * @return y de y waarde
     */
    public float getY() {
        return y;
    }

    /**
     * Deze functie geeft y een waarde
     * @param y de y waarde
     */
    public void setY(float y) {
        this.y = y;
    }
}
