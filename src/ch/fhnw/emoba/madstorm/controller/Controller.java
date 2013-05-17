package ch.fhnw.emoba.madstorm.controller;

public interface Controller {

	public Position getPosition();
	public void close();
	
	/**
	 * Immutable value for a position.
	 * 
	 * @author Florian Luescher
	 */
	public static final class Position {
		public final float x;
		public final float y;

		public Position(final float x, final float y) {
			this.x = x;
			this.y = y;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + Float.floatToIntBits(x);
			result = prime * result + Float.floatToIntBits(y);
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Position other = (Position) obj;
			if (Float.floatToIntBits(x) != Float.floatToIntBits(other.x))
				return false;
			if (Float.floatToIntBits(y) != Float.floatToIntBits(other.y))
				return false;
			return true;
		}
		
	}
}
