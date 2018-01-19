/* Copyright 2016 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.engedu.anagrams;

import android.annotation.TargetApi;
import android.os.Build;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class AnagramDictionary {

    private static final int MIN_NUM_ANAGRAMS = 5;
    private static final int DEFAULT_WORD_LENGTH = 3;
    private static final int MAX_WORD_LENGTH = 7;
    private Random random = new Random();
    String currentWord = "";
    ArrayList<String> words = new ArrayList<String>();
    HashMap<String, ArrayList<String>> anagrams = new HashMap<>();

    public AnagramDictionary(Reader reader) throws IOException {
        BufferedReader in = new BufferedReader(reader);
        String line;
        while ((line = in.readLine()) != null) {
            String word = line.trim();
            words.add(word);
            Character[] chars = new Character[word.length()];
            for (int i = 0; i < chars.length; i++)
                chars[i] = word.charAt(i);
            Arrays.sort(chars, new Comparator<Character>() {
                @TargetApi(Build.VERSION_CODES.KITKAT)
                public int compare(Character c1, Character c2) {
                    int cmp = Character.compare(
                            Character.toLowerCase(c1),
                            Character.toLowerCase(c2)
                    );
                    if (cmp != 0) return cmp;
                    return Character.compare(c1, c2);
                }
            });
            StringBuilder sb = new StringBuilder(chars.length);
            for (char c : chars) sb.append(c);
            String holder = sb.toString();
            ArrayList<String> alfa = anagrams.get(holder);
            if (alfa == null) {
                alfa = new ArrayList<String>();
                alfa.add(word);
                anagrams.put(holder, alfa);
            } else {
                alfa.add(word);
                anagrams.put(holder, alfa);
            }
        }
        Log.e("Dict", "Initialized");
    }

    public boolean isGoodWord(String word, String base) {
        return true;
    }

    public List<String> getAnagrams(String targetWord) {
        ArrayList<String> result = new ArrayList<String>();
        ArrayList<String> finalResult = new ArrayList<String>();
        Character[] chars = new Character[targetWord.length()];
        for (int i = 0; i < chars.length; i++)
            chars[i] = targetWord.charAt(i);
        Arrays.sort(chars, new Comparator<Character>() {
            @TargetApi(Build.VERSION_CODES.KITKAT)
            public int compare(Character c1, Character c2) {
                int cmp = Character.compare(
                        Character.toLowerCase(c1),
                        Character.toLowerCase(c2)
                );
                if (cmp != 0) return cmp;
                return Character.compare(c1, c2);
            }
        });
        StringBuilder sb = new StringBuilder(chars.length);
        for (char c : chars) sb.append(c);
        String holder = sb.toString();
        result = anagrams.get(holder);
        for (int i = 0; i < result.size(); i++) {
            String worde = result.get(i);
            if (!worde.contains(targetWord))
                finalResult.add(result.get(i));
        }
        return finalResult;
    }

    public List<String> getAnagramsWithOneMoreLetter(String targetWord) {
        ArrayList<String> finalResult = new ArrayList<String>();
        Character[] chars = new Character[targetWord.length() + 1];
        Character[] xhars = new Character[targetWord.length() + 1];
        for (int i = 0; i < chars.length - 1; i++)
            xhars[i] = targetWord.charAt(i);
        for (int j = 97; j < 123; j++) {
            xhars[targetWord.length()] = (char) j;
            for (int k = 0; k < xhars.length; k++)
                chars[k] = xhars[k];
            ArrayList<String> result = new ArrayList<String>();
            Arrays.sort(chars, new Comparator<Character>() {
                @TargetApi(Build.VERSION_CODES.KITKAT)
                public int compare(Character c1, Character c2) {
                    int cmp = Character.compare(
                            Character.toLowerCase(c1),
                            Character.toLowerCase(c2)
                    );
                    if (cmp != 0) return cmp;
                    return Character.compare(c1, c2);
                }
            });
            StringBuilder sb = new StringBuilder(chars.length);
            for (char c : chars) sb.append(c);
            String holder = sb.toString();
            result = anagrams.get(holder);
            if (result == null)
                continue;
            for (int i = 0; i < result.size(); i++) {
                String worde = result.get(i);
                if (!worde.contains(targetWord))
                    finalResult.add(result.get(i));
            }
        }
        return finalResult;
    }

    public String pickGoodStarterWord() {
        ArrayList<String> wordsDone = new ArrayList<String>();
        int x = random.nextInt(words.size() - 1);
        currentWord = words.get(x);
        return currentWord;
    }
}
