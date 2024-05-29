package game;

import utils.Vec2f;
import utils.Vec2i;

import java.util.function.Predicate;

public abstract class Entity implements Updatable {
    protected final Vec2f pos = new Vec2f(0, 0);
    protected final Vec2f vel = new Vec2f(0, 0);
    protected float speed = 0.01f;
    protected final PacmanBoard parent;
    protected Vec2i goal;
    private Vec2i prevPos;
    private Vec2i currFieldCenter;
    private boolean isInFieldCenter = true;

    public Entity(PacmanBoard parent) {
        this.parent = parent;
    }

    public Vec2f getPos() {
        return pos;
    }

    public Vec2i getGridPos() {
        return new Vec2i((int) pos.x, (int) pos.y);
    }

    /**
     * @param direction a normalized direction vector. For example {1, 0} points to the right.
     */
    public Entity setMovement(Vec2f direction) {
        var dir = direction.clone().normalize();
        vel.copy(dir.multiply(speed));
        return this;
    }

    protected boolean isInFieldCenter() {
        return isInFieldCenter;
    }

    public void step(float timeDelta) {
        if (prevPos == null || !prevPos.equals(getGridPos())) {
            onGridPosChange();
        }
        isInFieldCenter = getGridPos().toFloatCenter().subtract(pos).length() <= speed * timeDelta;
        if (isInFieldCenter) {
            onInFieldCenter();
            currFieldCenter = getGridPos();
        }
        prevPos = getGridPos();
        if (goal != null) {
            if (goal.toFloatCenter().subtract(pos).length() < vel.length() * timeDelta) {
                onGoalReached();
            } else {
                setMovement(goal.toFloatCenter().subtract(pos));
            }
        }
        pos.add(vel.clone().multiply(timeDelta));
    }

    protected void findNextGoal(Vec2i dir, Predicate<Integer> isValid) {
        var normalizedDir = dir.normalize();
        var grid = parent.getBoardGrid();
        var currPos = getGridPos();
        var finalPos = grid.walk(currPos, normalizedDir, (Vec2i p, Field f, int i) -> !f.isWall());
        if (!isValid.test(finalPos.distance(currPos))) {
            return;
        }
        if (finalPos.equals(currPos)) {
            onGoalReached();
            return;
        }
        goal = finalPos;
    }

    protected void onGridPosChange() {}

    protected void onGoalReached() {
        goal = null;
        vel.copy(Vec2f.ZERO);
    }

    protected void onInFieldCenter() {}
}
