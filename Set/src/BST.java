import java.util.*;

public class BST<T extends Comparable<T>> {

    private class Node {
        public T value;
        public Node left, right;

        public Node(T value) {
            this.value = value;
            this.left = null;
            this.right = null;
        }
    }

    private Node root = null;
    public int size;

    public BST() {
        this.root = null;
        setSize(0);
    }

    public BST(T value) {
        root = new Node(value);
        setSize(1);
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {

        this.size = size;
    }

    public boolean isEmpty(){
        return getSize() == 0;
    }

    public void add(T value) {
        if (root == null) {
            root = new Node(value);
            setSize(1);
            return;
        }
        add(root, value);
    }

    public Node getRoot() {
        return root;
    }

    public void setRoot(Node root) {
        this.root = root;
    }

    public void add2(T value) {
        root = add2(root, value);
    }

    /**
     * 非遞迴實現的add
     *
     * @param value
     */
    public void add3(T value) {
        if (root == null) {
            root = new Node(value);
            setSize(getSize() + 1);
            return;
        }

        Node temp = root;
        while (true) {
            if (value.compareTo(temp.value) < 0) {
                if (temp.left == null) {
                    temp.left = new Node(value);
                    setSize(getSize() + 1);
                    break;
                }
                temp = temp.left;
            } else if (value.compareTo(temp.value) > 0) {
                if (temp.right == null) {
                    temp.right = new Node(value);
                    setSize(getSize() + 1);
                    break;
                }
                temp = temp.right;
            }
        }
    }

    public void preOrder() {
        preOrder(getRoot());
    }

    public void preOrderNR() {
        preOrderNR(getRoot());
    }

    public void inOrder() {
        inOrder(getRoot());
    }

    public void postOrder() {
        postOrder(getRoot());
    }

    public void levelOrder() {
        levelOrder(getRoot());
    }

    public boolean contains(T value) {
        return contains(getRoot(), value);
    }

    public T min() {
        if (getSize() == 0) {
            throw new IllegalArgumentException("BST is empty!");
        }

        return min(getRoot()).value;
    }

    public T max() {
        if (getSize() == 0) {
            throw new IllegalArgumentException("BST is empty!");
        }

        return max(getRoot()).value;
    }

    public T removeMin() {
        T res = min();
        root = removeMin(getRoot());
        return res;
    }

    public T removeMax() {
        T res = max();
        root = removeMax(getRoot());
        return res;
    }

    public void remove(T target) {
        root = remove(getRoot(), target);
    }

    public T floor(T target) {
        if (target.compareTo(min()) < 0) {
            return null;
        }

        return floor(getRoot(), null, target);
    }

    public T ceil(T target) {
        if (target.compareTo(max()) > 0) {
            return null;
        }

        return ceil(getRoot(), null, target);
    }

    @Override
    public String toString() {
        StringBuilder res = new StringBuilder();
        generateBSTString(root, res, 0);
        return res.toString();
    }

    /**
     * 插入節點(用子節點來判斷)
     *
     * @param node
     * @param value
     */
    private void add(Node node, T value) {
        if (value.equals(node.value)) {
            return;
        }

        if (value.compareTo(node.value) < 0 && node.left == null) {
            node.left = new Node(value);
            return;
        } else if (value.compareTo(node.value) > 0 && node.right == null) {
            node.right = new Node(value);
            return;
        }

        add((value.compareTo(node.value) < 0) ? node.left : node.right, value);
    }

    /**
     * 遞迴優化版本，將邏輯統一並整理循環體本，使其更簡潔
     *
     * @param node
     * @param value
     * @return
     */
    private Node add2(Node node, T value) {
        /* 終止條件 */
        if (node == null) {
            setSize(getSize() + 1);
            return new Node(value);
        }

        if (value.equals(node.value)) {
            return node;
        }

        /* 遞迴本身(循環體) */
        if (value.compareTo(node.value) < 0) {
            node.left = add2(node.left, value);
        } else {
            node.right = add2(node.right, value);
        }

        /* 返回值 */
        return node;
    }

    /**
     * 前序走訪
     *
     * @param node
     */
    private void preOrder(Node node) {
        if (node == null) {
            return;
        }
        System.out.println(node.value);
        preOrder(node.left);
        preOrder(node.right);
    }

    /**
     * 中序走訪
     * 中序走訪可以將樹的數值從小到大排序
     *
     * @param node
     */
    private void inOrder(Node node) {
        if (node == null) {
            return;
        }

        inOrder(node.left);
        System.out.println(node.value);
        inOrder(node.right);
    }

    /**
     * 後序走訪
     *
     * @param node
     */
    private void postOrder(Node node) {
        if (node == null) {
            return;
        }

        postOrder(node.left);
        postOrder(node.right);
        System.out.println(node.value);
    }

    /**
     * 使用非遞迴方式編寫的前序走訪
     *
     * @param node
     */
    private void preOrderNR(Node node) {
        Stack<Node> stack = new Stack<>();
        stack.push(node);

        while (!stack.isEmpty()) {
            Node cur = stack.pop();
            System.out.println(cur.value);

            if (cur.right != null) {
                stack.push(cur.right);
            }

            if (cur.left != null) {
                stack.push(cur.left);
            }
        }
    }

    /**
     * 層序走訪
     *
     * @param node
     */
    private void levelOrder(Node node) {
        Deque<Node> deque = new ArrayDeque<>();
        deque.add(node);

        while (!deque.isEmpty()) {
            Node cur = deque.remove();
            System.out.println(cur.value);

            if (cur.left != null) {
                deque.add(cur.left);
            }

            if (cur.right != null) {
                deque.add(cur.right);
            }
        }
    }

    /**
     * 查詢指定元素
     *
     * @param node
     * @param value
     * @return
     */
    private boolean contains(Node node, T value) {
        if (node == null) {
            return false;
        } else if (value.equals(node.value)) {
            return true;
        }

        return (value.compareTo(node.value) > 0) ? contains(node.right, value) : contains(node.left, value);
    }

    /**
     * @param node
     * @return
     */
    private Node min(Node node) {
        if (node.left == null) {
            return node;
        }
        return min(node.left);
    }

    /**
     * @param node
     * @return
     */
    private Node max(Node node) {
        if (node.right == null) {
            return node;
        }
        return max(node.right);
    }

    /**
     * 移除最小值
     *
     * @param node
     * @return
     */
    private Node removeMin(Node node) {
        if (node.left == null) {
            Node ret = node.right;
            node.right = null;
            setSize(getSize() - 1);
            return ret;
        }

        node.left = removeMin(node.left);
        return node;
    }

    /**
     * 移除最大值
     *
     * @param node
     * @return
     */
    private Node removeMax(Node node) {
        if (node.right == null) {
            Node ret = node.left;
            node.left = null;
            setSize(getSize() - 1);
            return ret;
        }

        node.right = removeMax(node.right);
        return node;
    }

    /**
     * 刪除特定元素
     *
     * @param node
     * @return
     */
    private Node remove(Node node, T target) {
        if (node == null) {
            return null;
        }

        if (target.compareTo(node.value) > 0) {
            node.right = remove(node.right, target);
        } else if (target.compareTo(node.value) < 0) {
            node.left = remove(node.left, target);
        } else { // node.value == target
            if (node.left == null) { // 只有右子樹
                Node ret = node.right;
                node.right = null;
                setSize(getSize() - 1);
                return ret;
            } else if (node.right == null) { // 只有左子樹
                Node ret = node.left;
                node.left = null;
                setSize(getSize() - 1);
                return ret;
            }
            /* 同時擁有左柚子樹 */
            Node successor = min(node.right);
            node.right = removeMin(node.right);
            return successor;
        }

        return node;
    }


    private void generateBSTString(Node node, StringBuilder res, int depth) {
        if (node == null) {
            res.append(generateDepthString(depth) + "null\n");
            return;
        }

        res.append(generateDepthString(depth) + node.value + "\n");
        generateBSTString(node.left, res, depth + 1);
        generateBSTString(node.right, res, depth + 1);
    }

    private String generateDepthString(int depth) {
        StringBuilder res = new StringBuilder();
        for (int i = 0; i < depth; i++) {
            res.append("==");
        }
        return res.toString();
    }

    /**
     * 使用二分搜索樹實現floor(小於target的最大值)
     *
     * @param node
     * @param pre
     * @param target
     * @return
     */
    private T floor(Node node, Node pre, T target) {
        T floorValue;

        if (node == null) {
            return pre.value;
        }

        if (node.value.equals(target)) {
            return node.value;
        }

        if (node.value.compareTo(target) > 0) {
            floorValue = floor(node.left, node, target);
        } else {
            floorValue = floor(node.right, node, target);
        }

        if (target.compareTo(floorValue) < 0) {
            floorValue = node.value;
        }

        return floorValue;
    }

    /**
     * 使用二分搜索樹實現ceil(大於target的最小值)
     *
     * @param node
     * @param pre
     * @param target
     * @return
     */
    private T ceil(Node node, Node pre, T target) {
        T ceilValue;

        if (node == null) {
            return pre.value;
        }

        if (target.equals(node.value)) {
            return node.value;
        }

        if (node.value.compareTo(target) > 0) {
            ceilValue = ceil(node.left, node, target);
        } else {
            ceilValue = ceil(node.right, node, target);
        }

        if (target.compareTo(ceilValue) > 0) {
            ceilValue = node.value;
        }

        return ceilValue;
    }

    public static void main(String[] args) {
        BST<Integer> tree = new BST<Integer>();
        int[] arr = {40, 20, 60, 50, 80, 48, 58, 70, 90};

        for (int i = 0; i < arr.length; i++) {
            tree.add3(arr[i]);
        }

        System.out.println(tree.ceil(51));
    }

}
