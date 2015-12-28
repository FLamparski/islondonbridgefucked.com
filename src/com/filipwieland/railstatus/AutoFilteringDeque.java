package com.filipwieland.railstatus;

import java.util.Deque;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.function.Predicate;

public class AutoFilteringDeque<E> extends LinkedList<E> implements Deque<E> {
	private static final long serialVersionUID = 1L;
	private Predicate<? super E> predicate;
	
	public AutoFilteringDeque(Predicate<? super E> predicate) {
		this.predicate = predicate;
	}

	public void setAutoFilter(Predicate<? super E> predicate) {
		this.predicate = predicate;
	}
	
	@Override
	public synchronized void addFirst(E e) {
		if (this.predicate == null) {
			throw new IllegalStateException("AutoFilteringQueue must have a predicate, please construct it with one or call setAutoFilter.");
		}
		super.addFirst(e);
		
		Iterator<E> it = this.descendingIterator();
		while(it.hasNext()) {
			E x = it.next();
			if (!predicate.test(x)) {
				it.remove();
			}
		}
	}
}
