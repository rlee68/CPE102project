import processing.core.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;

public class Ore
   extends Entity
{
   public static final int ORE_CORRUPT_MIN = 20000;
   public static final int ORE_CORRUPT_MAX = 30000;

   public Ore(String name, Point position, int rate, List<PImage> imgs)
   {
      super(name, position, 5000, imgs, 0);
   }
  
   public String entity_string()
   {
      return "ore" + " " + this.get_name() + " " + this.get_position().getX() +
         " " + this.get_position().getY() + " " + this.get_rate();
   }

   public static Ore create_ore(WorldModel world, String name, Point pt, long ticks, Map<String, List<PImage>> imgs)
   {
      Ore o = new Ore(name, pt, world.random(ORE_CORRUPT_MIN,
         ORE_CORRUPT_MAX), new ImageStore().get_images(imgs, "ore"));
      schedule_ore(world, ticks, imgs);
      return o;
   }

   public void schedule_ore(WorldModel world, long ticks, Map<String, List<PImage>> imgs)
   {
      world.schedule_action(this, this.create_ore_action(world, imgs),
         ticks + this.get_rate());
   }

   public Action create_ore_action(WorldModel world, Map<String, List<PImage>> imgs)
   {
      Action[] func = {null};
      func[0] = (long current_ticks) ->
      {
         this.remove_pending_action(func[0]);
         OreBlob blob = blob.create_blob(world, this.getName() + " -- blob",
            this.get_position(), this.get_rate() / blob.BLOB_RATE_SCALE,
            current_ticks, imgs);
         world.remove_entity(this);
         world.add_entity(blob);
         return new Point[]{blob.get_position()};         
      };
      return func[0];
   }
}
