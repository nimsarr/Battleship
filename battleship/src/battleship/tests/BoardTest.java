package battleship.tests;

import static org.junit.Assert.*;

import org.junit.Test;

import battleship.model.Direction;
import battleship.model.HitType;
import battleship.model.Player;
import battleship.model.Ship;

//Checks that model does in fact work.
//Originally for when GUI was not done and I still wanted to see if the model worked.
public class BoardTest {

	@Test
	public void test() {
		Ship p1one=new Ship(3, 2, 3, Direction.Horizontal);
		Ship p2one=new Ship(5, 6, 2, Direction.Vertical);
		Player p1=new Player();
		Player p2=new Player();
		
		p1.getPBoard().placeShip(p1one);
		p2.getPBoard().placeShip(p2one);
		
		assertEquals(p1one.getSize(), 3);
		assertEquals(p1.getPBoard().isThereShipAt(2, 3), true);
		assertEquals(p1.getPBoard().isThereShipAt(3, 3), true);
		assertEquals(p1.getPBoard().isThereShipAt(4, 3), true);
		assertEquals(p1.getPBoard().isThereShipAt(1, 3), false);
		assertEquals(p1.getPBoard().isThereShipAt(5, 3), false);
		assertEquals(p1.getPBoard().getShipEnd(2, 3), 'l');
		assertEquals(p1.getPBoard().getShipEnd(3, 3), 'z');
		assertEquals(p1.getPBoard().getShipEnd(4, 3), 'r');
		
		assertEquals(p2one.getSize(), 5);
		assertEquals(p2.getPBoard().isThereShipAt(6, 2), true);
		assertEquals(p2.getPBoard().isThereShipAt(6, 3), true);
		assertEquals(p2.getPBoard().isThereShipAt(6, 4), true);
		assertEquals(p2.getPBoard().isThereShipAt(6, 5), true);
		assertEquals(p2.getPBoard().isThereShipAt(6, 6), true);
		assertEquals(p2.getPBoard().isThereShipAt(6, 7), false);
		assertEquals(p2.getPBoard().isThereShipAt(6, 1), false);
		assertEquals(p2.getPBoard().getShipEnd(6, 2), 'u');
		assertEquals(p2.getPBoard().getShipEnd(6, 3), 'z');
		assertEquals(p2.getPBoard().getShipEnd(6, 4), 'z');
		assertEquals(p2.getPBoard().getShipEnd(6, 5), 'z');
		assertEquals(p2.getPBoard().getShipEnd(6, 6), 'd');
		
		assertEquals(p1one.checkIfSunk(), false);
		p2.fireAt(2, 3, p1);
		assertEquals(p1one.checkIfHit(2, 3), true);
		assertEquals(p1one.checkIfSunk(), false);
		p2.fireAt(3, 3, p1);
		assertEquals(p1one.checkIfHit(3, 3), true);
		p2.fireAt(4, 3, p1);
		assertEquals(p1one.checkIfHit(4, 3), true);
		assertEquals(p1one.checkIfSunk(), true);
		
		assertEquals(p2.getOBoard().check(2, 3), HitType.Sunk);
		assertEquals(p2.getOBoard().check(3, 3), HitType.Sunk);
		assertEquals(p2.getOBoard().check(4, 3), HitType.Sunk);
		
		assertEquals(p1.getPBoard().check(2, 3), HitType.Sunk);
		assertEquals(p1.getPBoard().check(3, 3), HitType.Sunk);
		assertEquals(p1.getPBoard().check(4, 3), HitType.Sunk);
	}
}
