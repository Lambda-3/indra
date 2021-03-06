package org.lambda3.indra.response;

/*-
 * ==========================License-Start=============================
 * Indra Client Module
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

import org.lambda3.indra.ScoredTextPair;
import org.lambda3.indra.request.RelatednessPairRequest;

import java.util.Collection;

public final class RelatednessPairResponse extends AbstractBasicResponse {
    private Collection<ScoredTextPair> pairs;
    private String scoreFunction;

    private RelatednessPairResponse() {
        //jersey demands.
    }

    public RelatednessPairResponse(RelatednessPairRequest request, Collection<ScoredTextPair> pairs) {
        super(request);
        this.scoreFunction = request.getScoreFunction();
        this.pairs = pairs;
    }

    public Collection<ScoredTextPair> getPairs() {
        return pairs;
    }

    public String getScoreFunction() {
        return scoreFunction;
    }

    @Override
    public String toString() {
        return "RelatednessPairResponse{" + super.toString() +
                ", pairs=" + pairs +
                ", scoreFunction=" + scoreFunction +
                '}';
    }
}
