package sorcer.ssu2.provider.ui.mvc;

import net.jini.core.lookup.ServiceItem;
import net.jini.lookup.entry.UIDescriptor;
import net.jini.lookup.ui.MainUI;
import sorcer.ssu2.provider.Hoare;
import sorcer.ui.serviceui.UIComponentFactory;
import sorcer.ui.serviceui.UIDescriptorFactory;
import sorcer.util.Sorcer;

import javax.swing.*;
import java.awt.*;
import java.net.URL;
import java.util.Arrays;
import java.util.Observable;
import java.util.Observer;
import java.util.logging.Logger;

public class HoareView extends JPanel implements Observer {

	private static final long serialVersionUID = -3812646466769297683L;

	private JTextField inputTextField;
    private JTextField inputTextFieldK;

	private JTextField resultTextField;

	private HoareModel model;

	private HoareDispatcher dispatcher;

	private final static Logger logger = Logger.getLogger( "sorcer.provider.ssu2.ui.mvc" );

	public HoareView( Object provider ) {
		super();
		getAccessibleContext().setAccessibleName("FirstPrimeView Tester");
		ServiceItem item = (ServiceItem) provider;

		if (item.service instanceof Hoare) {
            Hoare sort = (Hoare) item.service;
			model = new HoareModel();
			dispatcher = new HoareDispatcher(model, this, sort);
			createView();
			model.addObserver(this);
		}
	}

	protected void createView() {
		setLayout(new BorderLayout());
		add(buildAccountPanel(), BorderLayout.CENTER);
	}

	private JPanel buildAccountPanel() {
        JPanel panel = new JPanel();
        JPanel actionPanel = new JPanel(new GridLayout(3, 3));

        actionPanel.add(new JLabel("Result:"));
        resultTextField = new JTextField();
        resultTextField.setEnabled( false );
        actionPanel.add( resultTextField );

        actionPanel.add(new JLabel("Input array:"));
        inputTextField = new JTextField();
        actionPanel.add( inputTextField );


        actionPanel.add(new JLabel("K param:"));
        inputTextFieldK = new JTextField();
        actionPanel.add( inputTextFieldK );
        JButton searchButton = new JButton("Search");
        searchButton.setActionCommand( HoareModel.SEARCH );
        searchButton.addActionListener( dispatcher );
        actionPanel.add( searchButton );

        panel.add(actionPanel);
        return panel;
	}

	public int[] getInput() {
		return readTextField(inputTextField);
	}

    public int getK() {
        return readTextFieldForK( inputTextFieldK );
    }


	public void displayResult() {
		int result = model.getResult();
		resultTextField.setText( Integer.toString( result ) );
	}

    private int[] readTextField(JTextField moneyField) {
        try {
            String text = moneyField.getText();
            String[] input = text.split( "," );
            int[] output = new int[input.length];
            for (int i=0; i<input.length;i++){
                output[i]=Integer.parseInt( input[i] );
            }
            return output;
        } catch (Exception e) {
            logger.info("Field doesn't contain a valid value");
        }
        return null;
    }
    private int readTextFieldForK(JTextField moneyField) {
        try {
            String text = moneyField.getText();
            return Integer.parseInt( text );
        } catch (Exception e) {
            logger.info("Field doesn't contain a valid value");
        }
        return -1;
    }

	public void update(Observable o, Object arg) {
		logger.info("update>>arg: " + arg);
		if (arg != null) {
			if ( HoareModel.RESULT.equals( arg )){
                displayResult();
            }
			else if ( HoareModel.INPUT.equals( arg )){

            }
		}
	}

	/**
	 * Returns a service UI descriptorfor this service. Usally this method is
	 * used as an entry in provider configuration files when smart proxies are
	 * deployed with a standard off the shelf {@link sorcer.core.provider.ServiceProvider}.
	 * 
	 * @return service UI descriptor
	 */
	public static UIDescriptor getUIDescriptor() {
		UIDescriptor uiDesc = null;
		try {
			uiDesc = UIDescriptorFactory.getUIDescriptor(MainUI.ROLE,
					new UIComponentFactory(new URL[] { new URL(Sorcer.getWebsterUrl() + "/ssu2-mvc-ui.jar") }, HoareView.class
							.getName()));
		} catch (Exception ex) {
			logger.throwing(HoareView.class.getName(), "getUIDescriptor", ex);
		}
		return uiDesc;
	}

}
