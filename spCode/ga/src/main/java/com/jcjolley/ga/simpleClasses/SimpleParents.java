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
package com.jcjolley.ga.simpleClasses;

import com.jcjolley.ga.interfaces.CrossoverMethod;
import com.jcjolley.ga.interfaces.Gene;
import com.jcjolley.ga.interfaces.Individual;
import com.jcjolley.ga.interfaces.MutationMethod;
import com.jcjolley.ga.interfaces.Parents;
import java.util.List;
import java.util.Random;

/**
 * A collection of Individuals to be used to create new individuals. 
 *
 * @author josh
 */
public class SimpleParents implements Parents {
	private static Random rng;
	private List<Individual> parents;

	/**
	 * Prefer static factory method to constructor. (Item 1)
	 */
	private SimpleParents() {
	}

	public static Parents create(List<Individual> individuals) {
		if (rng == null) {
			rng = new Random();
		}
		
		SimpleParents p = new SimpleParents();
		p.parents = individuals;
		return p;
	}

	public static SimpleParents create(Individual p1, Individual p2) {
		SimpleParents p = new SimpleParents();
		p.parents.add(p1);
		p.parents.add(p2);
		return p;
	}

	@Override
	public List<Individual> getParents() {
		return parents;
	}

	@Override
	public void setParents(List<Individual> parents) {
		this.parents = parents;
	}

	public Individual breed(CrossoverMethod crossoverMethod, MutationMethod mutationMethod) {
		List<Gene> chromosome = crossoverMethod.crossover(this);
		chromosome = mutationMethod.mutate(chromosome);
		return SimpleIndividual.create(chromosome);
	}

	@Override
	public Individual get(int i) {
		return parents.get(i);
	}
}
