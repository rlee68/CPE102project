import java.util.List;
import java.util.ArrayList;
import java.util.LinkedList;
 
public class OrderedList<T>
{
   private List<ListItem<T>> list;
   
   public OrderedList()
   {
      this.list = new LinkedList<ListItem<T>>();
   }
 
   public void insert(T item, long ord)
   {
      int size = this.list.size();
      int idx = 0;
      while(idx < size && this.list.get(idx).get_ord() < ord)
      {
         idx += 1;
      }
      this.list.add(idx, new ListItem<T>(item, ord));
   }

   public boolean remove(T item)
   {
      int size = this.list.size();
      if(size == 0)
      {
         return false;
      }
      else
      {
         int idx = 0;
         while(idx < size && this.list.get(idx).get_item() != item)
         {
            idx = idx + 1;
         }
         if(idx < size)
         {
            this.list.remove(idx);
            return true;
         }
         else
         {
            return false;
         }
      }
   }

   public ListItem<T> head()
   {
      if(this.list.size() > 0) 
      { 
         return this.list.get(0);
      }
      else
      { 
         return null; 
      }
   }

   public ListItem<T> pop()
   {
      if(this.list.size() > 0)
      {
         ListItem<T> item = this.list.get(0);
         this.list.remove(0);
         return item;
      }
      return null;
   }
}
