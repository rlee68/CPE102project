import processing.core.*;
import java.util.List;
import java.util.ArrayList;

public class Miner
   extends Collectors
{
   public Miner(String name, Point position,int rate, List<PImage> imgs, int animantion_rate, int resource_limit)
   {
      super(name, position, imgs, rate, animation_rate, resource_limit);
   }

   public Point next_position(WorldModel world, Point e_pt, Point d_pt)
   {
      int dx = d_pt.getX() - e_pt.getX();
      int dy = d_pt.getY() - e_pt.getY();
      Point new_pt = new Point(e_pt.getX() + world.sign(dx),
         e_pt.getY());

      if(dx == 0 || world.is_occupied(new_pt))
      {
         new_pt.setX(e_pt.getX());
         new_pt.setY(e_pt.getY() + world.sign(dy));
         if(dy == 0 || world.is_occupied(new_pt))
         {
            new_pt.setY(e_pt.getY());
         }
      }
      return new_pt;
   }

   public String entity_string()
   {
      return "miner" + " " + this.get_name() + " " + this.get_position().getX()
         + " " + this.get_position().getY() + " " + this.get_resource_limit() +
         " " + this.get_rate() + " " + this.get_animation_rate();
   }
}
