import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

class Transaction implements Comparable<Transaction> {
    private final String transactionID; // To make each transaction unique
    private final LocalDate date; // Date of the transaction
    private final BigDecimal amount; // Amount of the transaction
    private final Type type; // Type of the transaction
    //private final String description; // Description of the transaction

    public Transaction(BigDecimal amount, Type type) {
        this.transactionID = UUID.randomUUID().toString(); // Generate a unique ID for each transaction
        this.date = LocalDate.now(); // Set the date of the transaction to the current date
        this.amount = amount; // Set the amount of the transaction
        this.type = type; // Set the type of the transaction
    }

        public Type getType() {
        return type;
        }

        public String getTypeString() {
        return type.toString();
        }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Transaction that = (Transaction) o;
        return transactionID.equals(that.transactionID) && date.equals(that.date) && amount.equals(that.amount) && type == that.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(transactionID, date, amount, type);
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "transactionID='" + transactionID + '\'' +
                ", date=" + date +
                ", amount=" + amount.setScale(2) +
                ", type=" + type +
                '}';
    }

    @Override
    public int compareTo(Transaction transaction) {
        return transaction.amount.compareTo(this.amount);
    }


    public static final Comparator<Transaction> orderByDate = Transaction::compareTo;

    public String getTransactionID() {
        return transactionID;
    }

    public LocalDate getDate() {
        return date;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    enum Type {
        DEPOSIT(1), WITHDRAWAL(2), TRANSFER(3);
        private final int code;
        Type(int code) {
            this.code = code;
        }
        public int getCode() {
            return code;
        }
    }

    public static void main(String[] args){
        ArrayList<Transaction> transactionList = new ArrayList<>();
        transactionList.add(new Transaction(new BigDecimal(100), Type.DEPOSIT));
        transactionList.add(new Transaction(new BigDecimal(200), Type.WITHDRAWAL));
        transactionList.add(new Transaction(new BigDecimal(300), Type.TRANSFER));
        transactionList.add(new Transaction(new BigDecimal(400), Type.DEPOSIT));
        transactionList.add(new Transaction(new BigDecimal(500), Type.WITHDRAWAL));
        transactionList.add(new Transaction(new BigDecimal(600), Type.TRANSFER));
        transactionList.sort((Transaction a, Transaction b) -> b.amount.compareTo(a.amount));
        System.out.println(transactionList);
    }

}
