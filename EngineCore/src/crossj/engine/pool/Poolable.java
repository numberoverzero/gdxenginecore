package crossj.engine.pool;

import com.badlogic.gdx.utils.Disposable;

public interface Poolable extends Disposable {
    <E extends Poolable> E getNext();

    <E extends Poolable> void setNext(E next);

    boolean isActive();

    <E extends Poolable> E reset();
}
