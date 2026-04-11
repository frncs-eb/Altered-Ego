package util;

/**
 * Represents the character constants of the game.
 */
public enum GameCharacter {
    COSMIC_DASEL(
            "Cosmic Dasel",
            "Provoked Punch",
            "Bug Overflow",
            "Overclock"
    ),
    KANIEL_OUTIS(
            "Kaniel Outis",
            "Image Burn",
            "Spirit Compression",
            "Sanity Drain"
    ),
    KHYLLE_THE_REAPER(
            "Khylle The Reaper",
            "Karate Kick",
            "Flying Food",
            "Voice Of Destruction"
    ),
    VAN_BERKSVILLE(
            "Van Berksville",
            "Stab",
            "Getsuga",
            "Fang Sword Style"
    ),
    EARL(
            "Earl",
            "Knee Strike",
            "Double Kick",
            "Dodge"
    ),
    ASTA_CLOVER(
            "Asta Clover",
            "Arcane Blast",
            "Whirlwind",
            "Block"
    ),
    THE_ONE_JOHN(
            "The One John",
            "Uppercut",
            "Counter Palm",
            "Flaring Punches"
    ),
    JF_VOID(
            "JF Void",
            "Void Chop",
            "Void Deflect",
            "Void Stagger Palm"
    ),
    AND_REW(
            "And Rew",
            "Dragon Fist",
            "Draco Fist Missiles",
            "Dragon's Verdict Of Demise"
    ),
    DEIDRE(
            "Deidre",
            "Lightning Cut",
            "Thunder Cleave",
            "Final Turn"
    );

    private final String name;
    private final String skill1Name;
    private final String skill2Name;
    private final String skill3Name;

    GameCharacter(String name, String skill1Name, String skill2Name, String skill3Name) {
        this.name = name;
        this.skill1Name = skill1Name;
        this.skill2Name = skill2Name;
        this.skill3Name = skill3Name;
    }

    public String getName() {
        return name;
    }

    public String getSkill1Name() {
        return skill1Name;
    }

    public String getSkill2Name() {
        return skill2Name;
    }

    public String getSkill3Name() {
        return skill3Name;
    }
}
