import processing.core.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;

public class MinerNotFull
   extends Miner
{
   public MinerNotFull(String name, Point position, int rate, List<PImage> imgs, int animation_rate, int resource_limit)
   {
      super(name, position, rate, imgs, animation_rate, resource_limit);
   }

   public String entity_string()
   {
      return ("miner" + this.get_name() + this.get_position().getX() + " " +
         this.get_position().getY() + " " +
         this.get_resource_limit() + " "  + this.get_rate() + " " +
         this.get_animation_rate());
   }
   
   public Point[] miner_to_ore(WorldModel world, Ore ore)
   {
      Point e_pt = this.get_position();
      Point[] pts = new Point[2];
      if(ore == null)
      {
         pts[0] = e_pt;
         pts[1] = null;
         return pts;
      }
      Point ore_pt = ore.getPosition();
      if(e_pt.adjacent(o_pt))
      {
         this.set_resource_count(1 + this.get_resource_count());
         world.remove_entity(ore);
         pts[0] = null;
         pts[1] = o_pt;
         return pts;
      }  
      else
      {
         Point new_pt = this.next_position(world, e_pt, o_pt);
         return move_entity(world, new_pt);
      }      
   }

   public Action create_MinerNotFull_action(WorldModel world, Map<String, List<PImage>> imgs)
   {
      Action[] func = {null};
      func[0] = (long current_ticks) ->
      {
         this.remove_pending_action(func[0]);
         Point e_pt = this.get_position();
         Ore o = world.find_nearest(e_pt, Ore.class);
         Point[] returned_tiles = this.miner_to_ore(world, o);
         Point[] tiles;
         boolean found;
         if(returned_tiles[1] == null)
         {
            tiles = new Point[]{returned_tiles[0]};
            found = false;
         }
         else if(returned_tiles[0] == null)
         {
            tiles = new Point[]{returned_tiles[1]};
            found = true;
         }
         else
         {
            tiles = new Point[]{returned_tiles[0], returned_tiles[1]};
            found = false;
         }
         Miner new_entity;
         if(found)
         {
            new_entity = this.try_transform(world);
         }
         else
         {
            new_entity = this;
         }
         world.schedule_action(new_entity, new_entity.create_entity_action(world, imgs),
            current_ticks + new_entity.get_rate());
         return tiles;
      };
      return func[0];
   }

   public Miner try_transform(WorldModel world)
   {
      Miner new_entity;
      if(this.get_resource_count() < this.get_resource_limit())
      {
         new_entity = this;
      }
      else
      {
         new_entity = new MinerFull(this.get_name(), 
            this.get_position(), this.get_rate(), this.get_images(),
            this.get_animation_rate(), this.get_resource_limit());
      }
      if(this != new_entity)
      {
         world.clear_pending_actions(this);
         world.remove_entity_at(this.get_position());
         new_entity.add_entity(world);
         world.schedule_animation(new_entity);
      }
      return new_entity;
   }

   public void schedule_miner(WorldModel world, long ticks, Map<String, List<PImage>> imgs)
   {
      world.schedule_Action(this, this.create_MinerNotFull_action(world, images),
         ticks + this.get_rate());
      world.schedule_animation(this);
   }
}
