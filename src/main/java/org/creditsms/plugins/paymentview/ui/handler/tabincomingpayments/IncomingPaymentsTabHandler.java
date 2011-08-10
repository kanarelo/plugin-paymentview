package org.creditsms.plugins.paymentview.ui.handler.tabincomingpayments;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import net.frontlinesms.data.events.DatabaseEntityNotification;
import net.frontlinesms.events.EventObserver;
import net.frontlinesms.events.FrontlineEventNotification;
import net.frontlinesms.ui.UiGeneratorController;
import net.frontlinesms.ui.events.FrontlineUiUpateJob;
import net.frontlinesms.ui.handler.BaseTabHandler;
import net.frontlinesms.ui.handler.ComponentPagingHandler;
import net.frontlinesms.ui.handler.PagedComponentItemProvider;
import net.frontlinesms.ui.handler.PagedListDetails;
import net.frontlinesms.ui.i18n.InternationalisationUtils;

import org.creditsms.plugins.paymentview.PaymentViewPluginController;
import org.creditsms.plugins.paymentview.data.domain.Client;
import org.creditsms.plugins.paymentview.data.domain.IncomingPayment;
import org.creditsms.plugins.paymentview.data.domain.LogMessage;
import org.creditsms.plugins.paymentview.data.repository.IncomingPaymentDao;
import org.creditsms.plugins.paymentview.data.repository.LogMessageDao;
import org.creditsms.plugins.paymentview.ui.handler.AuthorisationCodeHandler;
import org.creditsms.plugins.paymentview.ui.handler.importexport.IncomingPaymentsExportHandler;
import org.creditsms.plugins.paymentview.ui.handler.importexport.IncomingPaymentsImportHandler;
import org.creditsms.plugins.paymentview.ui.handler.tabclients.dialogs.EditClientHandler;
import org.creditsms.plugins.paymentview.ui.handler.tabincomingpayments.dialogs.EditIncomingPaymentDialogHandler;

