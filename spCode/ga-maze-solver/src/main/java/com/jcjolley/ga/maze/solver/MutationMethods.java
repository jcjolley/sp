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

import com.jcjolley.ga.interfaces.Gene;
import com.jcjolley.ga.interfaces.MutationMethod;
import com.jcjolley.maze.Direction;
import java.util.List;
import java.util.Random;

/**
 *
 * @author josh
 */
public class MutationMethods {
	private static final Random RNG = new Random();
	
	public static MutationMethod basicMethod()
	{
		MutationMethod mm = (List<Gene> chromosome) -> {
			Gene g = MazeGene.create(Direction.random());
			int rand = RNG.nextInt(20);
			for (int i = 0; i < rand; i++) {
					int index = RNG.nextInt(chromosome.size());
					chromosome.remove(index);
					chromosome.add(index, g);
			}
			return chromosome;
		};
		return mm;
	}
}
