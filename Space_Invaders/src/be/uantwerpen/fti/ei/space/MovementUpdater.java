package be.uantwerpen.fti.ei.space;

import java.util.List;

public class MovementUpdater {
    /**
     * Deze functie update de movement component list door alle x en y waardes aan te passen.
     * @param list de lijst met movementcomponent objecten
     * @return geeft de geüpdatet lijst terug
     */
    List<MovementComponent> update(List<MovementComponent> list) {
        for (var elem : list) {
            elem.setX(elem.getX() + elem.getDx());
            elem.setY(elem.getY() + elem.getDy());
        }
        return list;
    }
}