public class IncomingPaymentsTabHandler extends BaseTabHandler implements
		PagedComponentItemProvider, EventObserver{
	private static final String COMPONENT_INCOMING_PAYMENTS_TABLE = "tbl_clients";
	private static final String COMPONENT_PANEL_INCOMING_PAYMENTS_TABLE = "pnl_clients";
	private static final String XML_INCOMING_PAYMENTS_TAB = "/ui/plugins/paymentview/incomingpayments/tabincomingpayments.xml";
	private static final String CONFIRM_DELETE_INCOMING = "message.confirm.delete.incoming";
	
	protected IncomingPaymentDao incomingPaymentDao;
	private LogMessageDao logMessageDao;
	
	private String incomingPaymentsFilter = "";
	private Object incomingPaymentsTab;

	protected Object incomingPaymentsTableComponent;
	protected ComponentPagingHandler incomingPaymentsTablePager;
	private Object pnlIncomingPaymentsTableComponent;
	private PaymentViewPluginController pluginController;
	private Object dialogConfirmation;


	public IncomingPaymentsTabHandler(UiGeneratorController ui,
			PaymentViewPluginController pluginController) {
		super(ui);
		this.incomingPaymentDao = pluginController.getIncomingPaymentDao();
		this.logMessageDao = pluginController.getLogMessageDao();
		this.pluginController = pluginController;
		ui.getFrontlineController().getEventBus().registerObserver(this);
		init();
	}
	
	@Override
	protected Object initialiseTab() {
		incomingPaymentsTab = ui.loadComponentFromFile(getXMLFile(), this);
		incomingPaymentsTableComponent = ui.find(incomingPaymentsTab, COMPONENT_INCOMING_PAYMENTS_TABLE);
		incomingPaymentsTablePager = new ComponentPagingHandler(ui, this, incomingPaymentsTableComponent);
		pnlIncomingPaymentsTableComponent = ui.find(incomingPaymentsTab, COMPONENT_PANEL_INCOMING_PAYMENTS_TABLE);
		this.ui.add(pnlIncomingPaymentsTableComponent, this.incomingPaymentsTablePager.getPanel());
		return incomingPaymentsTab;
	}

	protected String getXMLFile() {
		return XML_INCOMING_PAYMENTS_TAB;
	}

	// > EVENTS...
	public void exportIncomingPayments() {
		Object[] selectedItems = ui.getSelectedItems(incomingPaymentsTableComponent);
		if (selectedItems.length <= 0){
			exportIncomingPayments(getIncomingPaymentsForExport());
		}else{
			List<IncomingPayment> incomingPayments = new ArrayList<IncomingPayment>(selectedItems.length);
			for (Object o : selectedItems) {
				incomingPayments.add(ui.getAttachedObject(o, IncomingPayment.class));
			}
			exportIncomingPayments(incomingPayments);
		}
	}

	protected List<IncomingPayment> getIncomingPaymentsForExport() {
		return incomingPaymentDao.getActiveIncomingPayments();
	}
	
	public void exportIncomingPayments(List<IncomingPayment> incomingPayments) {
		new IncomingPaymentsExportHandler(ui, pluginController, incomingPayments).showWizard();
		this.refresh();
	}

	public Object getRow(IncomingPayment incomingPayment) {
		Object row = ui.createTableRow(incomingPayment);

		ui.add(row, ui.createTableCell(incomingPayment.getPaymentBy()));
		ui.add(row, ui.createTableCell(incomingPayment.getPhoneNumber()));
		ui.add(row, ui.createTableCell(incomingPayment.getAmountPaid().toPlainString()));
		ui.add(row, ui.createTableCell(InternationalisationUtils.getDatetimeFormat().format(new Date(incomingPayment.getTimePaid()))));
		ui.add(row, ui.createTableCell(incomingPayment.getPaymentId()));
		ui.add(row, ui.createTableCell(incomingPayment.getNotes()));
		return row;
	}

	public void importPayments() {
		new IncomingPaymentsImportHandler(ui).showWizard();
		this.refresh();
	}

	@Override
	public void refresh() {
		this.updateIncomingPaymentsList();
	}

	public void editIncoming() {
		Object[] selectedIncomings = this.ui.getSelectedItems(incomingPaymentsTableComponent);
		for (Object selectedIncoming : selectedIncomings) {
			IncomingPayment ip = (IncomingPayment) ui.getAttachedObject(selectedIncoming);
			ui.add(new EditIncomingPaymentDialogHandler(ui,pluginController,ip).getDialog());
		}
	}

	
//>PAGING METHODS
	protected PagedListDetails getIncomingPaymentsListDetails(int startIndex,
			int limit) {
		List<IncomingPayment> incomingPayments = new ArrayList<IncomingPayment>();
		incomingPayments = getIncomingPaymentsForUI(startIndex, limit);
		int totalItemCount = incomingPaymentDao.getActiveIncomingPaymentsCount();
		Object[] listItems = toThinletComponents(incomingPayments);

		return new PagedListDetails(totalItemCount, listItems);
	}

	protected List<IncomingPayment> getIncomingPaymentsForUI(int startIndex, int limit) {
		List<IncomingPayment> incomingPayments;
		if (this.incomingPaymentsFilter.equals("")) {
			incomingPayments = this.incomingPaymentDao.getActiveIncomingPayments(startIndex, limit);
		} else {
			//TODO: change this to add more columns to be filtered.
			incomingPayments = this.incomingPaymentDao.getActiveIncomingPaymentsByPhoneNo(this.incomingPaymentsFilter);
		}
		return incomingPayments;
	}

	public PagedListDetails getListDetails(Object list, int startIndex,
			int limit) {
		if (list == this.incomingPaymentsTableComponent) {
			return getIncomingPaymentsListDetails(startIndex, limit);
		} else {
			throw new IllegalStateException();
		}
	}

	private Object[] toThinletComponents(List<IncomingPayment> incomingPayments) {
		Object[] components = new Object[incomingPayments.size()];
		for (int i = 0; i < components.length; i++) {
			IncomingPayment in = incomingPayments.get(i);
			components[i] = getRow(in);
		}
		return components;
	}

	public void updateIncomingPaymentsList() {
		this.incomingPaymentsTablePager.setCurrentPage(0);
		this.incomingPaymentsTablePager.refresh();
	}
	
//> INCOMING PAYMENT DELETION
	public void deleteIncomingPayment() {
		Object[] selectedIncomings = this.ui.getSelectedItems(incomingPaymentsTableComponent);
		if (selectedIncomings.length == 0){
			ui.infoMessage("Please select incoming payment(s).");	
		} else {
			for (Object selectedIncoming : selectedIncomings) {
				IncomingPayment attachedIncoming = ui.getAttachedObject(selectedIncoming, IncomingPayment.class);
				attachedIncoming.setActive(false);
				incomingPaymentDao.updateIncomingPayment(attachedIncoming);
				logMessageDao.saveLogMessage(
						new LogMessage(LogMessage.LogLevel.INFO,
							   	"Delete Incoming Payment",
							   	attachedIncoming.toStringForLogs()));
			}
			ui.infoMessage("You have successfully deleted the selected incoming payment(s).");	
		}		
	}
	
	public void showAuthCode() {
		ui.remove(dialogConfirmation);
		new AuthorisationCodeHandler(ui).showAuthorizationCodeDialog("deleteIncomingPayment", this);
	}
	
	public final void showDeleteConfirmationDialog(String methodToBeCalled){
		dialogConfirmation = this.ui.showConfirmationDialog(methodToBeCalled, this, CONFIRM_DELETE_INCOMING);
	}
	
//> INCOMING PAYMENT NOTIFICATION...
	@SuppressWarnings("rawtypes")
	public void notify(final FrontlineEventNotification notification) {
		new FrontlineUiUpateJob() {
			public void run() {
				if (!(notification instanceof DatabaseEntityNotification)) {
					return;
				}
		
				Object entity = ((DatabaseEntityNotification) notification).getDatabaseEntity();
				if (entity instanceof IncomingPayment) {
					IncomingPaymentsTabHandler.this.refresh();
				}
			}
		}.execute();
	}
}