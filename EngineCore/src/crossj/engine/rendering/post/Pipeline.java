package crossj.engine.rendering.post;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import crossj.engine.rendering.FBORenderTarget;
import crossj.engine.rendering.RenderTarget;
import crossj.engine.rendering.Viewport;
import crossj.engine.rendering.ViewportListener;

public class Pipeline implements ViewportListener {
    public final SpriteBatch spriteBatch = new SpriteBatch();

    public enum Stage {
        /**
         * Before the reference size (1920x1080) is scaled to the user's screen
         * size
         */
        PreScaling,

        /**
         * After the reference size is scaled to the user's screen size - this
         * includes letterboxing.
         */
        PostScaling
    }

    Viewport viewport;
    RenderTarget referenceTarget = new FBORenderTarget(1920, 1080);
    Map<Stage, List<RenderTarget>> processors = new HashMap<>();

    public Pipeline(Viewport viewport) {
        for (Stage stage : Stage.values()) {
            processors.put(stage, new ArrayList<RenderTarget>());
        }
        this.viewport = viewport;
        viewport.addListener(this);
        spriteBatch.setProjectionMatrix(viewport.camera.combined);
    }

    @Override
    public void onViewportResize(Viewport viewport, int screenWidth, int screenHeight) {
        for (RenderTarget processor : processors.get(Stage.PostScaling)) {
            processor.resize(screenWidth, screenHeight);
        }
    }

    public void addProcessor(PostProcessor processor, Stage stage) {
        processor.setSpriteBatch(spriteBatch);
        processors.get(stage).add(processor);
    }

    /**
     * If moving a processor to a new pipeline, always remove from the old
     * pipeline before adding to the new pipeline
     */
    public void removeProcessor(PostProcessor processor, Stage stage) {
        processor.setSpriteBatch(null);
        processors.get(stage).remove(processor);
    }

    /**
     * Call before drawing anything. Render against reference coordinates
     */
    public void begin() {
        referenceTarget.begin();
        clear();

    }

    /**
     * Call after drawing everything. All post-processing happens here,
     * including viewport scaling
     */
    public void end() {
        referenceTarget.end();
        process();
    }

    private void process() {
        // ====================================================
        // PRE SCALING PROCESSORS
        // ====================================================
        RenderTarget last = processChain(referenceTarget, processors.get(Stage.PreScaling).iterator());

        // ====================================================
        // POST SCALING PROCESSORS
        // ====================================================
        Iterator<RenderTarget> postScalingProcessors = processors.get(Stage.PostScaling).iterator();

        // No post-scaling processors
        if (!postScalingProcessors.hasNext()) {
            // Render to screen
            clear();
            scale(last, null);
        } else {
            // Scale the last PreScaling onto the first PostScaling
            RenderTarget next = postScalingProcessors.next();
            scale(last, next);

            // Process the rest of the chain normally
            last = processChain(next, postScalingProcessors);

            // Render to screen
            clear();
            process(last, null);
        }
    }

    private void clear() {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }

    private void scale(RenderTarget src, RenderTarget dst) {
        viewport.begin();
        process(src, dst);
        viewport.end();
    }

    private void process(RenderTarget src, RenderTarget dst) {
        if (dst != null) {
            dst.begin();
        }

        spriteBatch.begin();
        spriteBatch.draw(src.getRegion(), 0, 0);
        spriteBatch.end();

        if (dst != null) {
            dst.end();
        }
    }

    /**
     * Returns the last {@link RenderTarget} that was rendered to. If there are
     * no render targets in the chain, returns the src render target
     */
    private RenderTarget processChain(RenderTarget src, Iterator<RenderTarget> chain) {
        RenderTarget dst;
        while (chain.hasNext()) {
            dst = chain.next();
            process(src, dst);
            src = dst;
        }
        return src;
    }
}
