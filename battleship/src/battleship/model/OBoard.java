package battleship.model;

public class OBoard {
	private HitType [] [] oboard=new HitType [10][10];

	public void setHitType(int x, int y, HitType type){
		oboard[x][y]=type;
	}
	public HitType check(int x, int y){
		return oboard[x][y];
	}
	public void compareOBoard(Player p){
		for(int x=0;x<10;x++){
			for(int y=0;y<10;y++){
				if(p.getPBoard().check(x, y)==HitType.Sunk){
					this.setHitType(x, y, HitType.Sunk);
				}
			}
		}
	}
}
