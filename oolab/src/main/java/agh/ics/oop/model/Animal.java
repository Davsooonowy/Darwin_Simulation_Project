    package agh.ics.oop.model;

    import java.util.ArrayList;
    import java.util.List;
    import java.util.Random;

    import static java.lang.Math.abs;

    public class Animal implements WorldElement {
        private MapDirection direction;
        private static final int REPRODUCTION_ENERGY = 50;
        private static final Random RANDOM = new Random();
        private Vector2d position;
        private int energy = 6;
        private final List<Integer> genome;
        private Genomes genomes;

        public Animal(Vector2d initialPosition, int initialenergy) {
            this.direction = MapDirection.randomDirection();
            this.position = initialPosition;
            this.energy = initialenergy;
            this.genome = List.of(0, 1, 2, 3, 4, 5, 6, 7);
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

        @Override
        public boolean isAnimal() {
            return true;
        }

        @Override
        public Animal asAnimal() {
            return this;
        }
//        public Animal reproduce(Animal other) {
//            int totalEnergy = this.energy + other.energy;
//            int thisGenes = (int) ((double) this.energy / totalEnergy * this.genomes.getGenes().size());
//            int otherGenes = this.genomes.getGenes().size() - thisGenes;
//
//            ArrayList<Integer> childGenes = new ArrayList<>();
//            childGenes.addAll(this.genomes.getGenes().subList(0, thisGenes));
//            childGenes.addAll(other.genomes.getGenes().subList(other.genomes.getGenes().size() - otherGenes, other.genomes.getGenes().size()));
//
//            int childEnergy = this.energy / 4 + other.energy / 4;
//            this.energy -= this.energy / 4;
//            other.energy -= other.energy / 4;
//
//            Animal child = new Animal(this.position, childEnergy);
//            child.genomes = new Genomes(childGenes);
//
//            child.mutate();
//
//            return child;
//        }


//        private void mutate() {
//            int mutations = RANDOM.nextInt(this.genomes.getGenes().size());
//            for (int i = 0; i < mutations; i++) {
//                int index = RANDOM.nextInt(this.genomes.getGenes().size());
//                this.genomes.getGenes().set(index, RANDOM.nextInt(8));
//            }
//        }
    public Animal reproduce(Animal other) {
        int totalEnergy = this.energy + other.energy;
        int thisGenes = (int) ((double) this.energy / totalEnergy * this.genomes.getGenes().size());
        int otherGenes = this.genomes.getGenes().size() - thisGenes;

        ArrayList<Integer> childGenes = new ArrayList<>();
        if (RANDOM.nextBoolean()) {
            childGenes.addAll(this.genomes.getGenes().subList(0, thisGenes));
            childGenes.addAll(other.genomes.getGenes().subList(other.genomes.getGenes().size() - otherGenes, other.genomes.getGenes().size()));
        } else {
            childGenes.addAll(other.genomes.getGenes().subList(0, otherGenes));
            childGenes.addAll(this.genomes.getGenes().subList(this.genomes.getGenes().size() - thisGenes, this.genomes.getGenes().size()));
        }

        int childEnergy = this.energy / 4 + other.energy / 4;
        this.energy -= this.energy / 4;
        other.energy -= other.energy / 4;

        Animal child = new Animal(this.position, childEnergy);
        child.genomes = new Genomes(childGenes);

        child.mutate();

        return child;
    }

    private void mutate() {
        int mutations = RANDOM.nextInt(this.genomes.getGenes().size());
        for (int i = 0; i < mutations; i++) {
            int index = RANDOM.nextInt(this.genomes.getGenes().size());
            this.genomes.getGenes().set(index, RANDOM.nextInt(8));
        }
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