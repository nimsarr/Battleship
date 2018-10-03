package battleship.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import battleship.controller.Game;
import battleship.model.CheckShip;
import battleship.model.Direction;
import battleship.model.HitType;
import battleship.model.Ship;

//each player has his/her own GUI, it's just the window

@SuppressWarnings("serial")
public class GUI extends JFrame implements Runnable {
  //2D arrays of the graphics tiles of each board in the GUI
	private Tile [] [] tileGridP=new Tile [10][10];
	private Tile [] [] tileGridO=new Tile [10][10];
	
	//this is just to access the current game, will be initialized to current game
	private Game game;
	
	//GUI text stuffs
	private JTextArea info;
	private JTextArea log;
	private Font font = new Font("Verdana", Font.BOLD, 12);

	//variables for placing ships
	private int p;
	private Integer size;
	private Integer x;
	private Integer y;
	private Direction dir;

	//whether ships are placed on board or not
	private boolean s5;
	private boolean s4;
	private boolean s31;
	private boolean s32;
	private boolean s2;

	public GUI(Game g, int inputP){
		p=inputP;
		game=g;
		try {
      buildView(this);
    }
    catch (IOException e) {
      e.printStackTrace();
      System.out.println("Error...");
    }

		setTitle("Battleship - Player "+p);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(1400, 1000);
		setLocationRelativeTo(null);
		setVisible(true);
	}

