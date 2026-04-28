package util;

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
    private final String skillOneName;
    private final String skillTwoName;
    private final String skillThreeName;

    GameCharacter(String name, String skill1Name, String skill2Name, String skill3Name) {
        this.name = name;
        this.skillOneName = skill1Name;
        this.skillTwoName = skill2Name;
        this.skillThreeName = skill3Name;
    }

    public String getName() {
        return name;
    }

    public String getSkillOneName() {
        return skillOneName;
    }

    public String getSkillTwoName() {
        return skillTwoName;
    }

    public String getSkillThreeName() {
        return skillThreeName;
    }
}