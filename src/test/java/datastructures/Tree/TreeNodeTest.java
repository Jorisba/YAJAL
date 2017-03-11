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

import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;

/**
 * Created by Joris on 28/02/2017.
 */
public class TreeNodeTest {

    @Test
    public void testIsFull() throws Exception {
        int minDegree = 4;
        TreeNode<String> node = new TreeNode<>(minDegree);
        assertFalse(node.isFull());
    }

    @Test
    public void testSetIsLeaf() throws Exception {
        int minDegree = 4;
        TreeNode<String> n = new TreeNode<>(minDegree);
        boolean leafValue = true;

        n.setLeaf(leafValue);
        assertEquals(n.isLeaf(), leafValue);

    }

    @Test(expectedExceptions = AssertionError.class)
    public void testAssertConstructor() throws AssertionError {
        int minDegree = 1;

        TreeNode<String> n = new TreeNode<>(minDegree);
    }

}