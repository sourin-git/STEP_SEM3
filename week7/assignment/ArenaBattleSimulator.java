public class ArenaBattleSimulator {
    interface Attackable {
        String attack();
        String attack(String weaponName);
    }

    interface Defendable {
        String defend();
    }

    static abstract class GameCharacter {
        private static int characterCount;
        private final String characterId;

        GameCharacter() {
            characterCount++;
            characterId = String.format("CHAR-%04d", 1000 + characterCount);
        }

        abstract String getSpecialMove();

        String getCharacterId() {
            return characterId;
        }
    }

    static class Warrior extends GameCharacter implements Attackable, Defendable {
        private String name;

        Warrior(String name) {
            if (name == null || name.trim().isEmpty()) {
                throw new IllegalArgumentException("Name is required");
            }
            this.name = name;
        }

        @Override
        public String attack() {
            return name + " strikes with a blade";
        }

        @Override
        public String attack(String weaponName) {
            return name + " strikes with an " + weaponName;
        }

        @Override
        public String defend() {
            return name + " raises a shield";
        }

        @Override
        String getSpecialMove() {
            return name + " unleashes Whirlwind Slash";
        }
    }

    static class Trap implements Defendable {
        private String trapType;

        Trap(String trapType) {
            if (trapType == null || trapType.trim().isEmpty()) {
                throw new IllegalArgumentException("Trap type is required");
            }
            this.trapType = trapType;
        }

        @Override
        public String defend() {
            return trapType + " triggers automatically";
        }
    }

    static void resolveDefense(Defendable[] combatants) {
        for (Defendable combatant : combatants) {
            System.out.println(combatant.defend());
        }
    }

    public static void main(String[] args) {
        Warrior warrior = new Warrior("Kael");
        Trap trap = new Trap("Spike Pit");
        System.out.println(warrior.attack());
        System.out.println(warrior.attack("Iron Sword"));
        System.out.println(warrior.defend());
        System.out.println(warrior.getSpecialMove());
        resolveDefense(new Defendable[]{warrior, trap});
    }
}