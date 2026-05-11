package entity;

public enum EntityState {
    COSMIC_DASEL(
            "Cosmic Dasel",
            "Provoked Punch",
            "Bug Overflow",
            "Overclock",
            new int[]{6, 6, 6, 6, 6, 6}
    ),
    KANIEL_OUTIS(
            "Kaniel Outis",
            "Image Burn",
            "Spirit Compression",
            "Sanity Drain",
            new int[]{14, 12, 14, 12, 14, 12}
    ),
    KHYLLE_THE_REAPER(
            "Khylle The Reaper",
            "Karate Kick",
            "Flying Food",
            "Voice Of Destruction",
            new int[]{4, 4, 4, 5, 5, 5}
    ),
    VAN_BERKSVILLE(
            "Van Berksville",
            "Stab",
            "Getsuga",
            "Fang Sword Style",
            new int[]{4, 4, 5, 6, 6, 11}
    ),
    EARL(
            "Earl",
            "Knee Strike",
            "Double Kick",
            "Dodge",
            new int[]{6, 6, 5, 6, 5, 5}
    ),
    ASTA_CLOVER(
            "Asta Clover",
            "Arcane Blast",
            "Whirlwind",
            "Block",
            new int[]{6, 6, 6, 5, 7, 6}
    ),
    THE_ONE_JOHN(
            "The One John",
            "Uppercut",
            "Counter Palm",
            "Flaring Punches",
            new int[]{6, 5, 7, 7, 6, 8}
    ),
    JF_VOID(
            "JF Void",
            "Void Chop",
            "Void Deflect",
            "Void Stagger Palm",
            new int[]{5, 6, 5, 5, 5, 8}
    ),
    AND_REW(
            "And Rew",
            "Dragon Fist",
            "Draco Fist Missiles",
            "Dragon's Verdict Of Demise",
            new int[]{5, 4, 6, 7, 6, 6}
    ),
    DEIDRE(
            "Deidre",
            "Lightning Cut",
            "Thunder Cleave",
            "Final Turn",
            new int[]{6, 6, 6, 4, 6, 9}
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