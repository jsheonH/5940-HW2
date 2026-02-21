public class MyTree<T extends Comparable<T>> {

    // root node of this BST
    private MyNode<T> root;

    // keeps track of how many nodes are in the tree
    private int size;

    // constructor: start with an empty tree
    public MyTree() {
        root = null;     // no root initially
        size = 0;        // size starts at 0
    }

    // insert an item into the BST
    public MyNode<T> insert(T item) {

        // BST does not allow null values
        if (item == null) {
            throw new IllegalArgumentException("Item cannot be null.");
        }

        // if tree is empty, new node becomes the root
        if (root == null) {
            root = new MyNode<>(item);  // create root node
            size++;                    // increase size
            return root;               // return inserted node
        }

        // start searching from root
        MyNode<T> current = root;

        // parent will track where to attach new node
        MyNode<T> parent = null;

        // walk down until we find a null position
        while (current != null) {

            parent = current;  // remember parent before moving

            // compare using Comparable
            int cmp = item.compareTo(current.getItem());

            if (cmp == 0) {
                // duplicate found, do not insert
                return current;
            } else if (cmp < 0) {
                // go left if smaller
                current = current.getLeft();
            } else {
                // go right if larger
                current = current.getRight();
            }
        }

        // create new node after loop ends
        MyNode<T> newNode = new MyNode<>(item);

        // set parent pointer
        newNode.setParent(parent);

        // attach to correct side of parent
        if (item.compareTo(parent.getItem()) < 0) {
            parent.setLeft(newNode);
        } else {
            parent.setRight(newNode);
        }

        size++;  // increase tree size

        return newNode;  // return inserted node
    }

    // search for a value in BST
    public MyNode<T> contains(T item) {

        // null check
        if (item == null) {
            throw new IllegalArgumentException("Item cannot be null.");
        }

        // start from root
        MyNode<T> current = root;

        // traverse until found or reach null
        while (current != null) {

            int cmp = item.compareTo(current.getItem());

            if (cmp == 0) {
                // found the node
                return current;
            } else if (cmp < 0) {
                // go left if smaller
                current = current.getLeft();
            } else {
                // go right if larger
                current = current.getRight();
            }
        }

        // not found
        return null;
    }

    // remove an item from BST
    public boolean remove(T item) {

        // null check
        if (item == null) {
            throw new IllegalArgumentException("Item cannot be null.");
        }

        // find node (single traversal)
        MyNode<T> target = contains(item);

        // if not found, nothing to remove
        if (target == null) {
            return false;
        }

        // case 1 & 2: node has 0 or 1 child
        if (target.getLeft() == null || target.getRight() == null) {

            // determine which child exists (if any)
            MyNode<T> child = (target.getLeft() != null)
                    ? target.getLeft()
                    : target.getRight();

            // if removing root node
            if (target.getParent() == null) {

                root = child;  // child becomes new root

                if (child != null) {
                    child.setParent(null);  // root has no parent
                }
            } else {
                // reconnect parent to child

                if (target == target.getParent().getLeft()) {
                    target.getParent().setLeft(child);
                } else {
                    target.getParent().setRight(child);
                }

                if (child != null) {
                    child.setParent(target.getParent());
                }
            }
        } else {
            // case 3: node has two children

            // find in-order successor (smallest in right subtree)
            MyNode<T> successor = findMin(target.getRight());

            // successor can only have right child (never left)
            MyNode<T> successorChild = successor.getRight();

            // if successor is not direct right child
            if (successor != target.getRight()) {

                // detach successor from original position
                if (successor == successor.getParent().getLeft()) {
                    successor.getParent().setLeft(successorChild);
                } else {
                    successor.getParent().setRight(successorChild);
                }

                if (successorChild != null) {
                    successorChild.setParent(successor.getParent());
                }

                // successor takes target's right subtree
                successor.setRight(target.getRight());
                target.getRight().setParent(successor);
            }

            // move successor into target's position
            successor.setParent(target.getParent());

            if (target.getParent() == null) {
                root = successor;  // target was root
            } else if (target == target.getParent().getLeft()) {
                target.getParent().setLeft(successor);
            } else {
                target.getParent().setRight(successor);
            }

            // successor always takes target's left subtree
            successor.setLeft(target.getLeft());
            target.getLeft().setParent(successor);

            // if successor was direct right child
            if (successor == target.getRight()) {

                successor.setRight(successorChild);

                if (successorChild != null) {
                    successorChild.setParent(successor);
                }
            }
        }

        size--;  // decrease size after removal

        return true;
    }

    // return sorted string using in-order traversal
    @Override
    public String toString() {

        if (root == null) {
            return "";  // empty tree
        }

        StringBuilder sb = new StringBuilder();  // build result

        inOrderTraversal(root, sb);  // recursive traversal

        // remove last ", "
        if (sb.length() >= 2) {
            sb.setLength(sb.length() - 2);
        }

        return sb.toString();
    }

    // return root node
    public MyNode<T> getRoot() {
        return root;
    }

    // return size of tree
    public int getSize() {
        return size;
    }

    // -------------------------
    // Optional Helper Methods
    // -------------------------

    // find smallest node in subtree
    private MyNode<T> findMin(MyNode<T> node) {

        // keep going left until no more left child
        while (node.getLeft() != null) {
            node = node.getLeft();
        }

        return node;
    }

    // in-order traversal helper
    private void inOrderTraversal(MyNode<T> node, StringBuilder sb) {

        if (node == null) {
            return;  // base case
        }

        inOrderTraversal(node.getLeft(), sb);  // visit left subtree

        sb.append(node.getItem()).append(", ");  // visit current node

        inOrderTraversal(node.getRight(), sb);  // visit right subtree
    }
}