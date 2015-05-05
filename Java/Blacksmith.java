public class Blacksmith
   extends Collectors
{
   private double rate;
   private double resource_distance;
  
   public Blacksmith(String name, Point postion, double resource_limit, double rate, double resource_distance)
   {
      super(name, position, resource_limit);
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
      return "blacksmith" + this.get_name() + " " + this.get_position().getX() +
         " " + this.get_position().getY() + " " + this.get_resource_limit()  
         + " " + this.get_rate() + " " + this.get_resource_distance();
   }
}
