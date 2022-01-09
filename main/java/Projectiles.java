import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.screen.Screen;

import java.awt.event.MouseListener;
import java.io.IOException;
import java.util.List;

public class Projectiles extends Element{
    private int speed;
    private int damage;
    public Projectiles(int x,int y, int speed,int damage){
        super(x, y);
        this.speed=speed;
        this.damage=damage;
    }
    public int getDamage() {
        return damage;
    }
    public int getSpeed() {
        return speed;
    }

    public void updatePosition(Position position) {
        this.position = position;
    }
    public void draw(TextGraphics graphics){
        graphics.setBackgroundColor(TextColor.Factory.fromString("#589BD9"));
        graphics.setForegroundColor(TextColor.Factory.fromString("#FFFFFF"));
        graphics.putString(new TerminalPosition(position.getX(), position.getY()),"*");
    }
    public boolean update(Position position_hero ,Screen screen,TextGraphics graphics, Position position,String direction) throws InterruptedException, IOException {
        updatePosition(position_hero);
        draw(graphics);
        if(direction=="up"){
            if (shoot_up(screen, graphics, position)) return true;
        }
        else if(direction=="front"||direction == "back"){
            if (shoot_straight(screen, graphics, position, direction)) return true;
        }
        else if(direction == "melee"){
            if (shoot_melee(screen, graphics, position)) return true;
        }
        return false;
    }

    private boolean shoot_melee(Screen screen, TextGraphics graphics, Position position) throws InterruptedException, IOException {
       int counter=0;
        while (counter < 2 && position.getX() < screen.getTerminalSize().getColumns()) {
            if (verifyBulletCollisions(screen, graphics, position)) {
                screen.refresh();
                Thread.sleep(550);
                return true;
            }
                setPosition(moveBullet(1, 0));
                screen.refresh();
                draw(graphics);
            counter++;
        }
        return false;
    }

    private boolean shoot_straight(Screen screen, TextGraphics graphics, Position position, String direction) throws InterruptedException, IOException {
       int counter=0;
        while (counter < 50 && position.getX() < screen.getTerminalSize().getColumns()) {
            if (verifyBulletCollisions(screen, graphics, position)) {
                screen.refresh();
                Thread.sleep(550);
                return true;
            }
            if (direction == "front")
                setPosition(moveBullet(speed, 0));
            else if (direction == "back")
                setPosition(moveBullet(-1 * speed, 0));

            if (counter % 3 == 1) {
                screen.refresh();
                draw(graphics);
            }
            counter++;
        }
        return false;
    }

    private boolean shoot_up(Screen screen, TextGraphics graphics, Position position) throws InterruptedException, IOException {
        int counter=0;
        while(counter <10 && position.getY()< screen.getTerminalSize().getRows()){
            if(verifyBulletCollisions(screen, graphics, position)) {
                screen.refresh();
                Thread.sleep(350);
                return true;
            }
            setPosition(moveBullet(0, -1*speed));

        if(counter %2 == 1) {
            screen.refresh();
            draw(graphics);
        }
        counter++;
        }
        return false;
    }

    public Position moveBullet( int x, int y) {
        return new Position(position.getX() + x, position.getY() + y);
    }
    private boolean verifyBulletCollisions(Screen screen, TextGraphics graphics, Position position) throws InterruptedException, IOException {
        graphics.setForegroundColor(TextColor.Factory.fromString("#FFFFFF"));
        graphics.setBackgroundColor(TextColor.Factory.fromString("#000000"));

        if(getPosition().equals(position)) {
            graphics.putString(new TerminalPosition(screen.getTerminalSize().getColumns()/2, 5), "Nice shot!");
            return true;
        }
        return false;
    }
}