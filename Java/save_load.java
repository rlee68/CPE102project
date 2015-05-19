public class save_load()
{
   private static final int PROPERTY_KEY = 0;
        
   private static final String BGND_KEY = 'background';
   private static final int BGND_NUM_PROPERTIES = 4;
   private static final int BGND_NAME = 1;
   private static final int BGND_COL = 2;
   private static final int BGND_ROW = 3;
        
   private static final String MINER_KEY = 'miner';
   private static final int MINER_NUM_PROPERTIES = 7;
   private static final int MINER_NAME = 1;
   private static final int MINER_LIMIT = 4;
   private static final int MOINER_COL = 2;
   private static final int MINER_RATE = 5;
   private static final int MINER_ANIMATION_RATE = 6;
        
   private static final String OBSTACLE_KEY = 'obstacle';
   private static final int OBSTACLE_NUM_PROPERTIES = 4;
   private static final int OBSTACLE_NAME = 1;
   private static final int OBSTACLE_COL = 2;
   private static final int OBSTACLE_ROW = 3;
        
   private static final String ORE_KEY = 'ore';
   private static final int ORE_NUM_PROPERTIES = 5;
   private static final int ORE_NAME = 1;
   private static final int ORE_COL = 2;
   private static final int ORE_ROW = 3;
   private static final int ORE_RATE = 4;
        
   private static final String SMITH_KEY = 'blacksmith';
   private static final int SMITH_NUM_PROPERTIES = 7;
   private static final int SMITH_NAME = 1;
   private static final int SMITH_COL = 2;
   private static final int SMITH_ROW = 3;
   private static final int SMITH_LIMIT = 4;
   private static final int SMITH_RATE = 5;
   private static final int SMITH_REACH = 6;
        
   private static final String VEIN_KEY = 'vein';
   private static final int VEIN_NUM_PROPERTIES = 6;
   private static final int VEING_NAME = 1;
   private static final int VEIN_RATE = 4;
   private static final int VEIN_COL = 2;
   private static final int VEIN_ROW = 3;
   private static final int VEIN_REACH = 5;

   public static void load_world(WorldModel world, Map<String, List<PImage>> imgs, String file, Boolean run)
   {
      try
      {
         Scanner in = new Scanner(new FileInputStream(file));
      }
      catch(FileNotFoundException e)
      {
         System.out.println(e);
         System.out.println("Error loading file");
         return;
      }
      while(in.hasNextLine())
      {
         String[] p = in.nextLine().split("\\s");
         if (p.length > 0)
         {
            if (p[PROPERTY_KEY] == BGND_KEY)
            {
               add_background(world, p, imgs);
            }
            else
            {
               add_entity(world, p, imgs, run);
            }
         }
      }
   }

   public void add_background(WorldModel world, String[] p, Map<String, List<PImage>> imgs)
   {
      if (p.length >= BGND_NUM_PROPERTIES)
      {
         Point pt = new Point((int)p[BGND_COL],(int)p[BGND_ROW]);
         String name = p[BGND_NAME];
         world.set_background(pt, new Background(name, ImageStore.get_images(imgs, name)));
      }
   }
 
   public void add_entity(WorldModel world, String[] p, Map<String, List<PImage>> imgs, boolean run)
   {
      Entity new_entity = create_from_properties(p, imgs);
      if (new_entity != null)
      {
         world.add_entity(new_entity);
         if (run)
         {
            new_entity.schedule_entity(world, imgs)
         }
      }
   }
 
   public Entity create_from_properties(String[] p, Map<String, List<PImage>> imgs);
   {
      int key = p[PROPERTY_KEY];
      if (p.length > 0)
      {
         if (key == MINER_KEY)
         {
            return create_miner(p, imgs);
         }
         else if (key == VEIN_KEY)
         {
            return create_vein(p, imgs);
         }
         else if (key == ORE_KEY)
         {     
            return create_ore(p, imgs);
         }
         else if (key == SMITH_KEY)
         {
            return create_blacksmith(p, imgs);
         }
         else if (key == OBSTACLE_KEY)
         {
            return create_obstacle(p, imgs);
         }
      }
      return None;
   }

   public Entity create_miner(String[] p, Map<String, List<PImage>> imgs)
   {
      if (p.length == MINER_NUM_PROPERTIES)
      {
         Entity miner = new Miner(new Point((int)p[MINER_COL], (int)p[MINER_ROW]), (int)p[MINER_LIMIT], 0);
         return miner;
      }
   }

   public Entity create_vein(String[] p, Map<String, List<PImage>> imgs)
   { 
      if (p.length == VEIN_NUM_PROPERTIES)
      {
         Entity vein = new Vein(new Point((int)p[VEIN_COL], (int)p[VEIN_ROW])
      }
   }

   public Entity create_ore(String [] p, Map<String, List<PImage>> imgs)
   {
      if(p.length == ORE_NUM_PROPERTIES)
      {
         Ore ore = new Ore(
         return ore;
      }
      return null;
   }

   public Entity create_blacksmith(String[] p, Map<String, List<PImage>> imgs)
   {
      if (p.length == SMITH_NUM_PROPERTIES)
      {
         Blacksmith smith = new Blacksmith(
         return smith;
      }
      return null;
   }

   public Entity create_obstacle(String[] p, Map<String, List<PImage>> imgs)
   {
      if (p.length == OBSTACLE_NUM_PROPERTIES)
      {
         Obstacle obstacle = new Obstacle(
         return obstacle;
      }
      return null;
   }
}    
