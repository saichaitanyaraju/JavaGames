package properties;

import positioning.Coordinate;
import game.Player;

public class Railway extends Property {
	
	private Railway neighbour1;
	private Railway neighbour2;
	private Railway neighbour3;
	private Coordinate coordinate;
	private boolean hasNeighbours;
	private int rent;
	public Railway(String _name, int _cost, int _rent, Coordinate _coordinate) {
		super(_name, _cost,_coordinate);
		rent = _rent;
	}

	public String buyProperty(Player currentPlayer) {

		if(owner == null){
			return buy(currentPlayer);
		}
		else
			return "This property is already owned";
	}


	public void payRent(Player currentPlayer) {
		if (owner == null) return;	//don't pay if nobody owns the property
		if(owner == currentPlayer) return;//don't pay if you own the property
		if(owner.inJail())return;//don't pay if the owner is in jail
		if(isMortgaged)return;// don't pay if property is mortgaged
		
		//***Otherwise**
		
		int noOfStationsOwned = 1;
		if(neighbour1.getOwner() == owner) noOfStationsOwned++;
		if(neighbour2.getOwner() == owner) noOfStationsOwned++;
		if(neighbour3.getOwner() == owner) noOfStationsOwned++;
		int toPay = rent* noOfStationsOwned;
		currentPlayer.deduct(toPay);
		owner.add(toPay);
		System.out.println(currentPlayer.name()+" paid $"+toPay+" to "+ owner.name()+" in rent.");
	}

	public void setNeighbours(Railway _neighbour1, Railway _neighbour2, Railway _neighbour3) {
		neighbour1 = _neighbour1;
		neighbour2 = _neighbour2;
		neighbour3 = _neighbour3;
		
		try{
		hasNeighbours = true;
		if(!neighbour1.hasNeighbours){
			neighbour1.setNeighbours(neighbour2,neighbour3,this);
		}
		if(!neighbour2.hasNeighbours){
			neighbour2.setNeighbours(neighbour3,this,neighbour1);
		}
		if(!neighbour3.hasNeighbours){
			neighbour3.setNeighbours(this,neighbour1,neighbour2);
		}
		}
		catch (NullPointerException e) {
			System.out.println("Error: Must have 4 Railways on each board");
		}
		
		
		
	}

	public String printOption(Player currentPlayer) {
		if(owner == null)
		return "Buy "+ name + "($" + cost +")";
		else return null;
	}





	

}
