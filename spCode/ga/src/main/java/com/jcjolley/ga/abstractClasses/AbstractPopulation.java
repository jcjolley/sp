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
package com.jcjolley.ga.abstractClasses;

import com.google.common.collect.Lists;
import com.jcjolley.ga.interfaces.Generation;
import com.jcjolley.ga.interfaces.Population;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author josh
 */
public abstract class AbstractPopulation implements Population {
	
	protected List<Generation> generations;
	protected Integer maxFitness;
	protected Double averageFitness;
	protected static Integer genSize;
	
	@Override
	public List<Generation> getGenerations() {
		return generations;
	}

	@Override
	public void setGenerations(List<Generation> generations) {
		this.generations = generations;
	}
	
	@Override
	public void writeStatistics(){
		List<String> lines = Lists.newArrayList();	
		lines.add(this.toString());
		generations.stream().map(Generation::toString).forEach(lines::add);
	}
	
	@Override
	public List<String> getStatistics(){
		List<String> lines = Lists.newArrayList();	
		lines.add(this.toString());
		generations.stream().map(Generation::toString).forEach(lines::add);	
		return lines;
	}
	
	@Override
	public abstract Generation select();

	@Override
	public String toString() {
		maxFitness = generations.stream()
				.map(Generation::getMaxFitness)
				.sorted()
				.findFirst().get();
		
		return "Overall Best Fitness: " + maxFitness + "\n\n" + generations.get(generations.size() - 1); 
	}

	@Override
	public double getAverageFitness() {
		return generations.stream()
				.filter(g -> g.getAverageFitness() != 0)
				.collect(Collectors.averagingDouble(g -> g.getAverageFitness()));
	}

	@Override
	public double getMaxFitness() {
		return generations.stream().mapToDouble(Generation::getMaxFitness).max().getAsDouble();
	}


	@Override
	public LinkedList<ArrayList<Double>> getAverageFitnesses(){
		DecimalFormat df2 = new DecimalFormat( "#,###,###,##0.00" );
		LinkedList<ArrayList<Double>> averageFitnesses = generations.stream()
				.map(g -> Lists.newArrayList((double) g.getCount(),new Double(df2.format(g.getAverageFitness() * -1))))
				.collect(Collectors.toCollection(LinkedList::new));
		return averageFitnesses;
	}

	@Override
	public LinkedList<ArrayList<Integer>> getMaxFitnesses(){
		LinkedList<ArrayList<Integer>> maxFitnesses = generations.stream()
				.map(g -> Lists.newArrayList(g.getCount(), g.getMaxFitness() * -1))
				.collect(Collectors.toCollection(LinkedList::new));
		return maxFitnesses;
		
	}
}