	public void run() {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				setInfo();
				checkGridP(game);
				checkGridO(game);
			}
		});
	}

	public void buildView(JFrame f) throws IOException{
		JPanel view=new JPanel();
		view.setLayout(new BorderLayout());

		JPanel grids=new JPanel();
		grids.setPreferredSize(new Dimension(1280,950));
		grids.setLayout(new BorderLayout());

		JPanel gridP=new JPanel();
		gridP.setPreferredSize(new Dimension(640,950));
		gridP.setLayout(new GridLayout(11, 10));

		for (int i=0; i<10; i++) {
			for(int k=0;k<10;k++){
				Tile tile=new Tile(game, "p", "sea", p, k, i);
				gridP.add(tile);
				tileGridP[k][i]=tile;
			}
		}

		JPanel gridO=new JPanel();
		gridO.setPreferredSize(new Dimension(640,950));
		gridO.setLayout(new GridLayout(11, 10));

		for (int i=0; i<10; i++) {
			for(int k=0;k<10;k++){
				Tile tile=new Tile(game, "o", "blank", p, k, i);
				gridO.add(tile);
				tileGridO[k][i]=tile;
			}
		}

		JPanel cPanel=new JPanel();
		cPanel.setLayout(new BorderLayout());
		cPanel.setPreferredSize(new Dimension(700,200));

		info=new JTextArea(4,35);
		info.setEditable(false);
		info.setFont(font);

		log=new JTextArea(4,20);
		log.setEditable(false);

		JScrollPane scrollPane=new JScrollPane(log);
		scrollPane.setPreferredSize(new Dimension(300,200));

		final JTextField textField=new JTextField(40);
		textField.setPreferredSize(new Dimension(800,25));
		textField.setFont(font);
		textField.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){
				String text=textField.getText();
				if(game.getMode().equals("start")){
					if(text.equals("start")){
						apLog(text);
						game.pReady(p, "build");
						game.setMode("build");
					}else if(text.equals("x")){
						apLog(text);
						System.exit(0);
					}else{
						apLog("Error: Not recognized");
					}
				}else if(game.getMode().equals("build")){
					if(checkL(text, 4)&&text.substring(0, 4).equals("size")){
						size=Integer.parseInt(text.substring(5));
						apLog(text);
						if(size>5){
							apLog("Error: Ship is too big");
							size=null;
						}else if(size<2){
							apLog("Error: Ship is too small");
							size=null;
						}else if(checkShipSet(size)){
							apLog("Error: Too many ships of that size");
							size=null;
						}
					}else if(checkL(text, 2)&&text.substring(0,2).equals("xy")){
						if(text.substring(3,5).equals("10")){
							x=Integer.parseInt(text.substring(3,5));
							y=Integer.parseInt(text.substring(6));
						}else{
							x=Integer.parseInt(text.substring(3,4));
							y=Integer.parseInt(text.substring(5));
						}
						apLog(text);
					}else if(checkL(text, 3)&&text.substring(0,3).equals("dir")){
						if(checkL(text, 5)&&text.substring(4).equals("vertical")){
							dir=Direction.Vertical;
						}else if(checkL(text, 5)&&text.substring(4).equals("horizontal")){
							dir=Direction.Horizontal;
						}
						apLog(text);
					}else{
						apLog("Error: Not recognized");
					}
					if(size!=null&&x!=null&&y!=null&&dir!=null){
						Ship ship=new Ship(size,x-=1,y-=1,dir);
						if(game.getP(p).getPBoard().checkPlace(ship)==CheckShip.True){
							game.getP(p).getPBoard().placeShip(ship);
							setShipSet(size);
							size=null;
							x=null;
							y=null;
							dir=null;
							if(checkShipSets()){
								game.pReady(p, "battle");
								game.setMode("battle");
							}
						}else if(game.getP(p).getPBoard().checkPlace(ship)==CheckShip.Overlap){
							apLog("Error: Ship overlap, please retry");
						}else if(game.getP(p).getPBoard().checkPlace(ship)==CheckShip.OutOfBounds){
							apLog("Error: Ship is off the board, please retry.");
						}
					}
				}else if(game.getMode().equals("battle")){
					apLog("The battle is on! Click on the right board to fire if it's your turn.");
				}else if(game.getMode().equals("end")){
					if(text.equals("x")){
						apLog(text);
						System.exit(0);
					}
				}
				run();
				textField.setText("");
			}
		});

		grids.add(gridP, BorderLayout.WEST);
		grids.add(gridO, BorderLayout.EAST);

		cPanel.add(info, BorderLayout.WEST);
		cPanel.add(scrollPane, BorderLayout.EAST);
		cPanel.add(textField, BorderLayout.SOUTH);

		view.add(grids, BorderLayout.CENTER);
		view.add(cPanel, BorderLayout.SOUTH);
		f.add(view);
		f.setIconImage(getToolkit().getImage("fullPTiles/fullShipU.png"));
	}

	public void apLog(String s){
		if(log.getText().equals("")){
			log.append(s);
		}else{
			log.append("\n"+s);
		}
	}

	private void setShipSet(int s){
		if(s==5){
			s5=true;
		}else if(s==4){
			s4=true;
		}else if(s==3){
			if(s31==true){
				s32=true;
			}else{
				s31=true;
			}
		}else if(s==2){
			s2=true;
		}
	}

	private boolean checkShipSet(int s){
		if(s==5&&s5==true){
			return true;
		}else if(s==4&&s4==true){
			return true;
		}else if(s==3&&s31==true&&s32==true){
			return true;
		}else if(s==2&&s2==true){
			return true;
		}
		return false;
	}

	private boolean checkShipSets(){
		return (checkShipSet(5)&&checkShipSet(4)&&checkShipSet(3)&&checkShipSet(2));
	}

	private boolean checkL(String s, int l){
		return (s.length()>=l);
	}

	private void setInfo(){
		if(game.getMode().equals("start")){
			info.setText("Welcome to Battleship!\nMade by Simon, with a lot of help from Zuse.\n\nYou are player "+p+".\n\nType start and press enter to begin.\nx to close.\n\n"
					+ "Both players must type start to begin.");
		}else if(game.getMode().equals("build")){
			info.setText("Build Your Ships!\n\nTo input size: size __\nTo input starting coordinates: xy _,_\nTo input direction: dir __(vertical or horizontal)\n\n"
					+ "One 5 ship, One 4 ship, Two 3 ships, One 2 ship.");
		}else if(game.getMode().equals("battle")){
			info.setText("Now it's time to battle!\n\nWhen it's your turn, click on the grid on the right to fire at the other player.\nThe board will show if you missed or hit.\n\n"
					+ "A miss is blue, a hit is yellow, and a sunk ship is red.\nPlayer 1 goes first.");
		}else if(game.getMode().equals("end")){
			info.setText("Player "+game.getPWinner()+" won battleship!\n\nEnter x to exit.");
		}
	}

	private void checkGridP(Game g){
		for(int i=0;i<10;i++){
			for(int k=0;k<10;k++){
				if(g.getP(p).getPBoard().isThereShipAt(i,k)){
					if(checkShipEnd(i,k,'u')){
						setShipI(i,k,"fullShipU","hitShipU");
					}else if(checkShipEnd(i,k,'d')){
						setShipI(i,k,"fullShipD","hitShipD");;
					}else if(checkShipEnd(i,k,'l')){
						setShipI(i,k,"fullShipL","hitShipL");
					}else if(checkShipEnd(i,k,'r')){
						setShipI(i,k,"fullShipR","hitShipR");
					}else{
						if(g.getP(p).getPBoard().getShipDir(i, k)==Direction.Vertical){
							setShipI(i,k,"fullShipV","hitShipV");
						}else{
							setShipI(i,k,"fullShipH","hitShipH");
						}
					}
				}else{
					tileGridP[i][k].setImage("sea");
				}
			}
		}
	}
	
	private void setShipI(int x, int y, String iFull, String iHit){
		if(checkShipHit(x,y)){
			tileGridP[x][y].setImage(iHit);
		}else{
			tileGridP[x][y].setImage(iFull);
		}
	}
	
	private boolean checkShipHit(int x, int y){
		return !(game.getP(p).getPBoard().check(x, y)==HitType.Miss);
	}

	private boolean checkShipEnd(int x, int y, char dir){
		return (game.getP(p).getPBoard().getShipEnd(x, y)==dir);
	}

	private boolean checkHitType(int x, int y, HitType t){
		return (game.getP(p).getOBoard().check(x, y)==t);
	}

	private void checkGridO(Game g){
		for(int i=0;i<10;i++){
			for(int k=0;k<10;k++){
				if(checkHitType(i,k,HitType.Miss)){
					tileGridO[i][k].setImage("miss");
				}else if(checkHitType(i,k,HitType.Hit)){
					tileGridO[i][k].setImage("hit");
				}else if(checkHitType(i,k,HitType.Sunk)){
					tileGridO[i][k].setImage("sunk");
				}else{
					tileGridO[i][k].setImage("blank");
				}
			}
		}
	}
}