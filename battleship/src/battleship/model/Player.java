package battleship.model;

public class Player {
	private PBoard pboard=new PBoard();
	private OBoard oboard=new OBoard();

	public void fireAt(int x, int y, Player p){
		if(p.getPBoard().fireAt(x, y)==HitType.Hit){
			oboard.setHitType(x, y, HitType.Hit);
		}else if(p.getPBoard().fireAt(x, y)==HitType.Sunk){
			oboard.setHitType(x, y, HitType.Sunk);
			this.getOBoard().compareOBoard(p);
		}else{
			oboard.setHitType(x, y, HitType.Miss);
		}
	}
	public PBoard getPBoard(){
		return pboard;
	}
	public OBoard getOBoard(){
		return oboard;
	}
}
