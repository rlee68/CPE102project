import processing.core.PImage;
import java.util.ArrayList;
import java.util.List;
import static java.lang.Math.abs;

public abstract class MobileAnimatedActor
   extends AnimatedActor
{
   private final static int OBSTACLE = 0;
   private final static int VISITED = 1;
   private static int[][] grid;
   private ArrayList<Point> path;  
   public MobileAnimatedActor(String name, Point position, int rate,
      int animation_rate, List<PImage> imgs)
   {
      super(name, position, rate, animation_rate, imgs);
   }

   public static void find_entity(WorldModel world, int[][] obstacle_grid)
   {
      for (WorldEntity entity : world.getEntities())
      {
         Point entity_point = entity.getPosition();
         obstacle_grid[entity_point.x][entity_point.y] = 1;
      }
   }

   public ArrayList<Point> getPath()
   {
      return path;
   }
   
   public static boolean dfs(Point pt, List<Point> path, Point dest_pt)
   {
      if (!(pt.y >= 0 && pt.y < 15 && pt.x >= 0 && pt.x < 20))
      {
         return false;
      }
      if (grid[pt.x][pt.y] == OBSTACLE)
      {
         return false;
      }
      if (grid[pt.x][pt.y] == VISITED)
      {
         return false;
      }
      if (pt.x == dest_pt.x && pt.y == dest_pt.y)
      {        
         path.add(0, pt);
         return true;
      }
      grid[pt.x][pt.y] = VISITED;
      boolean found = dfs(new Point(pt.x + 1, pt.y), path, dest_pt) ||                      
                      dfs(new Point(pt.x, pt.y + 1), path, dest_pt) ||
                      dfs(new Point(pt.x - 1, pt.y), path, dest_pt) ||
                      dfs(new Point(pt.x, pt.y - 1), path, dest_pt);      
      if (found)
      {
         path.add(0, pt);
      }
      return found;
   }
   
   protected Point nextPosition(WorldModel world, Point goal)
   {
      grid = new int[40][30];
      find_entity(world, grid);
      List<Point> path = new ArrayList<Point>();
      dfs(this.getPosition(), path, goal);
      int truth = 0;
      for (Point p : path) 
      {
         if (truth == 1)
         {
            return p;
         }         
         if (this.getPosition() == p)
         {   
            truth = 1;
         }
      } 
      return this.getPosition();
   }
   
   protected static boolean adjacent(Point p1, Point p2)
   {
      return (p1.x == p2.x && abs(p1.y - p2.y) == 1) ||
         (p1.y == p2.y && abs(p1.x - p2.x) == 1);
   }

   protected abstract boolean canPassThrough(WorldModel world, Point new_pt);
}
