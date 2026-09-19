import java.util.Arrays;

public class CanteenTrustRankingEngine {
    static class Canteen {
        private String canteenCode;
        private String canteenName;
        private int trustScore;

        Canteen(String canteenCode, String canteenName, int trustScore) {
            this.canteenCode = canteenCode;
            this.canteenName = canteenName;
            this.trustScore = trustScore;
        }

        Canteen(String canteenCode, String canteenName) {
            this(canteenCode, canteenName, 3);
        }

        int compareTo(Canteen other) {
            if (trustScore != other.trustScore) {
                return Integer.compare(other.trustScore, trustScore);
            }
            int codeResult = canteenCode.compareToIgnoreCase(other.canteenCode);
            if (codeResult != 0) {
                return codeResult;
            }
            return Integer.compare(canteenName.length(), other.canteenName.length());
        }

        static Canteen[] rankCanteens(Canteen[] canteens) {
            Canteen[] ranked = Arrays.copyOf(canteens, canteens.length);
            for (int index = 1; index < ranked.length; index++) {
                Canteen current = ranked[index];
                int position = index - 1;
                while (position >= 0 && ranked[position].compareTo(current) > 0) {
                    ranked[position + 1] = ranked[position];
                    position--;
                }
                ranked[position + 1] = current;
            }
            return ranked;
        }

        @Override
        public String toString() {
            return canteenCode;
        }
    }

    public static void main(String[] args) {
        Canteen[] canteens = {
                new Canteen("HB3-C", "Spice Junction", 3),
                new Canteen("hb1-c", "Grand Mess", 5),
                new Canteen("HB2-C", "Southern Treats")
        };
        System.out.println(Arrays.toString(Canteen.rankCanteens(canteens)));
    }
}