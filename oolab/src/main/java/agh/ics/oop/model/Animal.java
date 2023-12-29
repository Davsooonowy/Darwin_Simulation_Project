    package agh.ics.oop.model;


    public class Animal implements WorldElement {
        private MapDirection direction;
        private static final int REPRODUCTION_ENERGY = 50;
        private Vector2d position;
        private int energy;
        private Genomes genomes;
        private int age = 4;
        private int childrenCount = 2;

        public Animal(Vector2d initialPosition, int initialenergy, int genomeLength) {
            this.direction = MapDirection.randomDirection();
            this.position = initialPosition;
            this.energy = initialenergy;
            this.genomes = new Genomes(genomeLength);
        }

        public Animal(Vector2d position, int energy, Genomes genomes) {
            this.direction = MapDirection.randomDirection();
            this.position = position;
            this.energy = energy;
            this.genomes = genomes;
        }

        public Animal reproduce(Animal other) {
            int childEnergy = this.energy / 4 + other.energy / 4;
            this.energy -= this.energy / 4;
            other.energy -= other.energy / 4;

            Genomes childGenomes = this.genomes.crossover(other.genomes);
            childGenomes.mutate();

            return new Animal(this.position, childEnergy, childGenomes);
        }


        public int getEnergy() {
            return this.energy;
        }
        public int getAge() {
            return this.age;
        }
        public int getChildrenCount() {
            return this.childrenCount;
        }

        public void animalEnergyChange(int val) {
            this.energy = val + this.energy;
            if(this.energy < 0) {
                this.energy = 0;
            }
        }
        public Genomes getGenomes() {
            return this.genomes;
        }

        public boolean canReproduce() {
            return this.energy >= REPRODUCTION_ENERGY;
        }

        public boolean isDead() {
            return this.energy <= 0;
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

    public void move(Integer direction, AbstractWorldMap validator) {
        for(int i = 0; i < direction; i++) {
            this.direction = this.direction.next();
        }
        Vector2d newPosition;
        newPosition = this.position.add(this.direction.toUnitVector());

        if(validator.horizontaledge(newPosition) && validator.verticaledge(newPosition)){
            this.position = newPosition;
        }
        if(!validator.horizontaledge(newPosition)){
            for(int i = 0; i < 4; i++) {
                this.direction = this.direction.next();
            }
            newPosition = this.position.add(this.direction.toUnitVector());
            if(!validator.verticaledge(newPosition)){
                if(this.direction.toUnitVector().getX() >0){
                    newPosition = newPosition.add(new Vector2d(-newPosition.getX(),0));
                } else{
                    newPosition = newPosition.add(new Vector2d(validator.width,0));
                }
            }
            this.position=newPosition;
        } else{
            if(!validator.verticaledge(newPosition)){
                if(this.direction.toUnitVector().getX() >0){
                    newPosition = newPosition.add(new Vector2d(-newPosition.getX(),0));
                } else{
                    newPosition = newPosition.add(new Vector2d(validator.width,0));
                }

            }
            this.position=newPosition;
        }
    }

    
    public void goThroughTunnel(Vector2d Position, SecretTunnels validator){
            if(validator.objectAt(Position) instanceof Tunnel){
                this.position = validator.getTunnel(Position).getConnected();
            }
    }

        MapDirection getDirection() {
            return this.direction;
        }

        public Vector2d getPosition() {
            return this.position;
        }

    }