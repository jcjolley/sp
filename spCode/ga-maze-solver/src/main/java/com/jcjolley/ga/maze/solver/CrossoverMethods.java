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
import com.jcjolley.ga.interfaces.Parents;
import com.jcjolley.maze.Maze;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

/**
 *
 * @author josh
 */
public class CrossoverMethods {
	private static final Random RNG = new Random();
	
	/**
	 * 
	 * @param maze
	 * @return 
	 */
	public static CrossoverMethod basicMethod() {
			CrossoverMethod cm = (Parents p) -> {
			//get the primary parent
			List<Gene> pg = p.getParents().stream().sorted().findFirst().get().getChromosome();

			//get the secondary parent
			List<Gene> sg = p.getParents().stream().sorted().skip(1).findFirst().get().getChromosome();
			
			//Setup the new chromosome for our new individual
			List<Gene> newChromosome = Lists.newArrayList();
			
			//primarily take chromosomes from the primary parent, but get some from the
			//secondary parent too!
			IntStream.range(0, pg.size()).forEach(i -> {
				if (RNG.nextDouble() > .33){
					newChromosome.add(pg.get(i));
				} else
					newChromosome.add(sg.get(i));
			});
			return newChromosome;
		};	
		return cm;
	}
}
