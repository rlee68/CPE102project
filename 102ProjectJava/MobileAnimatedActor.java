import processing.core.PImage;
import java.util.List;
import static java.lang.Math.abs;
import java.util.LinkedList;
import java.util.HashMap;
import java.util.Map;

public abstract class MobileAnimatedActor
   extends AnimatedActor
{
   public MobileAnimatedActor(String name, Point position, int rate,
      int animation_rate, List<PImage> imgs)
   {
      super(name, position, rate, animation_rate, imgs);
   }

   protected Point nextPosition(WorldModel world, Point dest_pt)
   {
      Point start = getPosition();
      List<Point> path = Astar(start,dest_pt,world);
      return path.get(0);
   }

   /*protected Point nextPosition(WorldModel world, Point dest_pt)
   {
      int horiz = Integer.signum(dest_pt.x - getPosition().x);
      Point new_pt = new Point(getPosition().x + horiz, getPosition().y);

      if (horiz == 0 || !canPassThrough(world, new_pt))
      {
         int vert = Integer.signum(dest_pt.y - getPosition().y);
         new_pt = new Point(getPosition().x, getPosition().y + vert);

         if (vert == 0 || !canPassThrough(world, new_pt))
         {
            new_pt = getPosition();
         }
      }

      return new_pt;
   }*/

   protected static boolean adjacent(Point p1, Point p2)
   {
      return (p1.x == p2.x && abs(p1.y - p2.y) == 1) ||
         (p1.y == p2.y && abs(p1.x - p2.x) == 1);
   }

   public int h_cost(Point start, Point goal)
   {
      return Math.abs(start.x - goal.x) + Math.abs(start.y - goal.y);
   }

   public List<Point> Astar(Point start, Point goal, WorldModel world)
   {
      List<Point> open = new LinkedList<Point>();
      List<Point> closed = new LinkedList<Point>();
      open.add(start);

      Map<Point,Integer> g_score = new HashMap<Point,Integer>();
      Map<Point,Integer> f_score = new HashMap<Point,Integer>();
      Map<Point,Point> from = new HashMap<Point,Point>();
 
      g_score.put(start,0);
      f_score.put(start, g_score.get(start) + h_cost(start,goal));
 
      while(open.size() != 0)
      {
         int lowest = 0;
         for(int i = 0; i < open.size(); i++)
         {
            if(f_score.get(open.get(i)) < f_score.get(open.get(lowest)))
            {
               lowest = i;
            }
         }

         Point cur = open.get(lowest);
         if(cur.x == goal.x && cur.y == goal.y)
         {
            return calculate_path(from, goal);
         }
         closed.add(cur);
         open.remove(cur);
         
         for(Point n: find_adjacent(cur, goal, world))
         {
            for(Point pt: closed)
            {
               if(n.x == pt.x && n.y == pt.y)
               {
                  continue;
               }
            }
            int temp_g = g_score.get(cur) + 1;
            for(Point pt: open)
            {
               if(!(n.x == pt.x && n.y == pt.y) || temp_g < g_score.get(n))
               {
                  from.put(n,cur);
                  g_score.put(n,temp_g);
                  f_score.put(n,g_score.get(n) + h_cost(n,goal));
                  if(!(n.x == pt.x && n.y == pt.y))
                  {
                     open.add(n);
                  }
               }
            }   
         }
      }
      return null;
   }
  
   public List<Point> find_adjacent(Point cur, Point goal, WorldModel world)
   {
      List<Point> adjacent = new LinkedList<Point>();
      List<Point> temp = new LinkedList<Point>();
      temp.add(new Point(cur.x + 1, cur.y));
      temp.add(new Point(cur.x, cur.y + 1));
      temp.add(new Point(cur.x - 1, cur.y));
      temp.add(new Point(cur.x, cur.y - 1));

      for(Point pt: temp)
      {
         if((world.withinBounds(pt) && canPassThrough(world,pt)) || (goal.x == pt.x && goal.y == pt.y))
         {
            adjacent.add(pt);
         }
      }
      return adjacent;
   }         

   public List<Point> calculate_path(Map<Point,Point> from, Point cur)
   {
      List<Point> path = new LinkedList<Point>();
      path.add(cur);
      while(from.containsKey(cur))
      {
         cur = from.get(cur);
         path.add(0,cur);
      }
      return path;
   }

   protected abstract boolean canPassThrough(WorldModel world, Point new_pt);
}
