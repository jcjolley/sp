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
import com.jcjolley.ga.abstractClasses.AbstractPopulation;
import com.jcjolley.ga.interfaces.CrossoverMethod;
import com.jcjolley.ga.interfaces.Generation;
import com.jcjolley.ga.interfaces.Individual;
import com.jcjolley.ga.interfaces.MutationMethod;
import com.jcjolley.ga.interfaces.Parents;
import com.jcjolley.ga.interfaces.Population;
import com.jcjolley.maze.Maze;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import static java.util.stream.Collectors.toList;

/**
 *
 * @author josh
 */
public class MazePopulation extends AbstractPopulation implements Population{
	private Maze maze;
	private static final Logger LOG = Logger.getGlobal();
	private static final Random RNG = new Random();
	private MazePopulation() {
	}

	public static Population create(List<Generation> generations, Maze m) {
		MazePopulation mp = new MazePopulation();
		mp.generations = generations;
		mp.maxFitness = generations.stream().mapToInt(Generation::getMaxFitness).max().getAsInt();
		mp.averageFitness = Double.MIN_VALUE;
		mp.maze = m;
		return mp;
	}

	public static Population create(Maze m, int generationSize){
		List<Generation> generations = Lists.newArrayList();
		for (int i = 0; i < 3; i++){
			List<Individual> potentialGen = new ArrayList();
			for (int j = 0; j < generationSize; j++){
				Individual ind = MazeIndividual.create(m);
				while (ind.getFitness() > -5)
					ind = MazeIndividual.create(m);
				potentialGen.add(ind);
			}
			generations.add(MazeGeneration.create(potentialGen));
		}
		
		genSize	= generationSize;
		return create(generations, m);
	}

	/**
	 * Selects the most fit individuals and breeds them to create a new generation.
	 *
	 * @return
	 */
	@Override
	public Generation select() {
		LOG.info("Starting population select");
		//get the three most recent generations
		
		List<Generation> selectedGens = generations.stream()
				.sorted((g1, g2) -> Integer.compare(g2.getCount(), g1.getCount()))
				.limit(3)
				.collect(Collectors.toList());
		
//		List<Generation> selectedGens = generations.stream()
//				.sorted((g1, g2) -> Double.compare(g1.getAverageFitness(), g2.getAverageFitness()))
//				.limit(3)
//				.collect(toList());

		//selectedGens.addAll(bestGens);
		
		//get the best individuals in the generation
		List<Individual> elites = selectedGens.stream()
				.map(Generation::select)
				.flatMap(l -> l.stream())
				.distinct()
				.sorted()				
				.collect(Collectors.toList());
		
		LOG.log(Level.INFO, "Elites selected from latest 3 generations. We found {0} elites", elites.size());


		//select our crossover and mutation algorithms	
		CrossoverMethod cm = CrossoverMethods.basicMethod(); 
		MutationMethod mm =  MutationMethods.basicMethod();
		
		List<Individual> children = Lists.newArrayList();

		List<Individual> best = generations.stream().flatMap(g -> g.getIndividuals().stream()).distinct().sorted().limit(3).collect(toList());
		
		children.addAll(best);
		int i = 0;
		while (children.size() < genSize)
		{
			Parents p = MazeParents.create(elites.get(RNG.nextInt(5)), elites.get(weightedRandom(elites.size() - 1)), maze);
			Individual child = p.breed(cm, mm);
			if (!children.contains(child))
				children.add(p.breed(cm, mm));
			i = (i + 1) % elites.size() / 3;
		}

		//create a generation from our list of indivudals and add it to the pool
		Generation newGen = MazeGeneration.create(children);
		
		generations.add(newGen);
		return newGen;	
	}
	private int weightedRandom(int max)
	{
		Random RNG = new Random();
		return (int) (RNG.nextDouble() * RNG.nextInt(max));
	}

	@Override
	public String toString(){
		MazeIndividual best = generations.stream().flatMap(g -> g.getIndividuals().stream())
				.sorted()
				.limit(1)
				.map(i -> (MazeIndividual) i)
				.findFirst()
				.get();
	
		Integer difference = best.getPathLength() - maze.getShortestPath(maze.getEntrance(), maze.getExit()).size();
		return "Best Fitness: " + best.getFitness() + " Generations: " + getGenerations().size() + " Difference from shortest path: " + difference;
	}
}


