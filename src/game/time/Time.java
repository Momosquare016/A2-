package game.time;

/**
 * Enum representing the phases of time in the game day cycle.
 * Time progresses in the following sequence:
 * DAWN → NOON → DUSK → NIGHT → DAWN ...
 *
 * @author Chan Chee Wei
 */
public enum Time {
    DAWN {
        @Override
        public String toString() {
            return "Dawn";
        }
    },
    NOON {
        @Override
        public String toString() {
            return "Noon";
        }
    },
    DUSK {
        @Override
        public String toString() {
            return "Dusk";
        }
    },
    NIGHT {
        @Override
        public String toString() {
            return "Night";
        }
    },
}
