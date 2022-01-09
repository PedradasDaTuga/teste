import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.mockito.Mockito;

public class    HeroTest {
    @Test
    public void Hero(){
        Hero hero=new Hero(30,30);
        Position expected= new Position(30,30);
        Assertions.assertEquals(expected,hero.getPosition());
    }

    @Test
    public void Hero_draw(){
        Hero hero = new Hero(30,30);
        TextGraphics graphics = Mockito.spy(TextGraphics.class);
        hero.draw(graphics);
        Mockito.verify(graphics, Mockito.times(1)).setBackgroundColor(TextColor.Factory.fromString("#01579B"));
        Mockito.verify(graphics,Mockito.times(1)).setForegroundColor(TextColor.Factory.fromString("#964B00"));
    }

    @Test
    public void Hero_moveLeft(){
        Hero hero = new Hero(30,30);
        Position expected= new Position(29,30);
        Assertions.assertEquals(expected,hero.moveLeft());
    }
    @Test
    public void Hero_moveRight(){
        Hero hero = new Hero(30,30);
        Position expected= new Position(31,30);
        Assertions.assertEquals(expected,hero.moveRight());
    }
    @Test
    public void Hero_moveUp(){
        Hero hero = new Hero(30,30);
        Position expected= new Position(30,29);
        Assertions.assertEquals(expected,hero.moveUp());
    }
    @Test
    public void Hero_moveDown(){
        Hero hero = new Hero(30,30);
        Position expected= new Position(30,31);
        Assertions.assertEquals(expected,hero.moveDown());
    }
}
