import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.mockito.Mockito;

import java.io.IOException;

public class ProjectileTest {
    @Test
    public void Projectile() {
        Projectiles projectile = new Projectiles(10, 10, 5, 20);
        Position expected_position = new Position(10, 10);
        int expected_speed = 5;
        int expected_damage = 20;
        Assertions.assertEquals(expected_position, projectile.getPosition());
        Assertions.assertEquals(expected_speed, projectile.getSpeed());
        Assertions.assertEquals(expected_damage, projectile.getDamage());
    }

    @Test
    public void ProjectileDraw() {
        Projectiles projectile = new Projectiles(10, 10, 5, 20);
        TextGraphics graphics = Mockito.spy(TextGraphics.class);
        projectile.draw(graphics);
        Mockito.verify(graphics, Mockito.times(1)).setBackgroundColor(TextColor.Factory.fromString("#01579B"));
        Mockito.verify(graphics, Mockito.times(1)).setForegroundColor(TextColor.Factory.fromString("#FFFFFF"));
        Mockito.verify(graphics, Mockito.times(1)).putString(new TerminalPosition(projectile.getPosition().getX(), projectile.getPosition().getY()), "*");
    }

    @Test
    public void MoveBullet() {
        Projectiles projectile = new Projectiles(10, 10, 5, 20);
        int new_x = 6;
        int new_y = 7;
        Assertions.assertEquals(projectile.moveBullet(6, 7), new Position(16, 17));

    }

    @Test
    public void UpdatePosition() {
        Projectiles projectile = new Projectiles(12, 15, 5, 5);
        projectile.updatePosition(new Position(10, 10));
        Assertions.assertEquals(new Position(10, 10), projectile.getPosition());
    }

    @Test
    public void Update() {
        Projectiles projectiles= new Projectiles(10, 10,1,10);
        Screen screen = null;
        try {
            
            TerminalSize terminalSize = new TerminalSize(150, 50);
            DefaultTerminalFactory terminalFactory = new DefaultTerminalFactory().setInitialTerminalSize(terminalSize);
            Terminal terminal = terminalFactory.createTerminal();
            screen = new TerminalScreen(terminal);

            // Shoots straight from hero to monster that is at a x between [0;50] higher than hero.getX, and the same y position from the Hero.
            Assertions.assertTrue(projectiles.update(new Position(10, 10),screen,screen.newTextGraphics(),new Position(15,10),"front"));
            Assertions.assertFalse(projectiles.update(new Position(10, 10),screen,screen.newTextGraphics(),new Position(66,10),"front"));
            Assertions.assertFalse(projectiles.update(new Position(10, 10),screen,screen.newTextGraphics(),new Position(15,11),"front"));
            // Shoots straight from hero to monster that is at a x between [0;50] lower than hero.getX, and the same y position from the Hero.
            Assertions.assertTrue(projectiles.update(new Position(10, 10),screen,screen.newTextGraphics(),new Position(6,10),"back"));
            Assertions.assertFalse(projectiles.update(new Position(10, 10),screen,screen.newTextGraphics(),new Position(15,10),"back"));
            Assertions.assertFalse(projectiles.update(new Position(10, 10),screen,screen.newTextGraphics(),new Position(6,11),"back"));
            // Shoots straight from hero to monster that is at a x equals to the hero's X, and the  y between[0;10] lower from the Hero's y.
            Assertions.assertTrue(projectiles.update(new Position(10, 10),screen,screen.newTextGraphics(),new Position(10,6),"up"));
            Assertions.assertFalse(projectiles.update(new Position(10, 10),screen,screen.newTextGraphics(),new Position(10,12),"up"));
            Assertions.assertFalse(projectiles.update(new Position(10, 10),screen,screen.newTextGraphics(),new Position(11,6),"up"));
            // Shoots straight from hero to monster that is at a x between [0;3] higher than the Hero's X, and the same y position from the Hero's y.
            Assertions.assertTrue(projectiles.update(new Position(10, 10),screen,screen.newTextGraphics(),new Position(11,10),"melee"));
            Assertions.assertFalse(projectiles.update(new Position(10, 10),screen,screen.newTextGraphics(),new Position(12,10),"melee"));
            Assertions.assertFalse(projectiles.update(new Position(10, 10),screen,screen.newTextGraphics(),new Position(11,11),"melee"));


        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

    }

}
