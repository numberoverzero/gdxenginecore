package crossj.engine.event;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import org.junit.Before;
import org.junit.Test;

import crossj.engine.event.EventPool.Behavior;

public class EventPoolTest {
    private static final int POOL_SIZE = 50;
    private static final Behavior BEHAVIOR = Behavior.NULL;

    EventPool pool;

    private class TestEvent extends BasicPoolEvent<Object> {
        public int value = 0;

        @Override
        public boolean notify(Object listener) {
            return false;
        }

        @Override
        public void reset() {
            value = 0;
        }
    }

    private final List<TestEvent> factoryCreatedEvents = new ArrayList<>();

    Callable<TestEvent> factory = new Callable<EventPoolTest.TestEvent>() {

        @Override
        public TestEvent call() throws Exception {
            TestEvent event = new TestEvent();
            factoryCreatedEvents.add(event);
            return event;
        }
    };

    @Before
    public void setUp() throws Exception {
        pool = new EventPool(POOL_SIZE, BEHAVIOR);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testPoolSizeZero() {
        pool = new EventPool(0, BEHAVIOR);
    }

    @Test
    public void testAcquireNull() {
        assert null == pool.acquire(null);
    }

    @Test
    public void testAcquireUnknownClass() {
        assert null == pool.acquire(TestEvent.class);
    }

    @Test
    public void testReleaseNull() {
        pool.release(null);
    }

    @Test
    public void testReleaseUnknownClass() {
        TestEvent event = new TestEvent();
        pool.release(event);
    }

    @Test
    public void testAddTypeInvokesFactory() {
        pool.addType(TestEvent.class, factory);
        assert POOL_SIZE == factoryCreatedEvents.size();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddNullType() {
        pool.addType(null, factory);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddTypeTwice() {
        pool.addType(TestEvent.class, factory);
        pool.addType(TestEvent.class, factory);
    }

    @Test
    public void testAcquireReturnsNullWhenAllEventsActive() {
        pool.addType(TestEvent.class, factory);
        for (int i = POOL_SIZE; i > 0; i--) {
            assert null != pool.acquire(TestEvent.class);
        }
        assert null == pool.acquire(TestEvent.class);
    }

    @Test
    public void testAcquireReturnsFactoryEvents() {
        for (int i = POOL_SIZE; i > 0; i--) {
            assert factoryCreatedEvents.get(i) == pool.acquire(TestEvent.class);
        }
    }

    @Test
    public void testAcquireResetsEvent() {
        pool.addType(TestEvent.class, factory);
        for (int i = 0; i < POOL_SIZE; i++) {
            factoryCreatedEvents.get(i).value = i;
        }
        for (int i = 0; i < POOL_SIZE; i++) {
            assert 0 == pool.acquire(TestEvent.class).value;
        }

    }
}
