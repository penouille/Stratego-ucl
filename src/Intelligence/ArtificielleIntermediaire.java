package Intelligence;

import java.util.ArrayList;

import Controller.Controller;

public class ArtificielleIntermediaire extends ArtificielleNormal
{
	public ArtificielleIntermediaire(Controller controller)
	{
		super(controller);
		setForceIA("Intermediaire");
		System.out.println("IA Intermediaire");
		currentStrategy="Attaque";
		setListOfDisplacement(new ArrayList<Deplacement>());
		count=0;
	}
	
	public void placeYourPions()
	{
		super.initializePions();
		super.setDrapeau();
		super.setBombe();
		
		setStrongerPions();
		setOtherPions();
	}

	private void setOtherPions()
	{
		// TODO Auto-generated method stub
		
	}

	private void setStrongerPions()
	{
		// TODO Auto-generated method stub
		
	}

}
