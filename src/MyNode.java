/**
 * A single node used in the Binary Search Tree.
 * Each node stores:
 *  - the actual value
 *  - references to left and right children
 *  - a reference to its parent
 */
public class MyNode<T> {

    // value stored inside this node
    private T item;

    // left child reference
    private MyNode<T> left;

    // right child reference
    private MyNode<T> right;

    // parent reference (helps during removal)
    private MyNode<T> parent;

    // create a new node with given value
    public MyNode(T item) {

        // store the value
        this.item = item;

        // initially this node has no children
        this.left = null;
        this.right = null;

        // and no parent (parent will be set when attached to tree)
        this.parent = null;
    }

    // return the value stored in this node
    public T getItem() {
        return item;
    }

    // return left child
    public MyNode<T> getLeft() {
        return left;
    }

    // return right child
    public MyNode<T> getRight() {
        return right;
    }

    // return parent node
    public MyNode<T> getParent() {
        return parent;
    }

    // update left child reference
    public void setLeft(MyNode<T> left) {
        this.left = left;
    }

    // update right child reference
    public void setRight(MyNode<T> right) {
        this.right = right;
    }

    // update parent reference
    public void setParent(MyNode<T> parent) {
        this.parent = parent;
    }
}