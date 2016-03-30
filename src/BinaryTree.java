import java.util.Iterator;
import java.util.NoSuchElementException;

public class BinaryTree<E> implements Iterable<E> {

	private E data;
	private BinaryTree<E> right = null;
	private BinaryTree<E> left = null;
	private BinaryTree<E> father = null;

	public BinaryTree(E data, BinaryTree<E> left, BinaryTree<E> right) {
		if (left != null && left.father != null || right != null
				&& right.father != null)
			throw new IllegalArgumentException();
		this.data = data;
		this.left = left;
		this.right = right;
		if (left != null)
			left.father = this;
		if (right != null)
			right.father = this;
	}
	
	public Iterator<E> iterator() {
		return prefixDFS();
	}

	public Iterator<E> prefixDFS() {
		return new Iterator<E>() {
			BinaryTree<E> next = BinaryTree.this;

			public boolean hasNext() {
				return next != null;
			}

			public E next() {
				if (next == null)
					throw new NoSuchElementException();
				E result = next.data;
				BinaryTree<E> current = next;
				if (current.left != null)
					next = current.left;
				else if (current.right != null)
					next = current.right;
				else {
					BinaryTree<E> f = current.father;
					while (f != null && (f.right == current || f.right == null)) {
						current = f;
						f = f.father;
					}
					next = (f == null ? null : f.right);
				}
				return result;
			}

			public void remove() {
				throw new UnsupportedOperationException();
			}
		};
	}

	public Iterator<E> postfixDFS() {
		return new Iterator<E>() {
			BinaryTree<E> next = first(BinaryTree.this);

			public boolean hasNext() {
				return next != null;
			}

			private BinaryTree<E> first(BinaryTree<E> root) {
				if (root.left != null)
					return first(root.left);
				else if (root.right != null)
					return first(root.right);
				else
					return root;
			}

			private BinaryTree<E> next(BinaryTree<E> current) {
				BinaryTree<E> f = current.father;
				if (f != null && f.left == current && f.right != null)
					return first(f.right);
				else
					return f;
			}

			public E next() {
				if (next == null)
					throw new NoSuchElementException();
				E result = next.data;
				next = next(next);
				return result;
			}

			public void remove() {
				throw new UnsupportedOperationException();
			}
		};
	}

	public static void main(String[] args) {
		 BinaryTree<String> h = new BinaryTree<String>("h", null, null);
		 BinaryTree<String> i = new BinaryTree<String>("i", null, null);
		 BinaryTree<String> j = new BinaryTree<String>("j", null, null);
		 BinaryTree<String> k = new BinaryTree<String>("k", null, null);
		 BinaryTree<String> d = new BinaryTree<String>("d", null, null);
		 BinaryTree<String> e = new BinaryTree<String>("e", h, null);
		 BinaryTree<String> f = new BinaryTree<String>("f", i, j);
		 BinaryTree<String> g = new BinaryTree<String>("g", null, k);
		 BinaryTree<String> b = new BinaryTree<String>("b", d, e);
		 BinaryTree<String> c = new BinaryTree<String>("c", f, g);
		 BinaryTree<String> a = new BinaryTree<String>("a", b, c);

//		for (Iterator<String> it = a.prefixDFS(); it.hasNext();)
//			System.out.print(it.next() + " ");
		 for (String s : a) 
			 System.out.print(s + " ");
		 System.out.println();
		for (Iterator<String> it = a.postfixDFS(); it.hasNext();)
			System.out.print(it.next() + " ");
		System.out.println();
	}
}
