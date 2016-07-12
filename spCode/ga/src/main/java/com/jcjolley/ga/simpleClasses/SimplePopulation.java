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

import com.jcjolley.ga.abstractClasses.AbstractPopulation;
import com.jcjolley.ga.interfaces.Generation;
import com.jcjolley.ga.interfaces.Individual;
import com.jcjolley.ga.interfaces.Population;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * A simple, complete implementation of a Population See the Population interface for more details on Populations.
 *
 * @author josh
 */
public class SimplePopulation extends AbstractPopulation implements Population {

	private SimplePopulation() {
	}

	public static Population create(List<Generation> generations) {
		SimplePopulation sp = new SimplePopulation();
		sp.generations = generations;
		sp.maxFitness = Integer.MIN_VALUE;
		sp.averageFitness = 0d;
		return sp;
	}

	/**
	 * Selects the most fit individuals from the 3 most recent generations and breeds them to create a new generation.
	 *
	 * @param generations
	 * @return
	 */
	@Override
	public Generation select() {
		List<Individual> elites = generations.stream().skip(Math.max(0, generations.size() - 3))
			.peek(g -> maxFitness = Math.max(maxFitness, g.getMaxFitness()))
			.map(Generation::select)
			.flatMap(l -> l.stream())
			.collect(Collectors.toList());

		List<Individual> newGeneration = IntStream.range(0, elites.size())
			.filter(i -> i % 2 == 1)
			.mapToObj(i -> SimpleParents.create(elites.get(i), elites.get(i - 1)))
			.map(sp -> sp.breed(
				p -> p.get(0).getChromosome(), //We're cloning!  
				ind -> ind))		//Exact clones, no mutation here!
			.collect(Collectors.toList());

		return SimpleGeneration.create(newGeneration);
	}
}
