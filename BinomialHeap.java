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
    public HeapItem insert(int key, String info){
        HeapNode node = new HeapNode(null, key, info);
        mergeInto(node);
        this.size ++;
        return node.item;
    }


    public static HeapNode mergeTrees (HeapNode nodeA, HeapNode nodeB) {
        if (nodeA.getKey() > nodeB.getKey())
            return mergeTrees(nodeB, nodeA);
        if (nodeA.rank > 0) {
            nodeB.next = nodeA.child.next;
            nodeA.child.next = nodeB;
        }
        nodeA.child = nodeB;
        nodeB.parent = nodeA;
        nodeA.rank ++;
        return nodeA;
    }

    /*
    Add a Binomial Tree to the heap
     */
    public void mergeInto (HeapNode node) {
        if (last == null) { // The heap is empty - add the tree as is
            last = node;
            min = node;
            return;
        }
        if (node.getKey() < min.getKey())
            min = node;
        if (node.rank > last.rank) { //add at the end
            node.next = last.next;
            last.next = node;
            last = node;
            return;
        }
        HeapNode crnt = last;
        while (crnt.next.rank < node.rank)
            crnt = crnt.next;
        if (crnt.next.rank == node.rank) {
            HeapNode toBeNext = crnt.next.next;
            crnt.next.next = null;
            HeapNode joint = mergeTrees(crnt.next, node);
            crnt.next = joint;
            joint.next = toBeNext;
        }
        else {
            node.next = crnt.next;
            crnt.next = node;
        }
    }

    /**
     *
     * Delete the minimal item
     *
     */
    public void deleteMin() {
        if (!this.empty()) {
            if (this.size == 1){
                this.min = null;
                this.last = null;
                this.size = 0;
                return;
            }
            this.size -= 1;
            HeapNode nodeToDelete = this.min;
            HeapNode previusNode = nodeToDelete;
            HeapNode nodeInHeap = nodeToDelete.next; // iterate all tree for delete pointers to min
            this.min = nodeInHeap;
            while (nodeInHeap != nodeToDelete) {
                if (nodeInHeap.getKey() < this.min.getKey()) { //search for new min
                    this.min = nodeInHeap;
                }
                previusNode = nodeInHeap;
                nodeInHeap = nodeInHeap.next;
            }
            if (nodeToDelete == this.last){
                this.last = previusNode;
            }
            previusNode.next = nodeToDelete.next; // disconnect min's tree from heap
            HeapNode bigSon = nodeToDelete.child;
            HeapNode sonOfMin = nodeToDelete.child; // iterate min's sons and reconnect them to the heap
            do {
                sonOfMin.parent = null;
                HeapNode nextNode = sonOfMin.next;
                sonOfMin.next = null;
                mergeInto(sonOfMin);
                sonOfMin = nextNode;
            }
            while (sonOfMin != bigSon);
            sonOfMin.parent = null;
            sonOfMin.next = null;
            mergeInto(sonOfMin);
        }
    }

    /**
     *
     * Return the minimal HeapItem
     *
     */
    public HeapItem findMin()
    {
        if (this.min == null){
            return null;
        }
        return this.min.item; // should be replaced by student code
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
        item.key = item.key - diff;
        while(item.node.parent != null && item.key < item.node.parent.getKey()){
            item = swapFatherSon(item, item.node.parent.item);
        }
        if (this.min.getKey() > item.key){
            this.min = item.node;
        }
    }


    public HeapItem swapFatherSon(HeapItem son, HeapItem father){
        int sonKey = son.key;
        String sonInfo = son.info;
        int fatheKey = father.key;
        String fatherInfo = father.info;
        son.key = fatheKey;
        son.info = fatherInfo;
        father.key = sonKey;
        father.info = sonInfo;
        return father;
    }
    /**
     *
     * Delete the item from the heap.
     *
     */
    public void delete(HeapItem item){
        decreaseKey(item, item.key + 1);
        this.deleteMin();
        this.size = this.size -1;
    }

    /**
     *
     * Meld the heap with heap2
     *
     */
    public void meld(BinomialHeap heap2)
    {
        if (!heap2.empty()) {
            HeapNode crnt = heap2.last.next;
            int heap2Size = heap2.size;
            while (true) {
                HeapNode next = crnt.next;
                mergeInto(crnt);
                if (crnt == heap2.last)
                    break;
                crnt = next;
            }
            this.size = this.size + heap2Size;
        }
    }

    /**
     *
     * Return the number of elements in the heap
     *
     */
    public int size()
    {
        return this.size;
    }

    /**
     *
     * The method returns true if and only if the heap
     * is empty.
     *
     */
    public boolean empty()
    {
        return this.size == 0;
    }

    /**
     *
     * Return the number of trees in the heap.
     *
     */
    public int numTrees() {
        if (this.empty()){
            return 0;
        }
        int treesNum = 1;
        HeapNode last = this.last;
        HeapNode currentNode = last.next;
        while (currentNode.getKey() != last.getKey()){
            treesNum++;
            currentNode = currentNode.next;
        }
        return treesNum;
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
            this.next = this;
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

        private static int two_exp (int k) {
            if (k == 0) return 1;
            int b = two_exp(k / 2);
            return b * b * (k % 2 == 1 ? 2 : 1);
        }

        public int getSize () {
            return two_exp(rank);
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
