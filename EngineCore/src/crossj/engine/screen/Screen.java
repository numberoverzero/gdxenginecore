package crossj.engine.screen;

import com.badlogic.gdx.InputProcessor;

public interface Screen extends com.badlogic.gdx.Screen {

    /**
     * Return an {@link InputProcessor} to push events through
     * 
     * @return
     */
    InputProcessor getInputProcessor();

    String getDebugName();

}
