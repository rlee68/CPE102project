import processing.core.PImage;
import java.util.List;
import java.util.ArrayList;

public class Wyvern
   extends MobileAnimatedActor
{
   private Miner miner;
   private static final int QUAKE_ANIMATION_RATE = 100;
   public Wyvern(String name, Point position, int rate, int animation_rate,
      List<PImage> imgs)
   {
      super(name, position, rate, animation_rate, imgs);
   }

   protected boolean canPassThrough(WorldModel world, Point pt)
   {
      return !(world.getTileOccupant(pt) instanceof Vein) && !(world.getTileOccupant(pt) instanceof Blacksmith) && !(world.getTileOccupant(pt) instanceof MobileAnimatedActor);
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

         WorldEntity target = world.findNearest(getPosition(), (Miner.class));
         long nextTime = ticks + getRate();

         if (target != null)
         {
            Point tPt = target.getPosition();

            if (move(world, target))
            {
               world.removeEntityAt(tPt);
               Mario mario = new Mario("mario", tPt, 500, 500, imageStore.get("mario"));
               world.addEntity(mario);
               mario.schedule(world, System.currentTimeMillis() + mario.getRate(), imageStore);
            }
         }

         scheduleAction(world, this, createAction(world, imageStore),
            nextTime);
         
      };
      return action[0];
   }

}
