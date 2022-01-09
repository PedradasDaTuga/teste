import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.mockito.Mockito;

import java.io.IOException;


public class MainMenuTest {

    @Test
    public void MainMenuPointer() {
        MainMenu menu = Mockito.spy(MainMenu.class);
        KeyStroke key_up = new KeyStroke(KeyType.ArrowUp);
        KeyStroke key_down = new KeyStroke(KeyType.ArrowDown);
        Screen screen = null;
        try {
            TerminalSize terminalSize = new TerminalSize(150, 50);
            DefaultTerminalFactory terminalFactory = new DefaultTerminalFactory().setInitialTerminalSize(terminalSize);
            Terminal terminal = terminalFactory.createTerminal();
            screen = new TerminalScreen(terminal);

            menu.processkey(key_down, screen);
            Assertions.assertEquals(new Position(74, 26), menu.getPointer());

            menu.processkey(key_up, screen);
            Assertions.assertEquals(new Position(74, 25), menu.getPointer());
            // Goes to the last option whenever it tries to go above the first
            menu.processkey(key_up, screen);
            Assertions.assertEquals(new Position(74, 26), menu.getPointer());

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void MenuDraw() {
        MainMenu menu = new MainMenu();
        TextGraphics graphics = Mockito.spy(TextGraphics.class);
        menu.draw(graphics);
        Mockito.verify(graphics, Mockito.times(2)).setForegroundColor(TextColor.Factory.fromString("#FFF0FF"));

    }
}
