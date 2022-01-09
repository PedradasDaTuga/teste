import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class LadderTest {

        @Test
        public void LadderConstructor() {
            Ladder ladder = new Ladder(30, 3);
            Position expected = new Position(30,3);
            Assertions.assertEquals(expected, ladder.getPosition());
        }

        @Test
        public void LadderDraw() {
            TextGraphics graphics = Mockito.spy(TextGraphics.class);
            Ladder ladder = new Ladder(30, 3);

            ladder.draw(graphics);
            Mockito.verify(graphics, Mockito.times(1)).setBackgroundColor(TextColor.Factory.fromString("#8FCE00"));

        }
    }

