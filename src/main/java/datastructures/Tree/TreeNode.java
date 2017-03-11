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

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Joris on 28/02/2017.
 */

public class TreeNode<K extends Comparable<? super K>> {

    List<K> keys;
    List<TreeNode<K>> children;
    private boolean leaf;
    /**
     * maxChildren equals 2 times minDegree.
     */
    private int maxKeys;

    public TreeNode(int minDegree) throws AssertionError {
        assert minDegree > 1 : minDegree;


        this.maxKeys = 2 * minDegree - 1;
        this.children = new LinkedList<>();
        this.keys = new LinkedList<>();
    }

    public TreeNode(boolean leaf, int minDegree) throws AssertionError {
        this(minDegree);
        setLeaf(leaf);
    }

    public boolean isLeaf() {
        return leaf;
    }

    public void setLeaf(boolean leaf) {
        this.leaf = leaf;
    }

    public boolean isFull() {
        return this.keys.size() == this.maxKeys;
    }


    public K getKeyAt(int i) {
        return this.keys.get(i);
    }

    public int n() {
        return keys.size();
    }

    public boolean isEmpty() {
        return keys.isEmpty() && children.isEmpty();
    }

    public int keysNumber() {
        return this.keys.size();
    }

}
