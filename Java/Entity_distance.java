public class Entity_distance
{
    private Entity entity;
    private int distance;

    public Entity_distance(Entity entity, int distance)
    {
        this.entity = entity;
        this.distance = distance;
    }

    public int getDistance()
    {
        return this.distance;
    }

    public Entity getEntity()
    {
        return this.entity;
    }
}
