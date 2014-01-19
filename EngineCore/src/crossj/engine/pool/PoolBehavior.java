package crossj.engine.pool;

public enum PoolBehavior {
    /**
     * Expand the pool to accommodate the call
     */
    EXPAND,

    /**
     * Return an event without expanding the pool, likely by
     * destroying/resetting another event that is active
     */
    DESTROY,

    /**
     * Return null if there is no inactive event to return
     */
    NULL
}