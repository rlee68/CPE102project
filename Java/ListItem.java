public class ListItem<T>
{
   private T item;
   private long ord;

   public ListItem(T item, long ord)
   {
      this.item = item;
      this.ord = ord;
   }

   public T get_item()
   {
      return this.item;
   }
   
   public long get_ord()
   {
      return this.ord;
   }
}
