package com.company.homeworkloans.screen.loan;

import com.company.homeworkloans.entity.Client;
import com.company.homeworkloans.entity.LoanStatus;
import io.jmix.core.DataManager;
import io.jmix.ui.Notifications;
import io.jmix.ui.UiComponents;
import io.jmix.ui.action.Action;
import io.jmix.ui.component.*;
import io.jmix.ui.model.CollectionContainer;
import io.jmix.ui.model.CollectionLoader;
import io.jmix.ui.model.DataContext;
import io.jmix.ui.screen.*;
import com.company.homeworkloans.entity.Loan;
import io.jmix.ui.screen.LookupComponent;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.time.Period;

@UiController("LoanApproval.browse")
@UiDescriptor("loan-approval-browse.xml")
@LookupComponent("loansTable")
public class LoanApprovalBrowse extends StandardLookup<Loan> {
    @Autowired
    private CollectionContainer<Loan> loansDc;
    @Autowired
    private CollectionLoader<Loan> loansDl;
    @Autowired
    private DataManager dataManager;
    @Autowired
    private Notifications notifications;

    @Subscribe("approveLoan")
    public void onApproveLoan(Action.ActionPerformedEvent event) {
        Loan currentLoan = loansDc.getItem();
        currentLoan.setStatus(LoanStatus.APPROVED);
        dataManager.remove(currentLoan);
        notifications.create()
                .withCaption("Approved")
                .withType(Notifications.NotificationType.TRAY)
                .show();

        loansDl.load();
    }

    @Subscribe("rejectLoan")
    public void onRejectLoan(Action.ActionPerformedEvent event) {
        Loan currentLoan = loansDc.getItem();
        currentLoan.setStatus(LoanStatus.REJECTED);
        dataManager.remove(currentLoan);
        notifications.create()
                .withCaption("Rejected")
                .withType(Notifications.NotificationType.TRAY)
                .show();

        loansDl.load();
    }

    @Install(to = "loansTable.age", subject = "columnGenerator")
    private Component loansTableClientBirthDateColumnGenerator(Loan loan) {
        LocalDate birthDate = loan.getClient().getBirthDate();
        int year = Period.between(birthDate, LocalDate.now()).getYears();
        return new Table.PlainTextCell(Integer.toString(year));
    }

}

//    @Install(to = "loansTable.clientFullName", subject = "columnGenerator")
//    private Component loansTableClientFullNameColumnGenerator(Loan loan) {
//        Table.Column fullName = uiComponents.create(String.valueOf(Table.Column.class));
//        fullName.setCaption(loan.getClient().getFirstName() + " " + loan.getClient().getLastName());
//        return (Component) fullName;
//    }


//    @Install(to = "loansTable.clientFullName", subject = "columnGenerator")
//    private String loansTableClientFullNameColumnGenerator(DataGrid.ColumnGeneratorEvent<Client> columnGeneratorEvent) {
//        return columnGeneratorEvent.getItem().getFirstName() + " " + columnGeneratorEvent.getItem().getLastName();
//        DataGrid.Column fullName = uiComponents.create(String.valueOf(DataGrid.Column.class));
//        fullName.setCaption(loan.getClient().getFirstName() + " " + loan.getClient().getLastName());
//        return fullName;
//        return loan.getClient().getFirstName() + " " loan.getClient().getLastName();

//    @Install(to = "loansTable.[client.age]", subject = "columnGenerator")
//    private Integer loansTableClientAgeColumnGenerator(Loan loan) {
//        return LocalDate.now().getYear() - (loansDc.getItem().getClient().getBirthDate().getYear());
//    }

//    @Install(to = "loansTable.clientAge", subject = "columnGenerator")
//    private Integer loansTableClientAgeColumnGenerator1(DataGrid.ColumnGeneratorEvent<Client> columnGeneratorEvent) {
//        return LocalDate.now().getYear() - columnGeneratorEvent.getItem().getBirthDate().getYear();
////        return LocalDate.now().getYear() - (clientsDc.getItem().getBirthDate().getYear());
//    }
//
//    @Install(to = "loansTable.clientFullName", subject = "columnGenerator")
//    private String loansTableClientFullNameColumnGenerator(DataGrid.ColumnGeneratorEvent<Client> columnGeneratorEvent) {
//        Client client = columnGeneratorEvent.getItem();
//        return client.getFirstName() + " " + client.getLastName();
//    }
