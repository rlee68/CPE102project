public clas WorldModel
{
   private int rows;
   private int cols;
   private List<Entity> entities;
   private   background;
   private   occupancy;
 
   public WorldModel(int rows, int cols, backgroud)
   {
      this.rows = rows;
      this cols = cols;
      this.entities = new ArrayList<Entity>();
      this.background = new OccGrid(cols, rows, background);
      this.occupancy = new OccGrid(cols, row, null);
   }

   public boolean within_bounds(point pt)
   {
      return (pt.getX() >= 0 && pt.getX() < this.cols && pt.getY() >= 0 && pt.getY < this.rows);
   }
  
   public boolean is_occupied(Point pt)
   {
      return (this.within_bounds(pt) && this.occupancy.get_cell(pt) != null);
   }
 
   public findNearest(Point pt, Class type)
   {
   }

   public void add_entity(Entity entity)
   {
      Point pt = entity.get_position();
      if (this.within_bounds(pt))
      {
         Entity old_entity = this.occupany.get_cell(pt);
         if (old_entity != null)
         {
            old_entity.clear_pending_actions();
         }
         this.occupancy.set_cell(pt, entity);
         this.entities.add(entity);
   }

   public List<Point> move_entity(Entity entity, Point pt)
   {
      List<Point> tiles = new LinkedList<Point>();
      if (this.within_bounds(pt))
      {
         Point old_pt = entity.get_postion();
         this.occupancy.set_cell(old_pt, null);
         tiles.add(old_pt);
         this.occupancy.set_cell(pt, entity);
         tiles.add(pt);
         entity.set_position(pt);
      }    
      return tiles;
   }

   public void remove_entity(Entity entity)
   {
      this.remove_entity_at(entity.get_position());
   }

   public void remove_entity_at(Point pt)
   {
      if (this.within_bounds(pt) && this.occupancy.get_cell(pt) != null)
      {
         Entity entity = this.occupancy.get_cell(pt);
         entity.set_position(new Point(-1,-1));
         this.entities.remove(entity);
         this.occupancy.set_cell(pt, null);
      }
   }
 
   public Background get_background(Point pt)
   {
      if (this.within_bounds(pt))
      {
         return this.background.get_cell(pt);
      }
   }
 
   public void set_background(Point pt, Background background)
   {
      if (this.within_bounds(pt))
      {
         this.background.set_cell(pt, background);
      }
   }

   public Entity get_tile_occupant(Point pt)
   {
      if (this.within_bounds(pt))
      {
         return this.occupancy.get_cell(pt);
      }
   }
 
   public List<Entity> get_entities()
   {
      return this.entities;
   }
}
