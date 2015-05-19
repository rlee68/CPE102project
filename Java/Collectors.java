import processing.core.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;

public class Collectors
   extends Animated
{
   private int resource_count = 0;
   private int resource_limit;
 
   public Collectors(String name, Point position, int rate, List<PImage> imgs, int animation_rate, int resource_limit)
   {
      super(name, position, rate, imgs, animation_rate);
      this.resource_limit = resource_limit;
   }

   public int set_resource_count(int n)
   {
      this.resource_count = n;
   }
 
   public int get_resource_count()
   {
      return this.resource_count;
   }

   public int get_resource_limit()
   {
      return this.resource_limit;
   }
}
