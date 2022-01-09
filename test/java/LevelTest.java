
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
import org.w3c.dom.Text;

import java.io.IOException;

public class LevelTest {

    @Test
    public void LevelHeroPosition(){
        Level level=Mockito.spy(Level.class);
        // hero starts at (75, 26)

        KeyStroke key_up = new KeyStroke(KeyType.ArrowUp);
        KeyStroke key_down = new KeyStroke(KeyType.ArrowDown);
        KeyStroke key_left = new KeyStroke(KeyType.ArrowLeft);
        KeyStroke key_right = new KeyStroke(KeyType.ArrowRight);
        KeyStroke key_shoot = new KeyStroke('u', false, false);

        Mockito.verify(level, Mockito.never()).moveHero(new Position(74, 26));
        Screen screen = null;
        try {
            TerminalSize terminalSize = new TerminalSize(150, 50);
            DefaultTerminalFactory terminalFactory = new DefaultTerminalFactory().setInitialTerminalSize(terminalSize);
            Terminal terminal = terminalFactory.createTerminal();
            screen = new TerminalScreen(terminal);


            level.processKey(key_left, screen, screen.newTextGraphics());
            Mockito.verify(level, Mockito.times(1)).moveHero(new Position(74, 26));
            Assertions.assertEquals(new Position(74, 26), level.getHeroPosition());

            level.processKey(key_right, screen, screen.newTextGraphics());
            Mockito.verify(level, Mockito.times(1)).moveHero(new Position(75, 26));
            Assertions.assertEquals(new Position(75, 26), level.getHeroPosition());

            // There is no ladder, so the hero can't move up
            level.processKey(key_up, screen, screen.newTextGraphics());
            Assertions.assertEquals(new Position(75, 26), level.getHeroPosition());
            // There is no ladder and the hero is on the ground, so he can't move down
            level.processKey(key_down, screen, screen.newTextGraphics());
            Assertions.assertEquals(new Position(75, 26), level.getHeroPosition());


            //// To test the ladder, we push the hero to a ladder position ////

            level.processKey(key_left, screen, screen.newTextGraphics());
            level.processKey(key_left, screen, screen.newTextGraphics());

            // The hero is on the ground, so he can't move down
            level.processKey(key_down, screen, screen.newTextGraphics());
            Assertions.assertEquals(new Position(73, 26), level.getHeroPosition());

            // There is a ladder, so the hero can move up;
            level.processKey(key_up, screen, screen.newTextGraphics());
            Mockito.verify(level, Mockito.times(1)).moveHero(new Position(73, 25));
            Assertions.assertEquals(new Position(73, 25), level.getHeroPosition());

            // The hero is on a ladder, so he can't move nor right nor left
            level.processKey(key_left, screen, screen.newTextGraphics());
            Assertions.assertEquals(new Position(73, 25), level.getHeroPosition());

            level.processKey(key_right, screen, screen.newTextGraphics());
            Assertions.assertEquals(new Position(73, 25), level.getHeroPosition());

            // The hero is on a ladder and he isn't on the ground, so he can move down
            level.processKey(key_down, screen, screen.newTextGraphics());
            Assertions.assertEquals(new Position(73, 26), level.getHeroPosition());

            //// To test the platform and if the hero is on top of the ladder, we push him to the platform position ////
            for(int i = 0; i < 26-11;i++)
            {
                level.processKey(key_up, screen, screen.newTextGraphics());
            }
            Assertions.assertEquals(new Position(73, 11), level.getHeroPosition());

            // There isn't a ladder, so the hero can't move up
            level.processKey(key_up, screen, screen.newTextGraphics());
            Assertions.assertEquals(new Position(73, 11), level.getHeroPosition());

            // There isn't a platform on the left, so the hero can't move to the left
            level.processKey(key_left, screen, screen.newTextGraphics());
            Assertions.assertEquals(new Position(73, 11), level.getHeroPosition());

            // There is a platform on the right, so the hero can move to the right
            level.processKey(key_right, screen, screen.newTextGraphics());
            Mockito.verify(level, Mockito.times(1)).moveHero(new Position(74, 11));
            Assertions.assertEquals(new Position(74, 11), level.getHeroPosition());

            // There is a platform on the left, so the hero can move to the left
            level.processKey(key_left, screen, screen.newTextGraphics());
            Assertions.assertEquals(new Position(73, 11), level.getHeroPosition());

            // There is a ladder below, so the hero can move below
            level.processKey(key_down, screen, screen.newTextGraphics());
            Assertions.assertEquals(new Position(73, 12), level.getHeroPosition());

            // Verifies it didn't hit a monster, so the amount doesn't decrease
            level.processKey(key_shoot, screen, screen.newTextGraphics());
            Assertions.assertEquals(5, level.getAmount_monsters());


        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void LevelDraw() {
        Level level = new Level();

        TextGraphics graphics = Mockito.spy(TextGraphics.class);
        level.draw(graphics);
        Mockito.verify(graphics, Mockito.times(12)).setBackgroundColor(TextColor.Factory.fromString("#01579B"));

    }
    @Test
    public void LevelGameOver() {
        Level level=Mockito.spy(Level.class);
        KeyStroke key_quit = new KeyStroke('q', false, false);
        Screen screen = null;
        try {
            TerminalSize terminalSize = new TerminalSize(150, 50);
            DefaultTerminalFactory terminalFactory = new DefaultTerminalFactory().setInitialTerminalSize(terminalSize);
            Terminal terminal = terminalFactory.createTerminal();
            screen = new TerminalScreen(terminal);
            TextGraphics graphics = Mockito.spy(TextGraphics.class);
            level.processKey(key_quit, screen, graphics);
            Mockito.verify(graphics, Mockito.times(1)).setBackgroundColor(TextColor.Factory.fromString("#000000"));

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
