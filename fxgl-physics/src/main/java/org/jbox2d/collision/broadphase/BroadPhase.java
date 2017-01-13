/*
 * The MIT License (MIT)
 *
 * FXGL - JavaFX Game Library
 *
 * Copyright (c) 2015-2017 AlmasB (almaslvl@gmail.com)
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package org.jbox2d.collision.broadphase;

import com.almasb.fxgl.core.math.Vec2;
import org.jbox2d.callbacks.DebugDraw;
import org.jbox2d.callbacks.PairCallback;
import org.jbox2d.callbacks.TreeCallback;
import org.jbox2d.callbacks.TreeRayCastCallback;
import org.jbox2d.collision.AABB;
import org.jbox2d.collision.RayCastInput;


public interface BroadPhase {

    public static final int NULL_PROXY = -1;

    /**
     * Create a proxy with an initial AABB. Pairs are not reported until updatePairs is called.
     *
     * @param aabb
     * @param userData
     * @return
     */
    int createProxy(AABB aabb, Object userData);

    /**
     * Destroy a proxy. It is up to the client to remove any pairs.
     *
     * @param proxyId
     */
    void destroyProxy(int proxyId);

    /**
     * Call MoveProxy as many times as you like, then when you are done call UpdatePairs to finalized
     * the proxy pairs (for your time step).
     */
    void moveProxy(int proxyId, AABB aabb, Vec2 displacement);

    void touchProxy(int proxyId);

    Object getUserData(int proxyId);

    AABB getFatAABB(int proxyId);

    boolean testOverlap(int proxyIdA, int proxyIdB);

    /**
     * Get the number of proxies.
     *
     * @return
     */
    int getProxyCount();

    void drawTree(DebugDraw argDraw);

    /**
     * Update the pairs. This results in pair callbacks. This can only add pairs.
     *
     * @param callback
     */
    void updatePairs(PairCallback callback);

    /**
     * Query an AABB for overlapping proxies. The callback class is called for each proxy that
     * overlaps the supplied AABB.
     *
     * @param callback
     * @param aabb
     */
    void query(TreeCallback callback, AABB aabb);

    /**
     * Ray-cast against the proxies in the tree. This relies on the callback to perform a exact
     * ray-cast in the case were the proxy contains a shape. The callback also performs the any
     * collision filtering. This has performance roughly equal to k * log(n), where k is the number of
     * collisions and n is the number of proxies in the tree.
     *
     * @param input    the ray-cast input data. The ray extends from p1 to p1 + maxFraction * (p2 - p1).
     * @param callback a callback class that is called for each proxy that is hit by the ray.
     */
    void raycast(TreeRayCastCallback callback, RayCastInput input);

    /**
     * Get the height of the embedded tree.
     *
     * @return
     */
    int getTreeHeight();

    int getTreeBalance();

    float getTreeQuality();
}