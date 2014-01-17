package sorcer.ssu3.provider.ui.mvc;

import net.jini.core.lookup.ServiceItem;
import net.jini.lookup.entry.UIDescriptor;
import net.jini.lookup.ui.MainUI;
import sorcer.ssu3.provider.FirstPrime;
import sorcer.ui.serviceui.UIComponentFactory;
import sorcer.ui.serviceui.UIDescriptorFactory;
import sorcer.util.Sorcer;

import javax.swing.*;
import java.awt.*;
import java.net.URL;
import java.util.Observable;
import java.util.Observer;
import java.util.logging.Logger;

public class FirstPrimeView extends JPanel implements Observer {

	private static final long serialVersionUID = -3812646466769297683L;

    private JTextField inputTextFieldK;

	private JTextField resultTextField;

	private FirstPrimeModel model;

	private FirstPrimeDispatcher dispatcher;

	private final static Logger logger = Logger.getLogger( "sorcer.provider.ssu3.ui.mvc" );

	public FirstPrimeView( Object provider ) {
		super();
		getAccessibleContext().setAccessibleName("FirstPrimeView Tester");
		ServiceItem item = (ServiceItem) provider;

		if (item.service instanceof FirstPrime) {
            FirstPrime firstPrime = (FirstPrime) item.service;
			model = new FirstPrimeModel();
			dispatcher = new FirstPrimeDispatcher(model, this, firstPrime);
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

        actionPanel.add(new JLabel("Which prime:"));
        inputTextFieldK = new JTextField();
        actionPanel.add( inputTextFieldK );
        JButton searchButton = new JButton("Search");
        searchButton.setActionCommand( FirstPrimeModel.SEARCH );
        searchButton.addActionListener( dispatcher );
        actionPanel.add( searchButton );

        panel.add(actionPanel);
        return panel;
	}


    public int getK() {
        return readTextFieldForK( inputTextFieldK );
    }


	public void displayResult() {
		int result = model.getResult();
		resultTextField.setText( Integer.toString( result ) );
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
			if ( FirstPrimeModel.RESULT.equals( arg )){
                displayResult();
            }
			else if ( FirstPrimeModel.INPUT.equals( arg )){

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
					new UIComponentFactory(new URL[] { new URL(Sorcer.getWebsterUrl() + "/ssu3-mvc-ui.jar") }, FirstPrimeView.class
							.getName()));
		} catch (Exception ex) {
			logger.throwing(FirstPrimeView.class.getName(), "getUIDescriptor", ex);
		}
		return uiDesc;
	}

}
