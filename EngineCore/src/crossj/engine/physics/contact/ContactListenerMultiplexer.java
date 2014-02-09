package crossj.engine.physics.contact;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.utils.Array;

public class ContactListenerMultiplexer implements ContactListener {
    private Array<ContactListener> listeners = new Array<>(4);

    public ContactListenerMultiplexer() {
    }

    public ContactListenerMultiplexer(ContactListener... listeners) {
        for (ContactListener listener : listeners) {
            this.listeners.add(listener);
        }
    }

    public void addListener(int index, ContactListener listener) {
        listeners.insert(index, listener);
    }

    public void removeListener(int index) {
        listeners.removeIndex(index);
    }

    public void addListener(ContactListener listener) {
        listeners.add(listener);
    }

    public void removeListener(ContactListener listener) {
        listeners.removeValue(listener, true);
    }

    public int size() {
        return listeners.size;
    }

    public void clear() {
        listeners.clear();
    }

    public void setListeners(Array<ContactListener> listeners) {
        this.listeners = listeners;
    }

    public Array<ContactListener> getListeners() {
        return listeners;
    }

    @Override
    public void beginContact(Contact contact) {
        for (ContactListener listener : listeners) {
            listener.beginContact(contact);
        }
    }

    @Override
    public void endContact(Contact contact) {
        for (ContactListener listener : listeners) {
            listener.endContact(contact);
        }
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {
        for (ContactListener listener : listeners) {
            listener.preSolve(contact, oldManifold);
        }
    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {
        for (ContactListener listener : listeners) {
            listener.postSolve(contact, impulse);
        }
    }
}
