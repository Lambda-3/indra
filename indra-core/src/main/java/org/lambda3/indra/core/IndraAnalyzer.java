package org.lambda3.indra.core;

/*-
 * ==========================License-Start=============================
 * Indra Core Module
 * --------------------------------------------------------------------
 * Copyright (C) 2016 - 2017 Lambda^3
 * --------------------------------------------------------------------
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * ==========================License-End===============================
 */

import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.core.LowerCaseFilter;
import org.apache.lucene.analysis.core.StopFilter;
import org.apache.lucene.analysis.miscellaneous.ASCIIFoldingFilter;
import org.apache.lucene.analysis.miscellaneous.LengthFilter;
import org.apache.lucene.analysis.snowball.SnowballFilter;
import org.apache.lucene.analysis.standard.StandardFilter;
import org.apache.lucene.analysis.standard.StandardTokenizer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.util.CharArraySet;
import org.lambda3.indra.common.client.AnalyzedPair;
import org.lambda3.indra.common.client.Language;
import org.lambda3.indra.common.client.TextPair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tartarus.snowball.ext.*;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

class IndraAnalyzer {
    private static final int MIN_WORD_LENGTH = 3;
    private static final int MAX_WORD_LENGTH = 100;

    private static Logger logger = LoggerFactory.getLogger(IndraAnalyzer.class);

    private Tokenizer tokenizer;
    private TokenStream stream;

    IndraAnalyzer(Language lang, boolean stemming) {
        if (lang == null) {
            throw new IllegalArgumentException("lang is missing");
        }
        logger.info("Creating analyzer, lang={} (stemming={})", lang, stemming);
        tokenizer = new StandardTokenizer();
        stream = createStream(lang, stemming, tokenizer);
    }

    List<String> analyze(String text) throws IOException {
        List<String> result = new ArrayList<>();
        try (StringReader reader = new StringReader(text)) {
            tokenizer.setReader(reader);
            CharTermAttribute cattr = stream.addAttribute(CharTermAttribute.class);
            stream.reset();
            while (stream.incrementToken()) {
                result.add(cattr.toString());
            }
        }
        finally {
            stream.end();
            stream.close();
        }

        return result;
    }

    AnalyzedPair analyze(TextPair pair) throws IOException {
        AnalyzedPair analyzedPair = new AnalyzedPair(pair);
        analyzedPair.add(pair.t1, analyze(pair.t1));
        analyzedPair.add(pair.t2, analyze(pair.t2));
        return analyzedPair;
    }

    private TokenStream createStream(Language lang, boolean stemming, Tokenizer tokenizer) {
        TokenStream stream = new StandardFilter(tokenizer);

        StopFilter stopFilterStream = getStopFilter(lang, stream);
        stream = stopFilterStream != null ? stopFilterStream : stream;

        if (lang != Language.ZH && lang != Language.KO)
            stream = new LengthFilter(stream, MIN_WORD_LENGTH, MAX_WORD_LENGTH);

        TokenStream stemmerStream = stemming ? getStemmer(lang, stream) : null;
        stream = stemmerStream != null ? stemmerStream : stream;

        stream = new LowerCaseFilter(stream);
        return new ASCIIFoldingFilter(stream);
    }

    private SnowballFilter getStemmer(Language lang, TokenStream stream) {
        switch (lang) {
            case EN:
                return new SnowballFilter(stream, new EnglishStemmer());
            case PT:
                return new SnowballFilter(stream, new PortugueseStemmer());
            case ES:
                return new SnowballFilter(stream, new SpanishStemmer());
            case DE:
                return new SnowballFilter(stream, new GermanStemmer());
            case FR:
                return new SnowballFilter(stream, new FrenchStemmer());
            case SV:
                return new SnowballFilter(stream, new SwedishStemmer());
            case IT:
                return new SnowballFilter(stream, new ItalianStemmer());
            case NL:
                return new SnowballFilter(stream, new DutchStemmer());
            case RU:
                return new SnowballFilter(stream, new RussianStemmer());

            /**
             * TODO
             */
            case AR:
            case FA:
            case ZH:
            case KO:
                throw new RuntimeException("Language not implemented yet!");
            default:
                throw new RuntimeException("Language not supported yet!");
        }
    }

    private StopFilter getStopFilter(Language lang, TokenStream stream) {
        try {
            InputStream in = ClassLoader.getSystemResourceAsStream(lang.toString().toLowerCase() + ".stopwords");
            if (in != null) {
                logger.debug("Loading Stop words for lang={}", lang);
                CharArraySet stopWords = new CharArraySet(30, true);
                try (BufferedReader bin = new BufferedReader(new InputStreamReader(in))) {
                    String line;
                    String[] parts;
                    while ((line = bin.readLine()) != null) {
                        parts = line.split(Pattern.quote("|"));
                        line = parts[0].trim();

                        if (line.length() > 0) {
                            stopWords.add(line);
                        }
                    }

                }
                return new StopFilter(stream, stopWords);
            }
            else{
                logger.warn("No stop words found for lang={}", lang);
            }
        }
        catch (Exception e) {
            logger.error("Error creating stop filter for lang={}", lang, e);
        }
        return null;
    }
}
