
class TreapNode{
    int key;
    int priority;
    TreapNode left;
    TreapNode right;

    // Write a constructor
    public TreapNode(int key){
        this.key = key;
        this.priority = (int) (Math.random() * 100);
        this.left = this.right = null;
    }

}

interface BST {

    void insert(int key);

    void delete(int key);

    TreapNode search(int key);

    void printTree();

    boolean isEmpty();
}

interface Heap {
    void insert(int key);

    void delete(int key);

    TreapNode search(int key);
}

// Updated Treap.java with getKthLargestElement and inorder printing

public class Treap implements BST, Heap {
    private TreapNode root;

    public Treap() {
        root = null;
    }

    private TreapNode rightRotate(TreapNode y) {
        TreapNode x = y.left;
        y.left = x.right;
        x.right = y;
        return x;
    }

    private TreapNode leftRotate(TreapNode x) {
        TreapNode y = x.right;
        x.right = y.left;
        y.left = x;
        return y;
    }

    private TreapNode insert(TreapNode node, int key) {
        if (node == null) {
            return new TreapNode(key);
        }
        if (key <= node.key) {
            node.left = insert(node.left, key);
            if (node.left != null && node.left.priority > node.priority) {
                node = rightRotate(node);
            }
        } else {
            node.right = insert(node.right, key);
            if (node.right != null && node.right.priority > node.priority) {
                node = leftRotate(node);
            }
        }
        return node;
    }

    @Override
    public void insert(int key) {
        root = insert(root, key);
    }

    private TreapNode delete(TreapNode node, int key) {
        if (node == null)
            return null;

        if (key < node.key) {
            node.left = delete(node.left, key);
        } else if (key > node.key) {
            node.right = delete(node.right, key);
        } else {
            if (node.left == null)
                return node.right;
            if (node.right == null)
                return node.left;

            if (node.left.priority < node.right.priority) {
                node = leftRotate(node);
                node.left = delete(node.left, key);
            } else {
                node = rightRotate(node);
                node.right = delete(node.right, key);
            }
        }
        return node;
    }

    @Override
    public void delete(int key) {
        root = delete(root, key);
    }

    private TreapNode search(TreapNode node, int key) {
        if (node == null || node.key == key)
            return node;
        return key < node.key ? search(node.left, key) : search(node.right, key);
    }

    @Override
    public TreapNode search(int key) {
        return search(root, key);
    }

    private void preorder(TreapNode node) {
        if (node == null)
            return;
        System.out.println("Key: " + node.key + ", Priority: " + node.priority);
        preorder(node.left);
        preorder(node.right);
    }

    @Override
    public void printTree() {
        System.out.println("Treap (Preorder Traversal):");
        preorder(root);
    }

    @Override
    public boolean isEmpty() {
        return root == null;
    }

    // Inorder traversal to print sorted keys
    public void printInorder() {
        System.out.println("Treap (Inorder Traversal):");
        inorder(root);
        System.out.println();
    }

    private void inorder(TreapNode node) {
        if (node == null)
            return;
        inorder(node.left);
        System.out.print(node.key + " ");
        inorder(node.right);
    }

    // Function to get k-th largest using reverse inorder traversal
    public int getKthLargestElement(int k) {
        int[] result = new int[1];
        int[] count = new int[1];
        getKthLargest(root, k, count, result);
        if (count[0] < k) {
            throw new IllegalArgumentException("Not enough elements in the tree");
        }
        return result[0];
    }

    private void getKthLargest(TreapNode node, int k, int[] count, int[] result) {
        if (node == null || count[0] >= k)
            return;

        getKthLargest(node.right, k, count, result);
        count[0]++;
        if (count[0] == k) {
            result[0] = node.key;
            return;
        }
        getKthLargest(node.left, k, count, result);
    }

    public static void main(String[] args) {
        Treap treap = new Treap();
        treap.insert(10);
        treap.insert(20);
        treap.insert(30);
        treap.insert(5);
        treap.insert(25);

        treap.printInorder(); // Should print keys in sorted order

        System.out.println("3rd largest: " + treap.getKthLargestElement(3)); // Should give correct output

        treap.delete(20);
        treap.printInorder();
    }
}