    package agh.ics.oop.model;
    import javafx.scene.shape.Circle;

    import java.awt.Color;


    public class Animal implements WorldElement {
        private MapDirection direction;
        private int REPRODUCTION_ENERGY;
        private Vector2d position;
        private int energy;
        private Genomes genomes;
        private int age = 0;
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

        public int getEnergy() {
            return this.energy;
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

        // age management
        public void increaseAge() {
            this.age++;
        }

        public int getAge() {
            return this.age;
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


        // TODO: zmiana kolorków w zależności ile mu zostało energii, zamiast kierunku na mapie
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

    public Color toColor(int startEnergy) {
        if (energy == 0) return new Color(222, 221, 224);
        if (energy < 0.2 * startEnergy) return new Color(224, 179, 173);
        if (energy < 0.4 * startEnergy) return new Color(224, 142, 127);
        if (energy < 0.6 * startEnergy) return new Color(201, 124, 110);
        if (energy < 0.8 * startEnergy) return new Color(182, 105, 91);
        if (energy < startEnergy) return new Color(164, 92, 82);
        if (energy < 2 * startEnergy) return new Color(146, 82, 73);
        if (energy < 4 * startEnergy) return new Color(128, 72, 64);
        if (energy < 6 * startEnergy) return new Color(119, 67, 59);
        if (energy < 8 * startEnergy) return new Color(88, 50, 44);
        if (energy < 10 * startEnergy) return new Color(74, 42, 37);
        return new Color(55, 31, 27);
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