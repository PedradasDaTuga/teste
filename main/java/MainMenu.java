import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.screen.Screen;


import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class MainMenu {
    private Pointer point;
    private Level level;
    public MainMenu(){
        point=new Pointer(150/2-1,50/2);
    };
    public void draw(TextGraphics graphics){
        graphics.setForegroundColor(TextColor.Factory.fromString("#FFF0FF"));
        graphics.putString(new TerminalPosition(150/2, 1), "Welcome to...");
        point.draw(graphics);
        graphics.putString(new TerminalPosition(150/2, 50/2), "New Game");
        graphics.putString(new TerminalPosition(150/2, 50/2+1), "Exit");

    }
    private void setPointer(Position position) {
        point.setPosition(position);
    }
    public Position getPointer() {
        return point.getPosition();
    }
    private boolean movePointer(Position position){
        if(canPointermove(position)) {
            point.setPosition(position);
            return true;
        }
        return false;
    }
    private boolean canPointermove(Position position){
        if(position.getY()>(50/2+1) || position.getY()<50/2)
            return false;
        return true;
    }
    public void processkey(KeyStroke key,Screen screen) throws IOException, InterruptedException{
        if(key.getKeyType()== KeyType.ArrowUp){
           if(!movePointer(point.moveUp()))
               setPointer(point.moveDown());
        }
        else if(key.getKeyType()==KeyType.ArrowDown){
            if(!movePointer(point.moveDown()))
                setPointer(point.moveUp());
        }
        else if(key.getKeyType()==KeyType.Enter) {
            if(point.getY()==50/2) {
                newgame(screen);
            }
            else if(point.getY()==(50/2+1)) {
                exit();
            }

        }

    }
    private void newgame(Screen screen) throws IOException, InterruptedException {
        level = new Level();
        run(screen);
    }

    private void exit() {
        System.exit(0);
    }


    public void run(Screen screen) throws IOException, InterruptedException {
        while(true) {
            screen.clear();
            TextGraphics graphics = screen.newTextGraphics();
            level.draw(graphics);
            screen.refresh();
            KeyStroke key = screen.readInput();
            if (key.getKeyType() == KeyType.Character && key.getCharacter() == 'q')
                screen.close();
            if (key.getKeyType() == KeyType.EOF)
                break;
            level.processKey(key, screen, graphics);
        }
    }
}