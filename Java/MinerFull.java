import processing.core.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;

public class MinerFull
   extends Miner
{
   private int resource_count;

   public MinerFull(String name, Point position, int rate, List<PImage> imgs, int animation_rate, int resource_limit)
   {
      super(name, position, rate, imgs, animation_rate, resource_limit);
      this.resource_count = this.get_resource_limit();
   }

   public Point[] miner_to_smith(WorldModel world, Blacksmith smith)
   {
      Point e_pt = this.get_position();
      Point[] pts = new Point[2];
      if(smith == null)
      {
         pts[0] = e_pt;
         pts[1] = null;
         return pts;
      }
      Point s_pt = smith.get_position();
      if(e_pt.adjacent(s_pt))
      {
         smith.set_resource_count(smith.get_resource_count() + 
            this.get_resource_count());
         this.set_resource_count(0);
         pts[0] = null;
         pts[1] = null;
         return pts;
      }
      else 
      {
         Point new_pt = this.next_position(world, e_pt, s_pt);
         return this.move_entity(world, new_pt);
      }
   }

   public Action create_MinerFull_action(WorldModel world, Main<String, List<PImage>> imgs)
   {
      Action[] func = {null};
      func[0] = (long current_ticks) ->
      {
         this.remove_pending_action(func[0]);
         Point e_pt = this.get_position();
         Blacksmith smith = world.find_nearest(e_pt, Blacksmith.class);
         Point[] returned_tiles = this.miner_to_smith(world, smith);
         Point[] tiles;
         boolean found;
         if(returned_tiles[0] == null)
         {
            found = true;
            tiles = new Point[]{returned_tiles[1]};
         }
         else if(returned_tiles[1] == null)
         {
            found = false;
            tiles = new Point[]{returned_tiles[0]};
         }
         else
         {
            found = false;
            tiles = new Point[]{returned_tiles[0], returned_tiles[1]};
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
         world.schedule_action(new_entity, 
            new_entity.create_entity_action(world, imgs), 
            current_ticks + new_entity.get_rate());
         return tiles;
      };
      return func[0];
   }

   public Miner try_transform(WorldModel world)
   {
      Miner new_entity = new MinerNotFull(this.get_name(), this.get_position(),
         this.get_rate(), this.get_resource_limit(), this.get_animation_rate(),
         this.get_images());
      world.clear_pending_actions(this);
      world.remove_entity_at(this.get_position());
      new_entity.add_entity(world);
      world.schedule_animation(new_entity);
      return new_entity;
   }
}

