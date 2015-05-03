public class OreBlob
   extends Entity
{
   private double rate;
   private double animation_rate;
 
   public OreBlob(String name, Point position, double rate, double animation_rate)
   {
      super(name, position);
      this.rate = rate;
      this.animation_rate = animation_rate;
   }
  
   public double get_rate()
   {
      return this.rate;
   }
  
   public double animation_rate()
   {
      return this.animation_rate;
   }
}
