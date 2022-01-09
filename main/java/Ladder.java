import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;

public class Ladder extends Element{
    public Ladder(int x,int y){
        super(x,y);
    }
    @Override
    public void draw(TextGraphics graphics) {
        graphics.setForegroundColor(TextColor.Factory.fromString("#8CCE00"));
        graphics.setBackgroundColor(TextColor.Factory.fromString("#8CCE00"));
        graphics.putString(new TerminalPosition(position.getX(), position.getY()), "L");
    }
}
