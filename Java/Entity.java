public class Entity
{
   private String name;
   private Point position;
  
   public Entity(String name, Point position)
   {
      this.name = name;
      this.position = position;
   }

   public double set_position(Point point)
   {
      this.position = point;
   }
  
   public double get_position()
   {
      return this.position;
   }

   public get_name()
   {
      return this.name;
   }
}
