package entity;

public enum EntityState {
    COSMIC_DASEL(
            "Cosmic Dasel",
            "Provoked Punch",
            "Bug Overflow",
            "Overclock",
            new int[]{0, 0, 0, 0, 0, 0}
    ),
    KANIEL_OUTIS(
            "Kaniel Outis",
            "Image Burn",
            "Spirit Compression",
            "Sanity Drain",
            new int[]{0, 0, 0, 0, 0, 0}
    ),
    KHYLLE_THE_REAPER(
            "Khylle The Reaper",
            "Karate Kick",
            "Flying Food",
            "Voice Of Destruction",
            new int[]{0, 0, 0, 0, 0, 0}
    ),
    VAN_BERKSVILLE(
            "Van Berksville",
            "Stab",
            "Getsuga",
            "Fang Sword Style",
            new int[]{0, 0, 0, 0, 0, 0}
    ),
    EARL(
            "Earl",
            "Knee Strike",
            "Double Kick",
            "Dodge",
            new int[]{0, 0, 0, 0, 0, 0}
    ),
    ASTA_CLOVER(
            "Asta Clover",
            "Arcane Blast",
            "Whirlwind",
            "Block",
            new int[]{0, 0, 0, 0, 0, 0}
    ),
    THE_ONE_JOHN(
            "The One John",
            "Uppercut",
            "Counter Palm",
            "Flaring Punches",
            new int[]{0, 0, 0, 0, 0, 0}
    ),
    JF_VOID(
            "JF Void",
            "Void Chop",
            "Void Deflect",
            "Void Stagger Palm",
            new int[]{0, 0, 0, 0, 0, 0}
    ),
    AND_REW(
            "And Rew",
            "Dragon Fist",
            "Draco Fist Missiles",
            "Dragon's Verdict Of Demise",
            new int[]{0, 0, 0, 0, 0, 0}
    ),
    DEIDRE(
            "Deidre",
            "Lightning Cut",
            "Thunder Cleave",
            "Final Turn",
            new int[]{0, 0, 0, 0, 0, 0}
    );

    private final String name;
    private final String skill1;
    private final String skill2;
    private final String skill3;
    private final int[] frameCount;

    EntityState(String name, String skill1, String skill2, String skill3, int[] frameCount) {
        this.name = name;
        this.skill1 = skill1;
        this.skill2 = skill2;
        this.skill3 = skill3;
        this.frameCount = frameCount;
    }

    public String getName() {
        return name;
    }

    public String getSkill1() {
        return skill1;
    }

    public String getSkill2() {
        return skill2;
    }

    public String getSkill3() {
        return skill3;
    }

    public int[] getFrameCount() {
        return frameCount;
    }
}