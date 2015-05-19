import java.util.List;
import java.util.ArrayList;
import processing.core.*;

public class Blacksmith
   extends Collectors
{
   public int resource_distance;

   public Blacksmith(String name, Point postion, int rate, List<PImage> imgs, int resource_limit, int resource_distance)
   {
      super(name, position, rate, imgs, 0, resource_limit);
      this.resource_distance = resource_distance;
   }

   public int get_resource_distance()
   {
      return this.resource_distance;
   }

   public String entity_string()
   { 
      return "blacksmith" + this.get_name() + " " + this.get_position().getX() +
         " " + this.get_position().getY() + " " + this.get_resource_limit()  
         + " " + this.get_rate() + " " + this.get_resource_distance();
   }
}
