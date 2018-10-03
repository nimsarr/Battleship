package battleship.controller;
import battleship.model.*;
import battleship.view.GUI;

public class Game {
  //players
	private Player p1;
	private Player p2;
	
	//whether players are ready
	private boolean p1R;
	private boolean p2R;
	
	private int pWinner;
	private int pTurn=1;
	private String mode="start";
	
	private GUI gui1 = new GUI(this, 1);
	private GUI gui2 = new GUI(this, 2);
	
	public Game(){
		gui1.setVisible(true);
		gui2.setVisible(true);
		runGUIs();
		
		p1=new Player();
		p2=new Player();
	}
	
	public void runGUIs(){
		gui1.run();
		gui2.run();
	}
	
	public GUI getGUI(int p){
		if(p==1){
			return gui1;
		}else{
			return gui2;
		}
	}
	
	public String getMode(){
		return mode;
	}
	
	public void setMode(String m){
		String text="Error @ game.setMode()";
		if(p1R==true&&p2R==true){
			mode=m;
			p1R=false;
			p2R=false;
			runGUIs();
		}else{
			if(m.equals("build")){
				text="Type start!";
			}else if(m.equals("battle")){
				text="Finish placing your ships!";
			}else if(m.equals("end")){
				text="You have lost!";
			}
			if(p1R==false){
				gui1.apLog(text);
			}else{
				gui2.apLog(text);
			}
		}
	}
	
	public void pReady(int p, String nMode){
		String text="Error @ game.pReady()";
		if(nMode.equals("build")){
			text="Player "+p+" is ready to place ships.";
		}else if(nMode.equals("battle")){
			text="Player "+p+" is ready to battle.";
		}else if(nMode.equals("end")){
			text="Player "+pWinner+" has won the game!";
		}
		if(p==1){
			p1R=true;
			gui2.apLog(text);
		}else{
			p2R=true;
			gui1.apLog(text);
		}
	}
	
	public int getPTurn(){
		return pTurn;
	}
	
	public void setPTurn(int p){
		pTurn=p;
	}
	
	public Player getP(int p){
		if(p==1){
			return p1;
		}
		return p2;
	}
	
	public int getPWinner(){
		return pWinner;
	}
	
	public void setPWinner(int p){
		pWinner=p;
	}
}
