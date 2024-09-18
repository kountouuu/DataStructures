public class WordFreq {
    private int occurrences;
    private String key;

    WordFreq(){
        this.occurrences = 1;
    }

    WordFreq(String k){
        this.occurrences = 1;
        this.key = k;
    }

    public String getKey(){
        return this.key;
    }

    public void setKey(String w){
        this.key = w;
    }

    public int getOccurrences(){
        return this.occurrences;
    }

    public void setOccurrences(int x){
        this.occurrences = x;
    }

    public void incrementOccurrence(){
        this.occurrences += 1;
    }

    @Override
    public String toString(){
        return this.getKey() + " ";
    }


}
