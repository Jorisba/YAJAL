/*
      This file is part of YAJAL.

 YAJAL is free software: you can redistribute it and/or modify
 it under the terms of the GNU General Public License as published by
 the Free Software Foundation, either version 3 of the License, or
 (at your option) any later version.

 Foobar is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 GNU General Public License for more details.

 You should have received a copy of the GNU General Public License
 along with YAJAL.  If not, see <http://www.gnu.org/licenses/>.

 */

package datastructures.FibonacciStack;

import myMath.Constants;

import java.util.ArrayList;

/**
 * Created by Joris on 28/02/2017.
 */

public class FibonacciStack<K extends Comparable<? super K>> {

    private int n;
    private K minusInfinite;
    private FibonacciNode<K> min;
    private ArrayList<FibonacciNode<K>> roots;


    public FibonacciStack() {
        this.n = 0;
        this.min = null;
        this.roots = new ArrayList<>();
    }

    /**
     * The value minus infinite has to be defined by the user for the class K
     * in ordre to use the function removeNode.
     *
     * @param minusInfinite the arbitraty smallest possible value for the class K.
     */
    public FibonacciStack(K minusInfinite) {
        this();
        this.minusInfinite = minusInfinite;
    }

    /**
     * Insert un new node (not null) in the current stack in O(1).
     *
     * @param key : the key to insert in the current stack.
     */
    public void insert(K key) throws AssertionError {

        assert key != null : key;

        FibonacciNode<K> x = new FibonacciNode<>(key);

        insert(x);
    }

    public void insert(FibonacciNode<K> x) throws AssertionError {

        assert x != null : x;

        if (this.min == null) this.min = x;

        if (x.key.compareTo(this.min.key) < 0) this.min = x;

        this.roots.add(x);

        this.n++;
    }


    /**
     * @return the minimum node of the stack in O(1).
     */
    public FibonacciNode<K> getMin() {
        return this.min;
    }

    /**
     * Merge the current Fibonacci stack with the stack given in parameter.
     *
     * @param T : stack to merge with the current stack.
     */
    public void mergeWith(FibonacciStack<K> T) {

        assert T != null : T;

        this.roots.addAll(T.roots);

        if (this.min == null || (T.min != null && T.min.key.compareTo(this.min.key) < 0)) {
            this.min = T.min;
        }
        this.n += T.n;
    }

    /**
     * Extract (i.e. removes) the minimum (the first root) from the stack.
     * We make here the complexe operation of consolidation.
     *
     * @return the node that contains the minimum key of the stack.
     */
    public FibonacciNode<K> extractMin() {
        FibonacciNode<K> z = this.min;

        if (z != null) {
            for (FibonacciNode<K> x : z.c) {
                roots.add(x);
                x.p = null;
            }

            this.roots.remove(z);    //Remove z = this.min from the roots.

            if (this.roots.size() == 0) { //If z was alone in the stack
                this.min = null;
            } else {
                this.min = this.roots.get(0);        //The new min is z's right neighbour.
                this.consolidate();
            }
            this.n--;
        }
        return z;
    }

    /**
     * Consolidate the stack such as each node of the root have a different degree in two
     * operation.
     * 1) Finds in the root list two roots (x,y) of the same degree.
     * 2) Links y to x such as it's suppress y from the roots and makes y a child of x and
     * x the parent of y.
     */
    private void consolidate() {

        int d, logPhiN;
        logPhiN = ((int) Math.floor(Math.log(this.n) / Constants.LN_PHI));

        FibonacciNode<K> x, y;
        FibonacciNode<K>[] A;
        A = new FibonacciNode[logPhiN]; //log in base phi of the node number.

        for (int i = 0; i < logPhiN; i++) A[i] = null;


        for (int i = 0; i < this.roots.size(); i++) {

            x = this.roots.get(i);
            d = x.degree;

            while (A[d] != null) {
                y = A[d];

                if (x.key.compareTo(y.key) > 0) { //if x.key > y.key
                    FibonacciNode<K> temp = x;
                    x = y;
                    y = temp;
                }
                link(x, y);

                A[d] = null;
                d++;
            }
            A[d] = x;
        }

        this.min = null;

        for (int i = 0; i < logPhiN; i++) {
            if (A[i] != null) {
                if (this.min == null) {

                    this.roots = new ArrayList<>();
                    this.roots.add(A[i]);
                    this.min = A[i];

                } else {
                    this.roots.add(A[i]);
                    // if A[i].key < min.key
                    if (A[i].key.compareTo(this.min.key) < 0) this.min = A[i];

                }
            }
        }
    }

    /**
     * Links a root y to x by removing y from the roots and attaching y to x.
     *
     * @param x : node tobe linked at y.
     * @param y : node to link to x.
     */
    private void link(FibonacciNode<K> x, FibonacciNode<K> y) {

        this.roots.remove(y);   //Remove y from the roots to be add the the children of x.

        x.c.add(y);             //Make y the child of x.
        x.degree++;

        y.p = x;                //Make x a parent of y.
        y.mark = false;
    }


    /**
     * Decreases the given node key by the given key given.
     *
     * @param x : node which the kay has to be decreased.
     * @param k : New key value.
     * @throws AssertionError
     */
    public void decreaseKey(FibonacciNode<K> x, K k) throws AssertionError {
        assert x != null : x;

        if (k.compareTo(x.key) > 0) return;

        x.key = k;

        FibonacciNode<K> y = x.p;

        if (y != null && x.key.compareTo(y.key) < 0) {
            this.cut(x, y);
            this.cascadeCut(y);
        }

        if (x.key.compareTo(this.min.key) < 0) this.min = x;
    }

    private void cut(FibonacciNode<K> x, FibonacciNode<K> y) {

        y.c.remove(x);
        this.roots.add(x);

        x.p = null;
        x.mark = false;
    }


    private void cascadeCut(FibonacciNode<K> y) {

        FibonacciNode<K> z = y.p;

        if (z != null) {
            if (z.mark == false) {
                z.mark = true;
            } else {
                cut(y, z);
                cascadeCut(z);
            }
        }
    }

    /**
     * Removes the given node from the stack.
     *
     * @param x : node to remove.
     * @throws AssertionError
     */
    public void removeNode(FibonacciNode<K> x) throws AssertionError {
        decreaseKey(x, this.minusInfinite);
        extractMin();
    }

    /**
     * @return current minusInfinite attribute stored in the stack.
     */
    public K getMinusInfinite() {
        return this.minusInfinite;
    }

    /**
     * Set the minus infinite attribute used in the removeNode function.
     *
     * @param minusInfinite : new value to set.
     * @throws AssertionError
     */
    public void setMinusInfinite(K minusInfinite) throws AssertionError {
        assert minusInfinite != null : minusInfinite;

        this.minusInfinite = minusInfinite;
    }
}

