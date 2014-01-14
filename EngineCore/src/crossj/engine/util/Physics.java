package crossj.engine.util;

import java.util.Arrays;
import java.util.List;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;

import crossj.engine.physics.World;

public class Physics {
    static BodyDef circleBodyDef, rectangleBodyDef;
    static FixtureDef circleFixtureDef;
    static CircleShape circleShape;
    static PolygonShape rectangleShape;
    static {
        circleBodyDef = new BodyDef();
        rectangleBodyDef = new BodyDef();

        circleBodyDef.type = BodyType.DynamicBody;

        circleShape = new CircleShape();
        rectangleShape = new PolygonShape();

        circleFixtureDef = new FixtureDef();
        circleFixtureDef.shape = circleShape;
        circleFixtureDef.density = 1.0f;
        circleFixtureDef.friction = 0.0f;
        circleFixtureDef.restitution = 1;
    }

    public static Body createDynamicCircle(World world, float radius) {
        return createDynamicCircle(world, radius, new Vector2(Vector2.Zero));
    }

    public static Body createDynamicCircle(World world, float radius, Vector2 position) {
        circleBodyDef.position.set(world.toBox(position));
        Body body = world.createBody(circleBodyDef);
        circleShape.setRadius(world.toBox(radius));
        body.createFixture(circleFixtureDef);
        return body;
    }

    public static Body createStaticRectangle(World world, Vector2 dimensions) {
        return createStaticRectangle(world, dimensions, new Vector2(Vector2.Zero));
    }

    public static Body createStaticRectangle(World world, Vector2 dimensions, Vector2 position) {
        rectangleBodyDef.position.set(world.toBox(position));
        Body body = world.createBody(rectangleBodyDef);
        rectangleShape.setAsBox(world.toBox(dimensions.x) / 2, world.toBox(dimensions.y) / 2);
        body.createFixture(rectangleShape, 0.0f);
        return body;
    }

    public static List<Body> createContainer(World world, float wallThickness, Vector2 dimensions, Vector2 center) {
        return Arrays.asList(
                createStaticRectangle(world, new Vector2(dimensions.x + 2 * wallThickness, wallThickness), new Vector2(
                        center.x, center.y - wallThickness / 2 - dimensions.y / 2)), // Top
                createStaticRectangle(world, new Vector2(dimensions.x + 2 * wallThickness, wallThickness), new Vector2(
                        center.x, center.y + wallThickness / 2 + dimensions.y / 2)), // Bottom
                createStaticRectangle(world, new Vector2(wallThickness, dimensions.y + 2 * wallThickness), new Vector2(
                        center.x - wallThickness / 2 - dimensions.x / 2, center.y)), // Left
                createStaticRectangle(world, new Vector2(wallThickness, dimensions.y + 2 * wallThickness), new Vector2(
                        center.x + wallThickness / 2 + dimensions.x / 2, center.y))); // Right
    }
}
