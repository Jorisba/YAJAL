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

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.internal.collections.Pair;

import java.util.ArrayList;
import java.util.List;

import static org.testng.Assert.*;

/**
 * Created by Joris on 01/03/2017.
 */
public class TreeMinimalTest {

    private static final int minDegree = 3;
    private static TreeMinimal<String> tree = new TreeMinimal<>(TreeMinimalTest.minDegree);

    @BeforeTest
    public void buildTree() {

        List<String> L = new ArrayList<>();

        L.add("A");
        L.add("C");
        L.add("G");
        L.add("J");
        L.add("K");
        L.add("D");
        L.add("E");
        L.add("M");
        L.add("N");
        L.add("O");
        L.add("P");
        L.add("R");
        L.add("S");
        L.add("X");
        L.add("Y");
        L.add("Z");
        L.add("T");
        L.add("U");
        L.add("V");
        L.add("B");
        L.add("Q");
        L.add("L");
        L.add("F");

        for (String s : L) TreeMinimalTest.tree.insert(s);
        /* Same configuration as in the Book Algorithmique (french edition) Fig. 18.7 p. 459 */
    }

    @Test
    public void testSearchFound() throws Exception {

        String keyToFind = "V";

        Pair<TreeNode<String>, Integer> p = TreeMinimalTest.tree.search(keyToFind);

        assertEquals(keyToFind, p.first().getKeyAt(p.second()));
    }

    @Test
    public void testSearchNotFound() throws Exception {

        String keyToFind = "H";

        Pair<TreeNode<String>, Integer> p = TreeMinimalTest.tree.search(keyToFind);

        assertNull(p);
    }


    @Test
    public void testRemoveCase1() throws Exception {

        genericTestRemove("F");

    }

    @Test
    public void testRemoveCase2a() throws Exception {

        genericTestRemove("M");

    }

    @Test
    public void testRemoveCase2aac() throws Exception {

        genericTestRemove("G");

    }

    @Test
    public void testRemoveCase2ab() throws Exception {

        genericTestRemove("D");

    }

    @Test
    public void testRemoveCase3aa() throws Exception {

        genericTestRemove("B");

    }

    public void genericTestRemove(String S) throws Exception {

        assertNotNull(tree.search(S));

        tree.remove(S);

        assertNull(tree.search(S));
    }
}