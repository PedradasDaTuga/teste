import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.mockito.Mockito;

public class PointerTest {
    @Test
    public void Pointer(){
        Pointer pointer= new Pointer(30,20);
        Position expected= new Position(30,20);
        Assertions.assertEquals(expected,pointer.getPosition());
    }

    @Test
    public void Pointer_draw(){
        Pointer pointer= new Pointer(30,20);
        TextGraphics graphics = Mockito.spy(TextGraphics.class);
        pointer.draw(graphics);
        Mockito.verify(graphics,Mockito.times(1)).setForegroundColor(TextColor.Factory.fromString("#FFF0FF"));

    }

    @Test
    public void Pointer_moveLeft(){
        Pointer pointer= new Pointer(30,20);
        Position expected= new Position(29,20);
        Assertions.assertEquals(expected,pointer.moveLeft());
    }

    @Test
    public void Pointer_moveRight(){
        Pointer pointer= new Pointer(30,20);
        Position expected= new Position(31,20);
        Assertions.assertEquals(expected,pointer.moveRight());
    }

    @Test
    public void Pointer_moveUp(){
        Pointer pointer= new Pointer(30,20);
        Position expected= new Position(30,19);
        Assertions.assertEquals(expected,pointer.moveUp());
    }

    @Test
    public void Pointer_moveDown(){
        Pointer pointer= new Pointer(30,20);
        Position expected= new Position(30,21);
        Assertions.assertEquals(expected,pointer.moveDown());
    }
}
