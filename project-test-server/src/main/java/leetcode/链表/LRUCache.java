package leetcode.链表;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: wh
 * @Date: 2024/02/18/16:43
 * @Description: leetcode 150题， 146
 */
public class LRUCache {
    class Node{
        int key;
        int value;
        Node prev;
        Node next;
        Node(int key, int value){
            this.key = key;
            this.value = value;
        }
    }
    private Map<Integer, Node> map = new HashMap<>();
    private Node head;
    private Node tail;
    private int capacity;
    private int size;
    public LRUCache(int capacity){
        this.capacity = capacity;
        head = new Node(0,0);
        tail = new Node(0,0);
        head.next = tail;
        tail.prev = head;
    }

    public int get(int key) {
        if(map.containsKey(key)){
            Node node = map.get(key);
            removeToHead(node);
            return node.value;
        }else{
            return -1;
        }
    }

    private void removeToHead(Node node) {
        removeNode(node);
        addToHead(node);
    }
    private void removeNode(Node node) {
        node.prev.next = node.next;
        node.next.prev = node.prev;
    }
    private void addToHead(Node node) {
       node.prev = head;
       node.next = head.next;
       head.next.prev = node;
       head.next = node;
    }

    public void put(int key, int value) {
        Node node = map.get(key);
        if(node != null){
            node.value = value;
            removeToHead(node);
        }else{
            if(size == capacity){
                Node tailNode = tail.prev;
                map.remove(tailNode.key);
                removeNode(tailNode);
                size--;
            }
            Node newNode = new Node(key, value);
            addToHead(newNode);
            map.put(key, newNode);
            size++;
        }
    }

    public static void main(String[] args) {
        LRUCache lRUCache = new LRUCache(2);
        lRUCache.put(1, 1); // 缓存是 {1=1}
        lRUCache.put(2, 2); // 缓存是 {1=1, 2=2}
        System.out.println(lRUCache.get(1));    // 返回 1,并将其置顶
        lRUCache.put(3, 3); // 该操作会使得关键字 2 作废，缓存是 {1=1, 3=3}
        System.out.println(lRUCache.get(2));    // 返回 -1 (未找到)
        lRUCache.put(4, 4); // 该操作会使得关键字 1 作废，缓存是 {4=4, 3=3}
        System.out.println(lRUCache.get(1));    // 返回 -1 (未找到)
        System.out.println(lRUCache.get(3));    // 返回 3
        System.out.println(lRUCache.get(4));    // 返回 4
    }


}
