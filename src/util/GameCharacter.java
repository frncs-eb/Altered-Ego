package util;

public enum GameCharacter {
    // Ok na
    COSMIC_DASEL(
            "Cosmic Dasel",
            "Provoked Punch",
            "Bug Overflow",
            "Overclock",
            new int[]{ 6, 6, 6, 6, 6, 6 }
    ),
    // Ok na
    KANIEL_OUTIS(
            "Kaniel Outis",
            "Image Burn",
            "Spirit Compression",
            "Sanity Drain",
            new int[]{ 14, 12, 14, 12, 14, 12 }
    ),
    // Wala pa
    KHYLLE_THE_REAPER(
            "Khylle The Reaper",
            "Karate Kick",
            "Flying Food",
            "Voice Of Destruction",
            new int[]{ 0, 0, 0, 0, 0, 0 }
    ),
    // Wala pa
    VAN_BERKSVILLE(
            "Van Berksville",
            "Stab",
            "Getsuga",
            "Fang Sword Style",
            new int[]{ 0, 0, 0, 0, 0, 0 }
    ),
    // Wala pa
    EARL(
            "Earl",
            "Knee Strike",
            "Double Kick",
            "Dodge",
            new int[]{ 0, 0, 0, 0, 0, 0 }
    ),
    // Wala pa
    ASTA_CLOVER(
            "Asta Clover",
            "Arcane Blast",
            "Whirlwind",
            "Block",
            new int[]{ 0, 0, 0, 0, 0, 0 }
    ),
    // Wala pa
    THE_ONE_JOHN(
            "The One John",
            "Uppercut",
            "Counter Palm",
            "Flaring Punches",
            new int[]{ 0, 0, 0, 0, 0, 0 }
    ),
    // Wala pa
    JF_VOID(
            "JF Void",
            "Void Chop",
            "Void Deflect",
            "Void Stagger Palm",
            new int[]{ 0, 0, 0, 0, 0, 0 }
    ),
    // Wala pa
    AND_REW(
            "And Rew",
            "Dragon Fist",
            "Draco Fist Missiles",
            "Dragon's Verdict Of Demise",
            new int[]{ 0, 0, 0, 0, 0, 0 }
    ),
    // Ok na
    DEIDRE(
            "Deidre",
            "Lightning Cut",
            "Thunder Cleave",
            "Final Turn",
            new int[]{ 6, 6, 6, 4, 6, 9 }
    );

    private final String name;
    private final String skillOneName;
    private final String skillTwoName;
    private final String skillThreeName;
    private final int[]  frameCounts; // [idle, basicAtk, takeDmg, sk1, sk2, sk3, death]

    GameCharacter(String name,
                  String skill1, String skill2, String skill3,
                  int[] frameCounts) {
        this.name          = name;
        this.skillOneName  = skill1;
        this.skillTwoName  = skill2;
        this.skillThreeName= skill3;
        this.frameCounts   = frameCounts;
    }

    public int[] getFrameCounts() { return frameCounts; }

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