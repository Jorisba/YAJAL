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

import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Random;

import static org.testng.Assert.*;

/**
 * Created by joris on 08/03/2017.
 */
public class FibonacciStackTest {

    @Test(expectedExceptions = AssertionError.class)
    public void testInsertNull() throws Exception {

        FibonacciStack<Integer> s = new FibonacciStack<>();

        Integer i = null;
        s.insert(i);
    }

    @Test
    public void testInsert() throws Exception {

        FibonacciStack<Integer> s = new FibonacciStack<>();
        s.insert(12);
    }

    @Test
    public void testGetMin() throws Exception {

        FibonacciStack<Integer> s = new FibonacciStack<>();
        Integer i = new Integer(12);

        s.insert(i);

        assertEquals(s.getMin().key, i);
    }

    @Test
    public void testExtractMinRootAlone() throws Exception {
        FibonacciStack<Integer> T = new FibonacciStack<>();
        Integer min = new Integer(1);
        T.insert(min);

        assertEquals(min, T.extractMin().key);
    }

    @Test
    public void testExtractMin() throws Exception {

        FibonacciStack<Integer> T = new FibonacciStack<>();
        int keyToInsert = 10000;
        int maxKeyValue = 100000;

        Random rnd = new Random();

        for (int i = 0; i < keyToInsert / 2; i++)
            T.insert((Math.abs(rnd.nextInt() % maxKeyValue)) + 2); // the min inserted in 2.

        Integer min = new Integer(1);
        T.insert(min);

        for (int i = 0; i < keyToInsert / 2; i++)
            T.insert((Math.abs(rnd.nextInt() % maxKeyValue)) + 2);

        assertEquals(min, T.extractMin().key);
        assertNotNull(T.extractMin()); //We need to test it 2 times to cover all the code.
    }

    @Test(expectedExceptions = AssertionError.class)
    public void testMergeWithNull() throws Exception {
        FibonacciStack<Integer> T = new FibonacciStack<>();
        T.mergeWith(null);
    }

    @Test
    public void testMergeWith() throws Exception {

        FibonacciStack<Integer> T1 = new FibonacciStack<>();
        FibonacciStack<Integer> T2 = new FibonacciStack<>();
        int keyToInsert = 10000;
        int maxKeyValue = 100000;

        Random rnd = new Random();

        for (int i = 0; i < keyToInsert; i++) {
            T1.insert((Math.abs(rnd.nextInt() % maxKeyValue)) + 2);
            T2.insert((Math.abs(rnd.nextInt() % maxKeyValue)) + 2);
        }

        Integer min = new Integer(1);
        T2.insert(min);

        T1.mergeWith(T2);

        assertEquals(min, T1.extractMin().key);
    }

    @Test
    public void decreaseKeyTest() throws Exception {

        int j;
        int keyToInsert = 10000;
        int maxKeyValue = 100000;

        FibonacciStack<Integer> T = new FibonacciStack<>(Integer.MIN_VALUE);
        ArrayList<FibonacciNode<Integer>> L = new ArrayList<>();

        Random rnd = new Random();

        for (int i = 0; i < keyToInsert; i++) {
            L.add(new FibonacciNode<Integer>(Math.abs(rnd.nextInt() % maxKeyValue)));
            T.insert(L.get(i));
        }

        T.extractMin();

        for (j = 0; j < L.size(); j++) {
            if (L.get(j).p != null && L.get(j).p.p != null) {
                break;
            }
        }

        FibonacciNode<Integer> currentNode = L.get(j);

        T.decreaseKey(currentNode, currentNode.p.key - 1);

        do {
            for (j = 0; j < L.size(); j++) {
                if (L.get(j).p != null && L.get(j).p.p != null && L.get(j).p.p.mark) {
                    break;
                }
            }
        } while (j == keyToInsert);

        currentNode = L.get(j);

        T.decreaseKey(currentNode, currentNode.p.key - 1);
    }

    @Test
    public void removeNodeTest() throws Exception {
        int j;
        int keyToInsert = 100000;
        int maxKeyValue = 1000000;

        FibonacciStack<Integer> T = new FibonacciStack<>(Integer.MIN_VALUE);
        ArrayList<FibonacciNode<Integer>> L = new ArrayList<>();

        Random rnd = new Random();

        do {
            for (int i = 0; i < keyToInsert; i++) {
                L.add(new FibonacciNode<>(Math.abs(rnd.nextInt() % maxKeyValue)));
                T.insert(L.get(i));
            }

            T.extractMin();

            for (j = 0; j < L.size(); j++) {
                if (L.get(j).p != null && L.get(j).p.p != null) {
                    break;
                }
            }

            FibonacciNode<Integer> currentNode = L.get(j);

            T.decreaseKey(currentNode, currentNode.p.key - 1);

            for (j = 0; j < L.size(); j++) {
                if (L.get(j).p != null && L.get(j).p.p != null && L.get(j).p.p.mark) {
                    break;
                }
            }
        } while (j == keyToInsert);

        T.removeNode(L.get(j));
        assertNotEquals(L.get(j).key, T.extractMin().key);
    }

    @Test
    public void setGetMinusInfiniteTest() throws Exception {
        FibonacciStack<Integer> T = new FibonacciStack<>();
        T.setMinusInfinite(Integer.MIN_VALUE);

        assertEquals(Integer.MIN_VALUE, (int) T.getMinusInfinite());
    }


}