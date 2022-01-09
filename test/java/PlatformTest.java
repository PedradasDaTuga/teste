import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.mockito.Mockito;

public class PlatformTest {

    @Test
    public void Platform_floor() {
        Platform f = new Platform(10, 10 , "floor");
        Position p_expected = new Position(10,10);

        Assertions.assertEquals(p_expected, f.getPosition());
        Assertions.assertEquals("floor", f.getType());
    }

    @Test
    public void Platform_platform() {
        Platform p = new Platform(10, 10 , "platform");
        Position p_expected = new Position(10,10);

        Assertions.assertEquals(p_expected, p.getPosition());
        Assertions.assertEquals("platform", p.getType());
    }

    @Test
    public void Platform_drawfloor(){
        Platform f = new Platform(10, 10 , "floor");
        TextGraphics graphics = Mockito.spy(TextGraphics.class);
        f.draw(graphics);
        Mockito.verify(graphics, Mockito.times(1)).setBackgroundColor(TextColor.Factory.fromString("#47DA06"));
    }

    @Test
    public void Platform_drawplatform(){
        Platform f = new Platform(10, 10 , "platform");
        TextGraphics graphics = Mockito.spy(TextGraphics.class);
        f.draw(graphics);
        Mockito.verify(graphics, Mockito.times(1)).setBackgroundColor(TextColor.Factory.fromString("#E35259"));
    }
}