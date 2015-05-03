public class Ore
   extend Entity
{
   private double rate;
  
   public Ore(String name, Point position)
   {
      super(name, position);
      double rate = 5000;
      self.rate = rate;
   }

   public double get_rate()
   {
      return self.rate;
   }
  
   public String entity_string()
   {
   }
}
