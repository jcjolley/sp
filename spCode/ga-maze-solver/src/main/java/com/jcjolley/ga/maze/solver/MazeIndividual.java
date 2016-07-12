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
import com.jcjolley.ga.interfaces.Gene;
import com.jcjolley.ga.interfaces.Individual;
import com.jcjolley.maze.Direction;
import com.jcjolley.maze.Maze;
import com.jcjolley.maze.Node;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author josh
 */
public class MazeIndividual implements Individual {

	private List<Gene> chromosome;
	private List<Node> path;
	private int fitness;
	private Maze maze;
	private int pathLength;
	private static final Logger LOG = Logger.getGlobal();

	private MazeIndividual() {
	}

	public static Individual create(Maze m, List<Gene> chromosome) {
		MazeIndividual mi = new MazeIndividual();
		mi.maze = m;
		mi.chromosome = chromosome;
		mi.path = new ArrayList<>();
		mi.fitness = mi.determineFitness();
		return mi;
	}

	public static Individual create(Maze m) {
		List<Gene> chromosome = Lists.newArrayList();
		int cLength = m.getShortestPath(m.getEntrance(), m.getExit()).size() * 6; 
		Direction last = Direction.NORTH;
		for (int i = 0; i < cLength; i++) {
			Direction d = Direction.random();
			while (d == last.opposite())
				d = Direction.random();
			last = d;
			chromosome.add(MazeGene.create(Direction.random()));
		}
		return create(m, chromosome);
	}

	@Override
	public List<Gene> getChromosome() {
		return chromosome;
	}

	@Override
	public void setChromosome(List<Gene> chromosome) {
		this.chromosome = chromosome;
	}

	private int determineFitness() {
		Node n = maze.getEntrance();
		int i = 0;
		while (i < chromosome.size() && n != maze.getExit()) {
			Direction d = (Direction) chromosome.get(i).apply(n);
			if (d != null) {
				path.add(n);
				n = n.getDirection(d);
			} else {
				LOG.log(Level.INFO, "An individual hit a dead end at gene {0}.", i);
				break;
			}
			i++;
		}
		pathLength = i;
		fitness = maze.getShortestPath(n, maze.getExit()).size() * -1; //TODO: This could be mucking things up.

		//if (fitness == 0)
		//{
			fitness += 1000 / pathLength;
		//}
		return fitness;
	}

	public List<Node> getPath() {
		return path; 
	}
	
	@Override
	public int getFitness() {
		return fitness;
	}

	public int getPathLength() {
		return pathLength;
	}

	@Override
	public String toString()
	{
		return Integer.toString(fitness);
	}

	@Override
	public int hashCode() {
		int hash = 7;
		hash = 17 * hash + Objects.hashCode(this.path);
		hash = 17 * hash + this.fitness;
		return hash;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		
		final MazeIndividual other = (MazeIndividual) obj;
		if (this.fitness != other.fitness) {
			return false;
		}
		
		return Objects.equals(this.path, other.path);
	}

	
} 