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
import com.jcjolley.maze.Direction;
import com.jcjolley.maze.Node;
import java.util.Arrays;
import java.util.Collections;
import java.util.EnumSet;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author josh
 */
public class MazeGene implements Gene<Node, Direction>{
	private Direction d;

	@Override
	public Direction apply(Node n) {
		//Return a direction that actually goes somewhere, if it exists
		EnumSet<Direction> dirs = EnumSet.allOf(Direction.class);
		while(!dirs.isEmpty()){
			if (n.getDirection(d) != null)
				return d;
			dirs.remove(d);
			d = dirs.stream().findFirst().get();
		}
		return null;
	}
	
	private MazeGene(){}

	public static Gene create(Direction d){
		MazeGene mg = new MazeGene();
		mg.d = d;
		return mg;
	}

	@Override
	public int hashCode() {
		int hash = 3;
		hash = 71 * hash + Objects.hashCode(this.d);
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
		final MazeGene other = (MazeGene) obj;
		if (this.d != other.d) {
			return false;
		}
		return true;
	}
}
