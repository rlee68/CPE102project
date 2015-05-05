public class Ore
   extends Entity
{
   private double rate;
  
   public Ore(String name, Point position, double rate)
   {
      super(name, position);
      this.rate = rate;
   }

   public double get_rate()
   {
      return this.rate;
   }
  
   public String entity_string()
   {
      return "ore" + " " + this.get_name() + " " + this.get_position().getX() +
         " " + this.get_position().getY() + " " + this.get_rate();
   }
}
