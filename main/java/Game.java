import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextCharacter;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;
import java.io.IOException;

public class Game {
    private Screen screen;
    private MainMenu menu;
    public Game(){
        try {

            TerminalSize terminalSize = new TerminalSize(150, 50);
            DefaultTerminalFactory terminalFactory = new DefaultTerminalFactory().setInitialTerminalSize(terminalSize);
            Terminal terminal = terminalFactory.createTerminal();
            screen = new TerminalScreen(terminal);
            screen.setCursorPosition(null);
            screen.startScreen();
            screen.doResizeIfNecessary();

        } catch (IOException e) {
            e.printStackTrace();
        }
        menu =new MainMenu();

    }
    private void draw() throws IOException{
        screen.clear();
        menu.draw(screen.newTextGraphics());
        screen.refresh();
    }
    private void processKey(com.googlecode.lanterna.input.KeyStroke key,Screen screen) throws IOException, InterruptedException{
        menu.processkey(key,screen);
    }


    public void run() throws IOException, InterruptedException {
        while(true){
            draw();
            KeyStroke key = screen.readInput();
            if (key.getKeyType()==KeyType.EOF )
                break;
            processKey(key,screen);
        }
    }

}