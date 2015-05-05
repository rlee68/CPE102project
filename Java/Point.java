import java.lang.Math;

public class Point
{
   private int x;
   private int y;

   public Point(int x, int y)
   {
      this.x = x;
      this.y = y;
   }
  
   public int getX()
   {
      return this.x;
   }
  
   public int getY()
   {
      return this.y;
   }
  
   public boolean adjacent(Point p)
   {
      return ((this.x == p.getX() && Math.abs(this.y - p.getY()) == 1) || 
         (this.y == p.getY() && Math.abs(this.x - p.getX()) == 1));
   }

   public double distance_sq(Point p)
   {
      double xDist = this.x - p.getX();
      double yDist = this.y - p.getX();
      return (xDist * xDist) + (yDist * yDist);
   }
}
