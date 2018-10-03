package battleship.view;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;

import battleship.controller.Game;

@SuppressWarnings("serial")
public class Tile extends JButton{
  private Image image;

  private static Image sea;
  private static Image blank;

  private static Image fullShipV;
  private static Image fullShipH;
  private static Image fullShipU;
  private static Image fullShipD;
  private static Image fullShipL;
  private static Image fullShipR;

  private static Image hitShipV;
  private static Image hitShipH;
  private static Image hitShipU;
  private static Image hitShipD;
  private static Image hitShipL;
  private static Image hitShipR;

  private static Image miss;
  private static Image hit;
  private static Image sunk;
  
  private boolean isAnimating;
  private Image currentAnimationImage;

  private void setImages(){
    try{
      sea = ImageIO.read(ClassLoader.getSystemResource("uTiles/sea.png"));
      blank = ImageIO.read(ClassLoader.getSystemResource("uTiles/blank.png"));

      fullShipV = ImageIO.read(ClassLoader.getSystemResource("fullPTiles/fullShipV.png"));
      fullShipH = ImageIO.read(ClassLoader.getSystemResource("fullPTiles/fullShipH.png"));
      fullShipU = ImageIO.read(ClassLoader.getSystemResource("fullPTiles/fullShipU.png"));
      fullShipD = ImageIO.read(ClassLoader.getSystemResource("fullPTiles/fullShipD.png"));
      fullShipL = ImageIO.read(ClassLoader.getSystemResource("fullPTiles/fullShipL.png"));
      fullShipR = ImageIO.read(ClassLoader.getSystemResource("fullPTiles/fullShipR.png"));

      hitShipV = ImageIO.read(ClassLoader.getSystemResource("hitPTiles/hitShipV.png"));
      hitShipH = ImageIO.read(ClassLoader.getSystemResource("hitPTiles/hitShipH.png"));
      hitShipU = ImageIO.read(ClassLoader.getSystemResource("hitPTiles/hitShipU.png"));
      hitShipV = ImageIO.read(ClassLoader.getSystemResource("hitPTiles/hitShipD.png"));
      hitShipL = ImageIO.read(ClassLoader.getSystemResource("hitPTiles/hitShipL.png"));
      hitShipR = ImageIO.read(ClassLoader.getSystemResource("hitPTiles/hitShipR.png"));

      miss = ImageIO.read(ClassLoader.getSystemResource("oTiles/miss.png"));
      hit = ImageIO.read(ClassLoader.getSystemResource("oTiles/hit.png"));
      sunk = ImageIO.read(ClassLoader.getSystemResource("oTiles/sunk.png"));
    }catch(Exception e){
      System.out.println(e);
    }
  }

  public Tile(final Game game, final String type, String image, final int p, final int x, final int y){
    setImages();
    setImage(image);
    if(type.equals("o")){
      this.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e){
          System.out.println("p"+p+" OBoardUpdate");
          if(game.getMode().equals("battle")){
            if(game.getPTurn()==p){
              game.getP(p).fireAt(x, y, game.getP(findO(p)));
              int xx=x+1;
              int yy=y+1;
              game.getGUI(p).apLog("Player "+p+" fired at "+xx+","+yy+"!");
              game.getGUI(findO(p)).apLog("Player "+p+" fired at "+xx+","+yy+"!");
              game.setPTurn(findO(p));
              game.getGUI(findO(p)).apLog("It's now your turn, fire at will!");
            }else{
              game.getGUI(p).apLog("It's not your turn, wait for other player to fire.");
            }
            if(game.getP(findO(p)).getPBoard().checkSunkShips()){
              game.setPWinner(p);
              game.pReady(p, "end");
              game.pReady(findO(p), "end");
              game.setMode("end");
            }
          }
          game.runGUIs();
        }
      });
    }else if(type.equals("p")){
      this.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e){
          game.runGUIs();
          System.out.println("p"+p+" PBoardUpdate");
        }
      });
    }
  }

  private int findO(int p){
    if(p==1){
      return 2;
    }else{
      return 1;
    }
  }

  public void setImage(String type){
    if(type.equals("sea")){
      image=sea;
    }else if(type.equals("fullShipV")){
      image=fullShipV;
    }else if(type.equals("fullShipH")){
      image=fullShipH;
    }else if(type.equals("fullShipU")){
      image=fullShipU;
    }else if(type.equals("fullShipD")){
      image=fullShipD;
    }else if(type.equals("fullShipL")){
      image=fullShipL;
    }else if(type.equals("fullShipR")){
      image=fullShipR;
    }else if(type.equals("hitShipV")){
      image=hitShipV;
    }else if(type.equals("hitShipH")){
      image=hitShipH;
    }else if(type.equals("hitShipU")){
      image=hitShipU;
    }else if(type.equals("hitShipD")){
      image=hitShipD;
    }else if(type.equals("hitShipL")){
      image=hitShipL;
    }else if(type.equals("hitShipR")){
      image=hitShipR;
    }else if(type.equals("miss")){
      image=miss;
    }else if(type.equals("hit")){
      image=hit;
    }else if(type.equals("sunk")){
      image=sunk;
    }else{
      image=blank;
    }
    this.invalidate();
    this.repaint();
  }

  //Animation method made by Zuse! (he's that camp instructor at the Yale iD Tech camp)
  //not currently implemented in the game, but might be at some point
  public void animateButton(final String fullFileName, final int numberOfSlides, final int delayMilliseconds) {
    Thread animationThread = new Thread() {
      public void run() {
        for (int i = 0; i < numberOfSlides; i++) {
          try {
            isAnimating = true;
            System.out.println(fullFileName + "_" + String.valueOf(i) + ".png");
            currentAnimationImage = Toolkit.getDefaultToolkit().createImage(fullFileName + "_" + String.valueOf(i + 1) + ".png");

            Tile.this.invalidate();
            Tile.this.repaint();

            Thread.sleep(delayMilliseconds);
          } catch (InterruptedException v) {
            System.out.println(v);
          }
        }

        isAnimating = false;
        Tile.this.invalidate();
        Tile.this.repaint();
      }
    };

    animationThread.start();
  }

  @SuppressWarnings("unused")
  @Override
  public void paintComponent(Graphics g) {
    Dimension dim = getSize();
    super.paintComponent(g);

    //random line of magic, DO NOT DELETE
    //ImageIcon imagei = new ImageIcon(image);

    if(isAnimating == true) {

      //2nd random line of magic, DO NOT DELETE
      ImageIcon ic = new ImageIcon(currentAnimationImage);

      g.drawImage(currentAnimationImage, 0, 0, 100, 100, this);
    }

    g.drawImage(image, 0, 0, dim.width, dim.height, this);
  }
}
