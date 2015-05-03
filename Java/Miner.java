public class Miner
   extends Collectors
{
   private double rate; 
   private double animation_rate;
  
   public Miner(String name, Point position, double resource_limit, double rate, double animantion_rate)
   {
      super(name, position, resource_limit);
      this.rate = rate;
      this.animation_rate = animation_rate;
   }

   public double get_rate()
   {
      return this.rate;
   }

   public double get_animation_rate()
   {
      return this.animation_rate;
   }
}
