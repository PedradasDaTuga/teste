import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;

import java.util.Random;

public class Monster extends Element {
    private int damage;
    public Monster(int x, int y,int dmg){
        super(x,y);
        damage=dmg;
    }

    public int getDamage() {
        return damage;
    }
    @Override
    public void draw(TextGraphics graphics) {
        graphics.setBackgroundColor(TextColor.Factory.fromString("#589BD9"));
        graphics.setForegroundColor(TextColor.Factory.fromString("#BB0231"));
        graphics.putString(new TerminalPosition(position.getX(), position.getY()), "M");
    }

    public Position move() {
        return switch (new Random().nextInt(4)) {
            case 0 -> new Position(position.getX() +1, position.getY());
            case 1 -> new Position(position.getX() - 1, position.getY());
            default -> new Position(position.getX(), position.getY());
        };
    }
}