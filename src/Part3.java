import edu.duke.*;

public class Part3 {
    StoreGenes storeGenes = new StoreGenes();
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
    public void processGenes (StorageResource sr) {
        int count60 = 0;
        int count35 = 0;
        int longest = 0;
        StorageResource sr35 = new StorageResource();
        for(String gene : sr.data()) {
            if (gene.length() > 9) {
                System.out.println(gene);
                count60++;
            }
            if(cgRatio(gene) > 0.35) {
                count35++;
                sr35.add(gene);
            }
            System.out.println("The number of genes longer than 60 charatercs is: " + count60);
            for(String string35 : sr35.data()) {
                System.out.println(string35);
            }
            System.out.println("The number of genes with a CG ratio greater than 0.35 is: " + count35);
        }

    }

    public double cgRatio(String dna) {
        int cIndex = 0;
        int gIndex = 0;
        String letterC = "C";
        String letterG = "G";
        double totalC = 0;
        double totalG = 0;
        while (true) {
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
//        System.out.printf("There are %.2f C's and %.2f G's%n", totalC, totalG);

        double ratio = (totalC + totalG) / dna.length();
//        System.out.printf("The ratio of C/G is: ");
        return ratio;
    }



    public void testProcessGenes(String dna) {

    }

    public String mystery(String dna) {
        int pos = dna.indexOf("T");
        int count = 0;
        int startPos = 0;
        String newDna = "";
        if (pos == -1) {
            return dna;
        }
        while (count < 3) {
            count += 1;
            newDna = newDna + dna.substring(startPos,pos);
            startPos = pos+1;
            pos = dna.indexOf("T", startPos);
            if (pos == -1) {
                break;
            }
        }
        newDna = newDna + dna.substring(startPos);
        return newDna;
    }


}
