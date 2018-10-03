package battleship.model;

public class Ship {
	private int size;
	private int firstX;
	private int firstY;
	private Direction dir;
	private boolean [] hit;
	private char [] dirEnd;
	
	public Ship(int inputSize, int inputFirstX, int inputFirstY, Direction inputDir){
		size=inputSize;
		firstX=inputFirstX;
		firstY=inputFirstY;
		dir=inputDir;
		hit=new boolean [size];
		dirEnd=new char [size];
		if(dir==Direction.Vertical){
			dirEnd[0]='u';
			setDirEnd('d');
		}else{
			dirEnd[0]='l';
			setDirEnd('r');
		}
	}
	private void setDirEnd(char lastDir){
		for(int i=1;i<size;i++){
			if(i==size-1){
				dirEnd[i]=lastDir;
			}else{
				dirEnd[i]='z';
			}
		}
	}
	public char getDirEnd(int x, int y){
		return dirEnd[findPart(x, y)];
	}
	public int getSize() {
		return size;
	}
	public int getFirstX(){
		return firstX;
	}
	public int getFirstY(){
		return firstY;
	}
	public Direction getDir(){
		return dir;
	}
	public void setHit(int x, int y){
		hit[findPart(x, y)]=true;
	}
	public boolean checkIfSunk(){
		for(int i=0;i<size;i++){
			if(!hit[i]){
				return false;
			}
		}
		return true;
	}
	public boolean checkIfHit(int x, int y){
		return hit[findPart(x, y)];
	}
	public int findPart(int x, int y){
		int shipPart;
		if(dir==Direction.Horizontal){
			shipPart=x-firstX;
		}else{
			shipPart=y-firstY;
		}
		return shipPart;
	}
}
