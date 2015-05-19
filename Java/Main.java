import processing.core.*;
import java.util.List;
import java.util.ArrayList;
import java.util.LikedList;

public class Main
   extends PApplet
{
   private static final boolean RUN_AFTER_LOAD = true;
   private static String IMAGE_LIST_FILE_NAME = "imagelist";
   private static String WORLD_FILE = "gaia.sav";
  
   private static int WORLD_WIDTH_SCALE = 2;
   private static int WORLD_HEIGHT_SCALE = 2;
  
   private static int SCREEN_WIDTH = 640;
   private static int SCREEON_HEIGHT = 480;
   private static int TILE_WIDTH = 32;
   private static int TILE_HEIGHT = 32;
 
   private WorldModel world;
   private WorldView view;
 
   public Background create_default_background(List<PImage> img)
   {
      return new Background(new ImageStore().get_default_image_name(), img);
   }

   public void setup()
   {
      int cols = SCREEN_WIDTH / TILE_WIDTH * WORLD_WIDTH_SCALE;
      int rows = SCREEN_HEIGHT / TILE_HEIGHT * WORLD_HEIGHT_SCALE:
      size(SCREEN_WIDTH, SCREEN_HEIGHT);
      
      background(BGND_COLOR);
      Background default_background = this.create_default_background(new 
         ImageStore().get_images(images, new
         ImageStore().get_default_image_name()));
      this.world = new WorldModel(rows, cols, default_background);
      this.view = new WorldView(SCREEN_WIDTH / TILE_WIDTH, SCREEN_HEIGHT / 
         TILE_HEIGHT, world, TILE_WIDTH, TILE_HEIGHT);
      Save_load.load_world(world, images, WORLD_FILE, RUN_AFTER_LOAD);
      view.draw_viewport();
   }

   public void keyPressed()
   {
      int dx = 0;
      int dy = 0;
     
      switch(keyCode)
      {
         case UP:
            dy--;
            break;
         case DOWN:
            dy++;
            break;
         case LEFT:
            dx--;
            break;
         case RIGHT:
            dx++;
            break;
      }
      if(dx != 0 && dy != 0)
      {
         int[] delta = {dx, dy};
         view.update_view(delta);
      }
   }

   public void draw()
   {
      long time = System.currentTimeMillis();
      if(time >= next_time)
      {
         world.update_on_time(time);
         next_time += TIMER_FREQUENCY;
      }
      view.draw_viewport();
   }

   public static void main(String[] args)
   {
      PApplet.main("Main");
   }
}
