import processing.core.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;

public class WorldView
{
   public
   public
   public
   
   private Rectangle view;
   private WorldModel world;
   private int tile_width;
   private int tile_height;
   private int rows;
   private int cols;
   private PImage mouse_img;
   
   public WorldView(int cols, int rows, WorldModel world, int tile_width, int tile_height)
   {
      this.view = new Rectangle(0, 0, cols, rows);
      this.world = world;
      this.tile_width = tile_width;
      this.tile_height = tile_height;
      this.rows = world.get_rows();
      this.cols = world.get_cols();
   }
 
   public void draw_background()
   {
      for (int i = 0; i < view.height; i++)
      {
         for (int j = 0; j < view.width; j++)
         {
            Point w_pt = viewport_to_world(new Point(j,i));
            PImage img = world.get_background_image(w_pt);
            image(img, j * this.tile_width, i * this.tile_height);
         }
      }
   }

   public void draw_entities()
   {
      for (Entity e: world.get_entities())
      {  
         if (contains(view, e.get_position()))
         {
            Point v_pt = world_to_viewport(e.get_position());
            image(e.get_image(), (int)v_pt.getX() * this.tile_width,
               (int)v_pt.getY() * this.tile_height);
         }
      }
   }

   public void draw_viewport()
   {
      draw_background();
      draw_entities();
   }
  
   public void update_view()
   {
      this.view = create_shifted_viewport(delta, this.rows, this.cols);
   }
 
   public void update_view_tiles(List<Point> tiles)
   {
      Entity e;
      for (Point tile: tiles)
      {
         PImage img = world.get_background_image(pt);
         if (img != null)
         {
            image(img, pt.getX() * Main.TILE_WIDTH, pt.getY() * Main.TILE_HEIGHT)

         }
         e = world.get_tile_occupant(pt);
         if(entity != null)
         {
            PImage img = e.get_image();
            image(img, pt.getX() * Main.TILE_WIDTH, pt.getY() * Main.TILE_HEIGHT);
         }
      }
   }
 
   public Point viewport_to_world(Point pt)
   {
      return new Point(pt.getX() + this.view.getX(), pt.getY() + this.view.getY());
   }

   public Point world_to_viewport(Point pt)
   {
      return new Point(pt.getX() - this.view.getX(), pt.getY() - this.view.getY());
   }
 
   public int clamp(int v, int low, int high)
   {
      return Math.min(high, Math.max(v,low));
   }

   public Rectangle create_shifted_viewport(int[] delta, int rows, int cols)
   {
      int new_x = clamp(this.view.get_left() + delta[0], 0, cols - this.view.getWidth());
      int new_y = clamp(this.view.get_top() + delta[1], 0, rows - this.view.getHeight());

      return new Rectangle(new_x new_y, this.view.get_width(), this.view.get_height());
   }
}
