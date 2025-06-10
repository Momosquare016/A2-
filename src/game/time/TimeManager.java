package game.time;

import game.interfaces.TimeSensitive;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Manages the in-game time cycle and notifies registered listeners of changes.
 * The time progresses in a repeating cycle of four phases: DAWN → NOON → DUSK → NIGHT.
 * Each phase transition occurs every 4 ticks, and each complete cycle marks the passing of a day.
 *
 * @author Chan Chee Wei
 */
public class TimeManager {
    private boolean firstTick = true;
    private int tickCounter = 0;
    private final int tickModulo = 4;

    private Time currentTime;
    private int currentDay = 1;
    private final List<TimeSensitive> listeners = new ArrayList<>();

    public TimeManager() {
        this.currentTime = Time.DAWN;
    }

    public Time getCurrentTime() {
        return currentTime;
    }

    public void addListener(TimeSensitive listener) {
        listeners.add(listener);
        listener.onTimeChange(currentTime);
    }

    public void progressTime() {
        tickCounter++;
        if (firstTick) {
            firstTick = false;
            notifyListeners();
            return;
        }
        if (tickCounter % tickModulo == 1) {
            Time previousTime = getCurrentTime();
            currentTime = nextTime(currentTime);

            if (previousTime == Time.NIGHT && currentTime == Time.DAWN) {
                currentDay++;
            }

            notifyListeners();
        }
    }

    private Time nextTime(Time time) {
        return switch (time) {
            case DAWN -> Time.NOON;
            case NOON -> Time.DUSK;
            case DUSK -> Time.NIGHT;
            case NIGHT -> Time.DAWN;
        };
    }

    public void notifyListeners() {
        for (TimeSensitive listener : listeners) {
            listener.onTimeChange(getCurrentTime());
        }
    }

    public String getListenersMessages() {
        Set<String> messages = new HashSet<>();
        for (TimeSensitive listener : listeners) {
            String message = listener.getMessage(getCurrentTime());
            if (message != null) {
                messages.add(message);
            }
        }
        return (messages.isEmpty()? null : String.join("\n", messages));
    }

    @Override
    public String toString() {
        String timeString = (tickCounter % tickModulo == 1? "The hour is now ": "");
        String result = "Day " + currentDay + ": " + timeString + currentTime + ". Current tick: " + tickCounter;
        result +=  (tickCounter % tickModulo == 1? "\n" + getListenersMessages(): "");
        result +=  "\n";

        return result;
    }
}
