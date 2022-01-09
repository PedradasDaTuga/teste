import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.screen.Screen;

import java.io.IOException;

import java.util.ArrayList;
import java.util.List;

public class Level{
    private final Hero hero;

    private final int width;
    private final int height;
    private int amount_monsters;
    private int currentHealth;
    private List<Health> healthshot;
    private List<Monster> monsters;
    private List<Ladder> ladders;
    private List<Projectiles> projectiles;
    private List<Platform> platforms;

    public Level() {
        this.width = 150;
        this.height = 40;
        this.amount_monsters = 0;
        hero = new Hero(75, 26);
        this.healthshot = createHealthshots();
        this.monsters = createMonsters();
        this.ladders =createLadder();
        this.projectiles = createProjectiles();
        currentHealth = 100; // Starting health;
        this.platforms=createPlatforms1();
    }

    public int getAmount_monsters() {
        return amount_monsters;
    }

    private List<Monster> createMonsters() {
        ArrayList<Monster> monsters = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            amount_monsters++;
            monsters.add(new Monster(width-14-15*i, height/2-2*i -1,10));
        }
        monsters.add(new Monster(width-10, 2*height/3, 40));
        amount_monsters++;
        return monsters;
    }

    private List<Health> createHealthshots() {
        ArrayList<Health> healthshot = new ArrayList<>();
        for (int i = 0; i < 5; i++)
            healthshot.add(new Health(width - 5 - 25*i, 2*height/3, 20));
        return healthshot;
    }

    private List<Ladder> createLadder(){
        ArrayList<Ladder> ladder=new ArrayList<>();
        for(int k = 0; k < 5; k++) {
            for(int i=1;i<=(height/2-13 +2*k);i++){
                ladder.add(new Ladder(width-17-15*k,height/2-2*k + i - 1));
            }
        }
        return ladder;
    }

    private List<Projectiles> createProjectiles() {
        ArrayList<Projectiles> projectiles = new ArrayList<>();
        projectiles.add(new Projectiles(hero.getX(), hero.getY(), 1, 10));
        return projectiles;
    }
    private List<Platform> createPlatforms1(){
        ArrayList<Platform> platforms=new ArrayList<>();
        for (int i =0;i<150;i++){
            platforms.add(new Platform(i,2*height / 3+1,"floor"));
        }
        for(int i = 0; i < 5; i++) {
            for (int j=0;j<6;j++)
            platforms.add(new Platform(width-17-15*i+j, height/2-2*i,"platform"));
        }
        return platforms;
    }
    public void draw(TextGraphics graphics) {
        graphics.setBackgroundColor(TextColor.Factory.fromString("#589BD9"));
        graphics.fillRectangle(new TerminalPosition(0, height/4), new TerminalSize(width, 3*height/4), ' ');

        for(Platform platform: platforms)
            platform.draw(graphics);

        HUD(graphics);

        for (Health health : healthshot) health.draw(graphics);
        for (Monster monster : monsters) monster.draw(graphics);
        for(Ladder ladder: ladders)ladder.draw(graphics);
        hero.draw(graphics);
    }

    public void processKey(KeyStroke key, Screen screen, TextGraphics graphics) throws InterruptedException, IOException {
        if (key.getKeyType() == KeyType.ArrowRight){
            if(onlader()){
                if(lastlader())
                    moveHero(hero.moveRight());
            }
            else{
                if(ontopofplatform(hero.moveRight()))
                    moveHero(hero.moveRight());
            }

        }
        if (key.getKeyType() == KeyType.ArrowLeft){
            if(onlader()){
                if(lastlader())
                    moveHero(hero.moveLeft());
            }
            else{
                if(ontopofplatform(hero.moveLeft()))
                    moveHero(hero.moveLeft());
            }
        }
        if (key.getKeyType() == KeyType.ArrowUp)
        {
            if (onlader())
                moveHero(hero.moveUp());
        }
        if (key.getKeyType() == KeyType.ArrowDown){
            if (onlader() || upperlader())
                moveHero(hero.moveDown());
        }
        if(key.getKeyType() == KeyType.Character && key.getCharacter() == 'u'){
            shoot(hero.position, screen, graphics,"front");
        }
        if(key.getKeyType() == KeyType.Character && key.getCharacter() == 'y'){
            shoot(hero.position, screen, graphics,"back");
        }
        if(key.getKeyType() == KeyType.Character && key.getCharacter() == 'i'){
            shoot(hero.position, screen, graphics,"up");
        }
        if(key.getKeyType()==KeyType.Character && key.getCharacter() == 'j'){
            shoot(hero.position, screen, graphics,"melee");
        }
        if(key.getKeyType()==KeyType.Character && key.getCharacter() == 'q') { //ONLY FOR TEST PURPOSE
            allMonstersDead(graphics, screen);
        }

        retrieveHealthshot();
        verifyMonsterCollisions(screen, graphics);
        moveMonsters();
        verifyMonsterCollisions(screen, graphics);
    }

    public void verifyMonsterCollisions(Screen screen, TextGraphics graphics) throws InterruptedException, IOException {
        for (Monster monster : monsters)
            if (hero.getPosition().equals(monster.getPosition())) {
                graphics.setBackgroundColor(TextColor.Factory.fromString("#000000"));
                graphics.setForegroundColor(TextColor.Factory.fromString("#FFFFFF"));
                currentHealth -= monster.getDamage();
                if(currentHealth <= 0) {
                    graphics.putString(new TerminalPosition(width/2, 5), "You Died!");
                    HUD(graphics);
                    screen.refresh();
                    Thread.sleep(2000);
                    screen.clear();
                    System.exit(0);
                }
                for(int i = 0; i < 8; i++) {
                    if(i%2 == 0) graphics.setForegroundColor(TextColor.Factory.fromString("#FFFFFF"));
                    else graphics.setForegroundColor(TextColor.Factory.fromString("#000000"));
                    graphics.putString(new TerminalPosition(width/2, 5), "You Hurt!");
                    screen.refresh();
                    Thread.sleep(150);
                }

            }
    }

    public void moveMonsters() {
        for (Monster monster : monsters) {
            Position monsterPosition = monster.move();
            if(ontopofplatform(monsterPosition)){
                if (canHeroMove(monsterPosition))
                    monster.setPosition(monsterPosition);
            }
        }
    }

    private void retrieveHealthshot() {
        for (Health health : healthshot)
            if (hero.getPosition().equals(health.getPosition())) {
                currentHealth += health.getAmountOfHealth();
                healthshot.remove(health);
                break;
            }
    }

    public void moveHero(Position position) {
        if (canHeroMove(position)) {
            hero.setPosition(position);
        }
    }

    public Position getHeroPosition() {
        return hero.getPosition();
    }

    private boolean canHeroMove(Position position) {
        if (position.getX() < 0) return false;
        if (position.getX() > width - 1) return false;
        if (position.getY() < 0) return false;
        if (position.getY() > height - 1) return false;
        if(position.getY()>2*height / 3) return false; // TO BE CHANGED
        return true;
    }

    private void HUD(TextGraphics graphics) {
        graphics.setBackgroundColor(TextColor.Factory.fromString("#FFFFFF"));
        graphics.setForegroundColor(TextColor.Factory.fromString("#FFFFFF"));
        graphics.drawRectangle(new TerminalPosition(3, 3), new TerminalSize(15,1), ' ');
        graphics.drawRectangle(new TerminalPosition(3, 3), new TerminalSize(1,5), ' ');
        graphics.drawRectangle(new TerminalPosition(17, 3), new TerminalSize(1,5), ' ');
        graphics.drawRectangle(new TerminalPosition(3, 8), new TerminalSize(15,1), ' ');

        // Health status
        graphics.setBackgroundColor(TextColor.Factory.fromString("#000000"));
        graphics.putString(new TerminalPosition(5,5), "Health");
        graphics.putString(new TerminalPosition(5,6), String.valueOf(currentHealth));

        // Monster status
        graphics.setBackgroundColor(TextColor.Factory.fromString("#FFFFFF"));
        graphics.drawTriangle(new TerminalPosition( 20, 3), new TerminalPosition( 30, 3), new TerminalPosition( 25, 8), ' ');
        graphics.setBackgroundColor(TextColor.Factory.fromString("#000000"));
        graphics.putString(new TerminalPosition(25,5), String.valueOf(amount_monsters));

    }
    public boolean onlader(){
        for(Ladder ladder : ladders){
            if(hero.getPosition().equals(ladder.getPosition())){
                return true;
            }
        }
        return false;
    }
    public boolean upperlader(){
        for (Ladder ladder :ladders){
            Position temp= new Position(ladder.getPosition().getX(),ladder.getPosition().getY()-1);
            if(hero.getPosition().equals(temp))
                return true;
        }
        return false;
    }
    public boolean lastlader(){
        if(hero.getPosition().getY()==2*height / 3){
            return true;
        }
        return false;
    }
    public boolean ontopofplatform(Position position){
        for(Platform platform: platforms){
            Position temp=new Position(platform.getPosition().getX(),platform.getPosition().getY()-1);
            if(position.equals(temp))
                return true;
        }
        return false;
    }

    private void shoot(Position position, Screen screen, TextGraphics graphics,String direction) throws IOException, InterruptedException {

        for(Projectiles projectiles: projectiles) {
            for(Monster monster: monsters) {
                if(projectiles.update(position, screen, graphics, monster.getPosition(),direction)) {
                    monsters.remove(monster);
                    amount_monsters--;
                    if(amount_monsters == 0) {
                        allMonstersDead(graphics, screen);
                        screen.close();
                    }
                    break;
                }
            }
        }
    }

    private void allMonstersDead(TextGraphics graphics, Screen screen) throws InterruptedException, IOException {
        graphics.setBackgroundColor(TextColor.Factory.fromString("#000000"));
        graphics.setForegroundColor(TextColor.Factory.fromString("#FFFFFF"));
        graphics.putString(new TerminalPosition(75,5), "GAME OVER");
        screen.refresh();
        Thread.sleep(2000);
    }
}