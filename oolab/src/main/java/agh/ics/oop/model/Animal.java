    package agh.ics.oop.model;

    public class Animal implements WorldElement {
        private MapDirection direction;
        private Vector2d position;
        public Animal(Vector2d initialPosition, MapDirection initialDirection) {
            this.direction = initialDirection;
            this.position = initialPosition;
        }
        public Animal() {
            this.direction = MapDirection.NORTH;
            this.position = new Vector2d(2,2);
        }

        public String toString() {
            return switch (this.direction) {
                case NORTH -> "N";
                case SOUTH -> "S";
                case EAST -> "E";
                case WEST -> "W";
            };
        }

        public boolean isAt(Vector2d position){
            return this.position.equals(position);
        }

        public void move(MoveDirection direction, MoveValidator validator) {
            switch (direction) {
                case FORWARD, BACKWARD -> {
                    Vector2d newPosition;
                    if (direction == MoveDirection.FORWARD) {
                        newPosition = this.position.add(this.direction.toUnitVector());
                    } else {
                        newPosition = this.position.subtract(this.direction.toUnitVector());
                    }
                    if (validator.canMoveTo(newPosition)) {
                        this.position = newPosition;
                    }
                }
                case RIGHT -> this.direction = this.direction.next();
                case LEFT -> this.direction = this.direction.previous();
            }
        }
        MapDirection getDirection() {
            return this.direction;
        }

        public Vector2d getPosition() {
            return this.position;
        }

    }