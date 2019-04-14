package com.mycompany.a3.gameobject.objectcollection;

import java.util.ArrayList;

import com.mycompany.a3.gameobject.GameObject;

/* Class that stores an ArrayList of all of the GameObjects in
 * the current GameWorld. Stores an inner class that provides 
 * an iterator which allows for iterating through the list. 
 * No direct access to the GameObject list.
 * Implements the Iterator Design Pattern */
public class GameObjectCollection implements ICollection{
	private ArrayList<GameObject> allObjects;
	
	public GameObjectCollection() {
		allObjects = new ArrayList<>();
	}
	
	@Override
	public void add(Object o) {
		GameObject currentObject = (GameObject)o;
		allObjects.add(currentObject);
	}

	@Override
	public IIterator getIterator() {
		return new GameObjectIterator();
	}
	
	/* Provides a means of iterating through the list */
	private class GameObjectIterator implements IIterator{
		private int currentElementIndex;
		
		public GameObjectIterator() {
			currentElementIndex = -1;
		}
		
		@Override
		public boolean hasNext() {
			if(allObjects.size() <= 0)
				return false;
			if(currentElementIndex == allObjects.size() - 1)
				return false;
			return true;
		}
		
		@Override
		public Object getNext() {
			currentElementIndex++;
			return allObjects.get(currentElementIndex);
		}
	}
}

