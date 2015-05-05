public class MinerFull
   extends Miner
{
   private double resource_count;

   public MinerFull(String name, Point position, double resource_limt, double rate, double animation_rate)
   {
      super(name, position, resource_limit, rate, animation_rate);
      this.resource_count = this.get_resource_limit();
   }
}

