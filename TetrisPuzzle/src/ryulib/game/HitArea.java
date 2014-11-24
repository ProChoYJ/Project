package ryulib.game;

import java.util.ArrayList;

import ryulib.graphic.Boundary;

public class HitArea {
	
	private ArrayList<Boundary> _List = new ArrayList<Boundary>();
	
	public boolean checkCollision(HitArea hitArea) {
		// TODO 
		for (Boundary src : _List) {
			for (Boundary dst : hitArea._List) {
				if (src.CheckCollision(dst)) return true;
			}
		}
		
		return false;
	}
	
	public boolean getIsMyArea(int x, int y) {
		for (Boundary block : _List) {
			if (block.isMyArea(x, y)) return true;
		}
		return false;
	}

	public void clear() {
		_List.clear();
	}
	
	public void add(Boundary boundary) {
		_List.add(boundary);
	}
	
	public void remove(Boundary boundary) {
		_List.remove(boundary);
	}
	
	public ArrayList<Boundary> getArrayList() {
		return _List;
	}
	
}
