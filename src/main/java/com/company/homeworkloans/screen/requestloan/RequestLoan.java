package com.company.homeworkloans.screen.requestloan;

import com.company.homeworkloans.entity.Client;
import com.company.homeworkloans.entity.Loan;
import com.company.homeworkloans.entity.LoanStatus;
import io.jmix.core.DataManager;
import io.jmix.ui.Notifications;
import io.jmix.ui.ScreenBuilders;
import io.jmix.ui.action.Action;
import io.jmix.ui.component.Button;
import io.jmix.ui.component.EntityComboBox;
import io.jmix.ui.component.TextField;
import io.jmix.ui.model.CollectionContainer;
import io.jmix.ui.model.CollectionLoader;
import io.jmix.ui.screen.Screen;
import io.jmix.ui.screen.Subscribe;
import io.jmix.ui.screen.UiController;
import io.jmix.ui.screen.UiDescriptor;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.time.LocalDate;

@UiController("RequestLoan")
@UiDescriptor("request-loan.xml")
public class RequestLoan extends Screen {
    @Autowired
    private DataManager dataManager;
    @Autowired
    private EntityComboBox<Client> clientField;
    @Autowired
    private TextField amountField;
    @Autowired
    private CollectionLoader<Loan> loansDl;

    @Subscribe("windowClose")
    public void onWindowClose(Action.ActionPerformedEvent event) {
        closeWithDefaultAction();
    }

    @Subscribe("windowCommitAndClose")
    public void onWindowCommitAndClose(Action.ActionPerformedEvent event) {
        Loan newLoan = dataManager.create(Loan.class);
        newLoan.setAmount(new BigDecimal(amountField.getRawValue()));
        newLoan.setClient(clientField.getValue());
        newLoan.setRequestDate(LocalDate.now());
        newLoan.setStatus(LoanStatus.REQUESTED);

        dataManager.save(newLoan);

        closeWithDefaultAction();
    }
}
