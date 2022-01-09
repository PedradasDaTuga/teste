import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.mockito.Mockito;

public class HealthTest {
    @Test
    public void Health(){
        Health health=new Health(30,20,20);
        Position expected= new Position(30,20);
        Assertions.assertEquals(expected,health.getPosition());
        Assertions.assertEquals(20,health.getAmountOfHealth());
    }
    @Test
    public void Health_draw(){
        Health health= new Health(30,20,20);
        TextGraphics graphics = Mockito.spy(TextGraphics.class);
        health.draw(graphics);
        Mockito.verify(graphics, Mockito.times(1)).setBackgroundColor(TextColor.Factory.fromString("#01579B"));
        Mockito.verify(graphics,Mockito.times(1)).setForegroundColor(TextColor.Factory.fromString("#8FCE00"));
    }
}
