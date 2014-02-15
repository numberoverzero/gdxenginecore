package crossj.engine.rendering;

public interface ViewportListener {
    /**
     * Called when the size of the screen to project onto changes.
     *
     * @param viewport
     *            The viewport being updated
     * @param screenWidth
     *            New width of the screen that the reference is being scaled to
     * @param screenHeight
     *            New height of the screen that the reference is being scaled to
     */
    public void onViewportResize(Viewport viewport, int screenWidth, int screenHeight);
}
