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

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Joris on 07/03/2017.
 */

public class FibonacciNode<K extends Comparable<? super K>> {

    int degree;
    boolean mark;
    FibonacciNode<K> p; //Parent's node.
    List<FibonacciNode<K>> c;
    K key;

    public FibonacciNode(K key) {

        this.degree = 0;
        this.p = null;
        this.mark = false;
        this.key = key;
        c = new LinkedList<>();
    }

}
