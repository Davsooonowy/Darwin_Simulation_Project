package agh.ics.oop.model.mapObjects;

import agh.ics.oop.model.mapObjects.properties.Genomes;
import agh.ics.oop.model.mapObjects.properties.MapDirection;
import agh.ics.oop.model.maps.AbstractWorldMap;
import agh.ics.oop.model.maps.SecretTunnels;
import agh.ics.oop.model.vector_records.Vector2d;
import javafx.scene.paint.Paint;

import java.util.HashSet;
import java.util.List;


public class Animal implements WorldElement {
    private MapDirection direction;
    private final int REPRODUCTION_ENERGY;
    private Vector2d position;
    public Animal parent1;
    public Animal parent2;
    private int energy;
    private final Genomes genomes;
    private int age;
    private int offspringCount;
    private int childrenCount;
    private final int parentEnergy;
    private final int mingeneMutation;
    private final int maxgeneMutation;
    private int deathDay;
    private int eatenPlants = 0;
    private int activeGenome;

    public Animal(Vector2d initialPosition, int initialenergy, int genomeLength, int reproductionEnergy, int parentEnergy, int mingeneMutation,int maxgeneMutation) {
        this.direction = MapDirection.randomDirection();
        this.position = initialPosition;
        this.energy = initialenergy;
        this.genomes = new Genomes(genomeLength);
        this.age = 0;
        this.parent1=null;
        this.parent2=null;
        this.offspringCount = 0;
        this.childrenCount = 0;
        this.REPRODUCTION_ENERGY = reproductionEnergy;
        this.parentEnergy = parentEnergy;
        this.mingeneMutation=mingeneMutation;
        this.maxgeneMutation=maxgeneMutation;
        this.deathDay = -1;
    }

    // constructor for children
    public Animal(Vector2d position, int energy, Genomes genomes, Animal parent1, Animal parent2, int reproductionEnergy, int parentEnergy, int mingeneMutation,int maxgeneMutation) {
        this.direction = MapDirection.randomDirection();
        this.position = position;
        this.energy = energy;
        this.genomes = genomes;
        this.age = 0;
        this.parent1 = parent1;
        this.parent2 = parent2;
        this.offspringCount = 0;
        this.childrenCount = 0;
        this.REPRODUCTION_ENERGY = reproductionEnergy;
        this.parentEnergy = parentEnergy;
        this.mingeneMutation=mingeneMutation;
        this.maxgeneMutation=maxgeneMutation;
        this.deathDay = -1;
    }


    public void offspringincrease(HashSet<Animal> ancestors){
        if(this.parent1!= null && !ancestors.contains(this.parent1)){
            this.parent1.offspringCount++;
            ancestors.add(this.parent1);
            this.parent1.offspringincrease(ancestors);
        }
        if(this.parent2!=null && !ancestors.contains(this.parent2)){
            this.parent2.offspringCount++;
            ancestors.add(this.parent2);
            this.parent2.offspringincrease(ancestors);
        }
    }



    // sex
    public Animal reproduce(Animal partner) {
        List<Integer> childGenes = this.genomes.GeneMerger(this, partner);
        Genomes childGenomes = new Genomes(childGenes,this.mingeneMutation,this.maxgeneMutation);
        this.animalEnergyChange(-this.parentEnergy);
        partner.animalEnergyChange(-this.parentEnergy);
        this.childrenCount++;
        partner.childrenCount++;
        return new Animal(this.position, 2 * this.parentEnergy, childGenomes, this, partner, this.REPRODUCTION_ENERGY, this.parentEnergy, this.mingeneMutation, this.maxgeneMutation);
    }


    public int getEnergy() {
        return this.energy;
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

    public boolean isDead() {
        return this.energy <= 0;
    }

    public Paint toColor(int startEnergy) {
        if (this.energy == 0) return javafx.scene.paint.Color.rgb(255, 0, 0);
        if (this.energy < 0.2 * startEnergy) return javafx.scene.paint.Color.rgb(173, 216, 230);
        if (this.energy < 0.4 * startEnergy) return javafx.scene.paint.Color.rgb(100, 149, 237);
        if (this.energy < 0.6 * startEnergy) return javafx.scene.paint.Color.rgb(70, 130, 180);
        if (this.energy < 0.8 * startEnergy) return javafx.scene.paint.Color.rgb(0, 0, 255);
        if (this.energy < startEnergy) return javafx.scene.paint.Color.rgb(0, 0, 205);
        return javafx.scene.paint.Color.rgb(0, 0, 51);
    }

    public void move(int direction, AbstractWorldMap validator) {
        this.activeGenome = direction;
        for(int i = 0; i < direction; i++) {
            this.direction = this.direction.next();
        }
        Vector2d newPosition = this.position.add(this.direction.toUnitVector());

        if(validator.horizontaledge(newPosition) && validator.verticaledge(newPosition)){
            this.position = newPosition;
        }
        if(!validator.horizontaledge(newPosition)){
            for(int i = 0; i < 4; i++) {
                this.direction = this.direction.next();
            }
        } else{
            if(!validator.verticaledge(newPosition)){
                if(this.direction.toUnitVector().x() >0){
                    newPosition = newPosition.add(new Vector2d(-newPosition.x(),0));
                } else{
                    newPosition = newPosition.add(new Vector2d(validator.width,0));
                }

            }
            this.position=newPosition;
        }
    }


    public void goThroughTunnel(Vector2d Position, SecretTunnels validator){
        if(validator.objectAt(Position) instanceof Tunnel){
            this.position = validator.getTunnel(Position).connected();
        }
    }

    public void setDeathDay(int day) {
        this.deathDay = day;
    }

    public int getActiveGenome() {
        return activeGenome;
    }
    public int getDeathDay() {
        return deathDay;
    }

    public Vector2d position() {
        return this.position;
    }

    public int getoffspringCount() {
        return offspringCount;
    }

    public int getChildrenCount() {
        return childrenCount;
    }

    public void addEeatenPlants() {
        this.eatenPlants++;
    }
    public int getEatenPlants() {
        return this.eatenPlants;
}

    public MapDirection getDirection() {
        return this.direction;
    }

    void setDirection(MapDirection direction) {
        this.direction = direction;
    }

    public Vector2d getPosition() {
        return this.position;
    }
}