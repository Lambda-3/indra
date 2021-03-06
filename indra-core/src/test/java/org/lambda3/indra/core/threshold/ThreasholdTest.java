package org.lambda3.indra.core.threshold;

/*-
 * ==========================License-Start=============================
 * Indra Core Module
 * --------------------------------------------------------------------
 * Copyright (C) 2016 - 2017 Lambda^3
 * --------------------------------------------------------------------
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
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 * ==========================License-End===============================
 */

import org.lambda3.indra.threshold.Threshold;
import org.lambda3.indra.core.SuperFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class ThreasholdTest {

    private SuperFactory sf = new SuperFactory();
    private Map<String, Double> originalTerms;

    @BeforeTest
    public void addTerms() {
        Map<String, Double> localTerms = new HashMap<>();
        localTerms.put("i1", 1d);
        localTerms.put("i2", .9);
        localTerms.put("i3", .51);
        localTerms.put("i4", .5);
        localTerms.put("i5", .49999999999);
        localTerms.put("i6", .4);
        localTerms.put("i7", .3);
        localTerms.put("i8", .1);
        localTerms.put("i9", .00000000000001);
        localTerms.put("i0", 0d);
        originalTerms = Collections.unmodifiableMap(localTerms);
    }

    @Test
    public void max05Test() {
        LinkedHashMap<String, Double> terms = new LinkedHashMap<>(originalTerms);
        Threshold threshold = sf.create("max-0.5", Threshold.class);
        terms = threshold.apply(terms);

        Assert.assertEquals(7, terms.size());
        Assert.assertFalse(terms.containsKey("i1"));
        Assert.assertFalse(terms.containsKey("i2"));
        Assert.assertFalse(terms.containsKey("i3"));
    }

    @Test
    public void max01Test() {
        LinkedHashMap<String, Double> terms = new LinkedHashMap<>(originalTerms);
        Threshold threshold = sf.create("max-0.1", Threshold.class);
        terms = threshold.apply(terms);

        Assert.assertEquals(3, terms.size());
        Assert.assertTrue(terms.containsKey("i8"));
        Assert.assertTrue(terms.containsKey("i9"));
        Assert.assertTrue(terms.containsKey("i0"));
    }

    @Test
    public void autoTest() {
        Threshold threshold = sf.create("AUTO", Threshold.class);
        LinkedHashMap<String, Double> values = new LinkedHashMap<>();
        values.put("love", 1d);
        values.put("always", .9);
        values.put("never", .8);
        values.put("good", .6);
        values.put("hate", .5);
        values.put("cell", .1);
        values.put("phone", 0d);

        values = threshold.apply(values);
        Assert.assertEquals(values.size(), 5);
        Assert.assertTrue(values.containsKey("love"));
        Assert.assertTrue(values.containsKey("hate"));
        Assert.assertFalse(values.containsKey("cell"));
        Assert.assertFalse(values.containsKey("phone"));
    }

    @Test
    public void sameInstances() {
        Threshold t1 = sf.create("max-0.1", Threshold.class);
        Threshold t2 = sf.create("MAX-0.1", Threshold.class);
        Assert.assertEquals(t1, t2);

        Threshold t3 = sf.create("max-0.1", Threshold.class);
        Threshold t4 = sf.create("max-0.5", Threshold.class);
        Assert.assertNotEquals(t3, t4);
    }
}
