import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;

public class Platform extends Element{
    private String type;
    public Platform(int x,int y,String tipo){
        super(x,y);
        this.type=tipo;
    }

    public String getType() {
        return type;
    }

    @Override
    public void draw(TextGraphics graphics) {
        if(this.type=="floor"){
            graphics.setBackgroundColor(TextColor.Factory.fromString("#47DA06"));
            graphics.putString(new TerminalPosition(position.getX(), position.getY()), " ");
           
        }
        if (this.type == "platform") {
            graphics.setBackgroundColor(TextColor.Factory.fromString("#E35259"));
            graphics.putString(new TerminalPosition(position.getX(), position.getY()), " ");
        }
    }
}
