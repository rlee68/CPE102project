import processing.core.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;

public class Quake
   extends Animated
{
   public static final int QUAKE_STEPS = 10;
   public static final int QUAKE_DURATION = 1100;
   public static final int QUAKE_ANIMATION_RATE = 100;

   public Quake(String name, Point position, List<PImage> imgs, int animation_rate)
   {
      super(name, position, 0, imgs, animation_rate);
   }

   public static Quake create_quake(WorldModel world, Point pt, long ticks,
      Map<String, List<PImage>> imgs)
   {
      Quake q = new Quake("quake", pt, QUAKE_ANIMATION_RATE, 
         new ImageStore().get_images(imgs, "quake"));
      q.schedule_quake(world, ticks, imgs);
      return q;
   }
 
   public void schedule_quake(WorldModel world, long ticks, Map<String, List<PImage>> imgs)   
   {
      world.schedule_animation(this, QUAKE_STEPS);
      world.schedule_action_for(this, this.create_entity_death_action(world),
         ticks + QUAKE_DURATION);
   }
}
