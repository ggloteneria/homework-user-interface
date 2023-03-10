package com.company.homeworkloans.screen.client;

import com.company.homeworkloans.screen.requestloan.RequestLoan;
import io.jmix.ui.ScreenBuilders;
import io.jmix.ui.action.Action;
import io.jmix.ui.component.Table;
import io.jmix.ui.screen.*;
import com.company.homeworkloans.entity.Client;
import org.springframework.beans.factory.annotation.Autowired;

@UiController("Client.browse")
@UiDescriptor("client-browse.xml")
@LookupComponent("clientsTable")
public class ClientBrowse extends StandardLookup<Client> {


    @Autowired
    private Table<Client> clientsTable;
    @Autowired
    private ScreenBuilders screenBuilders;

    @Subscribe("clientsTable.viewRequestLoan")
    public void onClientsTableViewRequestLoan(Action.ActionPerformedEvent event) {
//        Client selectedClient = clientsTable.getSingleSelected();
//        if (selectedClient != null) {
//            return;
//        }
        RequestLoan requestLoanScreen = screenBuilders.screen(this)
                .withScreenClass(RequestLoan.class)
                .build();

        requestLoanScreen.show();
    }
}