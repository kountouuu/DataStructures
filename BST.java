import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintStream;

public class BST implements WordCounter{

    // Tree Node private Class
    private static class TreeNode{
        //Fields
        private WordFreq item;                  // item holds key which holds value
        private TreeNode left;                  // left child
        private TreeNode right;                 // right child
        private int subtreeSize;                // size of a part of the tree

        //Constructor
        TreeNode(String x) {
            this.item = new WordFreq(x);
        }

        // Setters & Getters
        public WordFreq getItem(){
            return this.item;
        }

        public TreeNode getLeftChild(){
            return this.left;
        }

        public TreeNode getRightChild(){
            return this.right;
        }
    }
    // Fields of BST
    private static int maxF;
    private static WordFreq maxFreq;
    private List stopList;
    private int bstSize = 0;
    int i;
    private static int distinctWordCounter = 0;
    private TreeNode head;                      //Binary Search Tree's head

    //BST Constructor

    BST(){
        this.head = null;
        stopList = new List<String>();
        distinctWordCounter++;
        maxFreq = new WordFreq();
        maxF = -1;
        i = 0;
    }

    // Implementing basic / helper functions

    private void printAlphabetically(TreeNode head, PrintStream stream) {
        if (head == null) return;

        printAlphabetically(head.left, stream);
        stream.println(head.item.getKey() + ' ');
        printAlphabetically(head.right, stream);
    }

    private boolean less(String ww, String aa){             // compares to string values
        int a = ww.compareToIgnoreCase(aa);
        return a < 0;
    }

    private WordFreq searchR(TreeNode head, String w) {         // search recursive
        if (head == null) {
            return null;
        }
        boolean h = w.equals(head.getItem().getKey());

        if (h){
            return head.getItem();
        }
        if (less(w,head.getItem().getKey())){
            return searchR(head.getLeftChild(), w);
        }else{
            return searchR(head.getRightChild(), w);
        }
    }

    private TreeNode insertR(TreeNode a,String x){          // insert recursive
        if( a == null ) {
            return new TreeNode(x);
        }
        if(a.getItem().getKey().equalsIgnoreCase(x)){
            distinctWordCounter--;
            a.getItem().incrementOccurrence();
            if(a.getItem().getOccurrences() > maxFreq.getOccurrences()){
                maxFreq = a.getItem();
            }
        }
        if(less(x,a.getItem().getKey()))
            a.left = insertR(a.getLeftChild(), x);
        else
            a.right = insertR(a.getRightChild(), x);
        return a;
    }

    private TreeNode removeR(TreeNode a, String w){             // remove recursive
        if ( a == null ) return null;
        String aString = a.item.getKey();
        if (less(w,aString)){
            head.left = removeR(head.getLeftChild(), w);
        }
        if (less(aString,w)){
            head.right = removeR(head.getRightChild() , w);
        }
        if(w.equals(aString)){
            a = joinLR(head.getLeftChild(), head.getRightChild());
        }
        return a;
    }

    private TreeNode joinLR(TreeNode a, TreeNode b){                // join left and right subtrees
        if (b == null) return a;
        b = partR(b, 0);
        b.left = a;
        return b;
    }

    private TreeNode partR(TreeNode h, int k) {                     // breaks apart subtrees
        int t = (h.left == null) ? 0 : h.left.subtreeSize;
        if (t > k) {
            h.left = partR(h.getLeftChild(), k);
            h = rotR(h); }
        if (t < k) {
            h.right = partR(h.getRightChild(), k-t-1);
            h = rotL(h); }
        return h;
    }

    private TreeNode rotR(TreeNode h){                      // rotate to the Right
        TreeNode x = h.left;
        h.left = x.right;
        x.right = h;
        return x;
    }

    private TreeNode rotL(TreeNode h){                      // rotate to the Left
        TreeNode x = h.right;
        h.right = x.left;
        x.left = h;
        return x;
    }

    private WordFreq[] rearrange(WordFreq[] arr) {
        for (int i = 0; i < arr.length; i++) {
            for (int j = i + 1; j < arr.length; j++) {
                int tmp;
                if (arr[i].getOccurrences() <= arr[j].getOccurrences()) {
                    tmp = arr[i].getOccurrences();
                    arr[i].setOccurrences(arr[j].getOccurrences());
                    arr[j].setOccurrences(tmp);
                }
            }
        }
        return arr;
    }

    private void makeArray(TreeNode head, WordFreq[] arr) {
        if(head != null) {
            if (head.getLeftChild() != null || head.getRightChild() != null) {
                makeArray(head.getLeftChild(), arr);
                arr[i] = head.getItem();
                i++;
                makeArray(head.getRightChild(), arr);
            }
        }
    }

    // Overriding Methods from WordCounter

    @Override
    public void insert(String w) {
        head = insertR(head, w);
        bstSize++;
    }

    @Override
    public WordFreq search(String w) {
        return searchR(head, w);
    }

    @Override
    public void remove(String w) {
        head = removeR(head, w);
        bstSize--;
    }

    @Override
    public int getTotalWords() {
        return bstSize;
    }

    @Override
    public int getFrequency(String w) {
        WordFreq x = search(w);
        return x.getOccurrences();
    }

    @Override
    public int getDistinctWords() {
        return distinctWordCounter + bstSize;
    }

    @Override
    public void addStopWord(String w) {
        stopList.add(w);
    }

    @Override
    public void removeStopWord(String w) {
        stopList.remove(w);
    }

    @Override
    public WordFreq getMaximumFrequency() {
        return maxFreq;
    }

    @Override
    public double getMeanFrequency() {
        return (double) getTotalWords() / getDistinctWords();
    }

    @Override
    public void printTreeAlphabetically(PrintStream stream) {
        printAlphabetically(head,stream);
    }

    @Override
    public void printTreeByFrequency(PrintStream stream) {
        WordFreq[] arr = new WordFreq[getDistinctWords()];
        i = 0;
        makeArray(head, arr);
        arr = rearrange(arr);
        for (int x = 0; x < arr.length - 1; x++) {
            stream.println(arr[x].getOccurrences());
        }
    }


    @Override
    public void load(String filename) throws FileNotFoundException {
        FileReader fr = new FileReader(filename);
        StringBuilder sb = new StringBuilder();
        char currChar;
        int counter;

    }








    // MAIN
    public static void main(String[] args) {
        BST b = new BST();


        b.insert("eeeeeeyoooooo");
        b.insert("hi");
        b.insert("ho");
        b.insert("hi");
        b.insert("Hi");
        b.insert("aaaa");
        b.insert("eeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee");

        System.out.println("Total elements " + b.getTotalWords());
        System.out.println("Distinct Elements " + b.getDistinctWords());
        PrintStream ps = new PrintStream(System.out);
        //b.printTreeAlphabetically(ps);
        b.printTreeByFrequency(ps);





    }
}
