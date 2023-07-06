package binomialheaptester.yourcode;

/**
 * BinomialHeap
 * <p>
 * An implementation of binomial heap over non-negative integers.
 * Based on exercise from previous semester.
 */
public class BinomialHeap {
    public int size;
    public HeapNode last;
    public HeapNode min;
    private int numTrees;

    public BinomialHeap() {
        size = 0;
        last = null;
        min = null;
        numTrees = 0;
    }

    /**
     * pre: key > 0
     * <p>
     * Insert (key,info) into the heap and return the newly generated HeapItem.
     */
    public HeapItem insert(int key, String info) {
        HeapNode tree = new HeapNode(null, key, info);
        mergeInto(tree);
        size++;
        return tree.item;
    }

    private static HeapNode mergeTrees(HeapNode tree1, HeapNode tree2) {
        if (tree1.getKey() > tree2.getKey())
            return mergeTrees(tree2, tree1);
        HeapNode child = tree1.child;
        if (child == null) {
            tree2.next = tree2;
        } else {
            tree2.next = child.next;
            child.next = tree2;
        }
        tree1.child = tree2;
        tree2.parent = tree1;
        tree1.rank++;
        tree1.parent = null;
        tree1.next = null;
        return tree1;
    }

    /**
     * Add a binomial tree to the heap
     * Does not handle size!!
     * The tree must be with null parent and null next
     *
     * @param treeToAdd
     */
    private void mergeInto(HeapNode treeToAdd) {
        if (numTrees == 0) {
            min = treeToAdd;
            last = treeToAdd;
            last.next = last;
            numTrees = 1;
            return;
        }
        if (last.rank < treeToAdd.rank) {
            treeToAdd.next = last.next;
            last.next = treeToAdd;
            last = treeToAdd;
            numTrees++;
            if (treeToAdd.getKey() < min.getKey())
                min = treeToAdd;
        } else {
            HeapNode crnt = last;
            while (crnt.next.rank < treeToAdd.rank)
                crnt = crnt.next;
            if (crnt.next.rank > treeToAdd.rank) {
                treeToAdd.next = crnt.next;
                crnt.next = treeToAdd;
                numTrees++;
                if (treeToAdd.getKey() < min.getKey())
                    min = treeToAdd;
            } else { //They are equal - merge trees
                if (numTrees == 1) {
                    HeapNode merged = mergeTrees(crnt, treeToAdd);
                    last = merged;
                    last.next = last;
                    min = merged;
                } else {
                    HeapNode treeToMerge = crnt.next;
                    if (treeToMerge == last) {
                        last = crnt;
                    }
                    if (treeToMerge == min)
                        min = crnt;
                    crnt.next = crnt.next.next;
                    numTrees--;
                    mergeInto(mergeTrees(treeToMerge, treeToAdd));
                }
            }
        }
    }

    /**
     * Delete the minimal item
     */
    public void deleteMin() {
        if (min == null) return;
        if (size == 1) {
            min = null;
            last = null;
            size = 0;
            numTrees = 0;
            return;
        }
        HeapNode toDelete = min;
        if (numTrees > 1) {
            HeapNode nodeBefore = toDelete.next;
            min = nodeBefore;
            if (numTrees > 2)
                while (nodeBefore.next != toDelete) {
                    if (nodeBefore.getKey() < min.getKey())
                        min = nodeBefore;
                    nodeBefore = nodeBefore.next;
                }
            if (nodeBefore.getKey() < min.getKey())
                min = nodeBefore;
            nodeBefore.next = toDelete.next;
            if (last == toDelete)
                last = nodeBefore;


        }
        else {
            last = null;
            min = null;
        }
        numTrees--;
        HeapNode lastChild = toDelete.child;
        if (lastChild != null) {
            HeapNode crnt = lastChild.next;
            lastChild.next = null;
            while (crnt != null) {
                HeapNode next = crnt.next;
                crnt.parent = null;
                crnt.next = null;
                mergeInto(crnt);
                crnt = next;
            }
        }
        size--;
    }

    /**
     * Return the minimal HeapItem
     */
    public HeapItem findMin() {
        if (min == null) return null;
        return min.item;
    }

    /**
     * pre: 0<diff<item.key
     * <p>
     * Decrease the key of item by diff and fix the heap.
     */
    public void decreaseKey(HeapItem item, int diff) {
        HeapNode crntNode = item.node;
        int key = crntNode.getKey() - diff;
        crntNode.setKey(key);
        while (crntNode.parent != null && crntNode.parent.getKey() > key) {
            crntNode = swapWithParent(crntNode);
        }
        if (min.getKey() > key)
            min = crntNode;
    }

    /**
     * Swap the values of the current node with those of its parent
     * parent should be not null!
     *
     * @param crnt
     * @return the (new) parent
     */
    public HeapNode swapWithParent(HeapNode crnt) {
        int key = crnt.getKey();
        String info = crnt.getInfo();
        HeapNode parent = crnt.parent;
        crnt.setInfo(parent.getInfo());
        crnt.setKey(parent.getKey());
        parent.setKey(key);
        parent.setInfo(info);
        return parent;
    }

    /**
     * Delete the item from the heap.
     */
    public void delete(HeapItem item) {
        decreaseKey(item, item.key + 1);
        deleteMin();
    }

    /**
     * Meld the heap with heap2
     */
    public void meld(BinomialHeap heap2) {
        if (heap2.empty()) return;
        HeapNode crnt = heap2.last;
        HeapNode next = crnt.next;
        crnt.next = null;
        crnt = next;
        while (crnt != null) {
            next = crnt.next;
            crnt.next = null;
            mergeInto(crnt);
            crnt = next;
        }
        size += heap2.size;
    }

    /**
     * Return the number of elements in the heap
     */
    public int size() {
        return size; // should be replaced by student code
    }

    /**
     * The method returns true if and only if the heap
     * is empty.
     */
    public boolean empty() {
        return numTrees == 0; // should be replaced by student code
    }

    /**
     * Return the number of trees in the heap.
     */
    public int numTrees() {
        return numTrees; // should be replaced by student code
    }

    /**
     * Class implementing a node in a Binomial Heap.
     */
    public class HeapNode {
        public HeapNode() {
        }

        public HeapNode(HeapNode parent, int key, String info) {
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


        public int getKey() {
            return item.key;
        }

        public String getInfo() {
            return item.info;
        }

        public void setKey(int key) {
            item.key = key;
        }

        public void setInfo(String info) {
            item.info = info;
        }
    }

    /**
     * Class implementing an item in a Binomial Heap.
     */
    public class HeapItem {

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
