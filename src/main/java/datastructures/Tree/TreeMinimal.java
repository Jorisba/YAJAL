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
package datastructures.Tree;

import org.testng.internal.collections.Pair;

/**
 * Created by Joris on 28/02/2017.
 */

public class TreeMinimal<K extends Comparable<? super K>> {
    /**
     * B-tree minimal degree, all nodes excepts the root have to contain t - 1 keys.
     */
    private int minDegree;
    private int maxKeys;

    /**
     * Tree root node.
     */
    private TreeNode<K> root;

    public TreeMinimal(int minDegree) {
        assert minDegree > 1 : minDegree;

        this.minDegree = minDegree;
        this.maxKeys = 2 * this.minDegree - 1;
        this.root = new TreeNode<>(true, this.minDegree);
    }

    /**
     * Split the full node, child of x of index *index* named y in the code, puts the center
     * key in x (this is why x has to be not-full) and attaches the 2 news nodes to x.
     * In the process y will be reused as the new left child of x.
     *
     * @param x     is a not-full node.
     * @param index is the index of a full node, child of rootNode.
     */
    private void splitChild(TreeNode<K> x, int index) {

        if (x.isFull()) return;             // Ensures that x is NOT full.

        TreeNode<K> y = x.children.get(index);

        if (!y.isFull()) return;            // Ensures that y IS full.

        int t = this.minDegree;             // More concise.

        TreeNode<K> z = new TreeNode<>(t);
        z.setLeaf(y.isLeaf());              //Get the same leaf state to z.

        for (int i = 0; i < t - 1; i++) {  //Put the right keys of y in the left part of z.
            z.keys.add(i, y.keys.get(t));   //Cause we remove the key at the position t, the index
            y.keys.remove(t);               // update and we do not need to increase it.
        }

        if (!y.isLeaf()) {                   //If y is not a leaf we need to deal with it children.

            for (int i = 0; i < t; i++) {    //Same thing than for the keys.
                z.children.add(i, y.children.get(t)); //Index updated no need to increase it.
                y.children.remove(t);
            }
        }

        int xn = x.n();                     //Get the number of keys in x before adding new keys.

        for (int i = xn + 1; i > index; i--) { //Move x children to the right to attach the newborn z.
            x.children.add(i, x.children.get(i - 1));
            x.children.remove(i - 1);
        }

        x.children.add(index + 1, z);             //Add z in the space we just created.

        int keysNumber = x.keysNumber();
        for (int i = keysNumber; i > index; i--) {//Same thing than for the children.
            x.keys.add(i, x.keys.get(i - 1));
            x.keys.remove(i - 1);
        }

        x.keys.add(index, y.keys.get(t - 1)); //Put the *middle* key of y in x...
        y.keys.remove(t - 1);                 //...and remove it from y.
    }


    /**
     * Insert a key k in the tree. Always begin with the root.
     *
     * @param k the key of type K to insert in the tree.
     */
    public void insert(K k) {
        assert k != null : k;

        if (this.root.n() == this.maxKeys) {

            TreeNode<K> s = new TreeNode<>(false, this.minDegree);
            s.children.add(this.root);
            this.root = s;
            splitChild(s, 0);
            insertIncomplete(s, k);

        } else insertIncomplete(this.root, k);

    }


    /**
     * Insert the key k in the node x. If x is not a, finds the child to continue with while it's
     * not a leaf. During the process, if it encounter a full node, it will slit it.
     *
     * @param x the node to start with.
     * @param k the k to insert in the tree.
     */
    private void insertIncomplete(TreeNode<K> x, K k) {

        int i;          //Get the max index of the children list of x.

        while (!x.isLeaf()) {

            i = x.n();
            //We will not insert k until we found a leaf.
            //Find the index of the ordered child to continue to look at.
            while ((i > 0) && (k.compareTo(x.keys.get(i - 1)) < 0)) i--;

            if (x.children.get(i).n() == this.maxKeys) {  //If the child is full...

                splitChild(x, i);                       //...we split it.

                if (k.compareTo(x.keys.get(i)) > 0) i++;

            }
            x = x.children.get(i);
        }

        if (x.isLeaf()) {                           //Ensure that we have a leaf.
            i = x.n() - 1;
            if (x.isEmpty()) {

                x.keys.add(k);

            } else {
                //Find the good ordered position to insert the key.
                while ((i >= 0) && k.compareTo(x.keys.get(i)) < 0) {

                    x.keys.add(i + 1, x.keys.get(i));   //Shift keys to right until we find THE place.
                    x.keys.remove(i);
                    i--;
                }
                x.keys.add(i + 1, k);
            }
            //Puts the key where it belongs to be.
        }
    }


