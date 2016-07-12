/* 
 * The MIT License
 *
 * Copyright 2016 josh.
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
package com.jcjolley.ga.maze.solver;

import com.google.common.collect.Lists;
import com.jcjolley.ga.interfaces.CrossoverMethod;
import com.jcjolley.ga.interfaces.Gene;
import com.jcjolley.ga.interfaces.Individual;
import com.jcjolley.ga.interfaces.MutationMethod;
import com.jcjolley.ga.interfaces.Parents;
import com.jcjolley.maze.Maze;
import java.util.List;
import java.util.Random;

/**
 *
 * @author josh
 */
public class MazeParents implements Parents {
	private static final Random RNG = new Random();
	private List<Individual> parents;
	Maze m;

	/**
	 * Prefer static factory method to constructor. (Item 1)
	 */
	private MazeParents() {
	}

	public static Parents create(List<Individual> individuals, Maze m) {
		MazeParents p = new MazeParents();
		p.parents = individuals;
		p.m = m;
		return p;
	}

	public static Parents create(Individual p1, Individual p2, Maze m) {
		List<Individual> inds = Lists.newArrayList();
		inds.add(p1);
		inds.add(p2);
		return create(inds, m);
	}

	@Override
	public List<Individual> getParents() {
		return parents;
	}

	@Override
	public void setParents(List<Individual> parents) {
		this.parents = parents;
	}

	@Override
	public Individual breed(CrossoverMethod crossoverMethod, MutationMethod mutationMethod) {
		List<Gene> chromosome = crossoverMethod.crossover(this);
		chromosome = mutationMethod.mutate(chromosome);
		return MazeIndividual.create(m, chromosome);
	}

	@Override
	public Individual get(int i) {
		return parents.get(i);
	}
}
