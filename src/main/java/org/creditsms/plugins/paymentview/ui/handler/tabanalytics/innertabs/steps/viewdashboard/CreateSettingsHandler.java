package org.creditsms.plugins.paymentview.ui.handler.tabanalytics.innertabs.steps.viewdashboard;

import net.frontlinesms.ui.UiGeneratorController;
import net.frontlinesms.ui.handler.BasePanelHandler;

import org.creditsms.plugins.paymentview.PaymentViewPluginController;
import org.creditsms.plugins.paymentview.data.repository.ClientDao;
import org.creditsms.plugins.paymentview.data.repository.CustomFieldDao;
import org.creditsms.plugins.paymentview.data.repository.CustomValueDao;
import org.creditsms.plugins.paymentview.ui.handler.importexport.ClientExportHandler;
import org.creditsms.plugins.paymentview.ui.handler.tabanalytics.dialogs.CreateAlertHandler;
import org.creditsms.plugins.paymentview.ui.handler.tabanalytics.innertabs.ViewDashBoardTabHandler;

public class CreateSettingsHandler extends BasePanelHandler {
	private static final String XML_STEP_VIEW_CLIENTS = "/ui/plugins/paymentview/analytics/viewdashboard/stepviewclients.xml";
	private ClientDao clientDao;
	private ViewDashBoardTabHandler viewDashBoardTabHandler;
	private CustomFieldDao customFieldDao;
	private CustomValueDao customDataDao;
	private PaymentViewPluginController pluginController;
	private SelectClientsHandler previousSelectClientsHandler;

	CreateSettingsHandler(UiGeneratorController ui,
			ViewDashBoardTabHandler viewDashBoardTabHandler,
			PaymentViewPluginController pluginController) {
		super(ui);
		this.viewDashBoardTabHandler = viewDashBoardTabHandler;
		this.pluginController = pluginController;
		this.loadPanel(XML_STEP_VIEW_CLIENTS);
	}
	
	CreateSettingsHandler(UiGeneratorController ui,
			ViewDashBoardTabHandler viewDashBoardTabHandler,
			PaymentViewPluginController pluginController, SelectClientsHandler selectClientsHandler) {
		this(ui, viewDashBoardTabHandler, pluginController);
		this.previousSelectClientsHandler = selectClientsHandler;
	}

	public void createAlert() {
		ui.add(new CreateAlertHandler((UiGeneratorController) ui).getDialog());
	}

	public void export() {
		new ClientExportHandler((UiGeneratorController) ui, pluginController).showWizard();
	}

	@Override
	public Object getPanelComponent() {
		return super.getPanelComponent();
	}

	public void previous() {
		viewDashBoardTabHandler.setCurrentStepPanel(previousSelectClientsHandler.getPanelComponent());
	}

	public void showDateSelecter(Object textField) {
		((UiGeneratorController) ui).showDateSelecter(textField);
	}
}