//package agh.ics.oop.model;
//
//public class Genomes {
//    public static final int GENOME_SIZE = 32;
//    public static final int GENOME_MAX_VALUE = 8;
//    public static final int GENOME_MIN_VALUE = 0;
//    public static final int GENOME_MAX_SUM = 32;
//    public static final int GENOME_MIN_SUM = 0;
//
//    public static int[] generateGenome() {
//        int[] genome = new int[GENOME_SIZE];
//        int sum = 0;
//        for (int i = 0; i < GENOME_SIZE; i++) {
//            genome[i] = (int) (Math.random() * (GENOME_MAX_VALUE - GENOME_MIN_VALUE + 1) + GENOME_MIN_VALUE);
//            sum += genome[i];
//        }
//        if (sum != GENOME_MAX_SUM) {
//            int diff = sum - GENOME_MAX_SUM;
//            int index = (int) (Math.random() * GENOME_SIZE);
//            genome[index] -= diff;
//        }
//        return genome;
//    }
//
//    public static int[] generateGenome(int[] parent1, int[] parent2) {
//        int[] genome = new int[GENOME_SIZE];
//        int sum = 0;
//        for (int i = 0; i < GENOME_SIZE; i++) {
//            int parent = (int) (Math.random() * 2);
//            if (parent == 0) {
//                genome[i] = parent1[i];
//            } else {
//                genome[i] = parent2[i];
//            }
//            sum += genome[i];
//        }
//        if (sum != GENOME_MAX_SUM) {
//            int diff = sum - GENOME_MAX_SUM;
//            int index = (int) (Math.random() * GENOME_SIZE);
//            genome[index] -= diff;
//        }
//        return genome;
//    }
//
//    public static int[] mutateGenome(int[] genome) {
//        int[] mutatedGenome = genome.clone();
//        int index = (int) (Math.random() * GENOME_SIZE);
//        int value = (int) (Math.random() * (GENOME_MAX_VALUE - GENOME_MIN_VALUE + 1) + GENOME_MIN_VALUE);
//        mutatedGenome[index] = value;
//        return mutatedGenome;
//    }
//
//    public static int[] mutateGenome(int[] genome, int mutationChance) {
//        int[] mutatedGenome = genome.clone();
//        int index = (int) (Math.random() * GENOME;
//}
//}