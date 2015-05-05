public class Obstacle
   extends Entity
{
   public Obstacle(String name, Point position)
   {
      super(name, position);
   }
 
   public String entity_string()
   {
      return "obstacle" + " " + this.get_name() + " " + 
         this.get_position().getX() + " " + this.get_position().getY();
   }
}
