package slicktest;
import View.*;
import org.newdawn.slick.*;
import org.newdawn.slick.command.BasicCommand;
import org.newdawn.slick.command.Command;
import org.newdawn.slick.command.InputProvider;
import org.newdawn.slick.command.InputProviderListener;
import org.newdawn.slick.command.KeyControl;

public class sli extends BasicGame implements InputProviderListener
{
	private Image localImg;
	private Graphics localImgG;
	
	private InputProvider provider;
	private Command option = new BasicCommand("option");
	
	private String message = "";
	
	public sli() 
	{
		super("SimpleTest");
	}
 
	@Override
	public void init(GameContainer container) throws SlickException 
	{
		provider = new InputProvider(container.getInput());
        provider.addListener(this);
        
        provider.bindCommand(new KeyControl(Input.KEY_O), option);
	}
 
	@Override
	public void update(GameContainer container, int delta)
			{}
	
	
	/**
     * @see org.newdawn.slick.command.InputProviderListener#controlPressed(org.newdawn.slick.command.Command)
     */
    public void controlPressed(Command command) {
            if (command.equals(option))
            	{
            		new Option();
            	}
            
            }
    

    /**
     * @see org.newdawn.slick.command.InputProviderListener#controlReleased(org.newdawn.slick.command.Command)
     */
    public void controlReleased(Command command) {
            message = "Released: "+command;
    }
    
	public void Affiche ()
	{
		try {
			//requires latest version of slick... old version used new Image(w, h)
			localImg = new Image(256,256);
			localImgG = localImg.getGraphics();
		} catch (SlickException e) {
			//Log.error("creating local image or graphics context failed: " + e.getMessage());
		}	
	}
	@Override
	public void render(GameContainer container, Graphics g)
			throws SlickException {
		g.setColor(Color.white);
		g.drawString("Stratégo", 400, 0);
		
		g.setColor(Color.yellow);
        g.fillRect(50,200,40,40);
        g.fillRect(50,190,40,1);
	}
 
	public static void main(String[] args) {
		try {
			AppGameContainer app = new AppGameContainer(new sli());
			app.setDisplayMode(800,600,false); //pleine écran.
			app.start();
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}
}