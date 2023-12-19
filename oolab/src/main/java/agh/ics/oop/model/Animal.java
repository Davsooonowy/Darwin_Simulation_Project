    package agh.ics.oop.model;

    import java.util.List;
    import java.util.Map;

    public class Animal implements WorldElement {
        private MapDirection direction;
        private Vector2d position;
        private int energy;
        private final List<Integer> genome;

        public Animal(Vector2d initialPosition, int initialenergy) {
            this.direction = MapDirection.randomDirection();
            this.position = initialPosition;
            this.energy = initialenergy;
            this.genome = List.of(0, 1, 2, 3, 4, 5, 6, 7);
        }

        public Animal(Vector2d initialPosition, int initialenergy, List<Integer> genome) {
            this.direction = MapDirection.randomDirection();
            this.position = initialPosition;
            this.energy = initialenergy;
            this.genome = genome;
        }

        public void animalEnergyChange(int val) {
            this.energy = val + this.energy;
            if(this.energy < 0) {
                this.energy = 0;
            }
        }

        public boolean isDead() {
            return this.energy == 0;
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

        public Integer getGenome(int index) {
            return this.genome.get(index);
        }
        public int getGenomesize(){
            return this.genome.size();
        }
    }