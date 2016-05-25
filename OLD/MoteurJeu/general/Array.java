/*
 * 
 * 
 * 
 */
package MoteurJeu.general;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

/**
 * Array.java
 *
 * @param <T>
 */
public class Array<T> extends ArrayList<T> {

	public Array() {
		super();
	}

	public Array(int capacity) {
		super(capacity);
	}

	public Array(int capacity, Class arrayType) {
		super(Arrays.asList((T[]) java.lang.reflect.Array.newInstance(arrayType, capacity)));
	}

	public Array(Class arrayType) {
		this(16, arrayType);
	}

	public Array(Array<? extends T> array) {
		this();
		addAll(array);
	}

	public Array(T[] array) {
		this();
		addAll(array);
	}

	public final void addAll(T... it) {
		addAll(Arrays.asList(it));
	}

	public void swap(int first, int second) {
		if (first >= size()) {
			throw new IndexOutOfBoundsException("first can't be >= size: " + first + " >= " + size());
		}
		if (second >= size()) {
			throw new IndexOutOfBoundsException("second can't be >= size: " + second + " >= " + size());
		}
		Collections.swap(this, first, second);
	}

	public T first() {
		return get(0);
	}

	public T last() {
		return get(size() - 1);
	}

	public T pop() {
		return remove(size() - 1);
	}

	public boolean removeValue(T value, boolean identity) {
		if (identity || value == null) {
			for (int i = 0, n = size(); i < n; i++) {
				if (get(i) == value) {
					remove(i);
					return true;
				}
			}
		} else {
			for (int i = 0, n = size(); i < n; i++) {
				if (value.equals(get(i))) {
					remove(i);
					return true;
				}
			}
		}
		return false;
	}
}
