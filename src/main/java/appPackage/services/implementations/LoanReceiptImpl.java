package appPackage.services.implementations;

import appPackage.data.Computer;
import appPackage.data.LoanReceipt;
import appPackage.data.Student;
import appPackage.repositories.LoanReceiptRepository;
import appPackage.repositories.ComputerRepository;
import appPackage.repositories.ComputerRepositoryImpl;
import appPackage.repositories.StudentRepository;
import appPackage.services.interfaces.LoanReceiptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class LoanReceiptImpl implements LoanReceiptService {

    private LoanReceiptRepository loanReceiptRepository;
    private ComputerRepository computerRepository;
    private StudentRepository studentRepository;



    public LoanReceiptImpl() {
    }

    @Autowired
    public LoanReceiptImpl(LoanReceiptRepository loanReceiptRepository) {
        this.loanReceiptRepository = loanReceiptRepository;
    }


    @Override
    public List<LoanReceipt> getAllReceipts() {
        return loanReceiptRepository.findAll();
    }

    @Override
    public List<LoanReceipt> getAllOpenReceipts() {

        return loanReceiptRepository.findByEndDateIsNull();

    }

    //TODO: Make this method return every receipt that has no end Date, that has been open for more than 9 months
    //TODO: Example1: A computer has been lend out the 28/10/2020 and not yet returned. It's been over 9 months. This needs to be on the list
    //TODO: Example2: A computer has been loaned on 11/07/2022 and not yet returned. It's only been 2 months, this should not be on the list
    @Override


        public List<LoanReceipt> getAllOpenReceiptsLongerThan9Months() {
            List<LoanReceipt> loanList = new ArrayList<>();
            LocalDate localDate = LocalDate.now();
            List<LoanReceipt> list = loanReceiptRepository.findAll();
            for(int i = 0; i<list.size(); i++){
                Period age = Period.between(list.get(i).getStartDate(),localDate);
                int months = age.getMonths();
                if(months>9){
                    loanList.add(list.get(i));
                }
            }
            return loanList;
    }

    //TODO: make a Method that lends a computer to a student. Make sure that the student is not blacklisted
    //TODO: , that the student doesn't already own a computer and that the PC is not already in use.
    //TODO: If it was succesful, the method returns true. When lending, startDate is today
    @Override
    public boolean loanComputerToStudent(Computer computer, Student student) {
        computer = computerRepository.getOne(computer.getSerialNumber());
        student = studentRepository.getOne(student.getUserName());
        if((!student.isOnBlackList()) && checkIfStudentCurrentlyOwnsPC(student)==null && isPcInUse(computer) ) {
        LoanReceipt loanReceipt = new LoanReceipt(LocalDate.now(), student, computer);
        loanReceiptRepository.save(loanReceipt);
        System.out.println(loanReceipt.getStartDate()+""+loanReceipt.getLoanedTo()+""+loanReceipt.getLoanedComputer());
        return true;
        }
        return false;
    }


    @Override
    public boolean isPcInUse(Computer computer) {
        LoanReceipt foundReceipt = loanReceiptRepository.findByComputerAndEndDateIsNull(computer);
        if (foundReceipt!=null){
            return true;
        }
        return false;
    }

    @Override
    public Computer checkIfStudentCurrentlyOwnsPC(Student student) {
        LoanReceipt foundReceipt = loanReceiptRepository.findByStudentAndEndDateIsNull(student);
        if (foundReceipt != null){
            return foundReceipt.getLoanedComputer();
        }
        return null;
    }
}
