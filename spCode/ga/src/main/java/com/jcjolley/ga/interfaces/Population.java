/* 
 * The MIT License
 *
 * Copyright 2015 josh.
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
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.jcjolley.ga.interfaces;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 *	A set of candidate solutions, grouped into generations.
 * @author josh
 */
public interface Population {
  public List<Generation> getGenerations();
  public void setGenerations(List<Generation> generations);
  public Generation select();
  public double getAverageFitness();
  public double getMaxFitness();
  public void writeStatistics();
  public List<String> getStatistics();
  public LinkedList<ArrayList<Double>> getAverageFitnesses();
  public LinkedList<ArrayList<Integer>> getMaxFitnesses();
}
