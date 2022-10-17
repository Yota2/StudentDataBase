package appPackage.repositories;

import appPackage.data.Computer;
import appPackage.data.LoanReceipt;
import appPackage.data.Student;

import java.util.List;


public interface LoanReceiptRepository  {

    LoanReceipt findByComputerAndEndDateIsNull(Computer computer);
    LoanReceipt findByStudentAndEndDateIsNull(Student student);
    List<LoanReceipt> findByEndDateIsNull();
    //LoanReceipt findByStudentAndEndDateIsGraterThan9Month(Student student);
    List<LoanReceipt> findAll();
    LoanReceipt getOne(Long id); 
    LoanReceipt save (LoanReceipt loanReceipt);

}
