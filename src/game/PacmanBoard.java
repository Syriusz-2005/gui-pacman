package game;

import utils.Grid;

import java.util.ArrayList;

public class PacmanBoard implements Updatable {
    private final Grid<Field> boardGrid;
    private final ArrayList<Entity> entities = new ArrayList<Entity>();

    public PacmanBoard(int width, int height) {
        boardGrid = new Grid<Field>(Field.class,  width, height);
        BoardRandomGenerator.loadFromFile(boardGrid);
        var ghost = new Ghost(this);
        ghost.pos.x = 1f;
        ghost.pos.y = 1f;
        entities.add(ghost);
        ghost.findNextRandomGoal();
    }
    public Grid<Field> getBoardGrid() {
        return boardGrid;
    }

    public ArrayList<Entity> getEntities() {
        return entities;
    }

    public void step(float timeDelta) {
        for (Entity entity : entities) {
            entity.step(timeDelta);
        }
    }
    public PacmanBoard add(Entity e) {
        entities.add(e);
        return this;
    }
}
