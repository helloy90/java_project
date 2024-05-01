package src.Components;

import src.Entity.Entity;
import src.GameEngine.InputHandler;

public class MovementComponent {
    private InputHandler inputHandler;

    private Direction horizontalDirection;
    private Direction verticalDirection;
    private int horizontalSpeed;
    private int verticalSpeed;

    public int speed;

    public MovementComponent(Entity player, InputHandler iHandler) {
        this.inputHandler = iHandler;

        speed = 8;

        horizontalDirection = Direction.None;
        verticalDirection = Direction.None;
    }

    public void update() {
        if (inputHandler.upPressed) {
            verticalSpeed = -speed;
            verticalDirection = Direction.Up;
        }
        if (inputHandler.downPressed) {
            verticalSpeed = speed;
            verticalDirection = Direction.Down;
        }
        if (inputHandler.leftPressed) {
            horizontalSpeed = -speed;
            horizontalDirection = Direction.Left;
        }
        if (inputHandler.rightPressed) {
            horizontalSpeed = speed;
            horizontalDirection = Direction.Right;
        }

        if (Math.abs(horizontalSpeed) > 0 && Math.abs(verticalSpeed) > 0) {
            horizontalSpeed = (int) Math.round((float) horizontalSpeed / 1.41);
            verticalSpeed = (int) Math.round((float) verticalSpeed / 1.41);
        }
    }

    public void resetSpeed() {
        horizontalSpeed = 0;
        verticalSpeed = 0;

        horizontalDirection = Direction.None;
        verticalDirection = Direction.None;
    }

    public int getHorizontalSpeed() {
        return horizontalSpeed;
    }

    public int getVerticalSpeed() {
        return verticalSpeed;
    }
    
    public Direction getHorizontalDirection() {
        return horizontalDirection;
    }

    public Direction getVerticalDirection() {
        return verticalDirection;
    }
}
