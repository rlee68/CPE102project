import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class WorldModel
{
   private int rows;
   private int cols;
   private List<Entity> entities;
   private Grid background;
   private Grid occupancy;
 
   public WorldModel(int rows, int cols, Background backgroud)
   {
      this.rows = rows;
      this.cols = cols;
      this.entities = new ArrayList<Entity>();
      this.background = new Grid(this.cols, this.rows, null);
      this.occupancy = new Grid(this.cols, this.rows, null);
   }

   public boolean within_bounds(Point pt)
   {
      return (pt.getX() >= 0 && pt.getX() < this.cols && pt.getY() >= 0 && pt.getY() < this.rows);
   }
  
   public boolean is_occupied(Point pt)
   {
      return (this.within_bounds(pt) && this.occupancy.get_cell(pt) != null);
   }
 
   public Entity find_nearest(Point pt, Class type)
   {
   }

   public List<Point> move_entity(Entity entity, Point pt)
   {
      List<Point> tiles = new LinkedList<Point>();
      if (this.within_bounds(pt))
      {
         Point old_pt = entity.get_position();
         this.occupancy.set_cell(old_pt, null);
         tiles.add(old_pt);
         this.occupancy.set_cell(pt, entity);
         tiles.add(pt);
         entity.set_position(pt);
      }    
      return tiles;
   }

   public void remove_entity(Entity entity)
   {
      this.remove_entity_at(entity.get_position());
   }

   public void remove_entity_at(Point pt)
   {
      if (this.within_bounds(pt) && this.occupancy.get_cell(pt) != null)
      {
         Entity entity = this.occupancy.get_cell(pt);
         entity.set_position(new Point(-1,-1));
         this.entities.remove(entity);
         this.occupancy.set_cell(pt, null);
      }
   }
 
   public Background get_background(Point pt)
   {
      if (this.within_bounds(pt))
      {
         return background.get_cell(pt);
      }
   }
 
   public void set_background(Point pt, Background background)
   {
      if (this.within_bounds(pt))
      {
         this.background.set_cell(pt, background);
      }
   }

   public Entity get_tile_occupant(Point pt)
   {
      if (this.within_bounds(pt))
      {
         return this.occupancy.get_cell(pt);
      }
      else 
      {
         return null;
      }
   }
 
   public List<Entity> get_entities()
   {
      return this.entities;
   }

   public Point next_position(Point e_pt, Point d_pt)
   {
      int horiz = this.sign(d_pt.getX() - e_pt.getX());
      Point new_pt = new Point(e_pt.getX() + horiz, e_pt.getY());
     
      if (horiz == 0 || this.is_occupied(new_pt))
      {
         int vert = this.sign(d_pt.getY() - e_pt.getY());
         new_pt = new Point(e_pt.getX(), e_pt.getY() + vert);

         if (vert == 0 || this.is_occupied(new_pt))
         {
            new_pt = new Point(e_pt.getX(), e_pt.getY());
         }
      }

      return new_pt;
   }  

   public Point find_open_around(Point pt, int distance)
   {
      for (int i = -distance; i <= distance; distance++)
      {
         for (int j = -distance; j <= distance; distance++)
         {
            Point new_pt = new Point(pt.getX() + j, pt.getY() + i);
      
            if (this.within_bounds(new_pt) && !(this.is_occupied(new_pt)))
            {
               return new_pt;
            }
         }
      }          
      return null;
   }

   public Point blob_next_position(Point e_pt, Point d_pt)
   {
      int horiz = this.sign(d_pt.getX() - e_pt.getX());
      Point new_pt = new Point(e_pt.getX() + horiz, e_pt.getY());
    
      if (horiz == 0 || (this.is_occupied(new_pt)) &&
         !(this.get_tile_occupant(new_pt) instanceof Ore))
      {
         int vert = this.sign(d_pt.getY() - e_pt.getY());
         new_pt = new Point(e_pt.getX(), e_pt.getY() + vert);
       
         if (vert == 0 || (this.is_occupied(new_pt)) &&
            !(this.get_tile_occupant(new_pt) instanceof Ore))
         {
            new_pt = new Point(e_pt.getX(), e_pt.getY());
         }
      }
    
      return new_pt;
   }

   public Entity nearest_entity(List<Entity> e_dist)
   {
      if (e_dist.size() > 0)
      {
         List<Entity> pair = e_dist.get(0);
         for(List<Entity> other : e_dist)
         {
            if (other.get(1) < pair.get(1))
            {
               pair = other;
            }
         }
         Entity nearest = pair.get(0);
      }
      else
      { 
         Entity nearest = null;
      }
      return nearest;
   }

   public static int sign(int x) {
      if (x < 0) 
      {
         return -1;
      } 
      else if (x > 0) 
      {
         return 1;
      } 
      else  
      {
         return 0;
      }
    }
}
