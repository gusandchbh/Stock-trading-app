import java.time.LocalDate;
import java.util.Objects;

class Transaction { // Researcha data class
    private final String transactionID;
    private final LocalDate date;
    private final double amount;
    private final String description;

    public Transaction(String transactionID, double amount, String description) {
        this.transactionID = transactionID;
        this.date = LocalDate.now();
        this.amount = amount;
        this.description = description;
    }

        public String getTransactionID() {
            return transactionID;
        }

        public LocalDate getDate() {
            return date;
        }

        public double getAmount() {
            return amount;
        }

        public String getDescription() {
            return description;
        }

        @Override
        public String toString() {
            return "Transaction{" +
                    "transactionID='" + transactionID + '\'' +
                    ", date=" + date +
                    ", amount=" + amount +
                    ", description='" + description + '\'' +
                    '}';
        }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Transaction that = (Transaction) o;
        return Double.compare(that.amount, amount) == 0 && transactionID.equals(that.transactionID) && date.equals(that.date) && description.equals(that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(transactionID, date, amount, description);
    }
}
