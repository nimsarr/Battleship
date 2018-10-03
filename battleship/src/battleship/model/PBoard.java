package battleship.model;

public class PBoard {
	private Ship [] [] board=new Ship [10][10];
	public boolean placeShip(Ship ship) {
		if(checkPlace(ship)==CheckShip.True){
			if(ship.getDir()==Direction.Horizontal){
				for(int i=0;i<ship.getSize();i++){
					board[ship.getFirstX()+i][ship.getFirstY()]=ship;
				}
			}else{
				for(int i=0;i<ship.getSize();i++){
					board[ship.getFirstX()][ship.getFirstY()+i]=ship;
				}
			}
			return true;
		}
		return false;
	}
	public Direction getShipDir(int x, int y){
		return board[x][y].getDir();
	}
	public char getShipEnd(int x, int y){
		return board[x][y].getDirEnd(x,y);
	}
	public CheckShip checkPlace(Ship ship){
		if(ship.getDir()==Direction.Horizontal){
			for(int i=0;i<ship.getSize();i++){
				if(checkShipC(ship)){
					return CheckShip.OutOfBounds;
				}else if(isThereShipAt(ship.getFirstX()+i,ship.getFirstY())){
					return CheckShip.Overlap;
				}
			}
		}else{
			for(int i=0;i<ship.getSize();i++){
				if(checkShipC(ship)){
					return CheckShip.OutOfBounds;
				}if(isThereShipAt(ship.getFirstX(),ship.getFirstY()+i)){
					return CheckShip.Overlap;
				}
			}
		}
		return CheckShip.True;
	}
	private boolean checkShipC(Ship ship){
		if(ship.getFirstX()>10||ship.getFirstY()>10){
			return true;
		}
		return false;
	}
	public boolean isThereShipAt(int x, int y){
		return !(board[x][y]==null);
	}
	public HitType fireAt(int x, int y){
		if(isThereShipAt(x, y)){
			board[x][y].setHit(x, y);
			if(board[x][y].checkIfSunk()){
				return HitType.Sunk;
			}
			return HitType.Hit;
		}
		return HitType.Miss;
	}
	public HitType check(int x, int y){
		if(isThereShipAt(x, y)){
			if(board[x][y].checkIfSunk()){
				return HitType.Sunk;
			}else if(board[x][y].checkIfHit(x, y)){
				return HitType.Hit;
			}
		}
		return HitType.Miss;
	}
	public boolean checkSunkShips(){
		for(int i=0;i<10;i++){
			for(int k=0;k<10;k++){
				if(isThereShipAt(i,k)){
					if(check(i,k)!=HitType.Sunk){
						return false;
					}
				}
			}
		}
		return true;
	}
}
