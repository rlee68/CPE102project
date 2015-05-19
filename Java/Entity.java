import processing.core.*;
import java.util.List;
import java.util.ArrayList;

public class Entity
   extends WorldObject
{
   private Point position;
  
   public Entity(String name, Point position, List<PImage> imgs)
   {
      super(name, imgs);
      this.position = position;
   }
 
   public Point set_position(Point pt)
   {
      this.position = pt;
   }

   public Point get_position()
   {
      return this.position;
   }
}
