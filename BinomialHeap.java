/**
 * BinomialHeap
 *
 * An implementation of binomial heap over non-negative integers.
 * Based on exercise from previous semester.
 */
public class BinomialHeap
{
    public int size;
    public HeapNode last;
    public HeapNode min;

    /**
     *
     * pre: key > 0
     *
     * Insert (key,info) into the heap and return the newly generated HeapItem.
     *
     */
    public HeapItem insert(int key, String info)
    {

    }

    public HeapNode mergeTrees (HeapNode nodeA, HeapNode nodeB) {

    }

    public void mergeInto (HeapNode node) {
        if (node.rank > last.rank) {
            node.next = last.next;
            last.next = node;
            last = node;
            return;
        }
        HeapNode crnt = last.next;
        while (crnt.rank < node.rank)
            crnt = crnt.next;
        if (crnt.rank == node.rank) {

        }
    }

    /**
     *
     * Delete the minimal item
     *
     */
    public void deleteMin()
    {
        return; // should be replaced by student code

    }

    /**
     *
     * Return the minimal HeapItem
     *
     */
    public HeapItem findMin()
    {
        return null; // should be replaced by student code
    }

    /**
     *
     * pre: 0<diff<item.key
     *
     * Decrease the key of item by diff and fix the heap.
     *
     */
    public void decreaseKey(HeapItem item, int diff)
    {
        return; // should be replaced by student code
    }

    /**
     *
     * Delete the item from the heap.
     *
     */
    public void delete(HeapItem item)
    {
        return; // should be replaced by student code
    }

    /**
     *
     * Meld the heap with heap2
     *
     */
    public void meld(BinomialHeap heap2)
    {
        return; // should be replaced by student code
    }

    /**
     *
     * Return the number of elements in the heap
     *
     */
    public int size()
    {
        return 42; // should be replaced by student code
    }

    /**
     *
     * The method returns true if and only if the heap
     * is empty.
     *
     */
    public boolean empty()
    {
        return false; // should be replaced by student code
    }

    /**
     *
     * Return the number of trees in the heap.
     *
     */
    public int numTrees()
    {
        return 0; // should be replaced by student code
    }

    /**
     * Class implementing a node in a Binomial Heap.
     *
     */
    public class HeapNode{
        public HeapNode (){}

        public HeapNode (HeapNode parent, int key, String info){
			this.item = new HeapItem(this, key, info);
			this.parent = parent;
			this.rank = 0;
        }
        public HeapItem item;
        public HeapNode child;
        public HeapNode next;
        public HeapNode parent;
        public int rank;

        public int getKey () {
            return item.key;
        }

        public String getInfo () {
            return item.info;
        }

        public void setKey (int key) {
            item.key = key;
        }

        public void setInfo (String info) {
            item.info = info;
        }
    }

    /**
     * Class implementing an item in a Binomial Heap.
     *
     */
    public class HeapItem{

        public HeapItem() {
        }

        public HeapItem(HeapNode node, int key, String info) {
            this.node = node;
            this.key = key;
            this.info = info;
        }

        public HeapNode node;
        public int key;
        public String info;
    }

}
