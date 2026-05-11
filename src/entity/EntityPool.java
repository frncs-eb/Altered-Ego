package entity;

import java.util.EnumMap;
import java.util.Map;

public class EntityPool {
    private final Map<EntityState, Entity> pool = new EnumMap<>(EntityState.class);

    public EntityPool() {
        for (EntityState gc : EntityState.values()) {
            pool.put(gc, new Entity(gc.getName(), gc.getSkill1(), gc.getSkill2(), gc.getSkill3()));
        }
    }

    public Entity getEntity(EntityState gameCharacter) {
        return pool.get(gameCharacter);
    }
}