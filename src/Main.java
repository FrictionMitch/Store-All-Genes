public class Main {

    public static void main(String[] args){
        StoreGenes storeGenes = new StoreGenes();
        storeGenes.test();
        storeGenes.findC("ATGCCATAG", 0);
        System.out.println("2 + 2 = ");
        System.out.println(simple(2));

    }

    public static int simple(int x) {
        x++;
//        int y;
        return x;
    }
}
