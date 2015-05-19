import processing.core.*;
import java.util.List;
import java.util.ArrayList;

public class WorldObject
{
   private String name;
   private List<PImage> imgs;
   private int current_img = 0;
  
   public WorldObject(String name, List<PImage> imgs)
   {
      this.name = name;
      this.imgs = imgs;
   }

   public String get_name()
   {
      return this.name;
   }
       
   public List<PImage> get_images()
   {
      return this.imgs;
   }

   public PImage get_image()
   {
      return get_image().get(this.current_img);
   }

   public int next_image()
   {
      this.current_img = (this.current_img + 1) % imgs.size();
      return this.current_img;
   }
}
