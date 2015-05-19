import java.util.List;
import java.util.function.*;
import java.util.LinkedList;
import processing.core.*;

public class Vein
   extends Animated
{
   public static final int VEIN_SPAWN_DELAY = 500;
   public static final int VEIN_RATE_MIN = 8000;
   public static final int VEIN_RATE_MAX = 17000;

   private int resource_distance = 1;

   public Vein(String name, Point position, int rate, List<PImage> imgs, int resource_distance)
   {
      super(name, position, rate, imgs, 0);
      this.resource_distance = resource_distance;
   }

   public int get_resource_distance()
   {
      return this.resource_distance;
   }
  
   public String entity_string()
   {
      return "vein" + " " + this.get_name() + " " + this.get_position().getX() +
         " " + this.get_position().getY() + " " + this.get_rate() + " " +
         this.get_resource_distance();
   }

   public Action create_vein_action(WorldModel world, Map<String, List<PImage>> imgs)
   {
      Action[] func = {null};
      func[0] = (long current_ticks) ->
      {
         this.remove_pending_action(func[0]);
         Point[] tiles;
         
         Point open_pt = world.find_open_around(this.get_position(), 
            this.get_resource_distance());
         if (open_pt != null)
         {
            Ore ore = ore.create_ore("ore -" + this.get_name() + " - " + 
               current_ticks.toString(), open_pt, current_ticks, imgs);
            world.add_entity(ore);
            tiles = new Point[](open_pt);
         }      
         else
         {
            tiles = new Point[]{null};
         }
         world.schedule_action(this, this.create_vein_action(world, imgs),
            current_ticks + this.get_rate());
      };
      return func[0];
   }

   public schedule_vein(WorldModel world, long ticks, Map<String, List<PImage>> imgs)
   {
      world.schedule_action(this, this.create_vein_action(world, imgs),
         ticks + this.get_rate());
   }
   
   public static Vein create_vein(WorldModel world, String name, Point pt, long ticks, Map<String, List<PImage>> imgs)
   {
      Vein v = new Vein("vein" + name, pt, world.random(VEIN_RATE_MIN,
         VEIN_RATE_MAX), new ImageStore().get_images(imgs, "vein"));
      return v; 
   } 
}
