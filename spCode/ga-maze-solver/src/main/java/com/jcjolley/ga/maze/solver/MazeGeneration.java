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
import com.jcjolley.ga.abstractClasses.AbstractGeneration;
import com.jcjolley.ga.interfaces.Generation;
import com.jcjolley.ga.interfaces.Individual;
import com.jcjolley.maze.Maze;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import static java.util.stream.Collectors.toList;

/**
 *
 * @author josh
 */
public class MazeGeneration extends AbstractGeneration implements Generation {

	private static final Logger LOG = Logger.getGlobal();

	private MazeGeneration() {
	}

	public static Generation create(List<Individual> individuals) {
		MazeGeneration mg = new MazeGeneration();
		mg.individuals = individuals.stream().sorted().collect(toList());
		mg.count = ++staticCount;
		return mg;
	}
	
	public static Generation create(int size, Maze m){
		List<Individual> individuals = Lists.newArrayList();
		for (int i = 0; i < size; i++){
			individuals.add(MazeIndividual.create(m));
		}
		return create(individuals);
	}
	
	@Override
	public String toString()
	{
		String strInd = "[" + individuals.stream().map(i -> i.toString() + " ").collect(Collectors.joining()) + "]";
		
		return "Gen " + count + ": Average: " + getAverageFitness() + " " + strInd;
	}
}
