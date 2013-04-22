package ch.fhnw.emoba.madstorm.controller;

public interface Controller {

	public Position getPosition();
	
	/**
	 * Immutable value for a position.
	 * 
	 * @author Florian Luescher
	 */
	public static final class Position {
		public final int x;
		public final int y;

		public Position(final int x, final int y) {
			this.x = x;
			this.y = y;
		}
	}
}
