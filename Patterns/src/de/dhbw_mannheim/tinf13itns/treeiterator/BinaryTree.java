package de.dhbw_mannheim.tinf13itns.treeiterator;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Queue;
import java.util.Stack;

public class BinaryTree<Item extends Comparable<Item>> {
	private Node root;
	
	private class Node {
	Item item;
	Node l, r;
	}
	
	public Iterator<Item> preorderIterator() {
		return new Preorder();
	}

	private class Preorder implements Iterator<Item> {
		Stack<Node> stack = new Stack<Node>();
		Preorder() {
			if (root != null) stack.push(root);
		}
		public void remove() { throw new UnsupportedOperationException(); }
		public boolean hasNext() { return !stack.isEmpty(); }
		public Item next() {
			if (!hasNext()) throw new NoSuchElementException();
			Node x = stack.pop();
			Item item = x.item;
			if (x.r != null) stack.push(x.r);
			if (x.l != null) stack.push(x.l);
			return item;
		}
	}
	
	public Iterator<Item> inorderIterator() {
		return new Inorder();
	}

	private class Inorder implements Iterator<Item> {
		Stack<Node> stack = new Stack<Node>();
		Inorder() {
			Node node = root;
			if (root != null) {
				while (node.l != null) {
					stack.push(node);
					node = node.l;
				}
				stack.push(node);
			}
		}

		public void remove() { throw new UnsupportedOperationException(); }
		public boolean hasNext() { return !stack.isEmpty(); }
		public Item next() {
			if (!hasNext()) throw new NoSuchElementException();
			Node x = stack.pop();
			Item item = x.item;
			if (x.r != null) {
				Node node=x.r;
				while (node.l != null) {
					stack.push(node);
					node = node.l;
				}
				stack.push(node);
			}
			return item;
		}
	}
	
	public Iterator<Item> postorderIterator() {
		return new Postorder();
	}

	private class Postorder implements Iterator<Item> {
		Stack<Node> stack = new Stack<Node>();
		Postorder() {
			//buildStack(root);
			Node node = root;
			if (root != null) {
				while (node.l != null) {
					stack.push(node);
					node = node.l;
				}
				stack.push(node);
			}
		}
		
		private void buildStack(Node p) {
			if (p!=null) {
				stack.push(p);
				buildStack(p.r);
				buildStack(p.l);
			}
		}
		public void remove() { throw new UnsupportedOperationException(); }
		public boolean hasNext() { return !stack.isEmpty(); }
		public Item next() {
			if (!hasNext()) throw new NoSuchElementException();
			//return stack.pop().item;
			Node x = stack.pop();
			Item item = null;
			if (x.r == null) {
				item = x.item;
			} else {
				Node node=x.r;
				Node copy = new Node();
				copy.item = x.item;
				copy.l=null;
				copy.r=null;
				stack.push(copy);
				while (node.l != null) {
					stack.push(node);
					node = node.l;
				}
				item = node.item;
			}
			return item;
			
		}
	}
	
	public Iterator<Item> levelorderIterator() {
		return new Levelorder();
	}

	private class Levelorder implements Iterator<Item> {
		Queue<Node> queue = new LinkedList<Node>();
		
		Levelorder() {
			if (root != null) {
				queue.offer(root);
			}
		}
		
		public void remove() { throw new UnsupportedOperationException(); }
		public boolean hasNext() { return !queue.isEmpty(); }
		public Item next() {
			if (!hasNext()) throw new NoSuchElementException();
			Node x = queue.poll();
			Item item = x.item;
			if (x.l != null) {
				queue.offer(x.l);
			}
			if (x.r != null) {
				queue.offer(x.r);
			}
			return item;
		}
	}
	
	Node insert (Node p, Item item) {
		if (p==null) {
			p=new Node();
			p.item=item;
			p.l=null;
			p.r=null;
			if (root==null) {
				root=p;
			}
		} else if (p.item.compareTo(item)<0) {
			p.r = insert (p.r, item);
		} else if (p.item.compareTo(item)>0) {
			p.l = insert (p.l, item);
		}
		return p;
	}
	
	void insert (Item item) {
		insert(root, item);
	}
	
}