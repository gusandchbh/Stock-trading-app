import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

class Transaction {
    private final String transactionID;
    private final LocalDate date;
    private final BigDecimal amount;
    private final Type type;

    public Transaction(BigDecimal amount, Type type) {
        this.transactionID = UUID.randomUUID().toString();
        this.date = LocalDate.now();
        this.amount = amount;
        this.type = type;
    }

        public String getTransactionID() {
            return transactionID;
        }

        public LocalDate getDate() {
            return date;
        }

        public BigDecimal getAmount() {
        return amount;
    }

        public Type getType() {
        return type;
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
        Transaction transaction1 = new Transaction(BigDecimal.valueOf(100.00), Transaction.Type.DEPOSIT);
        System.out.println(transaction1);
        System.out.println(transaction1.getAmount());
        Transaction transaction2 = new Transaction(BigDecimal.valueOf(100.00), Type.TRANSFER);
        System.out.println(transaction2);
    }
}
