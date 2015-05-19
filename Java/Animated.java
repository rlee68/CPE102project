import processing.core.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;

public class Animated
   extends Entity
{
   private int animation_rate;
   private int rate;
   private List<Action> pending_actions; 

   public Animated(String name, Point position, int rate, List<PImage> imgs, int animation_rate)
   {
      super(name, position, imgs);
      this.rate = rate;
      this.animation_rate = animation_rate;
      this.pending_actions = new ArrayList<Action>();
   }

   public int get_rate()
   {
      return this.rate;
   }

   public int get_animation_rate()
   {
      return this.animation_rate;
   }

   public void remove_pending_action(Action a)
   {
      this.pending_actions.remove(a);
   }
 
   public void add_pending_action(Action a)
   {
      this.pending_actions.add(a);
   }

   public List<Action> get_pending_actions()
   {
      return this.pending_actions;
   }

   public void clear_pending_actions()
   {
      this.pending_actions.clear();
   }

   public Action create_animation_action(WorldModel world, int repeat_count)
   {
      Action[] func = {null};
      func[0] = (long current_ticks) ->
      {
         this.remove_pending_action(func[0]);
         this.next_image();
  
         if (repeat_count != 1)
         {
            world.schedule_action(this, this.create_animation_action(world,
               Math.max(repeat_count - 1, 0)), current_ticks + 
               this.get_animation_rate());
         }
      };
      return func[0];
   }

   public Action create_entity_death_action(WorldModel world)
   {
      Action[] func = {null};
      func[0] = (long current_ticks) ->
      {
         this.remove_pending_action(func[0]);
         Point pt = this.get_position();
         world.remove_entity(this);
         return new Point[]{pt};
      };
      return func[0];
   }
}
