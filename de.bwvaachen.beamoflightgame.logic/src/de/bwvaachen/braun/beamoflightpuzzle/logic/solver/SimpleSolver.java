package de.bwvaachen.braun.beamoflightpuzzle.logic.solver;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import org.omg.CORBA.portable.Delegate;

import de.bwvaachen.braun.beamoflightpuzzle.model.IBeamsOfLightModel;
import de.bwvaachen.braun.beamoflightpuzzle.model.IField;
import de.bwvaachen.braun.beamoflightpuzzle.model.ILightField;
import de.bwvaachen.braun.beamoflightpuzzle.model.INumberField;

public class SimpleSolver {

	public IBeamsOfLightModel solve(IBeamsOfLightModel model) {
		/*
		 * Verknüpfung Index und festgestellte Anzahl an Abhängigkeiten
		 * Key=Index Value=
		 */
		HashMap<Integer, NumberFieldDependenciesContainer> numberfieldsMap = new HashMap<Integer, SimpleSolver.NumberFieldDependenciesContainer>();
		SolutionHelper[][] solutionHelpers = new SolutionHelper[model
				.getHeight()][model.getWidth()];
		HashSet<Integer>solvedNumberfields=new HashSet<Integer>();

		// Heraussuchen der Zahlenfelder und anlegen des Lösungsmodells
		for (int row = 0; row < model.getHeight(); row++) {
			for (int col = 0; col < model.getWidth(); col++) {
				IField field = model.getFieldAt(row, col);
				if (field instanceof INumberField) {
					int index=row * model.getWidth() + col;
					numberfieldsMap.put(index, new NumberFieldDependenciesContainer());				
					
				} else {
					solutionHelpers[row][col] = new SolutionHelper();
				}

			}
		}

		// Initiales berechnen der Abhängigkeiten
		for (Integer index : numberfieldsMap.keySet()) {
			int row = index / model.getWidth();
			int col = index - model.getWidth() * row;
			int max = ((INumberField) model.getFieldByIndex(index)).getNumber();
			NumberFieldDependenciesContainer dependenciesContainer = numberfieldsMap.get(index);

			for (int dRow = 1; row + dRow < model.getWidth()
					&& model.getFieldAt(row + dRow, col) instanceof ILightField
					&& dRow <= max; dRow++) {
				solutionHelpers[row + dRow][col].addDependency(index, false);
				dependenciesContainer.addDependency(row+dRow, col, model.getWidth());
				
			}
			for (int dRow = -1; row + dRow >= 0
					&& model.getFieldAt(row + dRow, col) instanceof ILightField
					&& -dRow <= max; dRow--) {
				solutionHelpers[row + dRow][col].addDependency(index, false);
				dependenciesContainer.addDependency(row+dRow, col, model.getWidth());
			}
			for (int dCol = -1; col + dCol >= 0
					&& model.getFieldAt(row, col + dCol) instanceof ILightField
					&& -dCol <= max; dCol--) {
				solutionHelpers[row][col + dCol].addDependency(index, true);
				dependenciesContainer.addDependency(row, col+dCol, model.getWidth());
			}
			for (int dCol = 1; col + dCol < model.getHeight()
					&& model.getFieldAt(row, col + dCol) instanceof ILightField
					&& dCol <= max; dCol++) {
				solutionHelpers[row][col + dCol].addDependency(index, true);
				dependenciesContainer.addDependency(row, col+dCol, model.getWidth());
			}
		}

		// Herausstreichen der eindeutigen Abhänigkeiten
		boolean change=true;
		while(change){
			change=false;

		for (int row = 0; row < model.getHeight(); row++) {
			for (int col = 0; col < model.getWidth(); col++) {
			SolutionHelper solutionHelper = solutionHelpers[row][col];
			if(solutionHelper!=null && ! solutionHelper.isSolved())
			{
				if(solutionHelper.allDependenciesAreHorizontal() || solutionHelper.allDependenciesAreVertical()){
					Set<Integer> dependencies = solutionHelper.getDependencies();
					for(Integer index:dependencies) {
						NumberFieldDependenciesContainer dependenciesContainer = numberfieldsMap.get(index);
						if(dependenciesContainer!=null){
							dependenciesContainer.addSettedDependency(row, col, model.getWidth());
						}
					}
					change=true;					
				}
			}
			}
		}
		
		for (Integer index : numberfieldsMap.keySet()){
			NumberFieldDependenciesContainer dependenciesContainer = numberfieldsMap.get(index);
			INumberField numberField = (INumberField) model.getFieldByIndex(index);
			if(!solvedNumberfields.contains(index)&&dependenciesContainer.getCountOfSettedDependencies()==numberField.getNumber()){
				List<Integer> unsettedDependencies = dependenciesContainer.getUnsettedDependencies();
				for(Integer lightFieldIndex:unsettedDependencies){
					int row = lightFieldIndex / model.getWidth();
					int col = lightFieldIndex - model.getWidth() * row;
					SolutionHelper solutionHelper = solutionHelpers[row][col];
					solutionHelper.removeDependenciesOfIndex(index);
				}
				solvedNumberfields.add(index);
			}
		}
		}
		
		// Aktuallisieren der Wertigkeit des Zahlenfeldes
		// MArkieren aller Felder an denen sich was geändert haben könnte
		// Bei Gleichheit aktueller Wertigkeit und soll Wertigkeit entferne alle Abhängikeiten
		
		return null;

	}
	
	
	class SolutionHelper {
		private HashMap<Integer, Boolean> dependencies = new HashMap<Integer, Boolean>();
		private Boolean horizontal, vertical;
		private boolean isSolved;

