import processing.core.*;
import java.util.List;
import java.util.ArrayList;

public class Obstacle
   extends Entity
{
   public Obstacle(String name, Point position, List<PImage> imgs)
   {
      super(name, position, imgs);
   }
 
   public String entity_string()
   {
      return "obstacle" + " " + this.get_name() + " " + 
         this.get_position().getX() + " " + this.get_position().getY();
   }
}
