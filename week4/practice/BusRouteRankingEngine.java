import java.util.Arrays;

public class BusRouteRankingEngine {
    static class BusRoute {
        private String routeCode;
        private String routeName;
        private int priority;

        BusRoute(String routeCode, String routeName, int priority) {
            this.routeCode = routeCode;
            this.routeName = routeName;
            this.priority = priority;
        }

        BusRoute(String routeCode, String routeName) {
            this(routeCode, routeName, 0);
        }

        int compareTo(BusRoute other) {
            if (priority != other.priority) {
                return Integer.compare(other.priority, priority);
            }
            int codeComparison = routeCode.compareToIgnoreCase(other.routeCode);
            if (codeComparison != 0) {
                return codeComparison;
            }
            return routeName.compareToIgnoreCase(other.routeName);
        }

        static BusRoute[] rankRoutes(BusRoute[] routes) {
            BusRoute[] ranked = Arrays.copyOf(routes, routes.length);
            for (int index = 1; index < ranked.length; index++) {
                BusRoute current = ranked[index];
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
            return routeCode;
        }
    }

    public static void main(String[] args) {
        BusRoute[] routes = {
                new BusRoute("RT205L", "Airport Express", 3),
                new BusRoute("rt201j", "City Central", 4),
                new BusRoute("RT299T", "Night Service")
        };
        System.out.println(Arrays.toString(BusRoute.rankRoutes(routes)));
    }
}