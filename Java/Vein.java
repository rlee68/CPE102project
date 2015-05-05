public class Vein
   extends Entity
{
   private double resource_distance;
   private double rate;
  
   public Vein(String name, Point position, double resource_distance, double rate)
   {
      super(name, position);
      resource_distance = 1;
      this.rate = rate;
      this.resource_distance = resource_distance;
   }

   public double get_rate()
   {
      return this.rate;
   }
   
   public double get_resource_distance()
   {
      return this.resource_distance;
   }
  
   public String entity_string()
   {
      return "vein" + " " + this.get_name() + " " + this.get_position().getX() +
         " " + this.get_position().getY() + " " + this.get_rate() + " " +
         this.get_resource_distance();
   }
}
