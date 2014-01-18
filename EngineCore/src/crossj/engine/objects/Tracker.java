package crossj.engine.objects;

import com.badlogic.gdx.math.Vector2;

/**
 * Controls tracking, locking, and interpolating between targets
 */
public class Tracker {
    public enum TrackingType {
        Dynamic, Static,
    }

    public enum TrackingStatus {
        Tracking, Locked
    }

    private GameObject object;
    private final Vector2 position = new Vector2();
    private final Vector2 lockPosition = new Vector2();
    private TrackingType type;
    private TrackingStatus status;

    public Tracker() {
        object = null;
        type = TrackingType.Static;
        status = TrackingStatus.Tracking;
    }

    /**
     * Tracks the position of a GameObject. Unlocks tracking if locked.
     *
     * @param object
     */
    public void track(GameObject object) {
        this.object = object;
        type = TrackingType.Dynamic;
        unlock();
    }

    /**
     * Tracks a static position (makes a copy). Unlocks tracking if locked.
     *
     * @param position
     */
    public void track(Vector2 position) {
        this.position.set(position);
        type = TrackingType.Static;
        unlock();
    }

    /**
     * Immediately stores the position of the currently tracked object or
     * vector, and maintains that position until unlocked. Tracking a new object
     * or position will unlock tracking.
     */
    public void lock() {
        switch (type) {
        case Dynamic:
            lockPosition.set(object.getPosition());
            break;
        case Static:
            lockPosition.set(position);
            break;
        }
        status = TrackingStatus.Locked;
    }

    /**
     * Release the tracking lock, resuming tracking the last object or position
     * the actor was set to track. Tracking a new object or position will unlock
     * tracking.
     */
    public void unlock() {
        status = TrackingStatus.Tracking;
    }

    /**
     * Whether tracking is currently locked
     *
     * @return
     */
    public boolean isLocked() {
        return status == TrackingStatus.Locked;
    }

    public Vector2 getPosition() {
        if (isLocked()) {
            return lockPosition;
        }
        switch (type) {
        case Dynamic:
            return object.getPosition();
        case Static:
            return position;
        default:
            throw new RuntimeException("Unknown tracking type '" + type + "'.");
        }
    }
}
