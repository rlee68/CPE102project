import processing.core.*;
import java.util.function.LongConsumer;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.lang.Math;

public class WorldModel
{
   private int rows;
   private int cols;
   private List<Entity> entities;
   private Background[][] background;
   private Entity[][] occupancy;
   private OrderedList<Action> action_queue;
 
   public WorldModel(int rows, int cols, Background backgroud)
   {
      this.rows = rows;
      this.cols = cols;
      this.entities = new ArrayList<Entity>();
      this.action_queue = new OrderedList<Action>();
      this.background = new Background[rows][cols];
      for (int i = 0; i < rows; i++)
      {
         for (int j = 0; j < cols; j++)
         {
            background[i][j] = background;
         }
      }
      this.occupancy = new Entity[rows][cols];
      for (int y = 0; y < rows; y++)
      {
         for (int x = 0; x < cols; x++)
         {      
            occupancy[y][x] = null;
         }
      }
   }

   public int get_rows()
   {
      return this.rows;
   }
   
   public int get_cols()
   {
      return this.cols;
   }

   public Entity[][] get_occupancy()
   {
      return this.occupancy;
   }

   public List<Entity> get_entities()
   {
      return this.entities;
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
      ArrayList<Integer> same = new ArrayList<Integer>();
      for (int i = 0; i < this.entities.size(); i++)
      {
         if(this.entities.get(i).getClass() == e.getClass())
         {
            same.add(i);
         }
      }
      return nearest_entity(same, pt);
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

   public void add_entity(Entity e)
   {
      Point pt = e.get_position();
  
      if (this.within_bounds(pt))
      {
         Entity old_entity = this.occupancy[pt.getY()][pt.getX()];
         if (old_entity != null && (old_entity instanceof Entity ||
             old_entity instanceof Obstacle))
         {
            if (old_entity instanceof Entity)
            {
               Entity old = old_entity;
               old.clear_pending_actions();
            }
         } 
         else
         {
            Obstacle old = (Entity) old_entity;
         }
      }
      this.occupancy.set_cell(pt, e);
      this.entities.add(e);
   }
 
   public void remove_entity(Entity e)
   {
      this.remove_entity_at(e.get_position());
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
         return this.background[pt.getY()][pt.getX()];
      }
      return null;
   }
 
   public PImage get_background_image(Point pt)
   {
      Background bgnd = get_background(pt);
      if (bgnd != null)
      {
         return bgnd.get_image();
      }
      return null;
   }

   public void set_background(Point pt, Background background)
   {
      if (this.within_bounds(pt))
      {
         this.background[pt.getY()][pt.getX()] = background;
      }
   }

   public Entity get_tile_occupant(Point pt)
   {
      if (this.within_bounds(pt))
      {
         return this.occupancy[pt.getY()][pt.getX()];
      }
      return null;
   }

   public void set_tile_occupant(Point pt, Entity e)
   {
      if(this.within_bounds(pt))
      {
         this.occupancy[pt.getY()][pt.getX()] = e;
      }
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

   public void schedule_action(Action a, long time)
   {
      this.action_queue.insert(a,time);
   }

   public void unschedule_action(Action a)
   {
      boolean still_removing = false;
      while(still_removing)
      {
         still_removing = this.action_queue.removie(a);
      }
   }

   public List<Point> update_on_time(long ticks)
   {
      List<Point> tiles = new ArrayList<Point>();
      ListItem<Action> next = this.action_queue.head();
      while(next != null && next.getOrd() < ticks)
      {
         this.action_queue.pop();
         Point[] tile = next.getItem().act(ticks);
         for(int i = 0; i < tile.length; i++)
         {
            if(tile[i] != null)
            {
               tiles.add(tile[i]);
            }
         }
         next = this.action_queue.head();
      }
      return tiles;
   }

   public void schedule_action_for(Entity e, Action a, long time)
   {
      e.add_pending_action(a);
      schedule_action(a, time);
   }

   public void schedule_animation(Entity e, int repeat_count)
   {
      schedule_action(e, e.create_animation_action(this, repeat_count),
         System.currentTimeMillis() + e.get_animation_rate());
   }
 
   public void schedule_animation(Entity e)
   {
      schedule_animation(e, 0);
   }

   public void clear_pending_actions(Entity e);
   {
      for(Action a: e.get_pending_actions())
      {
         unschedule_action(a);
      }
      e.clear_pending_actions();
   }

   public static int sign(int x) 
   {
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

   public static int random(int min, int max)
   {
      return (int)Math.floor((Math.random() * (max - min)) + min);
   } 
}
