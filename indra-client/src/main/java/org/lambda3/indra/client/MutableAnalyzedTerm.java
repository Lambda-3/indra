package org.lambda3.indra.client;

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

import java.util.List;

public class MutableAnalyzedTerm {

    private final String term;
    private List<String> originalTokens;
    private List<String> translatedTokens = null;
    private List<String> stemmedTargetTokens;

    public MutableAnalyzedTerm(String term) {
        this.term = term;
    }

    public String getTerm() {
        return term;
    }

    public void setOriginalTokens(List<String> originalTokens) {
        this.originalTokens = originalTokens;
    }

    public void setTranslatedTokens(List<String> translatedTokens) {
        this.translatedTokens = translatedTokens;
    }

    public List<String> getOriginalTokens() {
        return originalTokens;
    }

    public List<String> getTranslatedTokens() {
        return translatedTokens;
    }

    public void setStemmedTargetTokens(List<String> stemmedTargetTokens) {
        this.stemmedTargetTokens = stemmedTargetTokens;
    }

    public List<String> getStemmedTargetTokens() {
        return stemmedTargetTokens;
    }
}