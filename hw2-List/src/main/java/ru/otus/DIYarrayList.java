package ru.otus;

import java.util.*;

public class DIYarrayList<T> implements List<T> {

    private static final Object[] DEFAULTCAPACITY_EMPTY_ELEMENTDATA = {};

    Object[] elementData;
    private int size;

    public DIYarrayList() {
        this.elementData = DEFAULTCAPACITY_EMPTY_ELEMENTDATA;
    }

    public DIYarrayList(int size) {
        if (size > 0) {
            this.elementData = new Object[size];
        } else if (size == 0) {
            this.elementData = DEFAULTCAPACITY_EMPTY_ELEMENTDATA;
        } else {
            throw new IllegalArgumentException("Illegal Capacity: " +
                    size);
        }
    }

    @Override
    public boolean add(T t) {
        if (elementData.length > size()) {
            elementData[size++] = t;
            return true;
        } else {
            elementData = grow();
            elementData[size++] = t;
            return true;
        }
    }

    @Override
    public T set(int index, T element) {
        Objects.checkIndex(index, size);
        T oldValue = elementData(index);
        elementData[index] = element;
        return oldValue;
    }

    @Override
    public T get(int index) {
        Objects.checkIndex(index, size);
        return elementData(index);
    }

    @SuppressWarnings("unchecked")
    T elementData(int index) {
        return (T) elementData[index];
    }

    @Override
    @SuppressWarnings("unchecked")
    public void sort(Comparator<? super T> c) {
        Arrays.sort((T[]) elementData, 0, size, c);
    }

    @Override
    public ListIterator<T> listIterator() {
        return new ListItr((T[]) elementData);
    }

    private Object[] grow() {
        int newSize = (elementData.length * 3) / 2 + 1;
        return elementData = Arrays.copyOf(elementData, newSize);
    }

    @Override
    public int size() {
        return size;
    }

    private class Itr implements Iterator<T> {

        T[] arrayList;
        int currentSize;
        int cursor = 0;
        int lastRet = -1;

        public Itr(T[] newArray) {
            this.arrayList = newArray;
            this.currentSize = arrayList.length;
        }


        @Override
        public boolean hasNext() {
            return cursor < currentSize && arrayList[cursor] != null;
        }

        @Override
        public T next() {
            return arrayList[lastRet = cursor++];
        }
    }

    public class ListItr extends Itr implements ListIterator<T> {

        public ListItr(T[] newArray) {
            super(newArray);
        }

        @Override
        public void set(T t) {
            if (lastRet < 0) throw new IllegalStateException();

            try {
                DIYarrayList.this.set(lastRet, t);
            } catch (IndexOutOfBoundsException ex) {
                throw new ConcurrentModificationException();
            }
        }

        @Override
        public boolean hasPrevious() {
            throw new UnsupportedOperationException();
        }

        @Override
        public T previous() {
            throw new UnsupportedOperationException();
        }

        @Override
        public int nextIndex() {
            throw new UnsupportedOperationException();
        }

        @Override
        public int previousIndex() {
            throw new UnsupportedOperationException();
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }

        @Override
        public void add(T t) {
            throw new UnsupportedOperationException();
        }
    }

    @Override
    public boolean addAll(Collection<? extends T> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean isEmpty() {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean contains(Object o) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Iterator<T> iterator() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Object[] toArray() {
        throw new UnsupportedOperationException();
    }

    @Override
    public <T1> T1[] toArray(T1[] a) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean remove(Object o) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean addAll(int index, Collection<? extends T> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void clear() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void add(int index, T element) {
        throw new UnsupportedOperationException();
    }

    @Override
    public T remove(int index) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int indexOf(Object o) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int lastIndexOf(Object o) {
        throw new UnsupportedOperationException();
    }

    @Override
    public ListIterator<T> listIterator(int index) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<T> subList(int fromIndex, int toIndex) {
        throw new UnsupportedOperationException();
    }
}
