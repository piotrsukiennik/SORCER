package sorcer.ssu1.provider.ui.mvc;

import net.jini.core.lookup.ServiceItem;
import net.jini.lookup.entry.UIDescriptor;
import net.jini.lookup.ui.MainUI;
import sorcer.ssu1.provider.Sort;
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

public class SortView extends JPanel implements Observer {

	private static final long serialVersionUID = -3812646466769297683L;

	private JTextField inputTextField;

	private JTextField resultTextField;

	private SortModel model;

	private SortDispatcher dispatcher;

	private final static Logger logger = Logger.getLogger( "sorcer.provider.ssu1.ui.mvc" );

	public SortView( Object provider ) {
		super();
		getAccessibleContext().setAccessibleName("SortView Tester");
		ServiceItem item = (ServiceItem) provider;

		if (item.service instanceof Sort) {
			Sort sort = (Sort) item.service;
			model = new SortModel();
			dispatcher = new SortDispatcher(model, this, sort);
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
        JPanel actionPanel = new JPanel(new GridLayout(2, 3));


        inputTextField = new JTextField();
        JButton sortButton = new JButton("Sort");
        sortButton.setActionCommand( SortModel.SORT );
        sortButton.addActionListener( dispatcher );

        actionPanel.add( new JLabel( "Input array:" ) );
        actionPanel.add( inputTextField );
        actionPanel.add( sortButton );


        resultTextField = new JTextField();
        resultTextField.setEnabled( false );

        actionPanel.add(new JLabel("Result:"));
        actionPanel.add( resultTextField );
        actionPanel.add(new JLabel(" sorted"));

        panel.add(actionPanel);
        return panel;
	}

	public int[] getInput() {
		return readTextField(inputTextField);
	}

	public void displayResult() {
		int[] result = model.getResult();
		resultTextField.setText( Arrays.toString(result));
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

	public void update(Observable o, Object arg) {
		logger.info("update>>arg: " + arg);
		if (arg != null) {
			if (SortModel.RESULT.equals( arg )){
                displayResult();
            }
			else if (SortModel.INPUT.equals( arg )){

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
					new UIComponentFactory(new URL[] { new URL(Sorcer
							.getWebsterUrl()
							+ "/ssu1-mvc-ui.jar") }, SortView.class
							.getName()));
		} catch (Exception ex) {
			logger.throwing(SortView.class.getName(), "getUIDescriptor", ex);
		}
		return uiDesc;
	}

}
