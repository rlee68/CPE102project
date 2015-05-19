import processing.core.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;

public class OreBlob
   extends Animated
{
   public static final long BLOB_RATE_SCALE = 4;
   public static final long BLOB_ANIMATION_RATE_SCALE = 50;
   public static final long BLOB_ANIMATION_MIN = 1;
   public static final long BLOB_ANIMATION_MAX = 3;

   public OreBlob(String name, Point position, int rate, List<PImage> imgs, int animation_rate)
   {
      super(name, position, rate, imgs, animation_rate);
   }

   public static Oreblob create_blob(WorldModel world, String name, Point pt, int rate, long ticks, Map<String, List<PImage>> imgs)
   {
      OreBlob blob = new OreBlob(name, pt, rate, world.random(
         BLOB_ANIMATION_MIN, BLOB_ANIMATION_MAX) * BLOB_ANIMATION_RATE_SCALE, 
         new ImageStore().get_images(imgs, "blob"));
      schedule_blob(world, ticks, imgs);
      return blob;
   }  

   public void schedule_blob(WorldModel world, long ticks, Map<String, List<PImage>> imgs)
   {
      world.schedule_action(this, this.create_ore_blob_action(world, imgs),
         ticks + this.get_rate());
      world.schedule_animation(this);
   }

   public Point[] blob_to_vein(WorldModel world, Vein vein)
   {
      Point e_pt = get_position();
      Point[] pts = new Point[2];

      if(vein == null)
      {
         pts[0] = e_pt;
         pts[1] = null;
         return pts;
      }
      Point v_pt = vein.get_position();
      if(e_pt.adjacent(v_pt))
      {
         world.remove_entity(vein);
         pts[0] = null;
         pts[1] = v_pt;
         return pts;
      } 
      else 
      {
         Point new_pt = this.next_position(world, e_pt, v_pt);
         Entity old_entity = world.get_tile_occupant(new_pt);
         if(old_entity instanceof Ore)
         {
            world.remove_entity(old_entity);
         }
         return world.move_entity(this, new_pt);
      }
   }

   public Action create_ore_blob_action(WorldModel world, Map<String, List<PImage>> imgs)
   {
      Action[] func = {null};
      func[0] = (long current_ticks) ->
      {
         this.remove_pending_action(func[0]);
         Point e_pt = this.get_position();
         Vein v = world.find_nearest(e_pt, Vein.class);
         Point[] returned_tiles = this.blob_to_vein(world, v);
         Point[] tiles;
         boolean found;
         if(returned_tiles[1] == null)
         {
            found = false;
            tiles = new Point[]{returned_tiles[0]};
         }
         else if(returned_tiles[0] == null)
         {
            found = true;
            tiles = new Point[]{returned_tiles[1]};
         }
         else
         {
            found = false;
            tiles = new Point[]{returned_tiles[0], returned_tiles[1]};
         }
         long next_time = current_ticks + this.get_rate();
         if(found)
         {
            Quake q = q.create_quake(world, tiles[0], current_ticks,
               imgs);
            world.add_entity(q);
            next_time = current_ticks + this.get_rate() * 2;
         }
         world.schedule_action(this, this.create_ore_blob_action(world, imgs),
            next_time);
         return tiles;
      };
      return func[0];
   }
}
