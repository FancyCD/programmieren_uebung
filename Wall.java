class Wall {
	private boolean isDoor = false;
	Wall() {
		double randn = Math.random();
		if (randn > 0.95) {
			isDoor = true;
		}
	}
	public String toString() {
		if (isDoor) {
			return "O";
		} else {
			return "#";
		}
	}
}