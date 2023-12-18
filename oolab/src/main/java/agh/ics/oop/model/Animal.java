    package agh.ics.oop.model;

    import java.util.Map;

    public class Animal implements WorldElement {
        private MapDirection direction;
        private Vector2d position;
        public Animal(Vector2d initialPosition) {
            this.direction = MapDirection.randomDirection();
            this.position = initialPosition;
        }
        public Animal() {
            this.direction = MapDirection.NORTH;
            this.position = new Vector2d(2,2);
        }

        public String toString() {
            return switch (this.direction) {
                case NORTH -> "N";
                case NORTHEAST -> "NE";
                case EAST -> "E";
                case SOUTHEAST -> "SE";
                case SOUTH -> "S";
                case SOUTHWEST -> "SW";
                case WEST -> "W";
                case NORTHWEST -> "NW";
            };
        }

        public boolean isAt(Vector2d position){
            return this.position.equals(position);
        }

        public void move(Integer direction, MoveValidator validator) {
            for(int i = 0; i < direction; i++) {
                this.direction = this.direction.next();
            }
            Vector2d newPosition;
            newPosition = this.position.add(this.direction.toUnitVector());
            if (validator.canMoveTo(newPosition)) {
                this.position = newPosition;
            }
        }

        MapDirection getDirection() {
            return this.direction;
        }

        public Vector2d getPosition() {
            return this.position;
        }

    }