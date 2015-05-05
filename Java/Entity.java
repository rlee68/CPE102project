public class Entity
{
   private String name;
   private Point position;
  
   public Entity(String name, Point position)
   {
      this.name = name;
      this.position = position;
   }

   public void set_position(Point point)
   {
      this.position = point;
   }
  
   public Point get_position()
   {
      return this.position;
   }

   public String get_name()
   {
      return this.name;
   }
}
