package io.muzoo.ooc.ecosystems;

public abstract class Actor {
    protected Location location;

    public void setLocation(int row, int col) {
        this.location = new Location(row, col);
    }

    public void setLocation(Location location) {
        this.location = location;
    }
}
