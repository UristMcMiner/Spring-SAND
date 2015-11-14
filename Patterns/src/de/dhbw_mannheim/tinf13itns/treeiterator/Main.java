package de.dhbw_mannheim.tinf13itns.treeiterator;

import java.util.Iterator;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		BinaryTree<Integer> tree = new BinaryTree<Integer>();
		tree.insert(5);
		tree.insert(2);
		tree.insert(4);
		tree.insert(1);
		tree.insert(0);
		tree.insert(3);
		tree.insert(8);
		tree.insert(7);
		tree.insert(6);
		tree.insert(9);
		System.out.println("In Order");
		Iterator<Integer> iterator = tree.inorderIterator();
		while (iterator.hasNext()) {
			System.out.println(iterator.next());
		}
		System.out.println("Pre Order");
		iterator = tree.preorderIterator();
		while (iterator.hasNext()) {
			System.out.println(iterator.next());
		}
		System.out.println("Post Order");
		iterator = tree.postorderIterator();
		while (iterator.hasNext()) {
			System.out.println(iterator.next());
		}
		System.out.println("Level Order");
		iterator = tree.levelorderIterator();
		while (iterator.hasNext()) {
			System.out.println(iterator.next());
		}
		
	}

}
