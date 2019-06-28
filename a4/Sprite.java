import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Iterator;
import java.awt.event.ActionEvent;
abstract class Sprite
{
    //Member Variables
    int x;
    int y;
    int w;
    int h;
    double vert_vel = 0;
    int hvel;
    Model model;
    String type;
    
    abstract void update(Model m); 
    abstract void draw(Graphics g, int scrollPos);
    abstract Json marshall();
    
    boolean addcoin;
    boolean coinDead = false;
    
    boolean am_i_a_brick()
    {
        return false;
    }
    
    boolean am_i_a_coinBlock()
    {
        return false;
    }
    
    //Constructor that takes a model m
    Sprite(Model m)
    {
        model = m;
    }
    
    //Sprite collision method
    boolean doesCollide(Sprite that)
    {
        if(this.x + this.w <= that.x)
            return false;
        if(this.x >= that.x + that.w)
            return false;
        if(this.y + this.h <= that.y)
            return false;
        if(this.y >= that.y + that.h)
          return false;
        return true;
    }
}
