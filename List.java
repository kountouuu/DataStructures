// in order to keep track of keys in bst we have to create our own list

public class List<T> {

    private class Node{
        private Node next;
        private T data;

        public Node(){}

        public Node(T data){
            this.data = data;
        }

        public T getData(){
            return data;
        }
    }

    private Node head;
    private int size = 0;

    public List(){}

    public List(T dta){
        head = new Node(dta);
        size++;
    }

    public void add(T dta){
        Node tmp = new Node(dta);
        tmp.next = head;
        head = tmp;
        size++;
    }

    public void remove(T dta){
        Node curr = head;
        int pos = 0;

        if(curr.getData().toString().equalsIgnoreCase(dta.toString())){
            head = head.next;
            size--;
            return;
        }
        while(!curr.getData().toString().equalsIgnoreCase(dta.toString())){
            curr = curr.next;
            pos++;
        }

        Node prevNode = head;
        for(int i = 0; i < pos - 1; i++){
            prevNode = prevNode.next;
        }
        prevNode.next = prevNode.next.next;
        size--;
    }

    public int getSize(){
        return size;
    }

    public boolean contains(T dta){
        Node curr = head;
        for(int i = 0; i < getSize(); i++){
            if(curr.getData().toString().equalsIgnoreCase(dta.toString())){
                return true;
            }
            curr = curr.next;
        }
        return false;
    }

    public void print(){
        Node curr = head;
        while(curr != null){
            System.out.println(curr.data + " ");
            curr = curr.next;
        }
    }
}