		public List<Integer> removeDependenciesOfIndex(int index){
			LinkedList<Integer>deleteList=new LinkedList<Integer>();
			Boolean ignoreHorizontal= dependencies.get(index);
			if(ignoreHorizontal==null){
				return null;
			}
			for(Entry<Integer,Boolean>entry:dependencies.entrySet()){
				if(entry.getValue()==ignoreHorizontal)
				deleteList.add(entry.getKey());
			}
			for(Integer delete:deleteList)
			{
				dependencies.remove(delete);
			}
			horizontal=null;
			vertical=null;
			return deleteList;
		}

		public boolean isSolved() {
			return isSolved;
		}

		public void addDependency(int indexOf, boolean horizontal) {
			dependencies.put(indexOf, horizontal);
			this.horizontal= null;
			vertical=null;
		}

		public int getCountOfDependencies() {
			return dependencies.size();
		}

		public boolean allDependenciesAreHorizontal() {
			if(horizontal==null){
			horizontal = true;
			for (Entry<Integer, Boolean> dependency : dependencies.entrySet()) {
				horizontal = horizontal && dependency.getValue();
			}
			}
			if(horizontal){
			isSolved=true;
			}
			return horizontal;
		}

		public boolean allDependenciesAreVertical() {
			if(vertical==null){
			vertical = false;
			for (Entry<Integer, Boolean> dependency : dependencies.entrySet()) {
				vertical = vertical || dependency.getValue();
			}
			vertical=!vertical;
			}
			if(vertical){
				isSolved=true;
			}
			return vertical;
		}

		public Set<Integer> getDependencies() {
			return dependencies.keySet();
		}

	}
	
	class NumberFieldDependenciesContainer{
		private LinkedList<Integer> unsettedDependencies=new LinkedList<Integer>();
		private LinkedList<Integer> settedDependencies=new LinkedList<Integer>();
		
		private void addDependency(int row,int col,int colLength){
			int index=row*colLength+col;
			unsettedDependencies.add(index);
		}
		public int getCountOfSettedDependencies() {
			return settedDependencies.size();
		}
		public int getTotalCount(){
			return settedDependencies.size()+unsettedDependencies.size();
		}
		public void addSettedDependency(int row,int col,int colLength) {
			int index=row*colLength+col;
			if(!unsettedDependencies.contains(index)){
				throw new IllegalArgumentException();
			}
			unsettedDependencies.remove((Object)index);
			settedDependencies.add(index);
		}
		public List<Integer> getUnsettedDependencies(){
			return unsettedDependencies;
		}
	}
}
