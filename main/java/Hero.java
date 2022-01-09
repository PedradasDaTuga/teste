import com.googlecode.lanterna.SGR;
import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;

public class Hero extends Element{

    public Hero(int x, int y) { super (x, y);}

    public Position getPosition() { return position; }
    public void setPosition(Position position) {
        this.position = position;
    }
    public int getX() { return position.getX();}
    public int getY() { return position.getY();}
    public Position moveRight() {
        return new Position(position.getX() + 1, position.getY());
    }
    public Position moveLeft() {
        return new Position(position.getX() - 1, position.getY());
    }
    public Position moveUp(){
        return new Position(position.getX(), position.getY()-1);
    }
    public Position moveDown(){
        return new Position(position.getX(), position.getY()+1);
    }

    public void draw(TextGraphics graphics) {
        graphics.setBackgroundColor(TextColor.Factory.fromString("#589BD9"));
        graphics.setForegroundColor(TextColor.Factory.fromString("#FFD700"));
        graphics.enableModifiers(SGR.BOLD);
        graphics.putString(new TerminalPosition(position.getX(), position.getY()), "H");
    }
}
