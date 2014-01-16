package crossj.engine.physics.raycasting;

public abstract class RayCastCallback implements com.badlogic.gdx.physics.box2d.RayCastCallback {
    public RayCastResult result;

    public RayCastCallback() {
    }

    public RayCastCallback(RayCastResult result) {
        this.result = result;
    }
}