    /**
     * Finds a specific key k in the tree.
     *
     * @param k, the key to search in the tree.
     * @return Pair TreeNode K x, Integer i with x such as x.getKeyAt(i) == y. Returns null if
     * the key is not found.
     */
    public Pair<TreeNode<K>, Integer> search(K k) {
        assert k != null : k;

        TreeNode<K> x = new TreeNode<>(false, this.minDegree);
        x.children.add(this.root);
        int i = 0, numberOfKeys;

        do {

            x = x.children.get(i);
            i = 0;
            numberOfKeys = x.n();

            while (i < numberOfKeys && (k.compareTo(x.keys.get(i)) > 0)) i++;

            if (i < numberOfKeys && (k.compareTo(x.keys.get(i)) == 0))
                return new Pair<>(x, i);

        } while (!x.isLeaf());

        return null;
    }

    /**
     * Removes a key *key* in the tree.
     *
     * @param k to remove in the tree.
     */
    public void remove(K k) {
        assert k != null : k;
        remove(this.root, k);
    }

    private void remove(TreeNode<K> x, K k) {

        TreeNode<K> y, z;
        int i, numberOfKeys;
        K kPrime;
        i = 0;

        numberOfKeys = x.n();

        while (i < numberOfKeys && (k.compareTo(x.keys.get(i)) > 0)) i++;

        if ((k.compareTo(x.keys.get(i)) == 0)) {
            if (x.isLeaf()) x.keys.remove(i);//Case 1 : trivial

            else if (!x.isLeaf()) {                           //Case 2 : If k is in a node

                y = x.children.get(i);              //y the predecessor of x

                if (y.n() >= this.minDegree) { //Case 2.a : y has minDegree keys
                    kPrime = y.keys.get(y.n() - 1);
                    x.keys.set(i, kPrime);
                    remove(y, kPrime);

                } else { //Case 2.b : y has less than minDegree keys
                    z = x.children.get(i + 1);      //z the successor of x

                    if (z.n() >= this.minDegree) { //z has minDegree keys
                        kPrime = y.keys.get(0);
                        x.keys.set(i, kPrime);
                        remove(z, kPrime);
                    } else {                     //Case 2.c : neither has at least minDegree keys

                        z.keys.add(0, k);       //Adds k at the beginning of z
                        y.keys.addAll(z.keys);  //Merge y and z
                        x.keys.remove(i);       //Remove k from x
                        x.children.remove(i + 1);//Remove the child z from x
                        remove(y, k);            //Recall remove on y with k
                    }
                }
            }
        } else {
            y = x.children.get(i); //Case 3 : k is not in the current node
            if (y.n() == this.minDegree - 1) /*If the next child to go has not enough
                                                             keys (t-1)*/ {
                int zIndex, yIndex;
                //If i == 0 we take the the next node, else
                //the previous one.
                if (i != x.n() && x.children.get(i + 1).n() >= minDegree) { //We look at right

                    y.keys.add(x.keys.get(i));      //Put x i key in the right of y.
                    x.keys.remove(i);

                    z = x.children.get(i + 1);
                    zIndex = 0;

                    x.keys.add(i, z.keys.get(zIndex));      //Left key of the right child.

                    if (!z.isLeaf()) {
                        y.children.add(z.children.get(zIndex));
                        z.children.remove(zIndex);
                    }

                    z.keys.remove(zIndex);

                } else if (i != 0 && x.children.get(i - 1).n() >= minDegree) {//We look at left

                    y.keys.add(x.keys.get(i));
                    x.keys.remove(i);

                    z = x.children.get(i - 1);
                    zIndex = z.n() - 1;

                    x.keys.add(i, z.keys.get(zIndex));

                    if (!z.isLeaf()) {
                        y.children.add(0, z.children.get(z.n()));
                        z.children.remove(z.n());
                    }

                    z.keys.remove(zIndex);

                } else { //Cas 3.b If the two bothers have not enough keys

                    z = x.children.get(i + 1);
                    y.keys.add(x.keys.get(i));
                    x.keys.remove(i);              //Remove key i from x
                    y.keys.addAll(z.keys);         //Merge
                    y.children.addAll(z.children); //Merge y and z
                    x.children.remove(i + 1);

                    if (x == this.root && x.n() == 0) this.root = y;
                }
            }

            remove(x.children.get(i), k);
        }
    }
}