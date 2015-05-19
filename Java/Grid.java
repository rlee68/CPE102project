import java.util.List;
import java.util.ArrayList;

public class Grid
{
   private int width;
   private int height;
   private Entity[][] cells;
  
   public Grid(int width, int height, Entity occ_value)
   {
      this.width = width;
      this.height = height;
      this.cells = new Entity[height][width];
    
      for(int i; i < height; i++)
      {
         for(int j; j < width; j++)
         {
            this.cells[i][j] = occ_value;
         }
      }   
   }

   public void set_cell(Point p, Entity value)
   {
      this.cells[p.getY()][p.getX()] = value;
   }

   public Entity get_cell(Point p)
   {
      return this.cells[p.getY()][p.getX()];
   }
}
