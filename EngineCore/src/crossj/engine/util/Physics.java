package crossj.engine.util;

import java.util.Arrays;
import java.util.List;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;

import crossj.engine.physics.World;
import crossj.engine.physics.WorldBody;

public class Physics {
    static BodyDef dynamicBodyDef, staticBodyDef;
    static FixtureDef circleFixtureDef;
    static CircleShape circleShape;
    static PolygonShape rectangleShape;
    static {
        dynamicBodyDef = new BodyDef();
        dynamicBodyDef.fixedRotation = true;
        dynamicBodyDef.type = BodyType.DynamicBody;

        staticBodyDef = new BodyDef();

        circleShape = new CircleShape();
        rectangleShape = new PolygonShape();

        circleFixtureDef = new FixtureDef();
        circleFixtureDef.shape = circleShape;
        circleFixtureDef.density = 1.0f;
        circleFixtureDef.friction = 0.0f;
        circleFixtureDef.restitution = 1;
    }

    public static WorldBody createDynamicCircle(World world, float radius, Vector2 position) {
        dynamicBodyDef.position.set(world.toBox(position));
        WorldBody body = world.createBody(dynamicBodyDef);
        circleShape.setRadius(world.toBox(radius));
        body.getBox2DBody().createFixture(circleFixtureDef);
        return body;
    }

    public static WorldBody createDynamicRectangle(World world, Vector2 dimensions, Vector2 position) {
        dynamicBodyDef.position.set(world.toBox(position));
        WorldBody body = world.createBody(dynamicBodyDef);
        rectangleShape.setAsBox(world.toBox(dimensions.x) / 2, world.toBox(dimensions.y) / 2);
        body.getBox2DBody().createFixture(rectangleShape, 0.0f);
        return body;
    }

    public static WorldBody createStaticRectangle(World world, Vector2 dimensions, Vector2 position) {
        staticBodyDef.position.set(world.toBox(position));
        WorldBody body = world.createBody(staticBodyDef);
        rectangleShape.setAsBox(world.toBox(dimensions.x) / 2, world.toBox(dimensions.y) / 2);
        body.getBox2DBody().createFixture(rectangleShape, 0.0f);
        return body;
    }

    public static WorldBody createStaticRectangle(World world, Vector2 dimensions, Vector2 position, float angle) {
        staticBodyDef.position.set(world.toBox(position));
        WorldBody body = world.createBody(staticBodyDef);
        rectangleShape.setAsBox(world.toBox(dimensions.x) / 2, world.toBox(dimensions.y) / 2, world.toBox(position),
                angle);
        body.getBox2DBody().createFixture(rectangleShape, 0.0f);
        return body;
    }

    public static List<WorldBody> createContainer(World world, float wallThickness, Vector2 dimensions, Vector2 center) {
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
