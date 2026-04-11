package util;

import java.util.*;
import entities.Entity;

public class EntityPool {
    private final Map<GameCharacter, Entity> pool = new EnumMap<>(GameCharacter.class);

    public EntityPool() {
        for(GameCharacter gc : GameCharacter.values()) {
            pool.put(gc, new Entity(
                    gc.getName(),
                    gc.getSkill1Name(),
                    gc.getSkill2Name(),
                    gc.getSkill3Name()
            ));
        }
    }

    public Entity get(GameCharacter type) {
        return pool.get(type);
    }
}
