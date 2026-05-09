package util;

import entity.Entity;
import java.util.EnumMap;
import java.util.Map;

public class EntityPool {
    private final Map<GameCharacter, Entity> pool = new EnumMap<>(GameCharacter.class);

    public EntityPool() {
        for (GameCharacter gc : GameCharacter.values()) {
            pool.put(gc, new Entity(
                    gc.getName(),
                    gc.getSkillOneName(),
                    gc.getSkillTwoName(),
                    gc.getSkillThreeName()
            ));
        }
    }

    public Entity getEntity(GameCharacter gameCharacter) {
        return pool.get(gameCharacter);
    }
}