import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;

public class Health extends Element{
    private int amountOfHealth;
    public Health(int x, int y, int amountGiven) {
        super(x, y);
        amountOfHealth = amountGiven;
    }

    public int getAmountOfHealth() {
        return  amountOfHealth;
    }

    @Override
    public void draw(TextGraphics graphics) {
        graphics.setBackgroundColor(TextColor.Factory.fromString("#589BD9"));
        graphics.setForegroundColor(TextColor.Factory.fromString("#8FCE00"));
        graphics.putString(new TerminalPosition(position.getX(), position.getY()), "!");
    }

}
