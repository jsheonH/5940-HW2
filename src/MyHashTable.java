public class MyHashTable<T extends Comparable<T>> {

    // array of buckets (each bucket is a BST)
    private MyTree<T>[] body;

    // total number of buckets
    private int capacity;

    // number of elements currently stored
    private int size;

    // default constructor
    public MyHashTable() {

        // default bucket size (prime number helps distribution)
        this.capacity = 701;

        // initially no elements
        this.size = 0;

        // create array of BST buckets
        this.body = (MyTree<T>[]) new MyTree[capacity];
    }

    // constructor with custom capacity
    public MyHashTable(int capacity) {

        // store given capacity
        this.capacity = capacity;

        // start empty
        this.size = 0;

        // allocate bucket array
        this.body = (MyTree<T>[]) new MyTree[capacity];
    }

    // insert an item into hash table
    public MyNode<T> add(T item) {

        // null values are not allowed
        if (item == null) {
            throw new IllegalArgumentException("item is null");
        }

        // compute bucket index using hashCode
        int bucketIdx = getIndex(item);

        // if bucket is empty, create a new BST
        if (body[bucketIdx] == null) {
            body[bucketIdx] = new MyTree<>();
        }

        // check size of that BST before inserting
        int beforeSize = body[bucketIdx].getSize();

        // insert into the BST
        MyNode<T> node = body[bucketIdx].insert(item);

        // check size again to see if insertion really happened
        int afterSize = body[bucketIdx].getSize();

        // only increase global size if a new node was added
        if (afterSize > beforeSize) {
            size++;
        }

        // return the node where item is stored
        return node;
    }

    // check if item exists in hash table
    public MyNode<T> contains(T item) {

        // null not allowed
        if (item == null) {
            throw new IllegalArgumentException("item is null");
        }

        // find correct bucket
        int bucketIdx = getIndex(item);

        // if bucket is empty, item cannot exist
        if (body[bucketIdx] == null) {
            return null;
        }

        // delegate search to the BST
        return body[bucketIdx].contains(item);
    }

    // remove an item from hash table
    public boolean remove(T item) {

        // null not allowed
        if (item == null) {
            throw new IllegalArgumentException("item is null");
        }

        // compute bucket index
        int bucketIdx = getIndex(item);

        // if bucket empty, nothing to remove
        if (body[bucketIdx] == null) {
            return false;
        }

        // attempt removal in BST
        boolean removed = body[bucketIdx].remove(item);

        // if successfully removed, decrease total size
        if (removed) {
            size--;
        }

        return removed;
    }

    // check if hash table is empty
    public boolean isEmpty() {
        return size == 0;
    }

    // return total number of elements
    public int size() {
        return size;
    }

    // remove all elements
    public void clear() {

        // create new empty bucket array
        body = (MyTree<T>[]) new MyTree[capacity];

        // reset element count
        size = 0;
    }

    // -------------------------
    // Optional Helper Methods
    // -------------------------

    // compute bucket index using hashCode
    private int getIndex(T item) {

        // raw hash value
        int hash = item.hashCode();

        // ensure non-negative and within bounds
        return (hash % capacity + capacity) % capacity;
    }

    public static void main(String[] args) {

        MyHashTable<String> table = new MyHashTable<>();

        System.out.println("===== BASIC INSERT TEST =====");
        table.add("apple");
        table.add("banana");
        table.add("cherry");
        table.add("date");
        table.add("elderberry");
        System.out.println("Size after 5 inserts: " + table.size());  // expect 5


        System.out.println("\n===== DUPLICATE INSERT TEST =====");
        table.add("apple");  // duplicate
        table.add("banana"); // duplicate
        System.out.println("Size after duplicates: " + table.size());  // still 5


        System.out.println("\n===== CONTAINS TEST =====");
        System.out.println("Contains 'banana': " + table.contains("banana")); // not null
        System.out.println("Contains 'fig': " + table.contains("fig"));       // null


        System.out.println("\n===== REMOVE EXISTING TEST =====");
        System.out.println("Remove 'cherry': " + table.remove("cherry")); // true
        System.out.println("Size after removing cherry: " + table.size()); // 4


        System.out.println("\n===== REMOVE NON-EXISTENT TEST =====");
        System.out.println("Remove 'cherry' again: " + table.remove("cherry")); // false
        System.out.println("Remove 'fig': " + table.remove("fig")); // false
        System.out.println("Size should remain 4: " + table.size());


        System.out.println("\n===== REMOVE UNTIL EMPTY TEST =====");
        table.remove("apple");
        table.remove("banana");
        table.remove("date");
        table.remove("elderberry");
        System.out.println("Size after removing all: " + table.size()); // 0
        System.out.println("Is empty: " + table.isEmpty()); // true


        System.out.println("\n===== REMOVE FROM EMPTY TEST =====");
        System.out.println("Remove 'apple' from empty: " + table.remove("apple")); // false


        System.out.println("\n===== CONTAINS ON EMPTY TEST =====");
        System.out.println("Contains 'banana': " + table.contains("banana")); // null


        System.out.println("\n===== CLEAR TEST =====");
        table.add("one");
        table.add("two");
        System.out.println("Size before clear: " + table.size()); // 2
        table.clear();
        System.out.println("Size after clear: " + table.size()); // 0
        System.out.println("Is empty after clear: " + table.isEmpty()); // true


        System.out.println("\n===== SINGLE ELEMENT REMOVE TEST =====");
        table.add("solo");
        System.out.println("Size: " + table.size()); // 1
        System.out.println("Remove solo: " + table.remove("solo")); // true
        System.out.println("Size after removing solo: " + table.size()); // 0


        System.out.println("\n===== NULL SAFETY TEST (should throw) =====");
        try {
            table.add(null);
        } catch (IllegalArgumentException e) {
            System.out.println("Caught expected exception for add(null)");
        }

        try {
            table.contains(null);
        } catch (IllegalArgumentException e) {
            System.out.println("Caught expected exception for contains(null)");
        }

        try {
            table.remove(null);
        } catch (IllegalArgumentException e) {
            System.out.println("Caught expected exception for remove(null)");
        }

        System.out.println("\n===== ALL EDGE CASE TESTS COMPLETED =====");
    }
}