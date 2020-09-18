package com.company;

import java.util.Random;

public class Main {

    public static int bossHealth = 1000;
    public static int bossDamage = 50;
    public static String bossDefence = "";
    public static int[] heroesHealth = {270, 260, 250, 240, 150, 450, 200, 300, 350};
    public static int[] heroesDamage = {25, 15, 20, 30, 30, 10, 15, 35, 45};
    public static String[] heroesAttackType = {
            "Physical", "Magical", "Kinetic", "Archer", "Healer", "Golem", "Lucky", "Berserk", "Thor"};
    public static int roundCounter = 0;

    public static void main(String[] args) {
        printStatistics();
        while (!isGameOver()) {
            round();
        }
    }

    public static void changeDefence() {
        Random r = new Random();
        int randomIndex = r.nextInt(heroesAttackType.length);
        bossDefence = heroesAttackType[randomIndex];
        System.out.println("Boss choose: " + bossDefence);
    }

    public static boolean isGameOver() {
        if (bossHealth <= 0) {
            System.out.println("Heroes won!!!");
            return true;
        }
        boolean allHeroesDead = true;
        for (int i = 0; i < heroesHealth.length; i++) {
            if (heroesHealth[i] > 0) {
                allHeroesDead = false;
                break;
            }
        }
        if (allHeroesDead) {
            System.out.println("Boss won!!!");
        }
        return allHeroesDead;
    }

    public static void round() {
        if (bossHealth > 0) {
            changeDefence();
            bossHits();
        }
        heroesHit();
        printStatistics();
    }

    public static void bossHits() {
        for (int i = 0; i < heroesDamage.length; i++) {
            if (heroesHealth[i] > 0) {
                if (heroesHealth[i] - bossDamage < 0) {
                    heroesHealth[i] = 0;
                } else {
                    heroesHealth[i] = heroesHealth[i] - bossDamage;
                }
            }
        }
        Golem();
        Berserk();
        Thor();
    }

    public static void Healer() {
        if (heroesHealth[4] > 0) {
            for (int i = 0; i < heroesHealth.length; i++) {
                heroesHealth[i] = heroesHealth[i] + heroesDamage[4];
            }
        } else {
            heroesHealth[4] = 0;
        }
    }

    public static void Lucky() {
        Random r = new Random();
        int randomN = r.nextInt(2);
        if (randomN == 1) {
            System.out.println("Boss miss");
            System.out.println("________________");
            heroesHealth[6] = heroesHealth[6] + bossDamage;
        }

    }

    public static void Golem() {
        for (int i = 0; i < heroesHealth.length; i++) {
            Random r = new Random();
            int randomShield = r.nextInt(50);

            System.out.println("Shield hero " + heroesHealth[i] + " damage");
            System.out.println("____________________");
            if (heroesHealth[4] > 0) {
                heroesHealth[i] = heroesHealth[i] += randomShield;
                heroesHealth[4] = heroesHealth[4] - randomShield;
            } else if (heroesHealth[5] <= 0) {
                System.out.println("Golem died");

            }
        }
    }

    public static void Berserk() {
        Random ra = new Random();
        int randomM = ra.nextInt(50);
        System.out.println("Berserk return hit " + randomM);
        System.out.println("___________________");
        bossHealth = bossHealth - (heroesDamage[7] + randomM);

    }

    public static void Thor() {
        Random ra = new Random();
        int randomM = ra.nextInt(3);
        if (randomM == 1) {
            System.out.println("Thor stunned the Boss");
            System.out.println("_____________________");
            bossDamage = 0;
        } else {
            bossDamage = 50;
        }
    }

    public static void heroesHit() {
        for (int i = 0; i < heroesHealth.length; i++) {
            if (heroesHealth[i] > 0 && bossHealth > 0) {
                if (heroesAttackType[i] == bossDefence) {
                    Random r = new Random();
                    int coeff = r.nextInt(7) + 1; //1,2,3,4,5
                    if (bossHealth - heroesDamage[i] * coeff < 0) {
                        bossHealth = 0;
                    } else {
                        bossHealth = bossHealth - heroesDamage[i] * coeff;
                    }
                    if (coeff > 1) {
                        System.out.println("Critical damage: "
                                + heroesDamage[i] * coeff);
                    }
                } else {
                    if (bossHealth - heroesDamage[i] < 0) {
                        bossHealth = 0;
                    } else {
                        bossHealth = bossHealth - heroesDamage[i];
                    }
                }
            }
        }
        Healer();
        Lucky();
    }

    public static void printStatistics() {
        System.out.println("________________");
        System.out.println("Round ---- " + roundCounter);
        roundCounter++;
        System.out.println("Boss health: " + bossHealth);
        for (int i = 0; i < heroesHealth.length; i++) {
            System.out.println(heroesAttackType[i] + " health: "
                    + heroesHealth[i]);
        }
        System.out.println("________________");
    }
}