import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import org.junit.Test;

public class TestCases
{
   private static final double DELTA = 0.00001;

   private Point p1 = new Point(3,6);
   private Point p2 = new Point(1,2);
   private Point p3 = new Point(10,-2);
   private Background b = new Background("Heaven");
   private Blacksmith bs = new Blacksmith("Mike", p2, 11, 5, 1);
   private MinerFull mf = new MinerFull("Bob", p1, 10, 15, 2);
   private MinerNotFull mnf = new MinerNotFull("Jake", p3, 12, 32, 4);
   private Obstacle o = new Obstacle("Water", p2);
   private OreBlob ob = new OreBlob("Sludge", p2, 32, 23);
   private Ore ore = new Ore("Rock", p3, 10);
   private Quake q = new Quake("Birdy", p2, 10);
   private Grid grid = new Grid(1, 2, null);
   private WorldModel wm = new WorldModel(1, 8, b);
   private Vein v = new Vein("Money", p1, 23, 1);
  
   @Test
   public void test_EntitygetName()
   {
      String n = "Tofu";
      Point p = new Point(1,2);
      Entity e = new Entity(n,p);
      assertTrue(e.get_name().equals(n));
   }

   @Test
   public void test_EntitygetPosition()
   {
      String n = "Nala";
      Point p = new Point(2,3);
      Entity e = new Entity(n,p);
      assertTrue(e.get_position().getX() == p.getX()); 
      assertTrue(e.get_position().getY() == p.getY());
   }

   @Test
   public void test_Background()
   {
      String s = "Heaven";
      assertTrue(b.get_name().equals(s));
   }
 
   @Test
   public void test_ObstacleString()
   {
      String n = "name";
      Point p = new Point(1,2);
      Obstacle o = new Obstacle(n,p);
      String s = "obstacle name 1 2";
      assertTrue(o.entity_string().equals(s));
   }
  
   @Test
   public void test_BlacksmithString()
   {
      String s = "blacksmith Mike 1 2 11 5 1";
      assertTrue(bs.entity_string().equals(s));
   }

   @Test 
   public void test_MinerFullString()
   { 
      String s = "miner Bob 3 6 10 15 2";
      assertTrue(mf.entity_string().equals(s));
   }

   @Test
   public void test_MinerNotFullString()
   {
      String s = "miner Jake 10 -2 12 32 4";
      assertTrue(mnf.entity_string().equals(s));
   }
   
   @Test
   public void test_OreString()
   {
      String s = "ore Rock 10 -2";
      assertTrue(o.entity_string().equals(s));
   }
  
   @Test
   public void test_OreBlob()
   {
      assertEquals(ob.get_name(), "Sludge");
      assertEquals(ob.get_position().getX(), 1, DELTA);
      assertEquals(ob.get_position().getY(), 2, DELTA);
      assertEquals(ob.get_rate(), 32, DELTA);
      assertEquals(ob.get_animation_rate(), 23, DELTA);  
   }

   @Test
   public void test_VeinString()
   {
      String s = "vein Money 3 6 23 1";
      assertTrue(v.entity_string().equals(s));
   }

   @Test
   public void test_Quake()
   {
      assertEquals(q.get_name(), "Birdy");
      assertEquals(q.get_position().getX(), 1, DELTA);
      assertEquals(q.get_position().getY(), 2, DELTA);
      assertEquals(q.get_animation_rate(), 10, DELTA);
   }

   @Test
   public void test_FindOpenAround()
   {
      int distance = 3;
      assertTrue(p1.equals(wm.find_open_around(p1, distance)));
   }

   @Test
   public void test_BlobNextPos()
   {
      assertTrue(p1.getX() == wm.blob_next_position(p2,p3).getX());
   }

   @Test
   public void test_NextPosition()
   {
      Point e_pt = new Point(1,1);
      Point d_pt = new Point(2,2);
      Point new_pt = new Point(0,-1);
      WorldModel w = new WorldModel(5,5,null);
      assertTrue(new_pt.getX() == w.next_position(e_pt, d_pt).getX());
   }

   @Test
   public void test_WithinBounds()
   {
      assertTrue(wm.within_bounds(p2));
   }
}
