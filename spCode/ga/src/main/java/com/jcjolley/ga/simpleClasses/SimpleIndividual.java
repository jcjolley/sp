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

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import com.jcjolley.ga.interfaces.Gene;
import com.jcjolley.ga.interfaces.Individual;
import java.util.List;

/**
 *
 * @author josh
 */
public class SimpleIndividual implements Individual {

	private List<Gene> chromosome;
	private int fitness;

	private SimpleIndividual() {
	}

	public static Individual create(List<Gene> chromosome) {
		SimpleIndividual si = new SimpleIndividual();
		si.fitness = si.determineFitness();
		si.chromosome = chromosome;
		return si;
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
		return 0; //You'll want to do some work here
	}

	@Override
	public int getFitness() {
		return fitness;
	}
}
