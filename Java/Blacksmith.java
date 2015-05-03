public class Blacksmith
   extends Collectors
{
   private double rate;
   private doube resource_distance;
  
   public Blacksmith(String name, Point postion, double resource_limit, double rate)
   {
      super(name, position, resource_limit);
      double resource_distance = 1;
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
   }
}
