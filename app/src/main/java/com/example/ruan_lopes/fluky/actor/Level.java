package com.example.ruan_lopes.fluky.actor;

public class Level {

	int enabled;
	int numMoves;
	int timeToComplete;
	String word;
	int levelType;
    int levelNumber;

    public Level(int levelNumber, int levelType, int timeToComplete, String word, int enabled)
    {
        this.levelNumber = levelNumber;
        this.levelType = levelType;
        this.word = word;
        this.enabled = enabled;
        this.timeToComplete = timeToComplete;
    }

    public int getLevelType() {
        return levelType;
    }

    public void setLevelType(int levelType) {
        this.levelType = levelType;
    }

    public int getLevelNumber() {
        return levelNumber;
    }

    public void setLevelNumber(int levelNumber) {
        this.levelNumber = levelNumber;
    }

	public Level(String word, int numMoves, int timeToComplete)
	{
		this.word = word;
		this.numMoves = numMoves;
		this.timeToComplete = timeToComplete;
	}
	
	public int getTimeToComplete() {
		return timeToComplete;
	}

	public void setTimeToComplete(int timeToComplete) {
		this.timeToComplete = timeToComplete;
	}

	public int getNumMoves() {
		return numMoves;
	}

	public void setNumMoves(int numMoves) {
		this.numMoves = numMoves;
	}

	public int getEnabled() {
		return enabled;
	}

	public void setEnabled(int enabled) {
		this.enabled = enabled;
	}

	public String getWord() {
		return word;
	}

	public void setWord(String word) {
		this.word = word;
	}
	
	
}
