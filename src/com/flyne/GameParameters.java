package com.flyne;

public class GameParameters {

	private float searchRadius = 40;
	private float maxSpeed = 20;
	private float acceleration = .05f, deceleration = .1f;
	private int playerDamage = 50;
	private float startSpeed = 5;

	public float getSearchRadius() {
		return searchRadius;
	}

	public void setSearchRadius(float searchRadius) {
		this.searchRadius = searchRadius;
	}

	public float getMaxSpeed() {
		return maxSpeed;
	}

	public void setMaxSpeed(float maxSpeed) {
		this.maxSpeed = maxSpeed;
	}

	public float getAcceleration() {
		return acceleration;
	}

	public void setAcceleration(float acceleration) {
		this.acceleration = acceleration;
	}

	public float getDeceleration() {
		return deceleration;
	}

	public void setDeceleration(float deceleration) {
		this.deceleration = deceleration;
	}

	public int getPlayerDamage() {
		return playerDamage;
	}

	public void setPlayerDamage(int playerDamage) {
		this.playerDamage = playerDamage;
	}

	public float getStartSpeed() {
		return startSpeed;
	}

	public void setStartSpeed(float startSpeed) {
		this.startSpeed = startSpeed;
	}
	
}
