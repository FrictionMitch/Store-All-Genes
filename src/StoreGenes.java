import edu.duke.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class StoreGenes {
        int taaIndex;
        int tagIndex;
        int tgaIndex;

        int total;
        int geneTotal;
    int totalC;
    StorageResource geneList;

        public int findStopCodon(String dnaString, int startIndex, String stopCodon) {
            int currentIndex = dnaString.indexOf(stopCodon, startIndex + stopCodon.length());

            while(currentIndex != -1) {
                int difference = currentIndex - startIndex;
                if(difference % 3 == 0) {
                    return currentIndex;
                } else {
                    currentIndex = dnaString.indexOf(stopCodon, currentIndex + 1);
                }
            }
            return - 1;
        }

    public int findC(String dna, int position) {
        totalC = 0;
        position = 0;
        int startIndex = dna.indexOf("C", position);
        while (startIndex != -1) {
            totalC++;
            position++;
        }
        System.out.println("Total C's: " + totalC);
        return totalC;
    }

        public String findGene(String dna, int position) {
            int startIndex = dna.indexOf("ATG", position);
            if (startIndex == -1) {
                return "";
            }
            taaIndex = findStopCodon(dna, startIndex, "TAA");
            tagIndex = findStopCodon(dna, startIndex, "TAG");
            tgaIndex = findStopCodon(dna, startIndex, "TGA");

            // Find which of the stop codons comes first
            int minIndex = 0;
            if ((taaIndex == -1) || (tagIndex != -1 && tagIndex < tgaIndex)) {
                minIndex = tagIndex;
            } else {
                minIndex = taaIndex;
            }
            if ((minIndex == -1) || (tgaIndex != -1 && tgaIndex < minIndex)) {
                minIndex = tgaIndex;
            }

            if(minIndex == -1){
                return "";
            }

            return dna.substring(startIndex, minIndex + 3);
        }

        public StorageResource storeAllGenes(String dna) {
            geneList = new StorageResource();
            int startIndex = 0;
            geneTotal = 0;
            while(true) {
                String currentGene = findGene(dna, startIndex);
                if(currentGene.isEmpty()) {
                    break;
                }

//                System.out.println(currentGene);
//                    geneTotal++;

                startIndex = dna.indexOf(currentGene, startIndex) + currentGene.length();
                geneList.add(currentGene);
            }
//            System.out.println(geneTotal + " gene(s) found");
                return geneList;
        }

    public List<String> storeGeneArray(String dna) {
//        String[] badWords = mBadWords.split("[^\\w_']+");
//        return Arrays.asList(badWords);
        List<String>geneList = new ArrayList<String>();
        int startIndex = 0;
        geneTotal = 0;
        while(true) {
            String currentGene = findGene(dna, startIndex);
            if(currentGene.isEmpty()) {
                break;
            }

//                System.out.println(currentGene);
//                    geneTotal++;

            startIndex = dna.indexOf(currentGene, startIndex) + currentGene.length();
            geneList.add(currentGene);
        }
//            System.out.println(geneTotal + " gene(s) found");
        return geneList;
    }

        public int howMany(String gene, String dna) {
            int startIndex = 0;
            total = 0;
            while (true) {
                int currentIndex = dna.indexOf(gene, startIndex);
                startIndex = currentIndex + gene.length();
                if (currentIndex == -1) {
                    break;
                } else {
                    total++;
                }
            }

            return total;
        }

    public double cgRatio(String dna) {
        int cIndex = 0;
        int gIndex = 0;
        String letterC = "C";
        String letterG = "G";
        double totalC = 0;
        double totalG = 0;
        while(true) {
            int currentIndexC = dna.indexOf("C", cIndex);
            cIndex = currentIndexC + 1;
            if (currentIndexC == -1) {
                break;
            } else {
                totalC++;
            }

            int currentIndexG = dna.indexOf("G", gIndex);
            gIndex = currentIndexG + 1;
            if (currentIndexG == -1) {
                break;
            } else {
                totalG++;
            }
        }
        System.out.printf("There are %.2f C's and %.2f G's%n", totalC, totalG);

        double ratio = (totalC+totalG) / dna.length();
        System.out.printf("The ratio of C/G is: ");
        return ratio;
    }

    public int countCTG(String dna) {
        int startIndex = 0;
        int totalCTG = 0;
        while (true) {
            int currentIndex = dna.indexOf("CTG", startIndex);
            startIndex = currentIndex + 3;
            if (currentIndex == -1) {
                break;
            } else {
                totalCTG++;
            }
        }

        System.out.printf("There are %d CTG's%n", totalCTG);
        return totalCTG;
    }


        public void testHowMany() {
            String A = "CTG";
            String B = "CTGCTG";
            howMany(A, B);
            System.out.printf("There are %d %s's in %s%n%n", total, A, B);
            A = "C";
            B = "CATGCCCATAGC";
            howMany(A, B);
            System.out.printf("There are %d %s's in %s%n%n", total, A, B);
        }

        public void testOn(String dna) {
            StorageResource genes = storeAllGenes(dna);
            System.out.println("Testing \"printAllGenes\" on " + dna);
            for (String g : genes.data()) {
                System.out.println(g);
            }
            storeAllGenes(dna);
        }

    public void testCTG() {

    }

        public void test() {
            FileResource fr = new FileResource("brca1line.fa");
            String dna = fr.asString().toUpperCase();
            testOn("ATGATCTAATTTATGCTGCAACGGTGAAGA");
            testOn("");
            testOn("ATGATCATAAGAAGATAATAGAGGGCCATGTAA");
            testOn("GATGTGACATGTAAATGTAGATATTTATGCCCTAG");
            testOn(dna);
            System.out.println(cgRatio("ATGCCATAG"));
            testHowMany();
            countCTG("ATGCCATAG");
//            System.out.println("FUCK");
//            howMany("CTGCTG", "CTG");

        }



}
