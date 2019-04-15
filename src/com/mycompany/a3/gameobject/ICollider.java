package com.mycompany.a3.gameobject;

/* Interface that provides methods to handle collisions
 * between GameObjects */
public interface ICollider {
	public boolean collidesWith(GameObject otherObject);
	public void handleCollision(GameObject otherObject);
}
