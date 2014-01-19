package crossj.engine.pool;

public enum PoolBehavior {
    /**
     * Expand the pool to accommodate the call
     */
    EXPAND,

    /**
     * Return an item without expanding the pool, likely by
     * destroying/resetting another item that is active
     */
    DESTROY,

    /**
     * Return null if there is no inactive item to return
     */
    NULL
}