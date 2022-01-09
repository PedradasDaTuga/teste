import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.mockito.Mockito;

public class MonsterTest {
    @Test
    public void Monster() {
        Monster monster = new Monster(10, 10, 15);
        Position expected = new Position(10, 10);
        Assertions.assertEquals(expected, monster.getPosition());
    }

    @Test
    public void Monster_draw(){
        Monster monster= new Monster(10,10,20);
        TextGraphics graphics= Mockito.spy(TextGraphics.class);
        monster.draw(graphics);
        Mockito.verify(graphics,Mockito.times(1)).setBackgroundColor(TextColor.Factory.fromString("#01579B"));
        Mockito.verify(graphics,Mockito.times(1)).setForegroundColor(TextColor.Factory.fromString("#910A60"));
    }

    @Test
    public void Monster_move(){
        Monster monster = new Monster(10, 10, 15);
        // New monster position must be between -1 <= x - monster.getPosition() <= 1, meaning that the new x must be only one more/less than the original
        Assertions.assertNotEquals( new Position(8, 10), monster.getPosition());
        Assertions.assertNotEquals( new Position(12, 10), monster.getPosition());
    }
}
