package Java2D;

import be.uantwerpen.fti.ei.space.Input;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.LinkedList;

public class Java2DInput extends Input {
    private final LinkedList<Inputs> keyInputs;

    /**
     * Deze functie maakt een linked list aan voor de inputs.
     * @param gr Java2DFactory
     */
    public Java2DInput(Java2DFactory gr) {
        gr.getFrame().addKeyListener(new KeyInputAdapter());
        keyInputs = new LinkedList<Inputs>();
    }

    /**
     * Deze functie gaat na of er input beschikbaar is.
     * @return boolean isEmpty()
     */
    @Override
    public boolean inputAvailable() {
        return !keyInputs.isEmpty();
    }

    /**
     * Deze functie geeft de input terug
     * @return keyInput
     */
    @Override
    public Inputs getInput() {
        return keyInputs.poll();
    }

    /**
     * Deze functie maakt de linked list met inputs leeg.
     */
    @Override
    public void Clear(){
        keyInputs.clear();
    }

    /**
     * Deze klasse gaat na welke input key ingedrukt is.
     */
    class KeyInputAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            int keycode = e.getKeyCode();
            switch (keycode) {
                case KeyEvent.VK_LEFT:
                    keyInputs.add(Inputs.LEFT);
                    break;
                case KeyEvent.VK_RIGHT:
                    keyInputs.add(Inputs.RIGHT);
                    break;
                case KeyEvent.VK_DOWN:
                    keyInputs.add(Inputs.DOWN);
                    break;
                case KeyEvent.VK_UP:
                    keyInputs.add(Inputs.UP);
                    break;
                case KeyEvent.VK_SPACE:
                    keyInputs.add(Inputs.FIRE);
                    break;
            }
        }
    }
}
