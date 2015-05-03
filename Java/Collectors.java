public class Collectors
   extends Entity
{
   private double resource_count;
   private double resource_limit;

   public Collectors(String name, Point position, double resource_limit)
   {
      super(name, position);
      this.resource_limit = resource_limit;
      this.resource_count = 0;
   }
  
   public double set_resource_count(double n)
   {
      this.resource_count = n;
   }
  
   public double get_resource_count()
   {
      return this.resource_count;
   }

   public double get_resource_limit()
   {
      return this.resource_limit;
   }
}
