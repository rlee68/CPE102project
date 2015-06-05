
import processing.core.PImage;
import java.util.List;
import java.util.ArrayList;

public class Yoshi
   extends MobileAnimatedActor
{
   private static final int QUAKE_ANIMATION_RATE = 100;
   public Yoshi(String name, Point position, int rate, int animation_rate,
      List<PImage> imgs)
   {
      super(name, position, rate, animation_rate, imgs);
   }

   protected boolean canPassThrough(WorldModel world, Point pt)
   {
      return !world.isOccupied(pt);
   }

   private boolean move(WorldModel world, WorldEntity target)
   {
      if (target == null)
      {
         return false;
      }

      if (adjacent(getPosition(), target.getPosition()))
      {
         target.remove(world);
         return true;
      }

      else
      {
         Point new_pt = nextPosition(world, target.getPosition());
         WorldEntity old_entity = world.getTileOccupant(new_pt);
         if (old_entity != null && old_entity != this)
         {
            old_entity.remove(world);
         }
         world.moveEntity(this, new_pt);
         return false;
      }
   }

   public Action createAction(WorldModel world, ImageStore imageStore)
   {
      Action[] action = { null };
      action[0] = ticks -> {
         removePendingAction(action[0]);

         WorldEntity target = world.findNearest(getPosition(), (Ore.class));
         long nextTime = ticks + getRate();

         if (target != null)
         {
            Point tPt = target.getPosition();

            if (move(world, target))
            {
               Mushroom mush = createMushroom(world, tPt, ticks, imageStore);
               world.addEntity(mush);
            }
         }

         scheduleAction(world, this, createAction(world, imageStore),
            nextTime);

      };
      return action[0];
   }

   private Mushroom createMushroom(WorldModel world, Point pt, long ticks,
      ImageStore imageStore)
   {
      Mushroom mush = new Mushroom("mush", pt, QUAKE_ANIMATION_RATE,
         imageStore.get("mushroom"));
      return mush;
   }

}
