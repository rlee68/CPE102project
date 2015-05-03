public class Quake
   extends Entity
{
   private double animation_rate;
 
   public Quake(String name, Point position, double animation_rate)
   {
      super(name, position);
      this.animation_rate = animation_rate;
   }

   public double get_animation_rate()
   {
      return this.animation_rate;
   }
}
